package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.TimeSheetDayEntries;

public interface TimeSheetDayEntriesDao {

	 public TimeSheetDayEntries saveTimesheetDayEntries(TimeSheetDayEntries dayentries) throws Exception;
	 
	 public Collection<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionId(Long submissionId) throws Exception;
	
}
