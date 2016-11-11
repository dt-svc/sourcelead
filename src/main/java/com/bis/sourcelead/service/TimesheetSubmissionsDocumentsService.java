package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;

public interface TimesheetSubmissionsDocumentsService {
	
	
	public TimesheetSubmissionsDocuments addTimesheetSubmissionsDocuments(TimesheetSubmissionsDocuments timesheetSubmissionsDocuments)throws Exception;
	
	List<TimesheetSubmissionsDocuments> getAllTimesheetSubmissionsDocumentsBySubmissionId(Long submissionId)throws Exception;  
	
	public TimesheetSubmissionsDocuments getTimesheetSubmissionsDocuments(Long id)throws Exception;
	
}