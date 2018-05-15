package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetActivityDao;
import com.bis.sourcelead.entity.TimesheetActivities;
import com.bis.sourcelead.service.TimesheetActivityService;

@Service
public class TimesheetActivityServiceImpl implements TimesheetActivityService {

	@Autowired
	private TimesheetActivityDao timesheetActivityDao;
	
	/*@Override
	@Transactional(rollbackFor = { Exception.class })
	public List<TimeSheetSubmissionDTO> getTimesheetSubmissionApproverHirerchayHistoryBySubmissionId(Long submissionId) {
			List<TimesheetActivities> submissionHistory= timesheetActivityDao.getTimesheetSubmissionApproverHirerchayHistoryBySubmissionId(submissionId);
		 return null;
	}*/
	

	@Override
	public TimesheetActivities saveTimesheetActivity(TimesheetActivities ta) {
		return timesheetActivityDao.saveTimesheetActivity(ta);
		
	}

}
