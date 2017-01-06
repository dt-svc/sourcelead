package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateEducation;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.User;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */


public interface RequirementSubmissionService {
	
	RequirementSubmission getRequirementSubmissionById(Long submissionId)throws Exception;
	
	List<RequirementSubmission> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientInterview,User user,String loginUserRole) throws Exception;
	
	List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer,User user,String loginUserRole) throws Exception;
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;

	List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId, User user, OrgLocations loginOrgLocation) throws Exception;

	RequirementSubmission saveRequirementSubmission(JSONObject jsonObj,User user,OrgLocations orgLocations,String jsonStringvalues,MultipartFile multiFile) throws Exception;
	
	List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(OrgLocations loginOrgLocation, Long requirementId,String submissionStatus,User user,String loginUserRole) throws Exception;
	
	void storeClientSubmission(Long requirementId, User user, OrgLocations loginOrgLocation,String submissionIds, String selectedShortlistsRate, Org org) throws Exception;
	
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus,String dateRange,String userType,User user);
	
	//to get candidates for submit to client
	List<RequirementSubmission> getAllShortLitstedCandidatesForClientSubmissionByRequirementId(Long id,Long requirementId) throws Exception;
	
	
	List<RequirementSubmission> getRequirementPerCandidate(Long candudateId) throws Exception;

	RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,String submissionStatus)throws Exception;
	
	
	RequirementSubmission getVendorSubmissionByClientSubmissionIdAndCLientLocationId(Long sourceSubmissionId, Long vendorLocationID);
	
	List<RequirementSubmission>   getRequirementHistoryListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	List<RequirementSubmission>   getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementId(Long candidateId, Long orgLocationId,Long requirementId) throws Exception;
	
	/**
	 * @author Prasad Salina
	 * @param email
	 * @param requirementId
	 * @param orgLocationsId
	 * @return
	 */
	RequirementSubmission validateRequirementCandidateEmail(String email, Long requirementId, Long orgLocationsId) throws Exception;
	
	RequirementSubmission getSubmissionsBySourceSubmissionId(Long sourceSubmissionId, Long orgLocationsId);
	
	/**
	 * @author Prasad Salina
	 * @param submissionIds
	 * To get candidate resume ids of submissions
	 */
	List<Long> getResumeIdsByRequirementSubmissionId(List<Long> submissionIds) throws Exception;
	
	
	RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long sourceSubmissionId, Long vendorLocationID);
	
	Integer getAllSubmissionCountByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId, String submissionStatus);
	
	Integer getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(OrgLocations orgLocations,Long requirementId,User user,String loginUserRole) throws Exception;
	
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode) throws Exception;
	
	/*public void applicantReceiveMailAboutRequirement(Requirement requirement, User loginUser, Candidate candidate,
			OrgLocations orgLocations) throws Exception; */
	
	public void applicantAcceptMailAboutRequirement(Requirement requirement, User loginUser, Candidate candidate,
			OrgLocations orgLocations) throws Exception;
	
	public void applicantRejectMailAboutRequirement(Requirement requirement, User loginUser, Candidate candidate,
			OrgLocations orgLocations, String rejectreason) throws Exception;
	

	RequirementSubmission acceptRejectCandidate(JSONObject jsonObj, User user, RequirementSubmission requirementSubmission, String decision, MultipartFile multiFile,Org loginOrg,OrgLocations loginOrgLocation) throws Exception;


	public void applicantNegotiateEmail(Requirement requirement, User initiatorUser, Candidate candidate,
			OrgLocations orgLocations, RequirementRateSubmissionNegotiation negotiation, StringBuilder tempHTML) throws Exception;

	RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId);

	List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long valueOf, User user,
			OrgLocations loginOrgLocation, String submissionType)throws Exception;
	
	RequirementSubmission getClientSubmissionsBySubmissionIdAndOrgLocationId(Long submissionId, Long orgLocationId);

	void userNegotiateEmail(Requirement requirement, User user, Candidate candidate, OrgLocations orgLocations,
			RequirementRateSubmissionNegotiation neogtation, StringBuilder tempHTML) throws Exception;


	void sendEmailtoClient(Org loginOrg, User users, User pastUser, Requirement requirement, Candidate candidate,
			StringBuilder tempHTML, OrgLocations loginOrgLocation)throws Exception;


	//void storeClientSubmissionWithEmailPreview(Long requirementId, User user, OrgLocations orgLocations,String submissionsList, Org org, JSONObject jsonObj, Map files)throws Exception;
	

	void storeClientSubmissionWithEmailPreview(Long requirementId, User user, OrgLocations orgLocations,String submissionsList, Org org, JSONObject jsonObj, Map files, String selectedShortlistsRate)throws Exception;

	List<RequirementSubmission> duplicateCandidateCountList(Long requirementId, User user,
			OrgLocations loginOrgLocation)throws Exception;

	
	List<RequirementSubmission> getApplicantsByStatus(Long orgLocationsId, String status) throws Exception;
	
	public void sendEmailtoRequirementManager(Org loginOrg, User users,RequirementSubmission requirementSubmission,
			 OrgLocations loginOrgLocation) throws Exception;
	
	public void sendEmailtoApplicant(Org loginOrg, User users,RequirementSubmission requirementSubmission,
			 OrgLocations loginOrgLocation) throws Exception;
		

	void userAcceptRateNegotiation(Requirement requirement, User user, Candidate candidate, OrgLocations orgLocations, RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation) throws Exception;

	void sendEmailtoVendor(Org loginOrg, User users, User pastUser, Requirement requirement, Candidate candidate,
			StringBuilder tempHTML, OrgLocations orgLocations) throws Exception;

}
