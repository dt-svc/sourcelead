package com.bis.sourcelead.timesheet.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimeSheetDayEntries;

public interface TimeSheetDayEntriesService {

 public TimeSheetDayEntries saveTimesheetDayEntries(TimeSheetDayEntries dayentries) throws Exception;
	 
	 public List<Long> getAllTimeSheetDayEntryIdsBySubmissionId(Long submissionId) throws Exception;
	 
	 public Boolean deleteTimeSheetDayEntries(Long id)throws Exception;
	 
	 public TimeSheetDayEntries getTimesheetDayEntriesById(Long id) throws Exception;
	 
	 public TimeSheetDayEntries getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(Long submissionId,Long taskId,Date timesheetDate,String hourlyType) throws Exception;
	 
	 public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionId(Long submissionId) throws Exception;
	 
	 public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(Long submissionId,Date startDate,Date endDate) throws Exception;
	 
	 public ResponseList<TimeSheetDayEntries> getTimesheetDayEntriesByTaskUUIDAndUserId(String taskUUID, Long userId,Long locationId,int start, int length, String searchValue) throws Exception;

	 public Float getTotalHoursByProjectIdMasteCandidateIdandTimesheetDate(Long projectId, Long masterCandidateId,Date timesheetDate);
	 
	TimeSheetDayEntries getTimesheetDayEntryByProjectIdMasteCandidateIdandTimesheetDate(Long projectId,Long masterCandidateId, Date timesheetDate, Long projectTaskId);

	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(Long projectId, Date timesheetDate, Long projectMembers)throws Exception;

	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(Long projectId, Date startDate,Date endDate, Long projectMembers) throws Exception;

	public Boolean isMissingTimesheetByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,Date startDate,Date endDate,OrgLocations orgLocations) throws Exception;
	
	public Double getOverTimeHoursByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,Date startDate,Date endDate,OrgLocations orgLocations) throws Exception;

	public Map<String,String> getTimesheetFrequency(Date clockOutDate, OrgLocations loginOrgLocation, Projects projects);
	
	public TimeSheetDayEntries addTimesheetDayEntry(Long projectMemberId, Date subFrequencyStartDate, Date subFrequencyEndDate,
			Date frequencyStartDate, Date frequencyEndDate, Long loginUserId, Date timesheetDate, String hours,
			String comments, String taskUUID, Long taskId, String dateformat,String hourlyType) throws Exception;
}
