package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;

public interface RequirementInterviewService {

	void saveRequirementInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception;

	
	List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	

	RequirementInterview getRequirementInterviewById(String requirementInterviewId);

	void saveRequirementInterview(RequirementInterview requirementInterview) throws Exception;


	List<RequirementInterview> getCustomerInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long id) throws Exception;


	void sendEmailToVendors(Candidate candidate, RequirementSubmission requirementSubmission, User loginUser,String startTime, String endTime, Org loginOrg, String dateOfInterview, String location,
			String interviewType) throws Exception;


	void sendEmailToApplicants(Candidate candidate, RequirementSubmission requirementSubmission, User loginUser,
			String startTime, String endTime, Org loginOrg, String dateOfInterview, String location,
			String interviewType, RequirementInterview requirementInterview) throws Exception;
	
	void sendEmailToInterviewrs(Candidate candidate, User interviewerUser,RequirementSubmission requirementSubmission, User loginUser, String startTime, String endTime,Org loginOrg,String dateOfInterview,String location, String interviewType, OrgLocations orgLocations)	throws Exception;
	

}
