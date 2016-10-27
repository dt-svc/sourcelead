package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.User;

public interface RequirementInterviewService {

	void saveRequirementInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception;

	RequirementInterview getRequirementInterviewById(String requirementInterviewId);

	void saveRequirementInterview(RequirementInterview requirementInterview) throws Exception;;
	

}
