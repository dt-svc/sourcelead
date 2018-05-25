package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.TimesheetActivities;

public interface TimesheetActivityDao {

	/*List<TimesheetActivities> getTimesheetSubmissionApproverHirerchayHistoryBySubmissionId(Long submissionId);*/

	TimesheetActivities saveTimesheetActivity(TimesheetActivities ta);

}
