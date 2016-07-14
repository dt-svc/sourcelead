package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

@Service
public class TimesheetSubmissionsServiceImpl implements TimesheetSubmissionsService {

	@Autowired
	TimesheetSubmissionsDao timesheetSubmissionsDao;
	
	
	@Override
	public TimesheetSubmissions addTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		return timesheetSubmissionsDao.addTimesheetSubmissions(timesheetSubmissions);
	}

	@Override
	public TimesheetSubmissions updateTimesheetSubmissions(TimesheetSubmissions timesheetSubmissions) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.updateTimesheetSubmissions(timesheetSubmissions);
	}

	@Override
	public TimesheetSubmissions getTimesheetSubmissions(String id) throws Exception {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getTimesheetSubmissions(id);
	}

	@Override
	public Boolean deleteTimesheetSubmissions(String id) throws Exception {
		Boolean status = timesheetSubmissionsDao.deleteTimesheetSubmissions(id);
		return status;
		// TODO Auto-generated method stub
	}

	@Override
	public List<TimesheetSubmissions> getAllTimesheetSubmissions(Date startDate , Date endDate , List<Long>  projectMemberIds) throws Exception {
		List<TimesheetSubmissions> list = timesheetSubmissionsDao.getAllTimesheetSubmissions(startDate ,endDate ,projectMemberIds);
		// TODO Auto-generated method stub
		return list;
	}
	
		@Override
	public void saveAllTimesheetSubmissions(List<TimesheetSubmissions> timesheetSubmissionsList) throws Exception {
		 timesheetSubmissionsDao.saveAllTimesheetSubmissions(timesheetSubmissionsList);
	}

	@Override
	public Date getNextMondayByProjectMemberId(Long pojectMemberId) {
		// TODO Auto-generated method stub
		return timesheetSubmissionsDao.getNextMondayByProjectMemberId(pojectMemberId);
	}

	@Override
	public Collection<TimesheetSubmissions> getApprovalTimesheetSubmissions(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getApprovalTimesheetSubmissions(userId);
		return list;
	}

	@Override
	public Collection<TimesheetSubmissions> getTimesheetSubmissionsByAproverId(Long projectMemberId, Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		Collection<TimesheetSubmissions> list = timesheetSubmissionsDao.getTimesheetSubmissionsByAproverId(projectMemberId,userId);
		return list;
	}
	
	
	

}
