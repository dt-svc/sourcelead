package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.service.TimesheetEntriesService;

@Service
public class TimesheetEntriesServiceImpl implements TimesheetEntriesService {

	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;
	
	
	@Override
	public TimesheetEntries addTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		return timesheetEntriesDao.addTimesheetEntries(timesheetEntries);
	}

	@Override
	public TimesheetEntries updateTimesheetEntries(TimesheetEntries timesheetEntries) throws Exception {
		// TODO Auto-generated method stub
		return timesheetEntriesDao.updateTimesheetEntries(timesheetEntries);
	}

	@Override
	public TimesheetEntries getTimesheetEntries(String id) throws Exception {
		// TODO Auto-generated method stub
		return timesheetEntriesDao.getTimesheetEntries(id);
	}

	@Override
	public Boolean deleteTimesheetEntries(String id) throws Exception {
		Boolean status = timesheetEntriesDao.deleteTimesheetEntries(id);
		return status;
		// TODO Auto-generated method stub
	}

	@Override
	public List<TimesheetEntries> getAllTimesheetEntries() throws Exception {
		// TODO Auto-generated method stub
		List<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntries();
		return list;
	}

	@Override
	public Collection<TimesheetEntries> getAllTimesheetEntriesBySubmissionId(Long submissionId) throws Exception {
		// TODO Auto-generated method stub
		 
		Collection<TimesheetEntries> list = timesheetEntriesDao.getAllTimesheetEntriesBySubmissionId(submissionId);
		return list;
		
	}

}
