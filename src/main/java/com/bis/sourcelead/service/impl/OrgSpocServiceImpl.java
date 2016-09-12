package com.bis.sourcelead.service.impl;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
/**
 * Handles OrgSpoce activities
 * @author root
 *
 */
@Service
public class OrgSpocServiceImpl implements OrgSpocService{
	
	private static final Logger logger = LoggerFactory.getLogger(OrgSpocServiceImpl.class);
	
	@Autowired
	OrgSpocDao orgSpocDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	
	
	
	
	/**
	 * used to add spoc for vendor and client
	 * @param OrgSpoc
	 */
	@Override
	public OrgSpoc addOrgSpoc(OrgSpoc orgSpoc) throws Exception {
		return orgSpocDao.addOrgSpoc(orgSpoc);
	}

	/**
	 * Add Vendor contact
	 */
	@Override
	public void addVendorContacts(JSONObject jsonObj, User user, OrgLocations orgLocations, String orgRelationId, String clientLocationId) throws Exception {
		
		
		OrgRelations orgRelation = null;
		OrgLocations respondingOrgLocation = null;
		if(orgRelationId != null && !"".equals(orgRelationId) && !"''".equals(orgRelationId)){
			 orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(orgRelationId));
			 respondingOrgLocation = orgLocationService.getOrgLocations(Long.parseLong(clientLocationId));
		}else{
			String OrRelationId = jsonObj.getString("vendorName");
			String [] realtionId = OrRelationId.split(",");
			orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(realtionId[0]));
			respondingOrgLocation = orgLocationService.getOrgLocations(Long.parseLong(realtionId[1]));
		}
		
		User spocUser = userService.storeUser(jsonObj,user,respondingOrgLocation,"CREATEUSER","",new Org());
		
		if(jsonObj.has("owner")){
			String ownersId = jsonObj.getString("owner");
			ownersId = ownersId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] owners = ownersId.split(",");
			for(int i=0 ;i<owners.length;i++ ){
				OrgSpoc orgSpoc = new OrgSpoc();
				orgSpoc.setOrgRelations(orgRelation);
				orgSpoc.setSpocUser(spocUser);
				if(orgRelation.getResponderLocationId().getId() == orgLocations.getId()){
					orgSpoc.setResponderLocationId(orgRelation.getLocationId());
				}
				if(orgRelation.getLocationId().getId() == orgLocations.getId()){
					orgSpoc.setResponderLocationId(orgRelation.getResponderLocationId());
				}
					
				/*if(jsonObj.has("vendorContact") && jsonObj.getString("vendorContact").equals("vendorContact")){
					orgSpoc.setResponderLocationId(orgRelation.getResponderLocationId());
				}else{
					orgSpoc.setResponderLocationId(orgRelation.getLocationId());	
				}*/
				
				orgSpoc.setInitiatorLocationId(orgLocations);
				User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				orgSpoc.setOwnerUser(ownerUser);
			
				if(jsonObj.has("isMailSent")){
					orgSpoc.setInvitationStatus(Constants.MAIL_SENT);
				}else{
					orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
				}
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				orgSpoc.setSpocStatus(jsonObj.getString("spocStatus"));
				orgSpoc.setCreatedDate(CommonUtil.currentDate());
				orgSpoc.setCreatedBy(user.getUserCode());
				orgSpoc.setUpdatedDate(CommonUtil.currentDate());
				orgSpoc.setUpdatedBy(user.getUserCode());
				orgSpocDao.addOrgSpoc(orgSpoc);
				logger.debug("Vendor Contac is added successfully");
				} 
		}
	}
	@Override
	public List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId,Long orgLocationId) throws Exception {
		List<Long> userIds = userService.getAllUserIdsByReportingManagerId(userId);
		
		return orgSpocDao.getAllSpocsByOrgRelationId(orgRelationId, userId, userIds,orgLocationId);
	}
	
	
	@Override
	public OrgSpoc getOrgSpocById(Long Id) throws Exception {
		logger.debug("In OrgSpocService class...");
		return orgSpocDao.getOrgSpocById(Id);
	}

	@Override
	public OrgSpoc getSpocAndOwnerDetailsById(Long spocId, Long ownerId) {
		return orgSpocDao.getSpocAndOwnerDetailsById(spocId,ownerId);
	}
	
	@Override
	public OrgSpoc validateSpacEmailIdByOwnerUserIdAndOrgRelationId(String email, Long orgRelationId, Long userId) throws Exception {
		logger.debug("In OrgSpocService class...");
		return orgSpocDao.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(email, orgRelationId, userId);
	}

	@Override
	public List<Long> getOwnerIdbySpocId(Long spocUserId,Long ownerId) {
		return orgSpocDao.getOwnerIdbySpocId(spocUserId,ownerId);	
		}
	
	@Override
	public OrgSpoc getSpocUserByUserId(Long spocUserId) throws Exception {
		logger.debug("In OrgSpocService class...");
		return orgSpocDao.getSpocUserByUserId(spocUserId);
	}
	
}
