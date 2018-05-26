package com.bis.sourcelead.service;

import java.io.File;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface DayEntryReportsService {

	/*File exportDayEntryReports(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto, OrgLocations orgLocations, User loginUser) throws Exception;
*/
	/*File exportDayEntryReportsWithoutProject(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto,OrgLocations orgLocations, User loginUser) throws Exception;*/
	
	File exportDayEntryReportsTemplateOne(DayWiseTimesheetReoprtsDTO dayWiseTimesheetReoprtsDto, OrgLocations orgLocations, User loginUser) throws Exception;

}
