package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.OrgSpocDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgBankDetails;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.OrgBankDetailsService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class OrgRelationsServiceImpl implements OrgRelationsService {

	@Autowired
	OrgRelationsDao orgRelationsDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	private OrgSpocService orgSpocService;
	
	@Autowired
	VendorOnboardService vendorOnboardService;
	
	@Autowired
	OrgBankDetailsService orgBankDetailsService;

	@Override
	public OrgRelations addOrgRelations(OrgRelations orgRelations) throws Exception {
		orgRelations.setUpdatedDate(commonUtil.currentDate());
		return orgRelationsDao.addOrgRelations(orgRelations);
	}

	@Override
	public OrgRelations getOrgRelationIdByOrgLocationIdAndClientLocationId(Long orgLocationId, Long clientOrgLocationId)
			throws Exception {
		return orgRelationsDao.getOrgRelationIdByOrgLocationIdAndClientLocationId(orgLocationId, clientOrgLocationId);
	}

	
	
	@Override
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception {
		List<OrgRelations> emplist= orgRelationsDao.getAllClientsByOrgLocationId(orgLocationId);
		return emplist;
	}
	
	@Override
	public OrgRelations getClientsByOrgLocationIdAndName(Long orgLocationId, String name) throws Exception {
		OrgRelations client= orgRelationsDao.getClientsByOrgLocationIdAndName(orgLocationId, name);
		return client;
	}

	@Override
	public OrgRelations getClientByOrgLocationId(Long orgLocationId) throws Exception {
		
		OrgRelations orgRelation =  orgRelationsDao.getClientByOrgLocationId(orgLocationId);
		orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
		orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
		orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
		orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
		orgRelation.setState(orgRelation.getResponderLocationId().getState());
		orgRelation.setPhone(orgRelation.getResponderLocationId().getPhone());
		orgRelation.setEmail(orgRelation.getResponderLocationId().getEmail());
		return orgRelation;
	}

	@Override
	public Long getNumberOfProjectsPerClient(Long orgLocationId) throws Exception {
		return orgRelationsDao.getNumberOfProjectsPerClient(orgLocationId);
	}

	@Override
	public Long getProjectsCountByOrgRelationIdAndOrgLocationId(Long orgRelationId, Long orgLocationId)
			throws Exception {
		
		return orgRelationsDao.getProjectsCountByOrgRelationIdAndOrgLocationId(orgRelationId, orgLocationId);
	}
	
	/**
	 * @author VenkatKesav
	 * @since  aug 19 2016
	 * @version 0.0.1
	 * This method is for org Relatons saving..
	 * @param jsonObj, clientOrgLocation, orgLocations, loginUserCode
	 * @return Set<OrgRelations> 
	 * @throws NullPointer
	 */
	@Override
	public Set<OrgRelations> orgRelationsListByJson(ClientDTO clientDTO, OrgLocations clientOrgLocation, OrgLocations orgLocations, String loginUserCode,String responderType) throws Exception {
		
		HashSet<OrgRelations> orgRelationsList = new HashSet<OrgRelations>();
		
		OrgRelations orgRelations = new OrgRelations();
		orgRelations.setResponderLocationId(clientOrgLocation);
		orgRelations.setLocationId(orgLocations);
		orgRelations.setStatus("ACTIVE");
		orgRelations.setCreatedBy(loginUserCode);
		orgRelations.setCreatedDate(commonUtil.currentDate());
		orgRelations.setUpdatedDate(commonUtil.currentDate());
		
		if (StringUtils.isNotBlank(clientDTO.getPaymentTerms())) {
			orgRelations.setPaymentTerm(clientDTO.getPaymentTerms());
		}
		
		if(null != responderType  && !responderType.isEmpty()){
			if("Client".equalsIgnoreCase(responderType)){
				orgRelations.setInitiatorType("Vendor"); 
				orgRelations.setResponderType("Client");
				if(StringUtils.isNotBlank(clientDTO.getGenerateInvoice())){
				orgRelations.setGenerateInvoice(clientDTO.getGenerateInvoice());
				orgRelations.setGenerateBill(clientDTO.getGenerateInvoice());
				}
			}else{
				orgRelations.setInitiatorType("Client"); 
				orgRelations.setResponderType("Vendor");
				orgRelations.setIsMSPVendor(clientDTO.getIsMSPVendor());
				if(StringUtils.isNotBlank(clientDTO.getGenerateBill())){
				orgRelations.setGenerateBill(clientDTO.getGenerateBill());
				orgRelations.setGenerateInvoice(clientDTO.getGenerateBill());
				}
				if (StringUtils.isNotBlank(clientDTO.getIsLawFirm()) && clientDTO.getIsLawFirm().equalsIgnoreCase("Y")) {
						orgRelations.setIsLawFirm(Constants.YES);
				} else {
					orgRelations.setIsLawFirm(Constants.NO);
				}
				if (StringUtils.isNotBlank(clientDTO.getIsAccountingFirm()) && clientDTO.getIsAccountingFirm().equalsIgnoreCase("Y")) {
						orgRelations.setIsAccountingFirm(Constants.YES);
				} else {
					orgRelations.setIsAccountingFirm(Constants.NO);
				}
				if(StringUtils.isNotBlank(clientDTO.getClientSaveFromType()) && "BulkVendorSaving".equalsIgnoreCase(clientDTO.getClientSaveFromType())){
					orgRelations.setVendorRelationStatus("Sent");
					orgRelations.setIsVendorDetailsFilled(Constants.YES);
					orgRelations.setIsVendorDocumentsFilled(Constants.YES);
					orgRelations.setIsVendorTasksFilled(Constants.YES);
					}else{
					orgRelations.setVendorRelationStatus("Draft");
					orgRelations.setIsVendorDetailsFilled(Constants.YES);
					orgRelations.setIsVendorDocumentsFilled(Constants.NO);
					orgRelations.setIsVendorTasksFilled(Constants.NO);
					}
				
				 if(clientDTO.getTemplateId() != null && clientDTO.getTemplateId().size() > 0){
				    	StringBuilder templateIds = new StringBuilder();
				    	for(String templateId : clientDTO.getTemplateId()){
				    		templateIds.append(templateId.trim()).append(",");
				    	}
				    	String templateIdsStr = templateIds.toString();
				    	
				    	orgRelations.setVendorOnboardTemplateId(templateIdsStr.substring(0, templateIdsStr.length()-1)); 
				    }else{
				    	orgRelations.setVendorOnboardTemplateId(null);
				    }
			}
		  }
		if(StringUtils.isNotBlank(clientDTO.getLevel())){
			orgRelations.setInitiatorLevel(clientDTO.getLevel());
			orgRelations.setResponderLevel("Tier-5");
		}else{
			orgRelations.setInitiatorLevel("Tier-5");
			orgRelations.setResponderLevel("Tier-5");
		}
		orgRelations.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
		orgRelationsList.add(orgRelations);
		return orgRelationsList;
	}

	@Override
	public List<OrgRelations> getAllVendorsListByOrgLocationId(Long orgLocationId) {
		return orgRelationsDao.getAllVendorsByOrgLocationId(orgLocationId);
	}

	@Override
	public List<OrgRelationDTO> getAllVendorsDTOListByOrgLocationId(Long orgLocationId) {
		List<OrgRelationDTO> orgRelationDTOList = new LinkedList<>();
		try {
			List<OrgRelations> vendorList = orgRelationsDao.getAllVendorsByOrgLocationId(orgLocationId);
			if (vendorList != null && !vendorList.isEmpty()) {
				for (OrgRelations orgRelations : vendorList) {
					OrgRelationDTO orgRelationDTO = new OrgRelationDTO();
					orgRelationDTO.setId(orgRelations.getId());
					if (orgRelations.getResponderLocationId().getId().longValue() == orgLocationId) {
						orgRelationDTO.setOrgName(orgRelations.getLocationId().getOrgLocationName());
					}
					if (orgRelations.getLocationId().getId().longValue() == orgLocationId) {
						orgRelationDTO.setOrgName(orgRelations.getResponderLocationId().getOrgLocationName());
					}
					orgRelationDTO.setOrgLocationId(orgRelations.getLocationId().getId());
					orgRelationDTOList.add(orgRelationDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orgRelationDTOList;
	}
	
	@Override
	public OrgRelations getClientAndVendorOrgDeatilsById(Long orgLocationId, Long orgRelationId) {
		
		return orgRelationsDao.getClientAndVendorOrgDeatilsById(orgLocationId,orgRelationId);
	}
	
	@Override
	public OrgRelations getClientAndVendorOrgDeatilsByResponderLocationdIdAndOrgRelationId(Long orgLocationId, Long orgRelationId, Long loginOrgLocationId) throws Exception {
		
		OrgRelations orgRelation =  orgRelationsDao.getClientAndVendorOrgDeatilsById(orgLocationId,orgRelationId);
		if(orgRelation != null){
			//if login user org id is equals to vendor location id then we get vendor list of ResponderLocationId Org details 
			 if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== loginOrgLocationId.longValue()){ 
					if(orgRelation.getResponderLocationId() !=  null){
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
						if(StringUtils.isNotBlank(orgRelation.getResponderLocationId().getWebsite())) {
							orgRelation.setWebSite(orgRelation.getResponderLocationId().getWebsite());
						}
						
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setLocation(orgRelation.getResponderLocationId().getLocation());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setZipCode(orgRelation.getResponderLocationId().getZipCode());
						orgRelation.setPhone(orgRelation.getResponderLocationId().getPhone());
						orgRelation.setEmail(orgRelation.getResponderLocationId().getEmail());
						orgRelation.setOrgType(orgRelation.getResponderLocationId().getOrg().getOrgType());
						orgRelation.setAddress((orgRelation.getResponderLocationId().getOrgLocationAddress()));
						orgRelation.setBillingAddress((orgRelation.getResponderLocationId().getBillingAddress()));
						orgRelation.setPaymentTerms((orgRelation.getPaymentTerm()));
					}
					orgRelation.setLevel(orgRelation.getInitiatorLevel());
			 }
			 //to fetch user details by usercode 
			User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
			 StringBuilder vendorName = new StringBuilder();
			orgRelation.setVendorCreateduserName(vendorName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
			
			//if login user org id is equals to vendor ResponderLocationId id then we get vendor list of location Org details 
			 if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue()== loginOrgLocationId.longValue()){ 
					if(orgRelation.getLocationId() != null){
						orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
						if(StringUtils.isNotBlank(orgRelation.getLocationId().getOrg().getWebSite())) {
							orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						}
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setLocation(orgRelation.getLocationId().getLocation());
						orgRelation.setPhone(orgRelation.getLocationId().getPhone());
						orgRelation.setEmail(orgRelation.getLocationId().getEmail());
						orgRelation.setZipCode(orgRelation.getLocationId().getZipCode());
						orgRelation.setOrgType(orgRelation.getLocationId().getOrg().getOrgType());
						orgRelation.setAddress((orgRelation.getResponderLocationId().getOrgLocationAddress()));
						orgRelation.setBillingAddress((orgRelation.getResponderLocationId().getBillingAddress()));
						orgRelation.setPaymentTerms((orgRelation.getPaymentTerm()));
					}
					orgRelation.setLevel(orgRelation.getResponderLevel());
			 } 
			 
			 
			 VendorOnboarding onboarding = vendorOnboardService.getVendorOnboardingByOrgRelationId(orgRelationId);
			 if(onboarding != null) {
				 orgRelation.setIsVendorOnboardSubmitted(onboarding.getOnboardSubmitted());
					 if(onboarding.getOnboardSubmitted().equalsIgnoreCase(Constants.YES)) {
						 OrgBankDetails bankDetails = orgBankDetailsService.getOrgBankDetailsById(orgRelation.getBankDetailsId().getId());
						 if(bankDetails != null) {
							 orgRelation.setAccountName(bankDetails.getAccountName());
							 orgRelation.setAccountNumber(bankDetails.getAccountNumber());
							 orgRelation.setBankName(bankDetails.getBankName());
							 orgRelation.setBranchNumber(bankDetails.getBranchNumber());
							 orgRelation.setBankIdentifierCode(bankDetails.getBankIdentifierCode());
							 orgRelation.setBankAddress(bankDetails.getBankAddress());
							 orgRelation.setPoEmail(bankDetails.getPoEmail());
							 orgRelation.setPaymentRemittanceEmail(bankDetails.getPaymentRemittanceEmail());
						 }
					 }
				 }
		}
		return orgRelation;
	}

	@Override
	public OrgRelations getOrRelationsById(Long orgRelationId) throws Exception {
		return orgRelationsDao.getOrRelationsById(orgRelationId);
	}

	@Override
	public List<OrgSpoc> getAllVendorsContactListByOrgLocationId(Long loginUserId,Long orgLocationId, String relationType) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		return orgRelationsDao.getAllVendorsContactListByOrgLocationId(loginUserId,userIds,orgLocationId, relationType);
	}

	@Override
	public OrgRelations getRespondingPartyDetailsById(Long id) throws Exception {
		return orgRelationsDao.getRespondingPartyDetailsById(id);
	}

	@Override
	public List<OrgRelations> getAllClientsFilterByOrgLocationId(Long orgLocationId,String status, Long loginUserId) throws Exception {
		
		List<OrgRelations> filterClientList= orgRelationsDao.getAllClientsFilterByOrgLocationId(orgLocationId, status);	
		if(filterClientList != null && !filterClientList.isEmpty()){
			for (OrgRelations orgRelation : filterClientList) {
				 //to fetch user details by usercode 
				User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
				 StringBuilder vendorCreatedName = new StringBuilder();
				if(user != null){
					orgRelation.setVendorCreateduserName(vendorCreatedName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(),loginUserId,orgLocationId,"Client");
				orgRelation.setNoOfContatcs(orgSpocs.size());
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue()== orgLocationId.longValue()){
					if(orgRelation.getResponderLocationId() != null){
						if(orgRelation.getResponderLocationId().getOrg().getWebSite() !=null){
							orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						}
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					}
					
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue()== orgLocationId.longValue()){
					if(orgRelation.getLocationId() != null){
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
					}
					
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
			}
		}
		
		return filterClientList;
	}

	@Override
	public List<OrgRelations> getAllVendorsFilterByOrgLocationId(Long orgLocationId, String status, String vendorsRelationId) throws Exception{
		return orgRelationsDao.getAllVendorsFilterByOrgLocationId(orgLocationId, status, vendorsRelationId);
	}

	@Override
	public List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationId(Long loginUserId,Long orgLocationId, String relationType,String status,String vendorIds, String invitationStatus) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		List<OrgSpoc> vandorsContactsList =  orgRelationsDao.getAllVendorsContactFilterListByOrgLocationId(loginUserId,userIds,orgLocationId, relationType,status,vendorIds,invitationStatus);
		if(vandorsContactsList != null && !vandorsContactsList.isEmpty()){
		   for (OrgSpoc orgSpoc : vandorsContactsList) {
			     
				 StringBuilder ownerNameSB = new StringBuilder();
				 
				 if("Vendor".equalsIgnoreCase(relationType)){
					 StringBuilder spocNameSB = new StringBuilder();
					 if(null != orgSpoc.getClientUser() ){
					   orgSpoc.setSpocEmail(orgSpoc.getClientUser().getEmail()); 
					 }
					 if(null != orgSpoc.getClientUser().getFirstName() && null != orgSpoc.getClientUser().getLastName()){
						spocNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString(); 
					 }else{
						spocNameSB.append(orgSpoc.getClientUser().getFirstName());
					 }
					 orgSpoc.setSpocName(spocNameSB.toString());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getClientUser().getMobilePhone());
					 orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName()).toString());
					
					 orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
					 orgSpoc.setUserId(orgSpoc.getClientUser().getId());
					
					 orgSpoc.setSpocLocationId(orgSpoc.getClientLocationId().getId());
					 orgSpoc.setOrgName(orgSpoc.getClientLocationId().getOrgLocationName());
				 }else{
					 StringBuilder spocNameSB = new StringBuilder();
					 orgSpoc.setSpocEmail(orgSpoc.getVendorUser().getEmail());
					 if(null != orgSpoc.getVendorUser().getFirstName() && null != orgSpoc.getVendorUser().getLastName()){
							spocNameSB.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName()).toString(); 
						 }else{
							spocNameSB.append(orgSpoc.getVendorUser().getFirstName());
						 }
					 orgSpoc.setSpocName(spocNameSB.toString());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getVendorUser().getMobilePhone());
					 orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString());
					 
					 orgSpoc.setTempOwnerId(orgSpoc.getClientUser().getId());
					 orgSpoc.setUserId(orgSpoc.getVendorUser().getId());
					
					 orgSpoc.setSpocLocationId(orgSpoc.getVendorLocationId().getId());
					 orgSpoc.setOrgName(orgSpoc.getVendorLocationId().getOrgLocationName()); 
				 }
			   
				if(null != orgSpoc.getClientUser() && null != orgSpoc.getOwnerUser() && orgSpoc.getOwnerUser().getId().longValue() == orgSpoc.getClientUser().getId().longValue()){
					 orgSpoc.setSpocUserId(orgSpoc.getVendorUser().getId());
					 orgSpoc.setOwnerUserId(orgSpoc.getClientUser().getId().toString());
				}
				else{
					orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
					orgSpoc.setOwnerUserId(orgSpoc.getVendorUser().getId().toString());
				}
				
				if(orgSpoc.getOrgRelations() != null && orgSpoc.getOrgRelations().getId() != null){
					orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
				}
			}
	   }
		
		return vandorsContactsList;
	}
	
	
	/**
	 * @author VenkatKesav
	 * @since  March 02 2017
	 * @version 0.0.1
	 * This method is for get All Vendors Contact By OrgLocationId And TabAccess
	 * @param loginUserId, orgLocationId, relationType, status, vendorIds, invitationStatus, vendorOrgLocationsIds
	 * @return Set<OrgRelations> 
	 * @throws NullPointer
	 */
	@Override
	public List<OrgSpoc> getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(Long loginUserId,Long orgLocationId, String relationType,String status,String vendorIds, String invitationStatus, List<Long> vendorOrgLocationsIds) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		//List<OrgSpoc> vandorsContactsList =  orgRelationsDao.getAllVendorsContactFilterListByOrgLocationId(loginUserId,userIds,orgLocationId, relationType,status,vendorIds,invitationStatus);
		
		List<OrgSpoc> vandorsContactsList =  orgRelationsDao.getAllVendorsContactFilterListByOrgLocationIdAndTabAccess(loginUserId,userIds,orgLocationId, relationType,status,vendorIds,invitationStatus, Long.valueOf(Constants.TAB_REQUIREMENT), vendorOrgLocationsIds);
		if(vandorsContactsList != null && !vandorsContactsList.isEmpty()){
		   for (OrgSpoc orgSpoc : vandorsContactsList) {
				 
				 if("Vendor".equalsIgnoreCase(relationType)){
					 orgSpoc.setSpocEmail(orgSpoc.getClientUser().getEmail());
					 orgSpoc.setSpocName(orgSpoc.getClientUser().getFullName());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getClientUser().getMobilePhone());
					 orgSpoc.setOwnerName(orgSpoc.getVendorUser().getFullName());
					
					 orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
					 orgSpoc.setUserId(orgSpoc.getClientUser().getId());
					
					 orgSpoc.setSpocLocationId(orgSpoc.getClientLocationId().getId());
					 orgSpoc.setOrgName(orgSpoc.getClientLocationId().getOrgLocationName());
				 }else{
					 orgSpoc.setSpocEmail(orgSpoc.getVendorUser().getEmail());
					 orgSpoc.setSpocName(orgSpoc.getVendorUser().getFullName());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getVendorUser().getMobilePhone());
					 orgSpoc.setOwnerName(orgSpoc.getClientUser().getFullName());
					 
					 orgSpoc.setTempOwnerId(orgSpoc.getClientUser().getId());
					 orgSpoc.setUserId(orgSpoc.getVendorUser().getId());
					
					 orgSpoc.setSpocLocationId(orgSpoc.getVendorLocationId().getId());
					 orgSpoc.setOrgName(orgSpoc.getVendorLocationId().getOrgLocationName()); 
				 }
			   
				if(null != orgSpoc.getClientUser() && null != orgSpoc.getOwnerUser() && orgSpoc.getOwnerUser().getId().longValue() == orgSpoc.getClientUser().getId().longValue()){
					 orgSpoc.setSpocUserId(orgSpoc.getVendorUser().getId());
					 orgSpoc.setOwnerUserId(orgSpoc.getClientUser().getId().toString());
				}
				else{
					orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
					orgSpoc.setOwnerUserId(orgSpoc.getVendorUser().getId().toString());
				}
				
				if(orgSpoc.getOrgRelations() != null && orgSpoc.getOrgRelations().getId() != null){
					orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
				}
			}
	   }
		
		return vandorsContactsList;
	}
	
	

	@Override
	public List<OrgRelations> getAllVendorsListByOrgName(String orgName,Long orgLocationId) {
		return orgRelationsDao.getAllVendorsListByOrgName(orgName,orgLocationId);
	}

	@Override
	public List<OrgRelations> getAllClientsByOrgNameOrgLocationId(String orgName, Long orgLocationId) throws Exception {
		return orgRelationsDao.getAllClientsByOrgNameOrgLocationId(orgName,orgLocationId);
	}

	@Override
	public List<OrgRelations> getAllClientsByOrgLocationId(Long orgLocationId, Long loginUserId) throws Exception {
		List<OrgRelations> orgRelationsList =  orgRelationsDao.getAllClientsByOrgLocationId(orgLocationId);
		List<OrgRelations> clientsList = new ArrayList<OrgRelations>();
		if(orgRelationsList != null && !orgRelationsList.isEmpty()){
			for (OrgRelations orgRelation : orgRelationsList) {
				 //to fetch user details by usercode 
				User user = userService.getUserDetailsByUserCode(orgRelation.getCreatedBy());
				if(user != null){
					StringBuilder userName=new StringBuilder();
					orgRelation.setVendorCreateduserName(userName.append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
				}
				List<OrgSpoc> orgSpocs = orgSpocService.getAllSpocsByOrgRelationId(orgRelation.getId(),loginUserId,orgLocationId,"");
				orgRelation.setNoOfContatcs(orgSpocs.size());
				
				Integer spocsCount = orgSpocService.getAllClientContactsCountForCampaignByOrgLocationId(orgRelation.getId(),loginUserId,orgLocationId);
				orgRelation.setNoOfContatcsForCampaign(spocsCount);
				
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue() == orgLocationId.longValue()){
					if(orgRelation.getResponderLocationId() != null){
						orgRelation.setWebSite(orgRelation.getResponderLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getResponderLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getResponderLocationId().getCountry());
						orgRelation.setState(orgRelation.getResponderLocationId().getState());
						orgRelation.setCity(orgRelation.getResponderLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getResponderLocationId().getId());
					}
					
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
					if(orgRelation.getLocationId() != null){
						orgRelation.setWebSite(orgRelation.getLocationId().getOrg().getWebSite());
						orgRelation.setOrgName(orgRelation.getLocationId().getOrgLocationName());
						orgRelation.setCountry(orgRelation.getLocationId().getCountry());
						orgRelation.setState(orgRelation.getLocationId().getState());
						orgRelation.setCity(orgRelation.getLocationId().getCity());
						orgRelation.setVendorlocationId(orgRelation.getLocationId().getId());
					}
					if(orgRelation.getInitiatorLevel()!=null){
						orgRelation.setLevel(orgRelation.getInitiatorLevel());
					}
					else{
						orgRelation.setLevel("");
					}
				}
				
				clientsList.add(orgRelation);
				
			}
		}
		
		return clientsList;
	}

	@Override
	public List<OrgRelations> getClientOrVendorListByOrgLocationId(Long orgLocationId, String type) {
		return orgRelationsDao.getClientOrVendorListByOrgLocationId(orgLocationId, type);
	}
	
	@Override
	public List<OrgRelations> getClientOrVendorListByOrgLocationIdForImmigration(Long orgLocationId, String type) {
		return orgRelationsDao.getClientOrVendorListByOrgLocationIdForImmigration(orgLocationId, type);
	}

	@Override
	public Integer getAllClientsCountByOrgLocationId(Long orgLocationId) throws Exception {
		return orgRelationsDao.getAllClientsCountByOrgLocationId(orgLocationId);
	}

	@Override
	public Integer getAllVendorsCountByOrgLocationId(Long orgLocationId,String type) throws Exception {
		return orgRelationsDao.getAllVendorsCountByOrgLocationId(orgLocationId,type);

	}

	@Override
	public List<OrgRelations> getAllClientsByOrgLocationIdAndDomain(Long orgLocationId, String domain, String responderType)
	{
		return orgRelationsDao.getAllClientsByOrgLocationIdAndDomain(orgLocationId,domain,responderType);
	}

	@Override
	public OrgRelations getOrgRelationById(Long id) throws Exception {
		return orgRelationsDao.getOrgRelationById(id);
	}

	@Override
	public OrgRelations getVendorOrgRelationByResponderLocationIdAndLocationId(Long locationId, Long responderLocationId)
			throws Exception {
		return orgRelationsDao.getVendorOrgRelationByResponderLocationIdAndLocationId(locationId, responderLocationId);
	}

	@Override
	public OrgRelations getOrgRelationsByUUID(String UUID) throws Exception {
		return orgRelationsDao.getOrgRelationsByUUID(UUID);
	}

	@Override
	public ResponseList<OrgRelations> getAllVendorsFilterByOrgLocationIdAndOrgRelationDTO(OrgRelationDTO orgRelationDTO,
			Long id, String status, String vendorsRelationId) throws Exception {
		return orgRelationsDao.getAllVendorsFilterByOrgLocationIdAndOrgRelationDTO(orgRelationDTO, id, status, vendorsRelationId);
	}

	@Override
	public ResponseList<OrgSpoc> getAllVendorsContactFilterListByOrgLocationIdAndTabAccessAndDTO(OrgSpocDTO orgSpocDTO,Long loginUserId, Long orgLocationId, String relationType, String vendorStatus, List<Long> vendorOrgLocationsIds)throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(loginUserId);
		ResponseList<OrgSpoc> vandorContactList = orgRelationsDao.getAllVendorsContactFilterListByOrgLocationIdAndTabAccessDTO(orgSpocDTO,loginUserId, userIds, orgLocationId, relationType, vendorStatus, Long.valueOf(Constants.TAB_REQUIREMENT), vendorOrgLocationsIds);
		return vandorContactList;
	 
	}

	@Override
	public OrgRelations getOrgRelationByResponderLocationIdAndLocationId(Long responderLocationId, Long locationId)
			throws Exception {
		return orgRelationsDao.getOrgRelationByResponderLocationIdAndLocationId(responderLocationId, locationId);
	}

	@Override
	public List<OrgRelations> getAllVendorsByOrgLocationId(Long orgLocationId) {
		return orgRelationsDao.getAllVendorsByOrgLocationId(orgLocationId);
	}

	@Override
	public List<OrgRelationDTO> getAllClientsListOrgLocationId(Long orgLocationId) throws Exception {
		return orgRelationsDao.getAllClientsListOrgLocationId(orgLocationId);
	}

	@Override
	public List<OrgRelationDTO> getAllVendorsListOrgLocationId(Long orgLocationId) throws Exception {
		return orgRelationsDao.getAllVendorsListOrgLocationId(orgLocationId);
	}

	@Override
	public int updatePaymentTermsInOrgrelations() {
		return orgRelationsDao.updatePaymentTermsInOrgrelations();
	}

	@Override
	public List<OrgRelations> getMSPVendorListByOrgLocationId(Long orgLocationId,Long orgRelationId) throws Exception {
		return orgRelationsDao.getMSPVendorListByOrgLocationId(orgLocationId,orgRelationId);
	}

	@Override
	public List<OrgSpoc> getMSPVendorContactListByOrgRelationId(Long orgRelationId) throws Exception {
		return orgRelationsDao.getMSPVendorContactListByOrgRelationId(orgRelationId);
	}

	@Override
	public List<OrgRelations> selectedMSPVendorListByOrgRelationIds(String orgRelationIds) throws Exception {
		return orgRelationsDao.selectedMSPVendorListByOrgRelationIds(orgRelationIds);
	}
	
	
}