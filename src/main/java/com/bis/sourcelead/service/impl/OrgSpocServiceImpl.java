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
	public void addVendorContacts(JSONObject jsonObj, User user, OrgLocations orgLocations, String orgRelationId, String clientLocationId,String responderType) throws Exception {
		
		
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
		
		User spocUser = userService.storeUser(jsonObj,user,respondingOrgLocation,"CREATEUSER",responderType,new Org());
		
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
				if(jsonObj.has("spocStatus")){
					orgSpoc.setSpocStatus(jsonObj.getString("spocStatus"));
				}else{
					orgSpoc.setSpocStatus(Constants.ACTIVE);
				}
				
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
		List<OrgSpoc> orgSpocsList  = orgSpocDao.getAllSpocsByOrgRelationId(orgRelationId, userId, userIds,orgLocationId);
		
		if(orgSpocsList != null && !orgSpocsList.isEmpty()){
			for (OrgSpoc orgSpoc : orgSpocsList) {
					if(orgSpoc.getSpocUser().getId() == userId || orgSpoc.getOwnerUser().getId() == userId){
						//if login user is spoc
						if(orgSpoc.getSpocUser().getId() == userId){
		    				 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
		    				 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
		    				 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
		    				 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
		    				// orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
		    				 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
		    				 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
						//if login user is Owner
						else if(orgSpoc.getOwnerUser().getId() == userId){
								 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
								 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
								 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
								 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
								 //orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
								 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
								 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
							}
						 
					}else{
						if(orgSpoc.getInitiatorLocationId().getId() == orgLocationId ){
							 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
							 orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
							 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
							 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
							 orgSpoc.setTempOwnerId(orgSpoc.getOwnerUser().getId());
							 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
						 }
						if(orgSpoc.getResponderLocationId().getId() == orgLocationId){
						 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
						 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
						 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
						 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
						 orgSpoc.setTempOwnerId(orgSpoc.getSpocUser().getId());
						 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
						}
					}
					if(orgSpoc.getResponderLocationId().getId() == orgLocationId){
						orgSpoc.setSpocLocationId(orgSpoc.getInitiatorLocationId().getId());
						orgSpoc.setOrgName(orgSpoc.getInitiatorLocationId().getOrg().getOrgName());
					}
					if(orgSpoc.getInitiatorLocationId().getId() == orgLocationId ){
						orgSpoc.setSpocLocationId(orgSpoc.getResponderLocationId().getId());
						orgSpoc.setOrgName(orgSpoc.getResponderLocationId().getOrg().getOrgName());
					}
					
					orgSpoc.setOwnerUserId(orgSpoc.getOwnerUser().getId().toString());
					orgSpoc.setSpocUserId(orgSpoc.getSpocUser().getId());
					orgSpoc.setOrgRelationId(orgSpoc.getOrgRelations().getId());
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
	public OrgSpoc getSpocAndOwnerDetailsByIdAndOwnerId(Long spocId, Long ownerId, Long spocUserId, Long loginUserId, Long orgLocationId) {
		 OrgSpoc orgSpoc =  orgSpocDao.getSpocAndOwnerDetailsById(spocId,ownerId);
		 if(orgSpoc != null){
		       if(loginUserId==ownerId ){
		    	orgSpoc =  getOrgSpocDetails(orgSpoc);
		       }
		       else if(loginUserId==spocUserId){
		    	 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
		    	 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());  
			     orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
			     orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
			     orgSpoc.setSpocFirstName(orgSpoc.getOwnerUser().getFirstName());
			     orgSpoc.setSpocLastName(orgSpoc.getOwnerUser().getLastName());
			     orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
			     orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
			     //prasad salina
			     orgSpoc.setTempSpocId(orgSpoc.getOwnerUser().getId());
			     orgSpoc.setUserState(orgSpoc.getOwnerUser().getUserState().toString());
		      }
		     else {
		    	 if(orgSpoc.getInitiatorLocationId().getId() == orgLocationId ){
		    		 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
					 orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					 orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
					 orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setSpocFirstName(orgSpoc.getSpocUser().getFirstName());
				     orgSpoc.setSpocLastName(orgSpoc.getSpocUser().getLastName());
				     orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
					//prasad salina
				     orgSpoc.setTempSpocId(orgSpoc.getSpocUser().getId());
					 orgSpoc.setUserState(orgSpoc.getSpocUser().getUserState().toString());
					 
				 }
				if(orgSpoc.getResponderLocationId().getId() == orgLocationId){
					 orgSpoc.setUserId(orgSpoc.getOwnerUser().getId());
					 orgSpoc.setSpocName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
					 orgSpoc.setOwnerName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
					 orgSpoc.setSpocFirstName(orgSpoc.getOwnerUser().getFirstName());
				     orgSpoc.setSpocLastName(orgSpoc.getOwnerUser().getLastName());
					 orgSpoc.setSpocEmail(orgSpoc.getOwnerUser().getEmail());
					 orgSpoc.setSpocMobilePhone(orgSpoc.getOwnerUser().getMobilePhone());
					 orgSpoc.setUserState(orgSpoc.getOwnerUser().getUserState().toString());
					//prasad salina
				     orgSpoc.setTempSpocId(orgSpoc.getOwnerUser().getId());
				}
		     }
		 }
		 return orgSpoc;
	}
	
	private OrgSpoc getOrgSpocDetails(OrgSpoc orgSpoc) {
		 orgSpoc.setUserId(orgSpoc.getSpocUser().getId());
   	     orgSpoc.setOwnerName(orgSpoc.getOwnerUser().getFirstName()+" "+orgSpoc.getOwnerUser().getLastName());  
	     orgSpoc.setSpocStatus(orgSpoc.getSpocStatus());
	     orgSpoc.setSpocName(orgSpoc.getSpocUser().getFirstName()+" "+orgSpoc.getSpocUser().getLastName());
	     orgSpoc.setSpocFirstName(orgSpoc.getSpocUser().getFirstName());
	     orgSpoc.setSpocLastName(orgSpoc.getSpocUser().getLastName());
	     orgSpoc.setSpocMobilePhone(orgSpoc.getSpocUser().getMobilePhone());
	     orgSpoc.setSpocEmail(orgSpoc.getSpocUser().getEmail());
	     //prasad salina
	     orgSpoc.setTempSpocId(orgSpoc.getSpocUser().getId());
	     orgSpoc.setUserState(orgSpoc.getSpocUser().getUserState().toString());
		return orgSpoc;
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
	
	@Override
	public List<OrgSpoc> getAllContactsbyOrgLocationIdAndUserIdForRequirement(Long orgLocationId, Long userId,String relationType) throws Exception {
		
		List<OrgSpoc>  contactsList = orgSpocDao.getAllContactsbyOrgLocationIdAndUserIdForRequirement(orgLocationId,userId,relationType);
		
		if(contactsList!=null){
			for(OrgSpoc orgspoc :contactsList){
				
				orgspoc.setSpocName(orgspoc.getSpocUser().getFirstName() +""+orgspoc.getSpocUser().getLastName());
				orgspoc.setSpocEmail(orgspoc.getSpocUser().getEmail());
				orgspoc.setSpocMobilePhone(orgspoc.getSpocUser().getMobilePhone());
				orgspoc.setOrgName(orgspoc.getResponderLocationId().getOrg().getOrgName());
				orgspoc.setStatus(orgspoc.getStatus());
				
			}
		}
		return  contactsList;
	}

	
}
