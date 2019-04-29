package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.reports.dao.JobsPipelineReportDao;
import com.bis.sourcelead.reports.service.JobsPipelineReportService;

@Service
public class JobsPipelineReportServiceImpl implements JobsPipelineReportService{

	@Autowired
	JobsPipelineReportDao jobsPipelineReportDao;
	
	
	
	@Override
	public List<Requirement> getAllJobsForReportBasedOnStartAndEndDates(Long orgLocationId, Long loginUserId, Date startDateUTC, Date endDateUTC) throws Exception {
		return jobsPipelineReportDao.getAllJobsForReportBasedOnStartAndEndDates(orgLocationId, loginUserId, startDateUTC, endDateUTC);
	}



	@Override
	public List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(List<Long> jobIds,Date[] searchDates) {
		return jobsPipelineReportDao.getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(jobIds, searchDates);
	}



	@Override
	public List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonId(Long submissionId) throws Exception {
		return jobsPipelineReportDao.getAllJobsReportsCountsBySubmmisonId(submissionId);
	}
	
	@Override
	public List<RequirementSubmissionHistory> getAllJobsReportsCountsBySubmmisonIdAndMasterUserCode(long submissionId, String masterUserCode) {
		return jobsPipelineReportDao.getAllJobsReportsCountsBySubmmisonIdAndMasterUserCode(submissionId, masterUserCode);
	}



	@Override
	public Integer requirementListStatusCountByJobIdsAndDates(List<Long> jobIds, Date[] searchDates, String JobStage) {
		return jobsPipelineReportDao.requirementListStatusCountByJobIdsAndDates(jobIds, searchDates, JobStage);
	}



	@Override
	public List<Requirement> requirementListStatusCountByJobIdsAndDates(List<Long> jobIds, Date[] searchDates) {
		return jobsPipelineReportDao.requirementListStatusCountByJobIdsAndDates(jobIds, searchDates);
	}
	
	@Override
	public List<Requirement> vendorRequirementListStatusCountByJobIdsAndDates(List<Long> jobIds, Date[] searchDates) {
		return jobsPipelineReportDao.vendorRequirementListStatusCountByJobIdsAndDates(jobIds, searchDates);
	}
	
}
