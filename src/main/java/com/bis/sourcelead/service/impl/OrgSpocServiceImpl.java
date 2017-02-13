package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgSpocDao;
import com.bis.sourcelead.elk.index.ClientVendorContactsIndex;
import com.bis.sourcelead.elk.index.helper.ClientVendorContactsIndexHelper;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.UserRoleService;
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
	
	/**
	 * used to add spoc for vendor and client
	 * @param OrgSpoc
	 */
	@Override
	public OrgSpoc addOrgSpoc(OrgSpoc orgSpoc) throws Exception {
		
		orgSpoc.setCreatedDate(commonUtil.currentDate());
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
	public void addVendorContacts(JSONObject jsonObj, User user, OrgLocations orgLocations, String orgRelationId, String clientLocationId,String responderType,Org loginOrg) throws Exception {
		
		
		OrgRelations orgRelation = null;
		OrgLocations respondingOrgLocation = null;
		if(orgRelationId != null && !"".equals(orgRelationId) && !"''".equals(orgRelationId) && clientLocationId != null && !"".equals(clientLocationId)){
			 orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(orgRelationId));
			 respondingOrgLocation = orgLocationService.getOrgLocations(Long.parseLong(clientLocationId));
		}else{
			String OrRelationId = jsonObj.getString("vendorName");
			String [] realtionId = OrRelationId.split(",");
			orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(realtionId[0]));
			respondingOrgLocation = orgLocationService.getOrgLocations(Long.parseLong(realtionId[1]));
		}
		
		User spocUser = userService.storeUser(jsonObj,user,respondingOrgLocation,"CREATEUSER",responderType,loginOrg);
		
		if(jsonObj.has("owner")){
			String ownersId = jsonObj.getString("owner");
			ownersId = ownersId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] owners = ownersId.split(",");
			for(int i=0 ;i<owners.length;i++ ){
				User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				OrgSpoc orgSpoc = orgSpocDao.validateSpacEmailIdByOwnerUserIdAndOrgRelationId(jsonObj.getString("email"), Long.parseLong(orgRelationId), ownerUser.getId(),Constants.RECORD_INACTIVE);
			     if(orgSpoc == null){
			    	 orgSpoc = new OrgSpoc();
			    	 orgSpoc.setCreatedDate(CommonUtil.currentDate());
					 orgSpoc.setCreatedBy(user.getUserCode());
			     }
				orgSpoc.setOrgRelations(orgRelation);
				orgSpoc.setSpocUser(spocUser);
				if(orgRelation.getResponderLocationId() != null && orgRelation.getResponderLocationId().getId() != null && orgRelation.getResponderLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgSpoc.setResponderLocationId(orgRelation.getLocationId());
				}
				if(orgRelation.getLocationId() != null && orgRelation.getLocationId().getId() != null && orgRelation.getLocationId().getId().longValue() == orgLocations.getId().longValue()){
					orgSpoc.setResponderLocationId(orgRelation.getResponderLocationId());
				}
					
				/*if(jsonObj.has("vendorContact") && jsonObj.getString("vendorContact").equals("vendorContact")){
					orgSpoc.setResponderLocationId(orgRelation.getResponderLocationId());
				}else{
					orgSpoc.setResponderLocationId(orgRelation.getLocationId());	
				}*/
				
				orgSpoc.setInitiatorLocationId(orgLocations);
				//User ownerUser = userService.getUserById(Long.parseLong(owners[i].replace("\"", "")));
				orgSpoc.setOwnerUser(ownerUser);
			
				if(jsonObj.has("isMailSent")){
					orgSpoc.setInvitationStatus(Constants.MAIL_SENT);
				}else{
					orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
				}
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				if(jsonObj.has("spocStatus")){
					orgSpoc.setSpocStatus(jsonObj.getString("spocStatus"));
				}else{
					orgSpoc.setSpocStatus(Constants.ACTIVE);
				}
				
				
				orgSpoc.setUpdatedDate(CommonUtil.currentDate());
				orgSpoc.setUpdatedBy(user.getUserCode());
				OrgSpoc orgs  =orgSpocDao.addOrgSpoc(orgSpoc);
				
				 //Elastic Search Document Saving
				  ClientVendorContactsIndex clientVendorContactsIndex = clientVendorContactsIndexHelper.getClientVendorContactIndexFromSpoc(orgs);
				  sourceleadELKBaseIndexer.saveOrUpdateElkDocument(clientVendorContactsIndex, Constants.ELK_CLIENTVENDOR_CONTACT_INDEX, Constants.ELK_CLIENTVENDOR_CONTACT_TYPE,orgs.getId().toString());
				
				logger.debug("Vendor Contac is added successfully");
				} 
		}
	}
	@Override
	public List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId,Long orgLocationId) throws Exception {
		List<Long> userIds = userRoleService.getAllUserIdsByReportingManagerId(userId);
		List<OrgSpoc> orgSpocsList  = orgSpocDao.getAllSpocsByOrgRelationId(orgRelationId, userId, userIds,orgLocationId);
		if(orgSpocsList != null && !orgSpocsList.isEmpty()){
			for (OrgSpoc orgSpoc : orgSpocsList) {
					if((orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == userId.longValue()) || (orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null && orgSpoc.getOwnerUser().getId().longValue() == userId.longValue())){
						//if login user is spoc
						if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == userId.longValue()){
							 StringBuilder spocNameSB = new StringBuilder();
							 StringBuilder ownerNameSB = new StringBuilder();
		    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
		    				 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
		    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
		    				 orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
		    				// orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
		    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
		    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
						//if login user is Owner
						else if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null && orgSpoc.getOwnerUser().getId().longValue() == userId.longValue()){
							     StringBuilder spocNameSB1 = new StringBuilder();
							     StringBuilder ownerNameSB1 = new StringBuilder();
								 orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
								 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
								 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
								 orgSpoc.setOwnerName(ownerNameSB1.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
								 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
								 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
								 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
							}
						 
					}else{
						if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
							 StringBuilder spocNameSB2 = new StringBuilder();
						     StringBuilder ownerNameSB2 = new StringBuilder();
							 orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 orgSpoc.setOwnerName(ownerNameSB2.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						 }
						if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
							StringBuilder spocNameSB3 = new StringBuilder();
						    StringBuilder ownerNameSB3 = new StringBuilder();
						 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
						 orgSpoc.setSpocName(spocNameSB3.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
						 orgSpoc.setOwnerName(ownerNameSB3.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
						 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
					}
					if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
						if(orgSpoc.getInitiatorLocationId() != null){
							orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
							orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
						}
					}
					if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
						if(orgSpoc.getResponderLocationId() != null){
							orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
							orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
						}
					}
					if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null){
						orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
					}
					if(orgSpoc.getSpocUser() !=  null && orgSpoc.getSpocUser().getId() != null){
						orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
					}
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
		//List<OrgSpoc> orgSpocsList  = orgSpocDao.getAllSpocsByOrgRelationId(orgRelationId, userId, userIds,orgLocationId);
		List<OrgSpoc> orgSpocsList  = orgSpocDao.getAllActiveSpocsByOrgRelationId(orgRelationId, userId, userIds,orgLocationId);
		if(orgSpocsList != null && !orgSpocsList.isEmpty()){
			for (OrgSpoc orgSpoc : orgSpocsList) {
					if((orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == userId.longValue()) || (orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null && orgSpoc.getOwnerUser().getId().longValue() == userId.longValue())){
						//if login user is spoc
						if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == userId.longValue()){
							 StringBuilder spocNameSB = new StringBuilder();
							 StringBuilder ownerNameSB = new StringBuilder();
		    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
		    				 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
		    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
		    				 orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
		    				// orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
		    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
		    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
						//if login user is Owner
						else if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null && orgSpoc.getOwnerUser().getId().longValue() == userId.longValue()){
							     StringBuilder spocNameSB1 = new StringBuilder();
							     StringBuilder ownerNameSB1 = new StringBuilder();
								 orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
								 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
								 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
								 orgSpoc.setOwnerName(ownerNameSB1.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
								 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
								 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
								 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
							}
						 
					}else{
						if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
							 StringBuilder spocNameSB2 = new StringBuilder();
						     StringBuilder ownerNameSB2 = new StringBuilder();
							 orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 orgSpoc.setOwnerName(ownerNameSB2.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						 }
						if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
							StringBuilder spocNameSB3 = new StringBuilder();
						    StringBuilder ownerNameSB3 = new StringBuilder();
						 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
						 orgSpoc.setSpocName(spocNameSB3.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
						 orgSpoc.setOwnerName(ownerNameSB3.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
						 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
						 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
					}
					if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
						if(orgSpoc.getInitiatorLocationId() != null){
							orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
							orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
						}
					}
					if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
						if(orgSpoc.getResponderLocationId() != null){
							orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
							orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
						}
					}
					if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null){
						orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
					}
					if(orgSpoc.getSpocUser() !=  null && orgSpoc.getSpocUser().getId() != null){
						orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
					}
					if(orgSpoc.getOrgRelations() != null && orgSpoc.getOrgRelations().getId() != null){
						orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
					}
				}
	     }
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
	public OrgSpoc getSpocAndOwnerDetailsByIdAndOwnerId(Long spocId, Long ownerId, Long loginUserId, Long spocUserId, Long orgLocationId) throws Exception {
		 OrgSpoc orgSpoc =  orgSpocDao.getSpocAndOwnerDetailsById(spocId,ownerId);
		 if(orgSpoc != null){
		       if(loginUserId.longValue()==ownerId.longValue() ){
		    	orgSpoc =  getOrgSpocDetails(orgSpoc);
		       }
		       else if(loginUserId.longValue()==spocUserId.longValue()){
		    	   StringBuilder spocNameSB = new StringBuilder();
				   StringBuilder ownerNameSB = new StringBuilder();
		    	 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
		    	 if(orgSpoc.getOwnerUser().getLastName()!=null){
		    		 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());  
		    	 }
		    	 else{
		    		 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).toString());
		    	 }
		    	 orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
			     orgSpoc.setOwnerName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
			     orgSpoc.setSpocFirstName(orgSpoc.getOwnerUser().getFirstName());
			     orgSpoc.setSpocLastName(orgSpoc.getOwnerUser().getLastName());
			     orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
			     orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
			     //prasad salina
			     orgSpoc.setTempSpocId(orgSpoc.getOwnerUser().getId());
			     //orgSpoc.setUserState(orgSpoc.getOwnerUser().getUserState().toString());
			     
			     //here the owner must be active or inactive but not not logged in
			     orgSpoc.setUserState((Status.ACTIVE).toString());
			     
			     
		      }
		     else {
		    	 if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
		    		 StringBuilder spocNameSB1 = new StringBuilder();
					 StringBuilder ownerNameSB1 = new StringBuilder();
		    		 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
		    		 if(orgSpoc.getSpocUser().getLastName()!=null){
		    			 orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
		    		 }
		    		 else{
		    			 orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).toString());
		    		 }
		    		 orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
					 orgSpoc.setOwnerName(ownerNameSB1.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
					 orgSpoc.setSpocFirstName(orgSpoc.getSpocUser().getFirstName());
				     orgSpoc.setSpocLastName(orgSpoc.getSpocUser().getLastName());
				     orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
					//prasad salina
				     orgSpoc.setTempSpocId(orgSpoc.getSpocUser().getId());
					
				    // orgSpoc.setUserState(orgSpoc.getSpocUser().getUserState().toString());
					 UserRole  userorle = userRoleService.getUserRoleByUserIdAndLocationId(orgSpoc.getSpocUser().getId(), orgSpoc.getResponderLocationId().getId());
					 if(userorle != null){
						 orgSpoc.setUserState(userorle.getUserState().toString());
					 }
					 
				 }
				if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
					StringBuilder spocNameSB2 = new StringBuilder();
					 StringBuilder ownerNameSB2 = new StringBuilder();
					 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					 if(orgSpoc.getOwnerUser().getLastName()!=null){
						 orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
					 }
					 else{
						 orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getOwnerUser().getFirstName()).toString());
					 }
					 orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
					 orgSpoc.setOwnerName(ownerNameSB2.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
					 orgSpoc.setSpocFirstName(orgSpoc.getOwnerUser().getFirstName());
				     orgSpoc.setSpocLastName(orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 
					 //orgSpoc.setUserState(orgSpoc.getOwnerUser().getUserState().toString());
					 if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId() != null &&
							 orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null){
						 UserRole  userorle = userRoleService.getUserRoleByUserIdAndLocationId(orgSpoc.getOwnerUser().getId(), orgSpoc.getInitiatorLocationId().getId());
						 if(userorle != null){
							 orgSpoc.setUserState(userorle.getUserState().toString());
						 }
					 }
					//prasad salina
				     orgSpoc.setTempSpocId(orgSpoc.getOwnerUser().getId());
				}
		     }
		 }
		 return orgSpoc;
	}
	
	private OrgSpoc getOrgSpocDetails(OrgSpoc orgSpoc) throws Exception {
		 StringBuilder spocNameSB = new StringBuilder();
		 StringBuilder ownerNameSB = new StringBuilder();
		 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
   	     orgSpoc.setOwnerName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());  
	     orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
	     if(null != orgSpoc.getSpocUser().getLastName()){
	    	 orgSpoc.setSpocName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
	     }else{
	    	 orgSpoc.setSpocName(ownerNameSB.append(orgSpoc.getSpocUser().getFirstName()).toString());
	     }
	     orgSpoc.setSpocFirstName(orgSpoc.getSpocUser().getFirstName());
	     orgSpoc.setSpocLastName(orgSpoc.getSpocUser().getLastName());
	     orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
	     orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
	     //prasad salina
	     orgSpoc.setTempSpocId(orgSpoc.getSpocUser().getId());
	     
	     //orgSpoc.setUserState(orgSpoc.getSpocUser().getUserState().toString());
	     if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null &&
				 orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null){
	    	 UserRole  userorle = userRoleService.getUserRoleByUserIdAndLocationId(orgSpoc.getSpocUser().getId(), orgSpoc.getResponderLocationId().getId());
			 if(userorle != null){
				 orgSpoc.setUserState(userorle.getUserState().toString());
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
	public List<Long> getOwnerUserIdsbySpocEmailAndOrgRelationId(String email, Long orgRelationId) throws Exception{
		return orgSpocDao.getOwnerUserIdsbySpocEmailAndOrgRelationId(email,orgRelationId);	
	 }
	
	@Override
	public OrgSpoc getSpocUserByUserId(Long spocUserId) throws Exception {
		logger.debug("In OrgSpocService class...");
		return orgSpocDao.getSpocUserByUserId(spocUserId);
	}
	
	@Override
	public List<OrgSpoc> getAllContactsbyOrgLocationIdAndUserIdForRequirement(Long orgLocationId, Long userId,String relationType) throws Exception {
		
		List<OrgSpoc>  contactsList = orgSpocDao.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocationId,userId,relationType);
		StringBuilder contactsListStringBuilder=new StringBuilder();
		if(contactsList!=null && !contactsList.isEmpty()){
			for(OrgSpoc orgspoc :contactsList){
				
				orgspoc.setSpocName(contactsListStringBuilder.append(orgspoc.getSpocUser().getFirstName()).append(" ").append(orgspoc.getSpocUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getSpocUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getSpocUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getResponderLocationId().getOrg().getOrgName());
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
				orgspoc.setSpocName(contactsStringBuilder.append(orgspoc.getSpocUser().getFirstName()).append("").append(orgspoc.getSpocUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getSpocUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getSpocUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getResponderLocationId().getOrg().getOrgName());
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
				orgspoc.setSpocName(contactListStringBuilder.append(orgspoc.getSpocUser().getFirstName()).append("").append(orgspoc.getSpocUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getSpocUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getSpocUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getResponderLocationId().getOrg().getOrgName());
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
				
				orgspoc.setSpocName(contactStringBuilder.append(orgspoc.getSpocUser().getFirstName()).append("").append(orgspoc.getSpocUser().getLastName()).toString());
				orgspoc.setSpocEmail(orgspoc.getSpocUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getSpocUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getResponderLocationId().getOrg().getOrgName());
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
					
					if((orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == userId.longValue()) || (orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId()!= null && orgSpoc.getOwnerUser().getId().longValue() == userId.longValue())){
						//if login user is spoc
						if(orgSpoc.getSpocUser() != null && orgSpoc.getSpocUser().getId() != null && orgSpoc.getSpocUser().getId().longValue() == userId.longValue()){
							 StringBuilder spocNameSB = new StringBuilder();
		    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
		    				 orgSpoc.setSpocName(spocNameSB.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
		    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
						}
						//if login user is Owner
						else if(orgSpoc.getOwnerUser() != null && orgSpoc.getOwnerUser().getId()!= null && orgSpoc.getOwnerUser().getId().longValue() == userId.longValue()){
							    StringBuilder spocNameSB1 = new StringBuilder();
								if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
									orgSpoc.setSpocName(spocNameSB1.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
								}else{
									orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
								}
								 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
								 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							}
							
						 
					}else{
						if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
							StringBuilder spocNameSB2 = new StringBuilder();
							if(orgSpoc.getSpocUser().getFirstName() != null && orgSpoc.getSpocUser().getFirstName() != "" && orgSpoc.getSpocUser().getLastName() != null && orgSpoc.getSpocUser().getLastName() != "" ){
								orgSpoc.setSpocName(spocNameSB2.append(orgSpoc.getSpocUser().getFirstName()).append(" ").append(orgSpoc.getSpocUser().getLastName()).toString());
							}else{
								orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName());
							}
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 
						 }
						if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
							StringBuilder spocNameSB3 = new StringBuilder();
						    orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
						    orgSpoc.setSpocName(spocNameSB3.append(orgSpoc.getOwnerUser().getFirstName()).append(" ").append(orgSpoc.getOwnerUser().getLastName()).toString());
						    orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
						}
					}
					if(orgSpoc.getResponderLocationId() != null && orgSpoc.getResponderLocationId().getId() != null && orgSpoc.getResponderLocationId().getId().longValue() == orgLocationId.longValue()){
						 orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
					}
					if(orgSpoc.getInitiatorLocationId() != null && orgSpoc.getInitiatorLocationId().getId() != null && orgSpoc.getInitiatorLocationId().getId().longValue() == orgLocationId.longValue() ){
						orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
					}
					
					contactsList.add(orgSpoc);
				}
			   
			   
		   }
		
		return contactsList;
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
	
}
