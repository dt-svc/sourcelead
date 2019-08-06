package com.bis.sourcelead.service;

import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONObject;

import com.bis.sourcelead.dto.ApplicantAuthorizationDTO;
import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.AssociateJoineReportDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementIncentive;
import com.bis.sourcelead.entity.RequirementRateSubmissionNegotiation;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.RequirementSubmissionsView;
import com.bis.sourcelead.entity.User;

/**
 * 
 * @author Laxman
 * @date 20th October 2016
 *
 */


public interface RequirementSubmissionService {
	
	RequirementSubmission getRequirementSubmissionById(Long submissionId)throws Exception;
	
	RequirementSubmission getClientRequierementSubmission(Long submissionId)throws Exception;
	
	List<RequirementSubmissionsDTO> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientInterview,User user,String loginUserRole) throws Exception;
	
	List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidates(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer,User user,String loginUserRole,String isClientRequirement) throws Exception;
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;

	List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId, User user, OrgLocations loginOrgLocation,String loginUserRole) throws Exception;

	RequirementSubmission saveRequirementSubmission(JSONObject jsonObj,User user,OrgLocations orgLocations,String jsonStringvalues,String userRole) throws Exception;
	
	List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(OrgLocations loginOrgLocation, Long requirementId,String submissionStatus,User user,String loginUserRole) throws Exception;
	
	void storeClientSubmission(Long requirementId, User user, OrgLocations loginOrgLocation,String submissionIds, String selectedShortlistsRate, Org org) throws Exception;
	
	public Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus,String dateRange,String userType,User user, Date startUTCDate, Date endUTCDate);
	
	public Integer [] getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String dateRange,String userType,User user, Date startUTCDate, Date endUTCDate) throws Exception;
	

	//to get candidates for submit to client
	List<RequirementSubmission> getAllShortLitstedCandidatesForClientSubmissionByRequirementId(OrgLocations loginOrgLocation,Requirement requirement,User user,String loginUserRole) throws Exception;
	
	
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
	
	/**
	 * @author Prasad Salina
	 * @param requirementId
	 * @param user
	 * @param loginOrgLocation
	 * @param subQuery
	 * @return
	 * To get piplenine counts
	 */
	RequirementSubmissionsCountDTO getAllPipeLineCountsByRequirementIdAndSubmissionStage(Long requirementId, User user, OrgLocations loginOrgLocation, String submissionStatus)  throws Exception;
	
	Map<String, Integer> getAllPipeLineWorlflowCountsByRequirementIdAndSubmissionStage(Long requirementId, User user, OrgLocations loginOrgLocation, String submissionStatus, String[] workflowStages)  throws Exception;
	
	Integer duplicateCandidateCountsByRequirementId(Long requirementId, User user,OrgLocations loginOrgLocation)throws Exception;
	
	Integer getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(OrgLocations orgLocations,Long requirementId,User user,String loginUserRole) throws Exception;
	
	Integer getNumberOfApplicantsForRequirement(Long orgLocationId,Long requirementId) throws Exception;
	
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode) throws Exception;
	
	/*public void applicantReceiveMailAboutRequirement(Requirement requirement, User loginUser, Candidate candidate,
			OrgLocations orgLocations) throws Exception; */
	
	public void applicantAcceptMailAboutRequirement(Requirement requirement, User loginUser,RequirementSubmission requirementSubmission,
			OrgLocations orgLocations) throws Exception;
	
	public void applicantRejectMailAboutRequirement(Requirement requirement, User loginUser, Candidate candidate,
			OrgLocations orgLocations, String rejectreason, StringBuilder tempHTML, RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation, Long submissionId) throws Exception;
	

	RequirementSubmission acceptRejectCandidate(ApplicantAuthorizationDTO applicantAuthorizationDTO, User user, RequirementSubmission requirementSubmission, String decision,Org loginOrg,OrgLocations loginOrgLocation) throws Exception;


	public void applicantNegotiateEmail(Requirement requirement, User initiatorUser, Candidate candidate,
			OrgLocations orgLocations, RequirementRateSubmissionNegotiation negotiation, StringBuilder tempHTML) throws Exception;

	RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId);

	List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long valueOf, User user,
			OrgLocations loginOrgLocation, String submissionType,String loginUserRole)throws Exception;
	
	RequirementSubmission getClientSubmissionsBySubmissionIdAndOrgLocationId(Long submissionId, Long orgLocationId);

	void userNegotiateEmail(Requirement requirement, User user, Candidate candidate, OrgLocations orgLocations,
			RequirementRateSubmissionNegotiation neogtation, StringBuilder tempHTML) throws Exception;


	void sendEmailtoClient(Org loginOrg, User users, User pastUser, RequirementSubmission requirementSubmission, Candidate candidate,
			StringBuilder tempHTML, OrgLocations loginOrgLocation)throws Exception;


	//void storeClientSubmissionWithEmailPreview(Long requirementId, User user, OrgLocations orgLocations,String submissionsList, Org org, JSONObject jsonObj, Map files)throws Exception;
	

	void storeClientSubmissionWithEmailPreview(Long requirementId, User user, OrgLocations orgLocations,String submissionsList, Org org, JSONObject jsonObj, Map files, String selectedShortlistsRate, String selectedShortlistsRatePer)throws Exception;

	List<RequirementSubmission> duplicateCandidateCountList(Long requirementId, User user,
			OrgLocations loginOrgLocation)throws Exception;

	
	List<RequirementSubmission> getApplicantsByStatus(Long orgLocationsId, User user,String status) throws Exception;
	
	public void sendEmailtoRequirementManager(Org loginOrg, User users,RequirementSubmission requirementSubmission,
			 OrgLocations loginOrgLocation) throws Exception;
	
	public void sendEmailtoApplicant(Org loginOrg, User users,RequirementSubmission requirementSubmission,
			 OrgLocations loginOrgLocation) throws Exception;
		

	void userAcceptRateNegotiation(Requirement requirement, User user, Candidate candidate, OrgLocations orgLocations, RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation, StringBuilder tempHtml) throws Exception;

	void sendEmailtoVendor(Org loginOrg, User users, User pastUser, RequirementSubmission requirementSubmission, Candidate candidate,
			StringBuilder tempHTML, OrgLocations orgLocations) throws Exception;

	void userRejectRateNegotiation(Requirement requirement, User user, Candidate candidate, OrgLocations orgLocations,
			RequirementRateSubmissionNegotiation requirementRateSubmissionNegotiation, String rejectreason, StringBuilder tempHTML) throws Exception;

	void clientOrVendorAcceptOrRejectRateNegotiation(Org loginOrg, User users, User pastUser,
			RequirementSubmission requirementSubmission, Candidate candidate, StringBuilder tempHTML,
			OrgLocations loginOrgLocation,String type, String status) throws Exception;
	
	List<Long> getSubmittedCandidateIdsByRequirementId(Long requirementId) throws Exception;
	
	Integer getRequirementPerCandidateCount(Long candudateId,Long orgLocationId) throws Exception;
	
	Integer getRecruiterSubmissionsCountByRecruiterIdAndRequirementId(Long requirementId,Long userId) throws Exception;
	
	RequirementSubmission saveRequirementSubmissionFromCrowdSourcing(JSONObject jsonObj,User user,String jsonStringvalues,OrgLocations orgLocations) throws Exception;

	void applicantAcceptMailAboutRateNegotiation(Requirement requirement, User initiatorUser, RequirementSubmission requirementSubmission,
			OrgLocations orgLocations,StringBuilder tempHTML) throws Exception;
	
	RequirementSubmission getRequirementSubmissionByRequirementIdAndCandidateId(Long requirementId,Long candidateId) throws Exception;

	void sendRejectionMailToVendorContact(User user, String reason, Long submissionId, OrgLocations orgLocations) throws Exception;

	RequirementSubmission validateRequirementByRecruiterCandidateEmail(String email, Long requirementId, Long recruiterUserId) throws Exception;

	void shortListedMailToVendorFromClient(Requirement requirement, String submissionsList, OrgLocations orgLocations,
			User user) throws Exception;
	
	Integer getProfessionalJobAppliedCount(Long candidateId,Date startDate,Date endDate) throws Exception;
	
	Integer getProfessionalJobAppliedCountByappliedDate(Long candidateId,Date appliedDate) throws Exception;
	
	Integer getProfessionalJobSubmisssionsCountBySubmissionType(Long candidateId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission> getProfessionalJobAppliedCountList(Long candidateId,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission> getProfessionalJobSubmisssionsCountListBySubmissionType(Long candidateId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	Integer getReferByMeJobAppliedCount(Long userId,Date startDate,Date endDate) throws Exception;
	
	Integer getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(Long userId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission>  getReferByMeProfessionalJobSubmisssionsCountListBySubmissionType(Long userId,String submissionStatus,Date startDate,Date endDate) throws Exception;

	Integer getReferByMeJobAppliedCountByappliedDate(Long userId,Date appliedDate) throws Exception;
	
	Integer getRecruiterSubmittedJobCountByappliedDate(Long userId,Date appliedDate) throws Exception;
	
	Integer getRequiterReferJobAppliedCount(Long userId,Date startDate,Date endDate) throws Exception;
	
	Integer getRecruiterSubmittedJobCountByStartDateAndEndDate(Long userId,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission> getAllVendorsIdbyorgLocationId(OrgLocations orgLocation, Date startDateUTC, Date endDateUTC);


	Integer getRecruiterSubmittedCandidatesCountByStage(Long userId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	Integer getReferByMeJobAppliedCandidateCount(Long userId,Date startDate,Date endDate) throws Exception;
	
	Integer getRecruiterSubmittedCandidateCountByStartDateAndEndDate(Long userId,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission> getRecruiterSubmittedCandidatesCountListByStage(Long userId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	RequirementSubmission getRequirementSubmissionByRequirementIdAndUserId(Long requirementId,Long userId) throws Exception;
	
	List<RequirementSubmission>  getRecruiterSubmittedCandidatesList(Long recruiterUserId,Long requirementId) throws Exception;
	
	List<RequirementSubmission>  getRecruiterSubmittedCandidatesListForReportsByLocationIdAndStatus(Long loginOrgLocationId,String client,String filterDate,Date startDateUTC,Date endDateUTC) throws Exception;
	
	RequirementSubmission validateRecruiterSubmittedCandidateEmail(String email, Long requirementId, Long userId) throws Exception;

	List<RequirementSubmission> getApplicantsListByOrgLocationAndUserId(OrgLocations loginOrgLocations,
			List<Long> applicantsList, Date startUTCDate, Date endUTCDate);
	
	Integer  getConsolidatedReportsForMailingForIndividualUserForToday(User user, OrgLocations orgLocations,String status,Requirement requirement) throws Exception;
 
	List<RequirementSubmission>  getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(User user, OrgLocations orgLocations,String status,Requirement requirement) throws Exception;
	
	Integer getRequirementHistoryCountByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	boolean isCandidateSubmittedForRequirementByCandidateIdAndRequirementId(Long candidateId,Long requirementId) throws Exception;

	List<Long> getcandidateListBySubmissionId(List<Long> submissionsIds, Long orgLocationId);

	List<Long> getSubmissionIdByCandidateId(List<Long> candidateIds, Long orgLocationId);
	
	List<RequirementSubmissionsView> getOfferAcceptedCandidatesListByOrgLocationId(Long orgLocationId);
	
//	Long getCountOfAllApplicantsByCreatedUserAndOrglocationId(Long orgLocationId, User user,String stage,String timePeriod) throws Exception ;

	List<RequirementSubmission> getApplicantCountSubmittedByVendor(Long orgLocationId, Long orgRelationId);
	
	List<RequirementSubmission> getApplicantRequirmentCountSubmittedByVendor(Long orgLocationId,Long requirementId,Long vendorLocationID);

	Long getCountOfAllApplicantsByCreatedUserAndOrglocationIdByStage(Long orgLocationId, User user,String stage,String timePeriod) throws Exception ;

	List<RequirementSubmission> getVendorSpocSubmissions(String userCode, Long orgLocationId, Date startDateUTC, Date endDateUTC);
	
	List<RequirementSubmission> getSubmissionsForSOWByRequirementId(Long requirementId, String sowType, OrgLocations loginOrgLocation);
	
	List<RequirementSubmission> getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(Long orgLocationId, Long orgRelationId, Date startUTCDate, Date endUTCDate);
	
	RequirementSubmission getRequirementSubmissionByCandidateIdAndRequirementId(Long candidateId,Long requirementId) throws Exception;
	
	public void sendEmailtoAssignedMemberApplicantAuthorization(Org loginOrg, User users,RequirementSubmission requirementSubmission,OrgLocations loginOrgLocation) throws Exception;

	List<RequirementSubmission> getSubmissionsForSOWByOrgLocationId(String sowType, OrgLocations loginOrgLocation);

	List<RequirementSubmissionHistory> getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(Long id, String string, String tab, String userType, User user, Date startUTCDate, Date endUTCDate);
	
	RequirementSubmission editRequirementSubmission(JSONObject jsonObj,User user,OrgLocations orgLocations,String jsonStringvalues) throws Exception;
	
	List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidatesForClientOffer(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer) throws Exception;
	
	RequirementSubmission getClientSubmissionsBySourceSubmissionIdAndVendorLocationId(Long sourceSubmissionId, Long vendorLocationId) throws Exception;
	
	ResponseList<RequirementSubmission> loadSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, OrgLocations orgLocation)throws Exception;
	
	ResponseList<RequirementSubmission> loadWorkflowSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, OrgLocations orgLocation)throws Exception;
	
	List<RequirementSubmission> getSubmmisionsForIsClientJob(List<Long> submissionIds) throws Exception;
	
	RequirementSubmission getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(Long requirementId,Long candidateId) throws Exception;
	
	boolean getRequirementSubmissionByCandidateIdAndJobId(Long candidateId,Long requirementId) throws Exception;
	
	File getPdfFile(ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations loginOrgLocations);

	Sheet getExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations loginOrgLocations, String submissionStatus) throws Exception;
	
	List<RequirementSubmission> getAllSubmissionsNotInTemplateFileds() throws Exception;
	
	Integer getRequirementSubmissionCountByCandiateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	List<RequirementSubmissionsDTO> getCandidateSubmittedRequirementsListByCandidateId(Long candidateId,Long orgLocationId,String dateFormat) throws Exception;
	
	Sheet getApplicaantJoinedListExcelFile(Sheet createExcelsheet, Workbook createExcelworkbook, ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations loginOrgLocations, String submissionStatus) throws Exception;

	File getApplicantsJoinedListPdfFile(ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist, OrgLocations loginOrgLocations)throws Exception;

	public Integer getRequirementCountBySubmissionStatusJoinedandAssignedUsercode(Long requirementId, String userCode,Date[] searchDates) throws Exception;
	
	RequirementIncentive getRequirementIncentiveByRequirementId(Long requirementId) throws Exception;
	
	List<ApplicantsJoinedReportDTO> getRequirementIncentiveByRequirementIdAndLocation(Long requirementId,String userCode,Long orgLocationId, Date[] searchDates) throws Exception;


	ResponseList<AssociateJoineReportDTO> getAssociateJoineReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception;

	ResponseList<AssociateJoineReportDTO> getAssociateTerminateReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception;

	ResponseList<AssociateJoineReportDTO> getAssociateJoinedGraph(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception;

	List<RequirementSubmissionsDTO> getCandidateForInvoiceDashBoard(OrgLocations orgLocations,User user);
	
	List<RequirementSubmissionsDTO> getCandidateForInvoiceJobClosure(OrgLocations orgLocations,User user,Long orgRelationId,String contractType,String selectedAssociates);
	
	public List<RequirementSubmissionsDTO> getCandidateForBillsJobClosure(OrgLocations orgLocations, User user,Long orgRelationId,String contractType,String selectedAssociates);

	List<RequirementSubmission> getSubmissionListByOrgLocationAndSubmissionStatusAndJoiningDate(Long orgLocationId, String submissionStatus, Date joiningDate) throws Exception;
	
}