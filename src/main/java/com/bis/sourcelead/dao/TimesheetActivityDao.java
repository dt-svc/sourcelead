package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.TimesheetActivities;

public interface TimesheetActivityDao {

	List<TimesheetActivities> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(Long projectId, Long masterCAndidateId, Date frequencyStartDate, Date frequencyEndDate);

	TimesheetActivities saveTimesheetActivity(TimesheetActivities ta);
	
     Long getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDateAndStatus(Long projectId, Long masterCAndidateId, Date frequencyStartDate, Date frequencyEndDate,String status);
	
     TimesheetActivities getTimesheetActivityById(Long timesheetActivitiyId);
}
