package com.bis.sourcelead.service;

import java.io.File;
import java.util.Date;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface ExportTimesheetEntries {
	
	File timeSheetApprovelExport(User user, OrgLocations orgLocations,Date startDate,Date endDate,String timesheetFrequency,String client,String stage,String frequencyMonth,String weekFrequency)throws Exception;

	File timeSheetApprovelExportExcel(User user,OrgLocations orgLocations,Date startDate,Date endDate,String timesheetFrequency,String client,String stage,String frequencyMonth,String weekFrequency) throws Exception;

	File exportTimesheets(ProjectMembers projectMember, String frequencyStartDate, String frequencyEndDate,OrgLocations orgLocations, String requestFrom, User loginUser) throws Exception;

}
