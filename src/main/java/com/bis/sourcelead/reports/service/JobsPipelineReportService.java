package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;

public interface JobsPipelineReportService {

	public List<Requirement> getAllJobsForReportBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId, Date startDateUTC, Date endDateUTC) throws Exception;
	
	List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(List<Long> jobIds,Date[] searchDates);

	List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonId(Long submissionId) throws Exception;
}
