package com.bis.sourcelead.service;

import java.io.File;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface ExportTimesheetEntries {

	File exportTimesheets(ProjectMembers projectMember, String startDate, String endDate, OrgLocations orgLocations) throws Exception;

}
