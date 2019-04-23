package com.bis.sourcelead.timesheet.service;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimesheetActivities;

public interface TimesheetActivityService {

	List<TimesheetActivities> getTimesheetActivityByProjectIdMasterCandidateIdFrequencyStartAndEndDate(Long projectId, Long masterCandidateId, String timesheetFrequency, OrgLocations orgLocations) throws Exception;

	TimesheetActivities saveTimesheetActivity(TimesheetActivities ta);
}
