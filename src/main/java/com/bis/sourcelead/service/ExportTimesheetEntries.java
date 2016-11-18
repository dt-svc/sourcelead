package com.bis.sourcelead.service;

import java.io.File;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExportTimesheetEntries {

	File exportTimesheets(User user, String navigation, String startDate, OrgLocations orgLocations) throws Exception;

}
