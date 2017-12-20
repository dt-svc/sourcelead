package com.bis.sourcelead.reports.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;

public interface JobsPipelineReportDao {
	
	List<Requirement> getAllJobsForReportBasedOnStartAndEndDates(Long orgLocationId, Long loginUserId, Date startDateUTC, Date endDateUTC) throws Exception;
	
	List<RequirementSubmission> getAllJobsReportCountByOrglocationIdAndLoginUserCodeAndStartAndEndDate(Long orgLocationId, String loginUserCode, Date startUTCDate, Date endUTCDate);

}
