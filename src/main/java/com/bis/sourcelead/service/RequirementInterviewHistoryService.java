package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementInterviewHistory;

public interface RequirementInterviewHistoryService {

	
	RequirementInterviewHistory storeRequirementInterviewHistory(RequirementInterviewHistory requirementInterviewHistory)throws Exception;
	
	RequirementInterviewHistory getRequirementInterviewHistoryById(Long Id);
	
	List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	List<RequirementInterviewHistory> getClientInterviewsHistoryBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	List<RequirementInterviewHistory> getAllInterviewListByCurrentDate(String currentTime, String triggerTime);
	
	/**
	 * @author Prasad Salina
	 * @param orgLocationsId
	 * @param dateRange
	 * @param currentTime
	 * @param userType
	 * @param userId
	 * @param userCode
	 * @return
	 * @throws Exception
	 * To get dashboard interviews
	 */
	List<RequirementInterviewHistory> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)throws Exception;

}
