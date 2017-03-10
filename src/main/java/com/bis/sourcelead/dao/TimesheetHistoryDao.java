package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.TimesheetHistory;

public interface TimesheetHistoryDao {

	public TimesheetHistory saveTimesheetHistory(TimesheetHistory timesheetHistory)	throws Exception;
	
	public TimesheetHistory updateTimesheetHistory(TimesheetHistory timesheetHistory) throws Exception;
}