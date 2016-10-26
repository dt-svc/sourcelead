package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.User;

public interface RequirementInterviewService {

	void saveRequirementInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception;
	
	List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	

}
