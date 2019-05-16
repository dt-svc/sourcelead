package com.bis.sourcelead.timesheet.service;

import java.io.File;
import java.io.IOException;

import com.bis.sourcelead.dto.TimesheetReportsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface TimesheetReportService {

	public File prepareTimeSheetExport(TimesheetReportsDTO timesheetReportsDTO, User user, OrgLocations orgLocations,String globalUser) throws IOException, Exception;

	public File prepareTimeSheetExportExcel(TimesheetReportsDTO timesheetReportsDTO,User user,OrgLocations orgLocations,String globalUser)throws IOException, Exception;

}
