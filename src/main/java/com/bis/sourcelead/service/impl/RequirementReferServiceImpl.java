package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ReferContactsDao;
import com.bis.sourcelead.dao.RequirementReferDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ReferContacts;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ReferContactsService;
import com.bis.sourcelead.service.RequirementReferService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class RequirementReferServiceImpl implements RequirementReferService {

	
	@Autowired
	RequirementReferDao requirementReferDao;
	
	
	@Autowired
	ReferContactsDao referContactsDao;
	
	
	
	protected boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
	@Override
	public RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception {
		return requirementReferDao.addRequirementRefer(requirementRefer);
	}

	@Override
	public void saveReferCandidate(User user, OrgLocations orgLocations, JSONObject jsonObj) throws Exception {
		
		RequirementRefer requirementRefer = new RequirementRefer();
		
		requirementRefer.setReferFromEmail(user.getEmail());
		requirementRefer.setReferFromUser(user);
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			requirementRefer.setReferToEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("referRequirementId") && !isNullOrEmpty(jsonObj.getString("referRequirementId"))){
			Requirement requirement = new Requirement();
			requirement.setId(Long.valueOf(jsonObj.getString("referRequirementId")));
			requirementRefer.setRequirementId(requirement);
		}
		
		requirementRefer.setOrgLocationId(orgLocations);
		requirementRefer.setCreatedDate(new Date());
		requirementRefer.setUpdatedDate(new Date());
		requirementRefer.setCreatedBy(user.getUserCode());
		requirementRefer.setUpdatedBy(user.getUserCode());
		
		requirementReferDao.addRequirementRefer(requirementRefer);
		
		ReferContacts referContacts = new ReferContacts();
		
		if(jsonObj.has("firstName") && !isNullOrEmpty(jsonObj.getString("firstName"))){
			referContacts.setFirstName(jsonObj.getString("firstName"));
		}
		
		if(jsonObj.has("lastName") && !isNullOrEmpty(jsonObj.getString("lastName"))){
			referContacts.setLastName(jsonObj.getString("lastName"));
		}
		
		if(jsonObj.has("email") && !isNullOrEmpty(jsonObj.getString("email"))){
			referContacts.setContactUserEmail(jsonObj.getString("email"));
		}
		
		if(jsonObj.has("phone") && !isNullOrEmpty(jsonObj.getString("phone"))){
			referContacts.setContactUserPhone(jsonObj.getString("phone"));
		}
		
		referContacts.setReferFromUser(user);
		String contactUUID = SourceLeadRandomCodeHelper.generateUUID();
		referContacts.setContactUserUUID(contactUUID);
		referContacts.setOrgLocationId(orgLocations);
		referContacts.setCreatedDate(new Date());
		referContacts.setUpdatedDate(new Date());
		referContacts.setCreatedBy(user.getUserCode());
		referContacts.setUpdatedBy(user.getUserCode());
		
		referContactsDao.addReferContacts(referContacts);
	}

}
