package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;

public interface RequirementReportDao {

	List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate, Date startDateUTC, Date endDateUTC);

	List<RequirementSubmissionHistory> getRequirementReports(Long requirementId);

	Integer getApplicantsCountsForReports(Long orgLocationID, String applicantStatus)throws Exception;

	List<RequirementSubmission> getReceivedApplicantsCountsPerReq(Long requirementId);

	List<RequirementSubmissionHistory> getReceivedApplicantsCountForReports(Long orgLocationId, Long requirementId);

	List<RequirementSubmissionHistory> getApplicantsCountForChart(Long orgLocationId);

	List<RequirementSubmission> getTotalApplicants(OrgLocations orgLocation, Date startDateUTC, Date endDateUTC)throws Exception;

	List<RequirementSubmissionHistory> getApplicantsCountsByDateRange(Long submissionId, Date startDateUTC,Date endDateUTC)throws Exception;

}
