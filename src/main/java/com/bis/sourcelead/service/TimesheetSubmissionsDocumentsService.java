package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocumentsHistory;

public interface TimesheetSubmissionsDocumentsService {
	
	
	public TimesheetSubmissionsDocuments addTimesheetSubmissionsDocuments(TimesheetSubmissionsDocuments timesheetSubmissionsDocuments)throws Exception;
	
	public TimesheetSubmissionsDocumentsHistory addTimesheetSubmissionsDocumentsHistory(TimesheetSubmissionsDocumentsHistory timesheetSubmissionsDocuments)throws Exception;
	
	List<TimesheetSubmissionsDocuments> getAllTimesheetSubmissionsDocumentsBySubmissionId(Long submissionId)throws Exception;  
	
	List<Long> getAllTimesheetSubmissionsDocumentIdsBySubmissionId(Long submissionId)throws Exception; 
	
	public TimesheetSubmissionsDocuments getTimesheetSubmissionsDocuments(Long id)throws Exception;
	
	List<TimesheetSubmissionsDocumentsHistory> getAllTimesheetSubmissionsDocumentsHistoryBySubmissionId(Long submissionId)throws Exception;

	public List<TimesheetSubmissionsDocumentsHistory> getAllTimesheetSubmissionsDocumentsHistoryByTimesheetActivityId(Long id);  
	
}