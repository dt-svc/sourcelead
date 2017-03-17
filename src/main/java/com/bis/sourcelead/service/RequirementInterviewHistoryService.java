package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewHistory;

public interface RequirementInterviewHistoryService {

	
	RequirementInterviewHistory storeRequirementInterviewHistory(RequirementInterviewHistory requirementInterviewHistory)throws Exception;
	
	RequirementInterviewHistory getRequirementInterviewHistoryById(Long Id);
	
	List<RequirementInterviewHistory> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, OrgLocations orgLocationsId) throws Exception;
	
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
	 * @param endUTCDate 
	 * @param startUTCDate 
	 * @return
	 * @throws Exception
	 * To get dashboard interviews
	 */
	List<RequirementInterviewHistory> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode, Date startUTCDate, Date endUTCDate)throws Exception;
	
	List<RequirementInterviewHistory> getAllProfessinalInterviews(String dateRange,Long candidateId)throws Exception;
	
	Integer getRequirementInternalAndIsClientInterviewsCountBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	
	List<RequirementInterviewHistory> getAllRecruiterSubmittedInterviews(String dateRange, Long userId)throws Exception;
	
	List<RequirementInterviewHistory> getCrowdSorurcingInterviews(Long submissionId,OrgLocations orgLocations) throws Exception;

}
