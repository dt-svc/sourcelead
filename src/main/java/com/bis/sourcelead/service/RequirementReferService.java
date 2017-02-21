package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementRefer;
import com.bis.sourcelead.entity.User;

public interface RequirementReferService {
	
	RequirementRefer addRequirementRefer(RequirementRefer requirementRefer) throws Exception;
	
	public void saveReferCandidate(User user, OrgLocations orgLocations,JSONObject jsonObj) throws Exception;

}
