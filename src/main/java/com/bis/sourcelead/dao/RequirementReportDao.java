package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bis.sourcelead.dto.ApplicantReceivedReportsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementReportDao {

	List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate, Date startDateUTC, Date endDateUTC,String status,String searchValue);

	List<RequirementSubmissionHistory> getRequirementReports(Long requirementId);

	Integer getApplicantsCountsForReports(Long orgLocationID, String applicantStatus)throws Exception;

	List<RequirementSubmission> getReceivedApplicantsCountsPerReq(Long requirementId);

	List<RequirementSubmissionHistory> getReceivedApplicantsCountForReports(Long orgLocationId, Long requirementId);

	List<RequirementSubmissionHistory> getApplicantsCountForChart(Long orgLocationId);


	List<Requirement> getRequirementStageReports(OrgLocations orgLocation, String status) throws Exception;

	List<RequirementSubmissionHistory> getReceivedApplicantsCountForReportsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmission> getReceivedApplicantsCountsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmission> getTotalApplicants(OrgLocations orgLocation, Date startDateUTC, Date endDateUTC)throws Exception;

	List<RequirementSubmissionHistory> getApplicantsCountsByDateRange(Long submissionId, Date startDateUTC,Date endDateUTC)throws Exception;

	List<ApplicantReceivedReportsDTO> getReceivedApplicantsCountsPerOrgLocId(Long orgLocationId, Date startDateUTC,
			Date endDateUTC,String searchValue);
	
	ApplicantReceivedReportsDTO getReceivedApplicantsCounts(Long orgLocationId, Date startDateUTC,Date endDateUTC);

	List<RequirementSubmission> getAllSubmissionsByAssignTeamMember(Long orgLocationId, Set<String> userCodesList,Date startDateUTC, Date endDateUTC);

	public List<Long> getParentRequirementIdListByRequirementIds(List<Long> requirementIds);
	
}
