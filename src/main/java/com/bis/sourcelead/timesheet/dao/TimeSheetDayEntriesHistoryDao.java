package com.bis.sourcelead.timesheet.dao;

import com.bis.sourcelead.entity.TimeSheetDayEntriesHistory;

public interface TimeSheetDayEntriesHistoryDao {

	public Boolean deleteAllTimeSheetDayEntriesHistoryByEntryId(Long timesheetDayEntriesId)	throws Exception;
	
	public TimeSheetDayEntriesHistory saveTimeSheetDayEntriesHistory(TimeSheetDayEntriesHistory timeSheetDayEntriesHistory)	throws Exception;
	
	public TimeSheetDayEntriesHistory updateTimeSheetDayEntriesHistory(TimeSheetDayEntriesHistory timeSheetDayEntriesHistory) throws Exception;
}