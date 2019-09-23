package com.bis.sourcelead.timesheet.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.timesheet.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.timesheet.service.TimeSheetDayEntriesService;

@Service
public class TimeSheetDayEntriesServiceImpl implements TimeSheetDayEntriesService {

	@Autowired
	private TimeSheetDayEntriesDao timeSheetDayEntriesDao;
	
	@Override
	public TimeSheetDayEntries saveTimesheetDayEntries(TimeSheetDayEntries dayentries) throws Exception {
		return timeSheetDayEntriesDao.saveTimesheetDayEntries(dayentries);
	}

	@Override
	public List<Long> getAllTimeSheetDayEntryIdsBySubmissionId(Long submissionId) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntryIdsBySubmissionId(submissionId);
	}

	@Override
	public Boolean deleteTimeSheetDayEntries(Long id) throws Exception {
		return timeSheetDayEntriesDao.deleteTimeSheetDayEntries(id);
	}

	@Override
	public TimeSheetDayEntries getTimesheetDayEntriesById(Long id) throws Exception {
		return timeSheetDayEntriesDao.getTimesheetDayEntriesById(id);
	}

	@Override
	public TimeSheetDayEntries getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(Long submissionId,
			Long taskId, Date timesheetDate) throws Exception {
		return timeSheetDayEntriesDao.getTimesheetDayEntriesBySubmissionIdTaskIdAndTimesheetDate(submissionId, taskId, timesheetDate);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionId(Long submissionId) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionId(submissionId);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(Long submissionId,
			Date startDate, Date endDate) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesBySubmissionIdWithStartDateAndEndDate(submissionId, startDate, endDate);
	}

	@Override
	public ResponseList<TimeSheetDayEntries> getTimesheetDayEntriesByTaskUUIDAndUserId(String taskUUID, Long userId,
			Long locationId, int start, int length, String searchValue) throws Exception {
		return timeSheetDayEntriesDao.getTimesheetDayEntriesByTaskUUIDAndUserId(taskUUID, userId, locationId, start, length, searchValue);
	}

	@Override
	public Float getTotalHoursByProjectIdMasteCandidateIdandTimesheetDate(Long projectId, Long masterCandidateId,Date timesheetDate) {
		return timeSheetDayEntriesDao.getTotalHoursByProjectIdMasteCandidateIdandTimesheetDate(projectId,masterCandidateId,timesheetDate);
	}
	
	@Override
	public TimeSheetDayEntries getTimesheetDayEntryByProjectIdMasteCandidateIdandTimesheetDate(Long projectId,Long masterCandidateId, Date timesheetDate,Long projectTaskId) {
		return timeSheetDayEntriesDao.getTimesheetDayEntryByProjectIdMasteCandidateIdandTimesheetDate(projectId,masterCandidateId,timesheetDate,projectTaskId);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(Long projectId,Date timesheetDate,Long projectMemberId) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDate(projectId, timesheetDate,projectMemberId);
	}

	@Override
	public List<TimeSheetDayEntries> getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(Long projectId,Date startDate, Date endDate, Long projectMembers) throws Exception {
		return timeSheetDayEntriesDao.getAllTimeSheetDayEntriesByProjectIdAndTimesheetDateTest(projectId, startDate,endDate,projectMembers);
	}

	@Override
	public Double getOverTimeHoursByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,
			Date startDate, Date endDate, OrgLocations orgLocations) throws Exception {
		return timeSheetDayEntriesDao.getOverTimeHoursByMasterCandidateIdAndOrgLocationWithStartEndDates(masterCandidateId, startDate, endDate, orgLocations);
	}

	@Override
	public Boolean isMissingTimesheetByMasterCandidateIdAndOrgLocationWithStartEndDates(Long masterCandidateId,
			Date startDate, Date endDate, OrgLocations orgLocations) throws Exception {
		return timeSheetDayEntriesDao.isMissingTimesheetByMasterCandidateIdAndOrgLocationWithStartEndDates(masterCandidateId, startDate, endDate, orgLocations);
	}
	
	


}
