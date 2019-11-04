package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateGlobal;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;

public interface RequirementInterviewService {

	void saveRequirementInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception;

	void updateRescheduleInterview(JSONObject jsonObj, User user, OrgLocations orgLocations, Org loginOrg) throws Exception;
	
	void acceptProposeTimeBytProposeTimeId(String proposeDate,String proposeTime, User user, OrgLocations orgLocations, Org loginOrg ,Long proposeTimeId) throws Exception;
	
	List<RequirementInterview> getRequirementInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, OrgLocations orgLocations) throws Exception;
	
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

	RequirementInterview saveRequirementInterview(RequirementInterview requirementInterview) throws Exception;

	List<RequirementInterview> getAllInterviewAssignedToLoginUser(Long orgLocationsId, String dateRange, String currentTime, String userType, Long userId, String userCode)throws Exception; 

	List<RequirementInterview> getCustomerInterviewsBySubmissionIdAndOrgLocationsId(Long submissionId, Long id, Long requirementId) throws Exception;

	void sendEmailToVendors(CandidateGlobal candidate, RequirementSubmission requirementSubmission, User loginUser,Org loginOrg,RequirementInterview requirementInterview,OrgLocations orgLocations) throws Exception;

	void sendEmailToApplicants(CandidateGlobal candidate,OrgLocations loginOrgLocations, RequirementSubmission requirementSubmission, User loginUser,
			Org loginOrg, RequirementInterview requirementInterview) throws Exception;
	
	void sendEmailToInterviewrs(CandidateGlobal candidate, User interviewerUser,RequirementSubmission requirementSubmission, User loginUser,Org org,RequirementInterview requirementInterview,OrgLocations orgLocations)	throws Exception;
	
	Integer getInterviewCountByCandidateId(Long candidateId,String submissionStatus,Date startDate,Date endDate) throws Exception;


	List<RequirementInterview> getRequirementInterviewsBySubmissionAndOrgLocId(List<Long> submissionsIds, Long locationId, Date startUTCDate,
			Date endUTCDate);

	void applicantInterviewAcceptmailToInterviewInitiator(String requirementInterviewId) throws Exception;
	
	void applicantInterviewProposeTimeEmailToInterviewInitiator(String requirementInterviewId, OrgLocations orgLocations, Org loginOrg, User user) throws Exception;

	RequirementInterview getinterviewBySubmissionId(Long submissionId);
	
	RequirementInterview getLatestInterviewBySubmissionId(Long submissionId);
	
	List<Date> getInterviewDatesForCalendarEvent(Long userId,Long orgLocationId) throws Exception;
	
	List<Date> getInterviewDatesForInterviewrsInterviewSlots(List<Long> interivewerIds,Long orgLocationId) throws Exception;
	
	List<Date> getInterviewDatesForCandidateInterviewSlots(List<Long> applicantSubmissionIds) throws Exception;
	
	
	//List<RequirementInterview> getAllInterviewListByCurrentDate(String currentTime, String triggerTime);
	

}
