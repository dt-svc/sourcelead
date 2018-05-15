package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.TimesheetActivities;

public interface TimesheetActivityService {

	/*List<TimeSheetSubmissionDTO> getTimesheetSubmissionApproverHirerchayHistoryBySubmissionId(Long id);*/

	TimesheetActivities saveTimesheetActivity(TimesheetActivities ta);

}
