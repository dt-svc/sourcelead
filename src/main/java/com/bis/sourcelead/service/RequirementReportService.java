package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementReportService {

	List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmissionHistory> getApplicantsCountsPerReq(Long requirementId)throws Exception;

	Integer getApplicantsCountsForReports(Long id, String applicantStatus)throws Exception;

	List<RequirementSubmission> getReceivedApplicantsCountsPerReq(Long requirementId);

	List<RequirementSubmissionHistory> getReceivedApplicantsCountForReports(Long orgLocationId, Long requirementId);

	List<RequirementSubmissionHistory> getApplicantsCountForChart(Long orgLocationId);


	List<RequirementSubmissionHistory> getReceivedApplicantsCountForReportsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmission> getReceivedApplicantsCountsByVendorLocationId(Long orgLocationId,
			Long vendorLocationId, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmission> getTotalApplicants(OrgLocations orgLocation, Date startDateUTC, Date endDateUTC)throws Exception;

	List<RequirementSubmissionHistory> getApplicantsCountsByDateRange(Long id, Date startDateUTC, Date endDateUTC)throws Exception;

	List<RequirementSubmission> getReceivedApplicantsCountsPerOrgLocId(Long orgLocationId, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmission> getAllSubmissionsByAssignTeamMember(Long orgLocationId, Set<String> userCodesList,Date startDateUTC, Date endDateUTC);

}
