package com.bis.sourcelead.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.CandidateConfigurationDTO;
import com.bis.sourcelead.dto.CandidateVerificationConfigDTO;
import com.bis.sourcelead.dto.OfferAcceptanceRateDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.RecruiterJobsReportView;
import com.bis.sourcelead.entity.RecruiterReportView;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementSubmissionHistoryService {

	/**
	 * updating the Requirement Submission Hostory
	 * @param requirementSubmission
	 * @param user
	 * @param requirementSubmissionHistory
	 * @return
	 * @throws Exception
	 */
	RequirementSubmissionHistory updateRequirementHistory(RequirementSubmission requirementSubmission, User user, RequirementSubmissionHistory requirementSubmissionHistory, String masterUserCode)throws Exception;

	List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType, Long submissionId)throws Exception;
	
	List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(Long candidateId, Long orgLocationId, Long requirementId,Long submissionId)throws Exception;
	
	RequirementSubmissionHistory  isInCoolingOffPeriodByEmailAndLocationsId(String email, OrgLocations orgLocations, String reqCoolingOffPeriod, Long requirementId) throws Exception;

	RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId);

	List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)throws Exception;
	
	Integer getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user, String submissionStage ,Date startDateUTC, Date endDateUTC,String isGlobalUser) throws Exception ;

	List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndSubId(Long orgLocationID, Long submissionId);
	
	ResponseList<RequirementSubmissionHistory> getAllSubmissionHistoryApplicantsByOwner(RequirementSubmissionHistoryDTO sumissionHistoryDTO,String userCode,String stage,Date[] searchDates,Long locationId)throws Exception;

	RequirementSubmissionHistory addRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)throws Exception;
	
	RecruiterReportView getSubmissionCountsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user, String submissionStage ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;
	
	List<RecruiterReportView> getSubmissionCountsByStageAndCreatedUserListAndOrglocationId(Long orgLocationId, List<String> userCodeList ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;

	public int updateSourceUserCodeForSubmissionHistory();

	public List<RecruiterJobsReportView> getSubmissionApplicantCountsForRecruiterJobreports(Long orgLocationId, User user,Date[] searchDates,Long requirementId) throws Exception;

	public ResponseList<OfferAcceptanceRateDTO> getOfferAcceptanceRateList(String userCode, long orgLocationId, String selectedClients, String filterType, String filteredDate);
	
	public ResponseList<OfferAcceptanceRateDTO> getOfferAcceptanceRateListForVendor(List<Long> parentRequirementIdList, long orgRelationId, long spocId, String filterType, String filteredDate);

	RequirementSubmissionHistory isInCoolingOffPeriodByLocationID(
			CandidateVerificationConfig candidateVerificationConfig,
			CandidateConfigurationDTO candidateConfigurationDTO, OrgLocations orgLocations, String reqCoolingOffPeriod,
			Long requirementId) throws Exception;

}
