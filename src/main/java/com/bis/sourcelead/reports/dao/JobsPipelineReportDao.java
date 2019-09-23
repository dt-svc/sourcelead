package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;

public interface JobsPipelineReportDao {
	
	List<Requirement> getAllJobsForReportBasedOnStartAndEndDates(Long orgLocationId, Long loginUserId, Date startDateUTC, Date endDateUTC) throws Exception;
	
	List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(List<Long> jobIds,Date[] searchDates);
	
	List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonId(Long submissionId) throws Exception;
	
	List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonIdAndMasterUserCode(Long submissionId, String masterUserCode);
	
	Integer requirementListStatusCountByJobIdsAndDates(List<Long> jobIds,Date[] searchDates,String JobStage);

	List<Requirement> requirementListStatusCountByJobIdsAndDates(List<Long> jobIds,Date[] searchDates);

	List<Requirement> vendorRequirementListStatusCountByJobIdsAndDates(List<Long> jobIds, Date[] searchDates);
	
}
