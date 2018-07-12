package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.RequirementInterview;

public interface RequirementInterviewDao {
	
	RequirementInterview storeRequirementInterview(RequirementInterview requirementInterview)throws Exception;
	
	List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;

	List<RequirementInterview> getRequirementInterviewsHistoryBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	/**
	 * @author Prasad Salina
	 * @param submissionId
	 * @param orgLocationsId
	 * @return
	 * @throws Exception
	 * To get both internal and Is Client job INterviews
	 */
	List<RequirementInterview> getRequirementInternalAndIsClientInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long orgLocationsId) throws Exception;
	
	RequirementInterview getRequirementInterviewById(String requirementInterviewId);
	
	/**
	 * @author Prasad Salina
	 * @param requirementInterviewId
	 * @param submissionId
	 * @param orgLocationsId
	 * @return
	 */
	RequirementInterview getRequirementInterviewByIdAndSubmissionIdAndOrgLocationsId(Long requirementInterviewId, Long submissionId, Long orgLocationsId);
	
	List<RequirementInterview> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)throws Exception; 

	List<RequirementInterview> getCustomerInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId,
			Long orgLocationsId, Long requirementId) throws Exception;
	
	Integer getInterviewCountByCandidateId(Long candidateId,String submissionStatus,Date startDate,Date endDate) throws Exception;

	List<RequirementInterview> getRequirementInterviewsBySubmissionAndOrgLocId(List<Long> submissionsList,
			Long locationId, Date startUTCDate, Date endUTCDate);

	RequirementInterview getinterviewBySubmissionId(Long submissionId);
	
	RequirementInterview getLatestInterviewBySubmissionId(Long submissionId);
	
	List<Date> getInterviewDatesForCalendarEvent(Long userId,Long orgLocationId) throws Exception;
	
	List<Date> getInterviewDatesForInterviewrsInterviewSlots(List<Long> interivewerIds,Long orgLocationId) throws Exception;
	
	List<Date> getInterviewDatesForCandidateInterviewSlots(List<Long> applicantSubmissionIds) throws Exception;

	//List<RequirementInterview> getAllInterviewListByCurrentDate(String currentTime, String triggerTime);

}
