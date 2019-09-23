package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.bis.sourcelead.beans.ApplicantReport;
import com.bis.sourcelead.beans.AssignedRequirementReports;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementReportService {

	List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate, String startDateUTC, String endDateUTC,String status,String searchValue) throws Exception;

	List<RequirementSubmissionHistory> getApplicantsCountsPerReq(Long requirementId)throws Exception;

	Integer getApplicantsCountsForReports(Long id, String applicantStatus)throws Exception;

	List<RequirementSubmission> getReceivedApplicantsCountsPerReq(Long requirementId);

	List<RequirementSubmissionHistory> getReceivedApplicantsCountForReports(Long orgLocationId, Long requirementId);

	List<RequirementSubmissionHistory> getApplicantsCountForChart(Long orgLocationId);
	
	
	List<Requirement> getRequirementStageReports(OrgLocations orgLocation,String Status) throws Exception;


	List<RequirementSubmissionHistory> getReceivedApplicantsCountForReportsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmission> getReceivedApplicantsCountsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId, Date startDateUTC, Date endDateUTC);

	List<ApplicantReport> getTotalApplicants(OrgLocations orgLocation, String customStartDate, String customEndDate, String reportType)throws Exception;

	ApplicantReport getApplicantsCountsByDateRange(OrgLocations orgLocation,String reportType,String customStartDate,String customEndDate)throws Exception;

	List<ApplicantReport> getReceivedApplicantsCountsPerOrgLocId(OrgLocations orgLocation,String reportType,String customStartDate,String customEndDate,String searchValue) throws Exception;

	List<RequirementSubmission> getAllSubmissionsByAssignTeamMember(Long orgLocationId, Set<String> userCodesList,Date startDateUTC, Date endDateUTC);

	ApplicantReport getAllDayWiseApplicantsRecievedRports(String reportType, String customStartDate, String customEndDate, OrgLocations orgLocation)throws Exception;

	List<AssignedRequirementReports> getAllAssignedRequirementList(JSONObject jsonObj, OrgLocations orgLocation)throws Exception;

	AssignedRequirementReports getAllRequirementAssignToCounts(OrgLocations orgLocation, String json)throws Exception;

	List<RequirementSubmission> allSubmittedApplicantsForReports(String jsonData, String customStartDate, String customEndDate, String reportType, OrgLocations orgLocation)throws Exception;

	ApplicantReport getSubmittedApplicantsCountForReports(String jsonData, String customStartDate, String customEndDate, String reportType, OrgLocations orgLocation, User user)throws Exception;

	List<RequirementSubmissionHistory> getApplicantsCountsByDateRange(Long submissionId, Date startDateUTC, Date endDateUTC) throws Exception;
	
	public List<Requirement> getAllJobsBylocationIdAndRelationId(Long orglocationId,String orgRelationIds,Date startDate,Date endDate)throws Exception;
	
	public List<Long> getParentRequirementIdListByRequirementIds(List<Long> requirementIds);

}
