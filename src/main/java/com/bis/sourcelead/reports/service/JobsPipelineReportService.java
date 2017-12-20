package com.bis.sourcelead.reports.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;

public interface JobsPipelineReportService {

	public List<Requirement> getAllJobsForReportBasedOnStartAndEndDates(Long orgLocationId, Long orgRelationId, Date startDateUTC, Date endDateUTC) throws Exception;
	
	List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(Long orgLocationId, String loginUserCode, Date startUTCDate, Date endUTCDate);

}
