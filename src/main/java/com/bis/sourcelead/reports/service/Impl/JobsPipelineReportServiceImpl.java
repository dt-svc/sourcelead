package com.bis.sourcelead.reports.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
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
	public List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(Long orgLocationId, String loginUserCode, Date startUTCDate, Date endUTCDate) {
		return jobsPipelineReportDao.getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(orgLocationId, loginUserCode, startUTCDate, endUTCDate);
	}
	
}
