package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface TimesheetReportService {

	String prepareTimeSheetDiv(String frequencyYear, String frequencyMonth,User loginUser,OrgLocations orgLocations);

}
