package com.bis.sourcelead.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.CandidateConfigurationDTO;
import com.bis.sourcelead.dto.CandidateVerificationConfigDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateVerificationConfig;
import com.bis.sourcelead.entity.OfferAcceptanceRateView;
import com.bis.sourcelead.entity.OfferAcceptanceRateViewForVendor;
import com.bis.sourcelead.entity.RecruiterJobsReportView;
import com.bis.sourcelead.entity.RecruiterReportView;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementSubmissionHistoryDao {

	RequirementSubmissionHistory updateRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)throws Exception;

	List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndStage(Long orgLocationID,String submissionType, Long submissionId) throws Exception;

	RequirementSubmissionHistory getRequirementSubmissionHistoryBySubmissionId(Long submissionId);
	
	RequirementSubmissionHistory  isInCoolingOffPeriodByEmailAndLocationsId(String email, Long orgLocationId, String reqCoolingOffPeriod, Long requirementId) throws ParseException;
	
	List<RequirementSubmissionHistory> getRequirementHistoryListByCandidateIdAndOrgLocationIdAndRequirementIdAndSubmissionId(Long candidateId, Long orgLocationId, Long requirementId,Long submissionId)throws Exception;

	List<RequirementSubmissionHistory> getAllRequirementSubmissionHistoryBySubmissionId(List<Long> submissionIds)throws Exception;
	
	Integer getCountOfSubmissionsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user, String submissionStage ,Date startDateUTC, Date endDateUTC,String isGlobalUser) throws Exception ;

	List<RequirementSubmissionHistory> getRequirementHistoryByLocationIdAndSubId(Long orgLocationID, Long submissionId);
	
	RequirementSubmissionHistory addRequirementHistory(RequirementSubmissionHistory requirementSubmissionHistory)throws Exception;
	
	RecruiterReportView getSubmissionCountsByStageAndCreatedUserAndOrglocationId(Long orgLocationId, User user, String submissionStage ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;

	List<RecruiterReportView> getSubmissionCountsByStageAndCreatedUserListAndOrglocationId(Long orgLocationId, List<String> userCodeList ,String startDateUTC, String endDateUTC,String isGlobalUser,Long orgId) throws Exception ;

	ResponseList<RequirementSubmissionHistory> getAllSubmissionHistoryApplicantsByOwner(RequirementSubmissionHistoryDTO sumissionHistoryDTO, String userCode, String stage,Date[] searchDates,Long locationId) throws Exception;

	public int updateSourceUserCodeForSubmissionHistory();
	
	public List<RecruiterJobsReportView> getSubmissionApplicantCountsForRecruiterJobreports(Long orgLocationId, User user,Date[] searchDates,Long requirementId) throws Exception;

	public List<OfferAcceptanceRateView> getOfferAcceptanceRateList(String userCode, long orgLocationId, String selectedClients, int month, int year, boolean compare);
	
	public List<OfferAcceptanceRateViewForVendor> getOfferAcceptanceRateListForVendor(List<Long> parentRequirementIdList, String masterUserCode, int month, int year, boolean compare);

	RequirementSubmissionHistory isInCoolingOffPeriodByLocationID(CandidateVerificationConfig candidateVerificationConfig, CandidateConfigurationDTO candidateConfigurationDTO,
			Long orgLocationId, String reqCoolingOffPeriod, Long requirementId) throws Exception;

}
