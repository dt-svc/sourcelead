package com.bis.sourcelead.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ApplicantsJoinedReportDTO;
import com.bis.sourcelead.dto.AssociateJoineReportDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
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
public interface RequirementSubmissionDao {
	
	RequirementSubmission getRequirementSubmissionById(Long submissionId)throws Exception;
	
	RequirementSubmission getClientRequierementSubmission(Long submissionId)throws Exception;
	
	List<Object[]> getAllShortListCandidatesByOrgLocationsIdAndRequirementId(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientInterview,User user,String loginUserRole) throws Exception;
	
	List<RequirementSubmission>  getAllSubmittedAndShortListAndInterviewededCandidates(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer,User user,String loginUserRole,String isClientRequirement) throws Exception;
	
	RequirementSubmission storeRequirementSubmission(RequirementSubmission requirementSubmission)throws Exception;

	List<RequirementSubmission> getSubmissionsByRequirementId(Long requirementId, User user, OrgLocations loginOrgLocation,String loginUserRole) throws Exception;

	List<RequirementSubmission> getRequirementPerCandidate(Long candudateId)throws Exception;
	
	List<RequirementSubmission> getAllSubmissionListByRequirementIdAndSubmissionStage(OrgLocations loginOrgLocation, Long requirementId,String submissionStatus,User user,String loginUserRole) throws Exception;

	Integer getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String submissionStatus, String dateRange,String userType,User user, Date startUTCDate, Date endUTCDate);
	
	Integer getRequirementSubmissionsCountByOrgLocationsIdAndRequirementId(OrgLocations orgLocations,Long requirementId,User user,String loginUserRole) throws Exception;
	
	List<RequirementSubmission> getAllShortLitstedCandidatesForClientSubmissionByRequirementId(OrgLocations loginOrgLocation,Requirement requirement,User user,String loginUserRole) throws Exception;
	
	RequirementSubmission getRequirementSubmissionByIdAndSubmissionStatus(Long submissionId,String submissionStatus)throws Exception;
	
	List<RequirementSubmission>   getRequirementHistoryListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	List<RequirementSubmission>   getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementId(Long candidateId, Long orgLocationId,Long requirementId) throws Exception;

	public Integer [] getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId,String dateRange,String userType,User user, Date startUTCDate, Date endUTCDate) throws Exception;

	
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
	
	Integer getAllSubmissionCountByRequirementIdAndSubmissionStage(Long orgLocationsId, Long requirementId,String submissionStatus);
	
	/**
	 * @author Prasad Salina
	 * @param requirementId
	 * @param user
	 * @param loginOrgLocation
	 * @param subQuery
	 * @return
	 */
	RequirementSubmissionsCountDTO getAllPipeLineCountsByRequirementIdAndSubmissionStage(Long requirementId, User user, OrgLocations loginOrgLocation, String subQuery) throws Exception;
	
	/**
	 * @author Prasad Salina
	 * @param requirementId
	 * @param user
	 * @param loginOrgLocation
	 * @param candidateVerificationConfig 
	 * @return
	 * @throws Exception
	 */
	Integer duplicateCandidateCountsByRequirementId(Long requirementId, User user,OrgLocations loginOrgLocation, CandidateVerificationConfig candidateVerificationConfig)throws Exception;
	
	RequirementSubmission getSubmissionsByVendorSubmissionIdAndvendorOrgLocationId(Long vendorSubmissionId, Long vendororgLocationsId);
	
	public Integer getApplicantCountByrequirementIdandAssignedUsercode(Long requirementId, String userCode) throws Exception;
 
	RequirementSubmission getVendorSubmissionByClientSubmissionIdAndCLientLocationId(Long clientSubmissionId,Long clientOrgLocationId);


	RequirementRateSubmissionNegotiation getRequirementRateBySubmissionById(Long submissionId);

	List<RequirementSubmission> getSubmissionsByRequirementIdandStatus(Long requirementId, User user,
			OrgLocations loginOrgLocation, String submissionType,String loginUserRole)throws Exception;
	
	RequirementSubmission getClientSubmissionsBySubmissionIdAndOrgLocationId(Long submissionId, Long orgLocationId);

	List<RequirementSubmission> duplicateCandidateCountList(Long requirementId, User user, OrgLocations loginOrgLocation) throws Exception;
	
	List<RequirementSubmission> getApplicantsByStatus(Long orgLocationsId, User user,String status);
	
	List<Long> getSubmittedCandidateIdsByRequirementId(Long requirementId) throws Exception;

	Integer getRequirementPerCandidateCount(Long candudateId,Long loginOrgLocation)throws Exception;

	Integer getNumberOfApplicantsForRequirement(Long orgLocationId, Long requirementId)throws Exception;
	
	RequirementSubmission getRequirementSubmissionByRequirementIdAndCandidateId(Long requirementId,Long candidateId) throws Exception;
	
	RequirementSubmission validateRequirementByRecruiterCandidateEmail(String email, Long requirementId, Long recruiterUserId) throws Exception;
	
	Integer getRecruiterSubmissionsCountByRecruiterIdAndRequirementId(Long requirementId,Long userId) throws Exception;
	
	Integer getProfessionalJobAppliedCount(Long candidateId,Date startDate,Date endDate) throws Exception;
	
	Integer getProfessionalJobAppliedCountByappliedDate(Long candidateId,Date appliedDate) throws Exception;
	
	Integer getProfessionalJobSubmisssionsCountBySubmissionType(Long candidateId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission> getProfessionalJobAppliedCountList(Long candidateId,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission> getProfessionalJobSubmisssionsCountListBySubmissionType(Long candidateId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	Integer getReferByMeJobAppliedCount(Long userId,Date startDate,Date endDate) throws Exception;
	
	Integer getReferByMeProfessionalJobSubmisssionsCountBySubmissionType(Long userId,String submissionStatus,Date startDate,Date endDate) throws Exception;
	
	List<RequirementSubmission> getReferByMeProfessionalJobSubmisssionsCountListBySubmissionType(Long userId,String submissionStatus,Date startDate,Date endDate) throws Exception;

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

	Integer getRequirementHistoryCountByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	boolean isCandidateSubmittedForRequirementByCandidateIdAndRequirementId(Long candidateId,Long requirementId) throws Exception;

	List<Long> getcandidateListBySubmissionId(List<Long> submissionsIds, Long orgLocationId);

	List<Long> getSubmissionIdByCandidateId(List<Long> candidateIds, Long orgLocationId);
	
	List<RequirementSubmissionsView> getOfferAcceptedCandidatesListByOrgLocationId(Long orgLocationId);
	
	List<RequirementSubmission> getAllSubmissionsfromVendorsBasedOnLocationIdAndUserId(User user,OrgLocations orgLocations, String status, Requirement requirement) throws Exception;
	
//	Long getCountOfAllApplicantsByCreatedUserAndOrglocationId(Long orgLocationId, User user,String stage,String timePeriod) throws Exception ;
	
	List<RequirementSubmission> getApplicantCountSubmittedByVendor(Long orgLocationId, Long orgRelationId);
	List<RequirementSubmission> getApplicantRequirmentCountSubmittedByVendor(Long orgLocationId,Long requirementId,Long vendorLocationID);

	Long getCountOfAllApplicantsByCreatedUserAndOrglocationIdByStage(Long orgLocationId, User user,String stage,String timePeriod) throws Exception ;

	List<RequirementSubmission> getVendorSpocSubmissions(String userCode, Long orgLocationId, Date startDateUTC, Date endDateUTC);
	
	List<RequirementSubmission> getSubmissionsForSOWByRequirementId(Long requirementId, String sowType, OrgLocations loginOrgLocation);

	List<RequirementSubmission> getApplicantCountSubmittedByVendorBasedOnStartAndEndDate(Long orgLocationId,
			Long orgRelationId, Date startUTCDate, Date endUTCDate);
	
	RequirementSubmission getRequirementSubmissionByCandidateIdAndRequirementId(Long candidateId,Long requirementId) throws Exception;

	List<RequirementSubmission> getSubmissionsForSOWByOrgLocationId(String sowType, OrgLocations loginOrgLocation);

	List<RequirementSubmissionHistory> getRequirementSubmissionsListByOrgLocationsIdAndSubmissionStatus(Long orgLocationsId, String submissionStatus, String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate);
	
	List<RequirementSubmission> getAllSubmittedAndShortListAndInterviewededCandidatesForClientOffer(OrgLocations orgLocations, Long requirementId, List<Long> existingApplicantsIds, String isClientOffer) throws Exception;
	
	RequirementSubmission getClientSubmissionsBySourceSubmissionIdAndVendorLocationId(Long sourceSubmissionId, Long vendorLocationId) throws Exception;
	
	ResponseList<RequirementSubmission> loadSubmissionsListByRequirementId(RequirementSubmissionsDTO requirementSubmissionsDTO, OrgLocations orgLocation, CandidateVerificationConfig candidateVerificationConfig) throws Exception;
	
	List<RequirementSubmission> getSubmmisionsForIsClientJob(List<Long> submissionIds) throws Exception;
	
	RequirementSubmission getRequirementVendorOrCrowdsourcingSubmissionByRequirementIdAndCandidateId(Long requirementId,Long candidateId) throws Exception;

	ResponseList<RequirementSubmissionHistory> getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO, Long orgLocationsId, String submissionStatus, String dateRange, String userType, User user, Date startUTCDate, Date endUTCDate,OrgLocations orgLocations) throws Exception;
	
	public ResponseList<RequirementSubmission> getRequirementSubmissionsListWithJobIdByOrgLocationsIdAndSubmissionStatus(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO,Long requirementId, String submissionStatus, String dateRange, String userType, User user,OrgLocations orgLocations) throws Exception;

	boolean getRequirementSubmissionByCandidateIdAndJobId(Long candidateId,Long requirementId) throws Exception;
	
	List<RequirementSubmission> getAllSubmissionsNotInTemplateFileds() throws Exception;
	
	Integer getRequirementSubmissionCountByCandiateIdAndOrgLocationId(Long candidateId,Long orgLocationId) throws Exception;
	
	List<RequirementSubmissionsDTO> getCandidateSubmittedRequirementsListByCandidateId(Long candidateId,Long orgLocationId,String dateFormat) throws Exception;
	
	public Integer getRequirementCountBySubmissionStatusJoinedandAssignedUsercode(Long requirementId, String userCode,Date[] searchDates) throws Exception;

	RequirementIncentive getRequirementIncentiveByRequirementId(Long requirementId) throws Exception;
	
	List<ApplicantsJoinedReportDTO> getRequirementIncentiveByRequirementIdAndLocation(Long requirementId,String usercode,Long orgLocationId, Date[] searchDates) throws Exception;

	ResponseList<AssociateJoineReportDTO> getAssociateJoineReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception;

	ResponseList<AssociateJoineReportDTO> getAssociateTerminateReport(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception;
	
	ResponseList<AssociateJoineReportDTO> getAssociateJoinedGraph(AssociateJoineReportDTO requirementSubmissionHistoryDTO, Long orgLocationsId, Date startUTCDate, Date endUTCDate) throws Exception;

	List<RequirementSubmission> getCandidateForInvoiceDashBoard(OrgLocations orgLocations,User user);
	
	List<RequirementSubmission> getCandidateForInvoiceJobClosure(OrgLocations orgLocations,User user,Long orgRelationId);
	
	List<RequirementSubmission> getCandidateInvoiceJobClosureDashboard(OrgLocations orgLocations,Long orgRelationId,Long summissionId,Long candidateId);
	
	List<RequirementSubmission> getCandidateForInvoiceJobClosureEdit(OrgLocations orgLocations,User user,Long orgRelationId,List<Long> selectedAssociates);

    List<RequirementSubmission> getCandidateForBillJobClosure(OrgLocations orgLocations, User user,Long orgRelationId);
    
    List<RequirementSubmission> getCandidateForBillJobClosureEdit(OrgLocations orgLocations, User user,Long orgRelationId, List<Long> selectedAssociates);

    long getRequirementClientCountByRequirementIdAndUserIdAndDates(BigInteger requirementID, BigInteger userId, Date[] searchDates);

	long getRequirementCountByUserIdAndDates(BigInteger userId, Date[] searchDates);

	long getOfferCountByUserIdAndDates(String userCode, Date[] searchDates);

	long getJoinedCountByUserIdAndDates(String userCode, Date[] searchDates);

	List<RequirementSubmission> getSubmissionListByOrgLocationAndSubmissionStatusAndJoiningDate(Long orgLocationId, String submissionStatus, Date joiningDate);
}
