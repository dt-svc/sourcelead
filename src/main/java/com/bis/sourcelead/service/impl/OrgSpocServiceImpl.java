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
	public void addVendorContacts(JSONObject jsonObj, User user, OrgLocations orgLocations, String orgRelationId) throws Exception {
		User spocUser = userService.storeUser(jsonObj,user,orgLocations,"CREATEUSER","",new Org());
		
		OrgRelations orgRelation = null;
		if(orgRelationId != null && !"".equals(orgRelationId)){
			 orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(orgRelationId));
		}else{
			String OrRelationId = jsonObj.getString("vendorName");
			orgRelation = orgRelationsService.getOrRelationsById(Long.parseLong(OrRelationId));
		}
		
		
		if(jsonObj.has("owner")){
			String ownersId = jsonObj.getString("owner");
			ownersId = ownersId.replaceAll("\\[", "").replaceAll("\\]","");
			String[] owners = ownersId.split(",");
			for(int i=0 ;i<owners.length;i++ ){
				OrgSpoc orgSpoc = new OrgSpoc();
				orgSpoc.setOrgRelations(orgRelation);
				orgSpoc.setSpocUser(spocUser);
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
	public List<OrgSpoc> getAllSpocsByOrgRelationId(Long orgRelationId, Long userId) throws Exception {
		List<Long> userIds = userService.getAllUserIdsByReportingManagerId(userId);
		
		return orgSpocDao.getAllSpocsByOrgRelationId(orgRelationId, userId, userIds);
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
	

}
