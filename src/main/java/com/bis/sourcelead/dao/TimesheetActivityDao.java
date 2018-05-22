package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.TimesheetActivities;

public interface TimesheetActivityDao {

	List<TimesheetActivities> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(Long submissionId, Long masterCAndidateId, Date frequencyStartDate, Date frequencyEndDate);

	TimesheetActivities saveTimesheetActivity(TimesheetActivities ta);

}
