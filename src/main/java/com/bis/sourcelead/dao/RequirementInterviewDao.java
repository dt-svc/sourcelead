package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementInterview;

public interface RequirementInterviewDao {
	
	RequirementInterview storeRequirementInterview(RequirementInterview requirementInterview)throws Exception;
	
	List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;

	RequirementInterview getRequirementInterviewById(String requirementInterviewId);

}
