package com.bis.sourcelead.timesheet.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.TimesheetActivities;

public interface TimesheetActivityDao {

	List<TimesheetActivities> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(Long projectId, Long masterCAndidateId, Date frequencyStartDate, Date frequencyEndDate);

	TimesheetActivities saveTimesheetActivity(TimesheetActivities ta);
	
	 Map<String,Date> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDateAndStatus(Long projectId, Long masterCAndidateId, Date frequencyStartDate, Date frequencyEndDate,String status);
	
     TimesheetActivities getTimesheetActivityById(Long timesheetActivitiyId);
}
