package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.Timezone;

public interface RequirementInterviewHistoryDao {
	
	RequirementInterviewHistory storeRequirementInterviewHistory(RequirementInterviewHistory requirementInterviewHistory)throws Exception;

	List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(
			Long submissionId, Long orgLocationsId) throws Exception;

	RequirementInterviewHistory getRequirementInterviewHistoryById(Long id);
	
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
	 * @param timezone 
	 * @return
	 * @throws Exception
	 * To get dashboard interviews
	 */
	List<RequirementInterviewHistory> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)throws Exception;
	
	Integer getRequirementInternalAndIsClientInterviewsCountBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
}
