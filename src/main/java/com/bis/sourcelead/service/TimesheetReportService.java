package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;

import com.bis.sourcelead.dto.TimesheetReportsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface TimesheetReportService {

	public String prepareTimeSheetDiv(String frequencyYear, String frequencyMonth,User loginUser,OrgLocations orgLocations) throws Exception;

	public File prepareTimeSheetExport(TimesheetReportsDTO timesheetReportsDTO, User user, OrgLocations orgLocations,String globalUser) throws IOException, Exception;

	public File prepareTimeSheetExportExcel(TimesheetReportsDTO timesheetReportsDTO,User user,OrgLocations orgLocations,String globalUser)throws IOException, Exception;

}
