package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetSubmissionsDocumentsDao;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;
import com.bis.sourcelead.service.TimesheetSubmissionsDocumentsService;

@Service
public class TimesheetSubmissionsDocumentsServiceImpl implements TimesheetSubmissionsDocumentsService {

	
	@Autowired
	TimesheetSubmissionsDocumentsDao   timesheetSubmissionsDocumentsDao;
	
	@Override
	public TimesheetSubmissionsDocuments addTimesheetSubmissionsDocuments(TimesheetSubmissionsDocuments timesheetSubmissionsDocuments) throws Exception {
		
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

	
	
		
}
