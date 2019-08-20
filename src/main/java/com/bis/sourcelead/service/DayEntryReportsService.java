package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface DayEntryReportsService {

	File exportDayEntryReportsWithoutProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser, String isGlobalUser) throws Exception;
	
	File exportDayEntryReportsWithProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser,String isGlobalUser) throws IOException, Exception;

	ResponseList<DayWiseTimesheetReoprtsListDTO> getDayEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser, String isGlobalUser) throws Exception;
	
	ResponseList<DayWiseTimesheetReoprtsListDTO> getProjectTimeEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser, String isGlobalUser) throws Exception;
	
    File exportProjectTimeEntryReportsWithoutProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser, String isGlobalUser) throws Exception;
	
	File exportProjectTimeEntryReportsWithProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser,String isGlobalUser) throws IOException, Exception;


}
