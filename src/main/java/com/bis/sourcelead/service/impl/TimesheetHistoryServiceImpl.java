package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetHistoryDao;
import com.bis.sourcelead.entity.TimesheetHistory;
import com.bis.sourcelead.service.TimesheetHistoryService;

@Service
public class TimesheetHistoryServiceImpl implements TimesheetHistoryService { 
	
	
	@Autowired
	TimesheetHistoryDao timesheetHistoryDao;

	@Override
	public TimesheetHistory saveTimesheetHistory(TimesheetHistory timesheetHistory) throws Exception {
		return timesheetHistoryDao.saveTimesheetHistory(timesheetHistory);
	}

	@Override
	public TimesheetHistory updateTimesheetHistory(TimesheetHistory timesheetHistory) throws Exception {
		return timesheetHistoryDao.updateTimesheetHistory(timesheetHistory);
	}
	
	
	
}
