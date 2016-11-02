package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.RequirementInterview;

public interface RequirementInterviewDao {
	
	RequirementInterview storeRequirementInterview(RequirementInterview requirementInterview)throws Exception;
	
	List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;

	RequirementInterview getRequirementInterviewById(String requirementInterviewId);
	
	List<RequirementInterview> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)throws Exception; 

	List<RequirementInterview> getCustomerInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId,
			Long orgLocationsId) throws Exception;

	List<RequirementInterview> getAllInterviewListByCurrentDate(String currentTime, String triggerTime);

}
