package com.bis.sourcelead.timesheet.service;

import com.bis.sourcelead.entity.TimesheetHistory;

public interface TimesheetHistoryService {

   public TimesheetHistory saveTimesheetHistory(TimesheetHistory timesheetHistory)	throws Exception;
	
	public TimesheetHistory updateTimesheetHistory(TimesheetHistory timesheetHistory) throws Exception;
	
}