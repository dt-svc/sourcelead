package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bis.sourcelead.dao.TimeSheetDayEntriesDao;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.TimeSheetDayEntries;
import com.bis.sourcelead.service.TimeSheetDayEntriesService;

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

}
