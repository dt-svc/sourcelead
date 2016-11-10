package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;

public interface TimesheetSubmissionsDocumentsDao {

	
	public TimesheetSubmissionsDocuments addTimesheetSubmissionsDocuments(TimesheetSubmissionsDocuments timesheetSubmissionsDocuments)throws Exception;

	List<TimesheetSubmissionsDocuments> getAllTimesheetSubmissionsDocumentsBySubmissionId(Long submissionId)throws Exception;
	
	public TimesheetSubmissionsDocuments getTimesheetSubmissionsDocuments(Long id)throws Exception;
}