package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.User;

public interface ExportTimesheetEntries {

	void exportTimesheets(User user, String navigation, String startDate) throws Exception;

}
