package com.bis.sourcelead.timesheet.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocumentsHistory;
import com.bis.sourcelead.timesheet.dao.TimesheetSubmissionsDocumentsDao;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsDocumentsService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class TimesheetSubmissionsDocumentsServiceImpl implements TimesheetSubmissionsDocumentsService {

	
	@Autowired
	TimesheetSubmissionsDocumentsDao   timesheetSubmissionsDocumentsDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public TimesheetSubmissionsDocuments addTimesheetSubmissionsDocuments(TimesheetSubmissionsDocuments timesheetSubmissionsDocuments) throws Exception {
		timesheetSubmissionsDocuments.setUpdatedDate(commonUtil.currentDate());
		 return  timesheetSubmissionsDocumentsDao.addTimesheetSubmissionsDocuments(timesheetSubmissionsDocuments);

	}

	@Override
	public List<TimesheetSubmissionsDocuments> getAllTimesheetSubmissionsDocumentsBySubmissionId(Long submissionId)
			throws Exception {
		
		return  timesheetSubmissionsDocumentsDao.getAllTimesheetSubmissionsDocumentsBySubmissionId(submissionId);
		
	}

	@Override
	public TimesheetSubmissionsDocuments getTimesheetSubmissionsDocuments(Long id) throws Exception {
		return  timesheetSubmissionsDocumentsDao.getTimesheetSubmissionsDocuments(id);
	}

	@Override
	public TimesheetSubmissionsDocumentsHistory addTimesheetSubmissionsDocumentsHistory(TimesheetSubmissionsDocumentsHistory timesheetSubmissionsDocuments) throws Exception {
		timesheetSubmissionsDocuments.setUpdatedDate(commonUtil.currentDate());
		 return  timesheetSubmissionsDocumentsDao.addTimesheetSubmissionsDocumentsHistory(timesheetSubmissionsDocuments);
	}

	@Override
	public List<TimesheetSubmissionsDocumentsHistory> getAllTimesheetSubmissionsDocumentsHistoryBySubmissionId(Long submissionId) throws Exception {
		return  timesheetSubmissionsDocumentsDao.getAllTimesheetSubmissionsDocumentsHistoryBySubmissionId(submissionId);
	}

	@Override
	public List<TimesheetSubmissionsDocumentsHistory> getAllTimesheetSubmissionsDocumentsHistoryByTimesheetActivityId(Long activityId) {
		return timesheetSubmissionsDocumentsDao.getAllTimesheetSubmissionsDocumentsHistoryByTimesheetActivityId(activityId);
	}

	@Override
	public List<Long> getAllTimesheetSubmissionsDocumentIdsBySubmissionId(Long submissionId) throws Exception {
		return timesheetSubmissionsDocumentsDao.getAllTimesheetSubmissionsDocumentIdsBySubmissionId(submissionId);
	}

	@Override
	public List<TimesheetSubmissionsDocuments> getAllTimesheetSubmissionsDocumentsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(
			Long masterCandidateId, Long projectId, Date freqStartDate, Date freqEndDate) throws Exception {
		return timesheetSubmissionsDocumentsDao.getAllTimesheetSubmissionsDocumentsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(masterCandidateId, projectId, freqStartDate, freqEndDate);
	}

	@Override
	public List<Long> getAllTimesheetSubmissionsDocumentIdsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(
			Long masterCandidateId, Long projectId, Date freqStartDate, Date freqEndDate) throws Exception {
		return timesheetSubmissionsDocumentsDao.getAllTimesheetSubmissionsDocumentIdsByProjectIdMasterCandidateIdAndFrequencyStartDateEndDate(masterCandidateId, projectId, freqStartDate, freqEndDate);
	}

	
	
		
}
