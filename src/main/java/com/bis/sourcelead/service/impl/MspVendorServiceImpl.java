package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.MspVendorDao;
import com.bis.sourcelead.dto.MspVendorDTO;
import com.bis.sourcelead.elk.index.ClientVendorIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.MspVendor;
import com.bis.sourcelead.entity.MspVendorHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.MspVendorService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class MspVendorServiceImpl implements MspVendorService{
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	MspVendorDao mspVendorDao;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	private ClientVendorIndexHelper clientVendorIndexHelper;

	@Autowired
	private SourceleadELKBaseIndexer<ClientVendorIndex> sourceleadELKBaseIndexer;  

	@Override
	public MspVendor saveMspVendor(MspVendor mspVendor) throws Exception {
		return mspVendorDao.saveMspVendor(mspVendor);
	}


	/**
	 * @author Laxman
	 * This method is saveMspVendorDTO
	 * @param mspVendorDTO
	 * @return Success
	 */
	@SuppressWarnings("unused")
	@Override
	public String saveMspVendorDTO(MspVendorDTO mspVendorDTO) throws Exception {
		
		MspVendor mspVendor =  mspVendorDao.getMspVendorListByOrgLocationIdAndMspRelationId(Long.valueOf(mspVendorDTO.getLoginOrgLocationId()), Long.valueOf(mspVendorDTO.getMspOrgRelationId()));
		OrgLocations loginOrgLocation = orgLocationService.getOrgLocationById(mspVendorDTO.getLoginOrgLocationId());
		if(mspVendor == null) {
			mspVendor = new MspVendor();
			
			mspVendor.setOrgLocationId(loginOrgLocation);
			
			OrgLocations mspVendoroOgLocation = new OrgLocations();
			mspVendoroOgLocation.setId(mspVendorDTO.getMspVendorOrgLocationId());
			mspVendor.setMspOrgLocationId(mspVendoroOgLocation);
			
			OrgRelations mspOrgRelation = new OrgRelations();
			mspOrgRelation.setId(mspVendorDTO.getMspOrgRelationId());
			mspVendor.setMspRelationId(mspOrgRelation);
			
			mspVendor.setStatus(Constants.RECORD_ACTIVE);
			mspVendor.setCreatedDate(new Date());
		}
		
		if(mspVendorDTO.getMspVendorIds() != null && !mspVendorDTO.getMspVendorIds().equalsIgnoreCase("null")) {
			mspVendor.setOrgRelationIds(mspVendorDTO.getMspVendorIds());
			mspVendor.setStatus(Constants.RECORD_ACTIVE);
		}else {
			mspVendor.setOrgRelationIds(null);
			mspVendor.setStatus(Constants.RECORD_INACTIVE);
		}
		
		if(mspVendorDTO.getMspVendorIds() != null && !mspVendorDTO.getMspVendorIds().equalsIgnoreCase("null")) {
			
		String[] taggedVendors = mspVendorDTO.getMspVendorIds().split(",");
		Set<Long> vendorOrgRelationIds = new HashSet<>();
		
		for(String tagVendor : taggedVendors) {
			OrgRelations vendorOrgRelation = orgRelationsService.getOrgRelationById(Long.valueOf(tagVendor));
			Long vendorLocationId = null;
			if(vendorOrgRelation.getLocationId().getId() == mspVendorDTO.getLoginOrgLocationId()) {
				vendorLocationId = vendorOrgRelation.getResponderLocationId().getId();
			}else {
				vendorLocationId = vendorOrgRelation.getLocationId().getId();
			}
			
			OrgLocations orgLocations = orgLocationService.getOrgLocationById(mspVendorDTO.getMspVendorOrgLocationId());
			OrgRelations orgRelations = orgRelationsService.getOrgRelationByResponderLocationIdAndLocationId(vendorLocationId,mspVendorDTO.getMspVendorOrgLocationId());
			if(orgRelations == null) {
				OrgLocations vendorOrgLocation = orgLocationService.getOrgLocationById(vendorLocationId);
				orgRelations = saveOrgRelations(orgLocations,vendorOrgLocation,orgLocations.getUser());
 				orgRelations = orgRelationsService.addOrgRelations(orgRelations);
 				vendorOrgRelationIds.add(orgRelations.getId());
 				
 				//Save ELK Data
 				saveOrUpdateClientVendorElkDocument(orgLocations,orgRelations,mspVendorDTO.getLoginUser(),loginOrgLocation);
 				 
			}
			
			vendorOrgRelationIds.add(orgRelations.getId());
		}
			mspVendor.setMspOrgRelationIds(StringUtils.join(vendorOrgRelationIds, ','));

		}
		mspVendorDao.saveMspVendor(mspVendor);
		
		//Save MSP Vendor History
		saveMspVendorHistory(mspVendorDTO,loginOrgLocation);
		
		return "success";
	}

	@SuppressWarnings("unlikely-arg-type")
	private void saveMspVendorHistory(MspVendorDTO mspVendorDTO, OrgLocations loginOrgLocation) throws Exception {
		
		List<Long> freshMspVendorIds = new ArrayList<>();
		if(mspVendorDTO.getMspVendorIds() != null && !mspVendorDTO.getMspVendorIds().equalsIgnoreCase("null")) {
			String[] mspVendorIds = mspVendorDTO.getMspVendorIds().split(",");
			for(String mspVendorId : mspVendorIds) {
				freshMspVendorIds.add(Long.valueOf(mspVendorId));
				MspVendorHistory  mspVendorHistory = mspVendorDao.getMspVendorHistoryDetailsByMSPRelationIdAndOrgRelationId(Long.valueOf(mspVendorDTO.getMspOrgRelationId()), Long.valueOf(mspVendorId));
				if(mspVendorHistory == null) {
				MspVendorHistory mvh = new MspVendorHistory();
				
				mvh.setOrgLocationId(loginOrgLocation);
				
				OrgLocations mspVendoroOgLocation = new OrgLocations();
				mspVendoroOgLocation.setId(mspVendorDTO.getMspVendorOrgLocationId());
				mvh.setMspOrgLocationId(mspVendoroOgLocation);
				
				OrgRelations mspOrgRelation = new OrgRelations();
				mspOrgRelation.setId(mspVendorDTO.getMspOrgRelationId());
				mvh.setMspRelationId(mspOrgRelation);
				
				
				OrgRelations mspVendId = new OrgRelations();
				mspVendId.setId(Long.valueOf(mspVendorId));
				mvh.setOrgRelationId(mspVendId);
				
				
				OrgRelations vendorOrgRelation = orgRelationsService.getOrgRelationById(Long.valueOf(mspVendorId));
				Long vendorLocationId = null;
				if(vendorOrgRelation.getLocationId().getId() == mspVendorDTO.getLoginOrgLocationId()) {
					vendorLocationId = vendorOrgRelation.getResponderLocationId().getId();
				}else {
					vendorLocationId = vendorOrgRelation.getLocationId().getId();
				}
				
				OrgLocations orgLocations = orgLocationService.getOrgLocationById(mspVendorDTO.getMspVendorOrgLocationId());
				OrgRelations orgRelations = orgRelationsService.getOrgRelationByResponderLocationIdAndLocationId(vendorLocationId,mspVendorDTO.getMspVendorOrgLocationId());
				if(orgRelations == null) {
					OrgLocations vendorOrgLocation = orgLocationService.getOrgLocationById(vendorLocationId);
					orgRelations = saveOrgRelations(orgLocations,vendorOrgLocation,orgLocations.getUser());
	 				orgRelations = orgRelationsService.addOrgRelations(orgRelations);
	 				mvh.setMspOrgRelationId(orgRelations);				
				}
				mvh.setMspOrgRelationId(orgRelations);				
				mvh.setStatus(Constants.RECORD_ACTIVE);
				mvh.setCreatedDate(new Date());
				mvh.setCreatedBy(mspVendorDTO.getLoginUser());
				mspVendorDao.saveMspVendorHistory(mvh);
				}
			}
		}
		List<Long> dbMspVendorIds = new ArrayList<>();
		List<MspVendorHistory> mpsVendorHisList = mspVendorDao.mspVendorHistoryListByMspRelationIdMspOrgLocationId(Long.valueOf(mspVendorDTO.getMspOrgRelationId()), Long.valueOf(mspVendorDTO.getMspVendorOrgLocationId()));
		for(MspVendorHistory mvh:mpsVendorHisList) {
			dbMspVendorIds.add(mvh.getOrgRelationId().getId());
		}
		dbMspVendorIds.removeAll(freshMspVendorIds);
		for(Long dbId:dbMspVendorIds) {
			MspVendorHistory  removeMspHistory = mspVendorDao.getMspVendorHistoryDetailsByMSPRelationIdAndOrgRelationId(Long.valueOf(mspVendorDTO.getMspOrgRelationId()), dbId);
			removeMspHistory.setStatus(Constants.RECORD_INACTIVE);
			removeMspHistory.setUpdatedDate(new Date());
			removeMspHistory.setUpdatedBy(mspVendorDTO.getLoginUser());
			mspVendorDao.saveMspVendorHistory(removeMspHistory);
		}		
	}


	/**
	 * @author Laxman
	 * This method is saveOrgReleation
	 * @param orglocations
	 * @param vendorOrgLocation
	 * @param user
	 * @return orgRelations
	 */
	public OrgRelations saveOrgRelations(OrgLocations orglocations,OrgLocations vendorOrgLocation,User user){
		
		OrgRelations orgRelations = new OrgRelations();
			orgRelations.setLocationId(orglocations);
			orgRelations.setResponderLocationId(vendorOrgLocation);
			orgRelations.setInitiatorType("Client");
			orgRelations.setResponderType("Vendor");
			orgRelations.setInitiatorLevel("Tier-5");
			orgRelations.setGenerateBill("Bulk");
			orgRelations.setResponderLevel("Tier-5");
			orgRelations.setVendorRelationStatus("Sent");
			orgRelations.setStatus("ACTIVE");
			orgRelations.setCreatedBy(user.getUserCode());
			orgRelations.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			orgRelations.setCreatedDate(new Date());
			orgRelations.setUpdatedBy(user.getUserCode());
			orgRelations.setUpdatedDate(new Date());
			
			Set<OrgSpoc> orgSpocSet = new HashSet<>();
			OrgSpoc orgSpoc = new OrgSpoc();
			orgSpoc.setOrgRelations(orgRelations);
			orgSpoc.setClientUser(user);
			orgSpoc.setVendorUser(vendorOrgLocation.getUser());
			orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
			orgSpoc.setClientLocationId(orglocations);
			orgSpoc.setVendorLocationId(vendorOrgLocation);
			orgSpoc.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
			orgSpoc.setSpocStatus("Active");
			orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
			orgSpoc.setOwnerUser(user);
			orgSpoc.setCreatedDate(new Date());
			orgSpoc.setCreatedBy(user.getUserCode());
			orgSpoc.setUpdatedDate(new Date());
			orgSpoc.setUpdatedBy(user.getUserCode());
			orgSpocSet.add(orgSpoc);
			orgRelations.setOrgSpocList(orgSpocSet);
			return orgRelations;
	}


	private void saveOrUpdateClientVendorElkDocument(OrgLocations clientOrgLocations,OrgRelations orgRelation,User user,
			OrgLocations loginOrgLocation) throws Exception {
		// Elastic Search Document Saving
		ClientVendorIndex clientVendorIndex = clientVendorIndexHelper.getClientVendorIndexFromCandidate(null,clientOrgLocations, "Vendor", null, orgRelation, null, user, loginOrgLocation);
		sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorIndex, Constants.ELK_CLIENTVENDOR_INDEX, Constants.ELK_CLIENTVENDOR_TYPE, orgRelation.getId().toString());
	}
	
	
	@Override
	public MspVendor getMspVendorListByOrgLocationIdAndMspRelationId(Long orgLcoationId, Long orgRelationId) throws Exception {
		return mspVendorDao.getMspVendorListByOrgLocationIdAndMspRelationId(orgLcoationId, orgRelationId);
	}



	@Override
	public List<MspVendorDTO> getMspClientlistByOrgLocationId(Long mspOrgLocationId) throws Exception {
		return mspVendorDao.getMspClientlistByOrgLocationId(mspOrgLocationId);
	}



	@Override
	public List<OrgRelations> selectedMSPVendorListByOrgLocationId(MspVendorDTO mspVendorDTO) throws Exception {
		return mspVendorDao.selectedMSPVendorListByOrgLocationId(mspVendorDTO);
	}



	@Override
	public MspVendorHistory saveMspVendorHistory(MspVendorHistory mspVendorHistory) throws Exception {
		return mspVendorDao.saveMspVendorHistory(mspVendorHistory);
	}


	@Override
	public MspVendorHistory getMspVendorHistoryDetailsByMSPRelationIdAndOrgRelationId(Long mpsRealtionId, Long orgRelationId) throws Exception {
		return mspVendorDao.getMspVendorHistoryDetailsByMSPRelationIdAndOrgRelationId(mpsRealtionId, orgRelationId);
	}


	@Override
	public List<MspVendorHistory> mspVendorHistoryListByMspRelationIdMspOrgLocationId(Long mspRelationId, Long mspOrgLocationId) throws Exception {
		return mspVendorDao.mspVendorHistoryListByMspRelationIdMspOrgLocationId(mspRelationId, mspOrgLocationId);
	}
	
}
