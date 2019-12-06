package com.bis.sourcelead.timesheet.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.DayWiseTimesheetReoprtsListDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface DayEntryReportsDao {

	public ResponseList<DayWiseTimesheetReoprtsListDTO> getDayWiseReportListBasedOnProjectListIdStartDateEndDate(List<Long> projectId,Date startDate,Date endDate, String searchString, int start, int length, OrgLocations orgloc, User loginUser, String isGlobalUser, List<Long> mastercandidateIds,String dateFormat)  throws Exception;
	
	public ResponseList<DayWiseTimesheetReoprtsListDTO> getProjectTimeEntriesReportListBasedOnProjectListIdStartDateEndDate(List<Long> projectId,Date startDate,Date endDate, String searchString, int start, int length, OrgLocations orgloc, User loginUser, String isGlobalUser, List<Long> mastercandidateIds,String dateFormat)  throws Exception;
	
	public ResponseList<DayWiseTimesheetReoprtsListDTO> getResourceTimeEntriesReportListBasedOnProjectListIdStartDateEndDate(List<Long> projectId,Date startDate,Date endDate, String searchString, int start, int length, OrgLocations orgloc, User loginUser, String isGlobalUser, List<Long> mastercandidateIds,String dateFormat)  throws Exception;
	
}
