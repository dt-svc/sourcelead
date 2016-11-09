package com.bis.sourcelead.service.impl;

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

	
	
		
}
