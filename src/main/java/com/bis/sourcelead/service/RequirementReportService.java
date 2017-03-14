package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

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

}
