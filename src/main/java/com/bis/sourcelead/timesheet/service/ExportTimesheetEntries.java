package com.bis.sourcelead.timesheet.service;

import java.io.File;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface ExportTimesheetEntries {
	
	File timeSheetApprovelExport(User user, OrgLocations orgLocations,Date startDate,Date endDate,String timesheetFrequency,String client,String project,String stage,String frequencyMonth,String weekFrequency,String globalUser,String searchValue)throws Exception;

	File timeSheetApprovelExportExcel(User user,OrgLocations orgLocations,Date startDate,Date endDate,String timesheetFrequency,String client,String project,String stage,String frequencyMonth,String weekFrequency,String globalUser,String searchValue) throws Exception;

	File exportTimesheets(ProjectMembers projectMember, String frequencyStartDate, String frequencyEndDate,OrgLocations orgLocations, String requestFrom, User loginUser,String globalUser) throws Exception;

}
