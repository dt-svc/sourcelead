package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetHistoryDao;
import com.bis.sourcelead.service.TimesheetHistoryService;

@Service
public class TimesheetHistoryServiceImpl implements TimesheetHistoryService { 
	
	
	@Autowired
	TimesheetHistoryDao timesheetHistoryDao;
	
	
	
}
