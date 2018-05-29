package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface DayEntryReportsService {

	File exportDayEntryReportsWithoutProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser) throws Exception;
	
	File exportDayEntryReportsWithProjectExcel(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto, OrgLocations orgLocations, User loginUser) throws Exception;

	File exportDayEntryReportsWithoutProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser) throws IOException;

	File exportDayEntryReportsWithProjectPdf(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,	OrgLocations orgLocations, User loginUser)throws IOException;

	Map<List<DayWiseTimesheetReoprtsListDTO>, String[]> getDayEntriesList(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser) throws Exception;

}
