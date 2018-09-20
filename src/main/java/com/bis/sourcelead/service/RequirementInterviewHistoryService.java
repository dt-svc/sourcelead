package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;

public interface RequirementInterviewHistoryService {

	
	RequirementInterviewHistory storeRequirementInterviewHistory(RequirementInterviewHistory requirementInterviewHistory)throws Exception;
	
	RequirementInterviewHistory getRequirementInterviewHistoryById(Long Id);
	
	RequirementInterviewHistory getRequirementInterviewHistoryByInterviewId(Long interviewId)throws Exception;
	
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
	 * @param user 
	 * @return
	 * @throws Exception
	 * To get dashboard interviews
	 */
	List<RequirementInterviewHistory> getAllInterviewAssignedToLoginUser(OrgLocations loginOrgLocations, String dateRange, String currentTime, String userType, Long userId, String userCode, Date startUTCDate, Date endUTCDate,Timezone timezone, User user)throws Exception;
	
	List<RequirementInterviewHistory> getAllProfessinalInterviews(String dateRange,Long candidateId,String currentTime,Date startUTCDate, Date endUTCDate,Timezone timezone)throws Exception;
	
	Integer getRequirementInternalAndIsClientInterviewsCountBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	
	List<RequirementInterviewHistory> getAllRecruiterSubmittedInterviews(String dateRange,Long userId,String currentTime,Date startUTCDate, Date endUTCDate,Timezone timezone)throws Exception;
	
	List<RequirementInterviewHistory> getCrowdSorurcingInterviews(Long submissionId,OrgLocations orgLocations) throws Exception;
	
	Long getCountOfAllInterviewAssignedToLoginUser(OrgLocations loginOrgLocations, String userType, Long userId, String userCode) throws Exception;
	
}
