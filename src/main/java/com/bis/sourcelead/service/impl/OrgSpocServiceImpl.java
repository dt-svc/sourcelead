package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.dto.AddUserDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ClientDTO;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.service.VendorOnboardService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

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
	UserRoleService userRoleService;
	
	@Autowired
	private OrgRelationsService orgRelationsService;
	
	@Autowired
	private OrgLocationService orgLocationService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
    private SourceleadELKBaseIndexer<ClientVendorContactsIndex> sourceleadELKBaseIndexer;
	

	@Autowired
	ClientVendorContactsIndexHelper clientVendorContactsIndexHelper;
	
	@Autowired
	VendorOnboardService vendorOnboardService;
	
	
	/**
	 * used to add spoc for vendor and client
	 * @param OrgSpoc
	 */
	@Override
	public OrgSpoc addOrgSpoc(OrgSpoc orgSpoc) throws Exception {
		
		orgSpoc.setUpdatedDate(commonUtil.currentDate());
		OrgSpoc orgSpocElk = orgSpocDao.addOrgSpoc(orgSpoc);
		
		 //Elastic Search Document Saving
		  ClientVendorContactsIndex clientVendorContactsIndex = clientVendorContactsIndexHelper.getClientVendorContactIndexFromSpoc(orgSpocElk);
		  sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,orgSpoc.getId().toString());
		
		return  orgSpocElk;
	}

	/**
	 * Add Vendor contact
	 */
	@Override
	public void addVendorContacts(ClientDTO clientDTO,AddUserDTO addUserDTO,CandidateDTO candidateDTO, User user, OrgLocations orgLocations, String orgRelationId, String clientLocationId,String responderType,Org loginOrg) throws Exception {
		
		
		OrgRelations orgRelation = null;
		OrgLocations respondingOrgLocation = null;
		if(orgRelationId != null && !"".equals(orgRelationId) && !"''".equals(orgRelationId) && clientLocationId != null && !"".equals(clientLocationId)){
			 orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(orgRelationId));
			 respondingOrgLocation = orgLocationService.getOrgLocations(Long.parseLong(clientLocationId));
		}else{
			String OrRelationId = clientDTO.getVendorName();
			String [] realtionId = OrRelationId.split(",");
			orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(realtionId[0]));
			respondingOrgLocation = orgLocationService.getOrgLocations(Long.parseLong(realtionId[1]));
		}
		
		User spocUser = userService.storeUser(addUserDTO,candidateDTO,user,respondingOrgLocation,"CREATEUSER",responderType,loginOrg);
		
		if(clientDTO.getOwner() != null && !clientDTO.getOwner().isEmpty()){
			for(String owners :clientDTO.getOwner()){
				User ownerUser = userService.getUserById(Long.parseLong(owners));
				OrgSpoc orgSpoc = orgSpocDao.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(clientDTO.getEmail(), Long.parseLong(orgRelationId), ownerUser.getId(),Constants.RECORD_INACTIVE);
			     if(orgSpoc == null){
			    	 orgSpoc = new OrgSpoc();
			    	 orgSpoc.setCreatedDate(CommonUtil.currentDate());
					 orgSpoc.setCreatedBy(user.getUserCode());
			     }
				orgSpoc.setOrgRelations(orgRelation);
				
				/*if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgSpoc.setClientLocationId(orgRelation.getLocationId());
				}
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgSpoc.setClientLocationId(orgRelation.getResponderLocationId());
				}*/
				
				//to differentiate client and vendor in ORG_SPOC Table
				if(responderType.equalsIgnoreCase("Client")){
					orgSpoc.setClientLocationId(respondingOrgLocation);
					orgSpoc.setVendorLocationId(orgLocations);
					orgSpoc.setClientUser(spocUser);
					orgSpoc.setVendorUser(ownerUser);
					orgSpoc.setOwnerUser(ownerUser);
				}else if(responderType.equalsIgnoreCase("Vendor")){
					orgSpoc.setVendorLocationId(respondingOrgLocation);
					orgSpoc.setClientLocationId(orgLocations);
					orgSpoc.setClientUser(ownerUser);
					orgSpoc.setVendorUser(spocUser);
					orgSpoc.setOwnerUser(ownerUser);
				}
			
				if(StringUtils.isNotBlank(clientDTO.getIsMailSent())){
					orgSpoc.setInvitationStatus(Constants.MAIL_SENT);
				}else{
					orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
				}
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				if(StringUtils.isNotBlank(clientDTO.getSpocStatus())){
					orgSpoc.setSpocStatus(clientDTO.getSpocStatus());
				}else{
					orgSpoc.setSpocStatus(Constants.ACTIVE);
				}
				String UUID = SourceLeadRandomCodeHelper.generateUUID();
				orgSpoc.setUUID(UUID);
				
				orgSpoc.setUpdatedDate(CommonUtil.currentDate());
				orgSpoc.setUpdatedBy(user.getUserCode());
				OrgSpoc orgs  = orgSpocDao.addOrgSpoc(orgSpoc);
				
				 //Elastic Search Document Saving
				  ClientVendorContactsIndex clientVendorContactsIndex = clientVendorContactsIndexHelper.getClientVendorContactIndexFromSpoc(orgs);
				  sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,orgs.getId().toString());
				
				  if((("Client".equals(responderType)) 
							&& StringUtils.isNotBlank(clientDTO.getIsMailSent()) ) 
							&& ("Y".equals(clientDTO.getIsMailSent()))){
						userService.sendEmailToClientOrVendor(orgSpoc.getUUID(),spocUser,ownerUser,user,spocUser.getTempPwd(),responderType,loginOrg,respondingOrgLocation.getOrg(),respondingOrgLocation,orgLocations);
					}
				  
				     commonUtil.updateClientVendorElk(orgRelation,orgLocations,user,responderType);
				  
				} 
		}
		
		 if((("Vendor".equals(responderType)) 
					&& StringUtils.isNotBlank(clientDTO.getIsMailSent()) ) 
					&& ("Y".equals(clientDTO.getIsMailSent()))){
			 VendorOnboarding vendorOnboarding = vendorOnboardService.getVendorOnboardingByOrgRelationId(orgRelation.getId());
			 vendorOnboardService.sendMailAndNotificationToVendorContact(vendorOnboarding, vendorOnboarding.getVendorLocationId(), orgLocations, user,spocUser);
			}
	}
	@Override
	public List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId,Long orgLocationId,String type) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(userId);
		List<OrgSpoc> orgSpocsList  = orgSpocDao.getAllSpocsByOrgRelationId(orgRelationId, userId, userIds,orgLocationId,type);
		if(orgSpocsList != null && !orgSpocsList.isEmpty()){
			for (OrgSpoc orgSpoc : orgSpocsList) {
				
				 StringBuilder spocNameSB = new StringBuilder();
				 StringBuilder ownerNameSB = new StringBuilder();
				
				 //client summary contacts
				 if(null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)){
					 if(null != orgSpoc.getClientUser()){
						 orgSpoc.setSpocEmail(orgSpoc.getClientUser().getEmail());
					 }
					if(null != orgSpoc.getClientUser()){
						if(orgSpoc.getClientUser().getLastName() != null && !"".equalsIgnoreCase(orgSpoc.getClientUser().getLastName())){
							spocNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()); 
						}else{
							spocNameSB.append(orgSpoc.getClientUser().getFirstName()); 
						}
						orgSpoc.setSpocName(spocNameSB.toString());
						
						/*if(orgSpoc.getClientUser().getFirstName() !=null && "".equalsIgnoreCase(orgSpoc.getClientUser().getFirstName()) && orgSpoc.getClientUser().getLastName() !=null && "".equalsIgnoreCase(orgSpoc.getClientUser().getLastName())){
							 spocNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()); 
						 }else{
							 spocNameSB.append(orgSpoc.getClientUser().getFirstName());
						 }*/
						 
					}
					 
					if(null != orgSpoc.getClientUser()){
						orgSpoc.setSpocMobilePhone(orgSpoc.getClientUser().getMobilePhone());
					}
				
					 orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName()).toString());
					if(null != orgSpoc.getClientUser()){
						orgSpoc.setTempOwnerId(orgSpoc.getClientUser().getId());	
					}
					if(null != orgSpoc.getVendorUser()){
					 orgSpoc.setUserId(orgSpoc.getVendorUser().getId());
					}
					 orgSpoc.setSpocLocationId(orgSpoc.getClientLocationId().getId());
					 orgSpoc.setOrgName(orgSpoc.getClientLocationId().getOrgLocationName());
					 
					 orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
				 }
				//vendor summary contacts
				  else if(null != type && !"".equalsIgnoreCase(type) && "Vendor".equalsIgnoreCase(type)){
					 orgSpoc.setSpocEmail(orgSpoc.getVendorUser().getEmail());
					 if(orgSpoc.getVendorUser().getLastName() !=null && !"".equalsIgnoreCase(orgSpoc.getVendorUser().getLastName())){
						 spocNameSB.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName()); 
					 }else{
						 spocNameSB.append(orgSpoc.getVendorUser().getFirstName());
					 }
					 orgSpoc.setSpocName(spocNameSB.toString());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getVendorUser().getMobilePhone());
					 orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString());
					
					 orgSpoc.setTempOwnerId(orgSpoc.getVendorUser().getId());
					 orgSpoc.setUserId(orgSpoc.getClientUser().getId());
					
					 orgSpoc.setSpocLocationId(orgSpoc.getVendorLocationId().getId());
					 orgSpoc.setOrgName(orgSpoc.getVendorLocationId().getOrgLocationName());
					 
					 orgSpoc.setSpocUserId(orgSpoc.getVendorUser().getId());
				 }
				 
				 if(null != orgSpoc.getClientUser() && null != orgSpoc.getOwnerUser() && orgSpoc.getOwnerUser().getId().longValue() == orgSpoc.getClientUser().getId().longValue()){
					// orgSpoc.setSpocUserId(orgSpoc.getVendorUser().getId());
					 orgSpoc.setOwnerUserId(orgSpoc.getClientUser().getId().toString());
				}
				else{
					//orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
					orgSpoc.setOwnerUserId(orgSpoc.getVendorUser().getId().toString());
				}
				
				/* if(orgSpoc.getVendorUser() != null && orgSpoc.getVendorUser().getId() != null){
						orgSpoc.setOwnerUserId(orgSpoc.getVendorUser().getId().toString());
					}
					if(orgSpoc.getClientUser() !=  null && orgSpoc.getClientUser().getId() != null){
						orgSpoc.setSpocUserId(orgSpoc.getClientUser().getId());
					}*/
					if(orgSpoc.getOrgRelations() != null && orgSpoc.getOrgRelations().getId() != null){
						orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
					}
				}
	     }
		return orgSpocsList;
		
	}
	
	
	@Override
	public List<OrgSpoc> getAllActiveSpocsByOrgRelationId(Long orgRelationId, Long userId,Long orgLocationId) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(userId);
		List<OrgSpoc> orgSpocsList  = orgSpocDao.getAllActiveSpocsByOrgRelationId(orgRelationId, userId, userIds,orgLocationId);
		return orgSpocsList;
		
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
	public OrgSpoc getSpocAndOwnerDetailsByIdAndOwnerId(Long spocId, Long ownerId, Long loginUserId, Long spocUserId, Long orgLocationId,String type) throws Exception {
		 OrgSpoc orgSpoc =  orgSpocDao.getSpocAndOwnerDetailsById(spocId,ownerId);
		 if(orgSpoc != null){
			 
			 StringBuilder spocNameSB = new StringBuilder();
			 StringBuilder ownerNameSB = new StringBuilder();
			 
			 if(null != type && !"".equalsIgnoreCase(type) && "Client".equalsIgnoreCase(type)){
		    	 orgSpoc.setUserId(orgSpoc.getVendorUser().getId());
		    	 if(orgSpoc.getClientUser().getLastName()!=null){
		    		 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString());  
		    	 }
		    	 else{
		    		 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getClientUser().getFirstName()).toString());
		    	 }
		    	 orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
			     orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName()).toString());
			     orgSpoc.setSpocFirstName(orgSpoc.getClientUser().getFirstName());
			     orgSpoc.setSpocLastName(orgSpoc.getClientUser().getLastName());
			     orgSpoc.setSpocMobilePhone(orgSpoc.getClientUser().getMobilePhone());
			     orgSpoc.setSpocEmail(orgSpoc.getClientUser().getEmail());
			     orgSpoc.setTempSpocId(orgSpoc.getClientUser().getId());
			     
			     //here the owner must be active or inactive but not not logged in
			     UserRole  userorle = userRoleService.getUserRoleByUserIdAndLocationId(orgSpoc.getClientUser().getId(), orgSpoc.getClientLocationId().getId());
				 if(userorle != null){
					 orgSpoc.setUserState(userorle.getUserState().toString());
				 }
			 }else if(null != type && !"".equalsIgnoreCase(type) && "Vendor".equalsIgnoreCase(type)){
				 orgSpoc.setUserId(orgSpoc.getClientUser().getId());
				 
		    	 if(orgSpoc.getVendorUser().getLastName()!=null){
		    		 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getVendorUser().getFirstName()).append(" ").append(orgSpoc.getVendorUser().getLastName()).toString());  
		    	 }
		    	 else{
		    		 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getVendorUser().getFirstName()).toString());
		    	 }
		    	 orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
		    	 
			     orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString());
			     
			     orgSpoc.setSpocFirstName(orgSpoc.getVendorUser().getFirstName());
			     orgSpoc.setSpocLastName(orgSpoc.getVendorUser().getLastName());
			     orgSpoc.setSpocMobilePhone(orgSpoc.getVendorUser().getMobilePhone());
			     orgSpoc.setSpocEmail(orgSpoc.getVendorUser().getEmail());
			     orgSpoc.setTempSpocId(orgSpoc.getVendorUser().getId());
			     
			     //here the owner must be active or inactive but not not logged in
			     UserRole  userorle = userRoleService.getUserRoleByUserIdAndLocationId(orgSpoc.getVendorUser().getId(), orgSpoc.getVendorLocationId().getId());
				 if(userorle != null){
					 orgSpoc.setUserState(userorle.getUserState().toString());
			     }
			  }
		 }
		 return orgSpoc;
	}
	
	
	
	@Override
	public OrgSpoc validateSpacEmailIdByOwnerUserIdAndOrgRelationId(String email, Long orgRelationId, Long userId,Integer status) throws Exception {
		logger.debug("In OrgSpocService class...");
		return orgSpocDao.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(email, orgRelationId, userId, status);
	}

	@Override
	public List<Long> getOwnerIdbySpocId(Long spocUserId,Long ownerId) {
		return orgSpocDao.getOwnerIdbySpocId(spocUserId,ownerId);	
		}
	
	@Override
	public List<Long> getOwnerUserIdsbySpocEmailAndOrgRelationId(String email, Long orgRelationId,String type) throws Exception{
		return orgSpocDao.getOwnerUserIdsbySpocEmailAndOrgRelationId(email,orgRelationId,type);	
	 }
	
	@Override
	public OrgSpoc getSpocByClientOrVendorUserId(Long userId) throws Exception {
		logger.debug("In OrgSpocService class...");
		return orgSpocDao.getSpocByClientOrVendorUserId(userId);
	}
	
	@Override
	public OrgSpoc getSpocBySpocUUID(String spocUUID) throws Exception {
		logger.debug("In OrgSpocService class...");
		return orgSpocDao.getSpocBySpocUUID(spocUUID);
	}
	
	@Override
	public List<OrgSpoc> getAllContactsbyOrgLocationIdAndUserIdForRequirement(Long orgLocationId, Long userId,String relationType) throws Exception {
		
		List<OrgSpoc>  contactsList = orgSpocDao.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocationId,userId,relationType);
		StringBuilder contactsListStringBuilder=new StringBuilder();
		if(contactsList!=null && !contactsList.isEmpty()){
			for(OrgSpoc orgspoc :contactsList){
				orgspoc.setSpocName(contactsListStringBuilder.append(orgspoc.getClientUser().getFirstName()).append(" ").append(orgspoc.getClientUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getClientUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getClientUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getClientLocationId().getOrgLocationName());
				orgspoc.setStatus(orgspoc.getStatus());
				
			}
		}
		return  contactsList;
	}

	@Override
	public List<OrgSpoc> getAllContactsbyOrgId(Long orgId) throws Exception {
		
		List<OrgSpoc>  contactsList = orgSpocDao.getAllContactsbyOrgId(orgId);
		StringBuilder contactsStringBuilder=new StringBuilder();
		if(contactsList != null && !contactsList.isEmpty()){
			for(OrgSpoc orgspoc :contactsList){
				orgspoc.setSpocName(contactsStringBuilder.append(orgspoc.getClientUser().getFirstName()).append("").append(orgspoc.getClientUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getClientUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getClientUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getClientLocationId().getOrgLocationName());
				orgspoc.setStatus(orgspoc.getStatus());
				
			}
		}
		return  contactsList;
	}
	
	
	@Override
	public List<OrgSpoc> getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(Long orgId, String invitationStatus, String spocStatus) throws Exception {
		
		List<OrgSpoc>  contactsList = orgSpocDao.getAllContactsbyOrgIdAndInvitationStatusAndSpocStatus(orgId, invitationStatus, spocStatus);
		StringBuilder contactListStringBuilder=new StringBuilder();
		if(contactsList !=null && !contactsList.isEmpty()){
			for(OrgSpoc orgspoc :contactsList){
				orgspoc.setSpocName(contactListStringBuilder.append(orgspoc.getClientUser().getFirstName()).append("").append(orgspoc.getClientUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getClientUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getClientUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getClientLocationId().getOrgLocationName());
				orgspoc.setStatus(orgspoc.getStatus());
				
			}
		}
		return  contactsList;
	}

	
	@Override
	public List<OrgSpoc> getAllContactsbyOrgRelationIdAndLocationId(Long relationId, Long locationId, String initiator) throws Exception {
		
		List<OrgSpoc>  contactsList = orgSpocDao.getAllContactsbyOrgRelationIdAndLocationId(relationId, locationId, initiator);
		StringBuilder contactStringBuilder=new StringBuilder();
		if(contactsList!=null && !contactsList.isEmpty()){
			for(OrgSpoc orgspoc :contactsList){
				orgspoc.setSpocName(contactStringBuilder.append(orgspoc.getClientUser().getFirstName()).append("").append(orgspoc.getClientUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getClientUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getClientUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getClientLocationId().getOrgLocationName());
				orgspoc.setStatus(orgspoc.getStatus());
				
			}
		}
		return  contactsList;
	}

	@Override
	public List<Long> getAssignedAsClientOrVendorSpocCountByUserId(Long userId, Long orgLocationId,String type) throws Exception {
		return orgSpocDao.getAssignedAsClientOrVendorSpocCountByUserId(userId, orgLocationId,type);
	}

	@Override
	public List<OrgSpoc> getAssignedAsClientOrVendorSpocByUserId(Long userId, Long orgLocationId, String type)
			throws Exception {
		return orgSpocDao.getAssignedAsClientOrVendorSpocByUserId(userId, orgLocationId, type);
	}

	@Override
	public List<OrgSpoc> getAllClientContactsListByOrgLocationId(Long userId,Long orgLocationId) throws Exception {
		List <OrgSpoc> clientContactsList =  orgSpocDao.getAllClientContactsListByOrgLocationId(userId,orgLocationId);
		List<OrgSpoc> contactsList = new ArrayList<OrgSpoc>();
		if(clientContactsList != null && !clientContactsList.isEmpty()){
			   for (OrgSpoc orgSpoc : clientContactsList) {
				     StringBuilder spocNameSB = new StringBuilder();
				     orgSpoc.setSpocEmail(orgSpoc.getClientUser().getEmail());
				     if(null != orgSpoc.getClientUser().getLastName()){
				    	 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getClientUser().getFirstName()).append(" ").append(orgSpoc.getClientUser().getLastName()).toString());
				     }else{
				    	 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getClientUser().getFirstName()).toString());
				     }
					
					 orgSpoc.setSpocMobilePhone(orgSpoc.getClientUser().getMobilePhone());
					 orgSpoc.setOrgName(orgSpoc.getClientLocationId().getOrgLocationName());
					 contactsList.add(orgSpoc);
				}
		   }
		
		return contactsList;
	}

	@Override
	public Integer getAllClientContactsCountForCampaignByOrgLocationId(Long orgRelationId,Long userId,Long orgLocationId) throws Exception {
		return orgSpocDao.getAllClientContactsCountForCampaignByOrgLocationId(orgRelationId,userId,orgLocationId);
	}
	
	@Override
	public List<OrgSpoc> getOrgSpocByOrgRelationId(Long orgRelationId) throws Exception {
		return orgSpocDao.getOrgSpocByOrgRelationId(orgRelationId);
	}

	@Override
	public Integer getAllVendorContactsCountbyOrgLocationId(Long orgId,String type,Long userId) throws Exception {
		return orgSpocDao.getAllVendorContactsCountbyOrgLocationId(orgId,type,userId);
	}

	public void saveOrUpdateClientVendorContactElkDocument(OrgSpoc orgSpoc) throws Exception {
		
		 
		}

	@Override
	public OrgSpoc getSpocListByResponderLocationId(Long initiatingPartyOrgLocationId,Long responderLocationId,String respondingOrgType) throws Exception {
		return orgSpocDao.getSpocListByResponderLocationId(initiatingPartyOrgLocationId,responderLocationId,respondingOrgType);
	}

	@Override
	public List<OrgSpoc> getOrgSpocByUserIdAndOrgLocationCode(Long userId, Long orgLocationId) throws Exception {
		return orgSpocDao.getOrgSpocByUserIdAndOrgLocationCode(userId,orgLocationId);
	}

	@Override
	public List<Long> getOwnerListByUserIdAndOrglocationId(User user, OrgLocations orgLocations,String type) {
		return  orgSpocDao.getOwnerListByUserIdAndOrglocationId(user,orgLocations,type);
	}

	@Override
	public OrgSpoc getValidateOwnersBySpocUserIdAndSpocOwnerUserId(Long spocUserId, Long spocOwnderUserId,Long responderLocationId, String responderType) throws Exception
	{
		return  orgSpocDao.getValidateOwnersBySpocUserIdAndSpocOwnerUserId(spocUserId,spocOwnderUserId,responderLocationId,responderType);
	}
	
	@Override
	public List<OrgSpoc> getOrgSpocContactEmailsByOrgRelationIdAndUserId(Long userId, Long orgRelationId,Long orgLocationId)
			throws Exception {
		return orgSpocDao.getOrgSpocContactEmailsByOrgRelationIdAndUserId(userId, orgRelationId,orgLocationId);
	}

	@Override
	public OrgSpoc getValidateOwnersByVendorUserIdAndSpocOwnerUserId(Long spocUserId, Long spocOwnderUserId, Long responderLocationId) throws Exception
	{
		return orgSpocDao.getValidateOwnersByVendorUserIdAndSpocOwnerUserId(spocUserId, spocOwnderUserId, responderLocationId);
	}

	@Override
	public List<OrgSpoc> getAllClientsByOrgLocationId(Long orgLocationId) throws Exception
	{
		return orgSpocDao.getAllClientsByOrgLocationId(orgLocationId);
	}

	@Override
	public List<OrgSpoc> getSpoclistByDomain(Long orgRelationId, String domain) throws Exception
	{
		return orgSpocDao.getSpoclistByDomain(orgRelationId,domain);
	}

	@Override
	public boolean getOwnerUserExistedByOrgRelationIdAndOwnerUserId(Long orgRelationId, Long ownerUserId)
			throws Exception {
		return orgSpocDao.getOwnerUserExistedByOrgRelationIdAndOwnerUserId(orgRelationId, ownerUserId);
	}

	@Override
	public OrgSpoc getSpocByOrgRelationIdAndAssignedUserId(Long orgRelationId, Long assignUserId) throws Exception {
		return orgSpocDao.getSpocByOrgRelationIdAndAssignedUserId(orgRelationId, assignUserId);
	}

	@Override
	public List<OrgSpoc> getSpocListByVendorUserIdAndOrgRelationId(Long orgRelationId, Long vendorUserId)
			throws Exception {
		return orgSpocDao.getSpocListByVendorUserIdAndOrgRelationId(orgRelationId, vendorUserId);
	}
	
}
