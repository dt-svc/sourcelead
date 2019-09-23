package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.LeaveManagementHistoryDao;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LeaveManagementHistoryService;

@Service
public class LeaveManagementHistoryServiceImpl implements LeaveManagementHistoryService{
	
	@Autowired
	LeaveManagementHistoryDao leaveManagementHistoryDao;

	@Override
	public LeaveManagementHistory saveLeaveManagementHistory(Object obj,User user,LeaveDTO leaveDTO) throws Exception {
		LeaveManagementHistory leaveManagementHistory = new LeaveManagementHistory(); 
		if(obj instanceof Leave) {
			Leave leave  = (Leave)obj;
			leaveManagementHistory.setLeaveid(leave);
			leaveManagementHistory.setType(Constants.LEAVE);
			
			if(leaveDTO.getLeaveId() != null) {
				if(leaveDTO.getCurrentStage().equalsIgnoreCase(Constants.LEAVE_WITHDRAW))
					leaveManagementHistory.setFromStage(Constants.LEAVE_WITHDRAW);
				else if(leaveDTO.getCurrentStage().equalsIgnoreCase(Constants.LEAVE_REJECTED))
					leaveManagementHistory.setFromStage(Constants.LEAVE_REJECTED);
			}else {
				leaveManagementHistory.setFromStage(Constants.LEAVE_NEW);
			}
			leaveManagementHistory.setToStage(leave.getStatus());
			leaveManagementHistory.setStatus(leave.getStatus());
			leaveManagementHistory.setCreatedBy(user.getUserCode());
			leaveManagementHistory.setCreatedDate(new Date());
			leaveManagementHistory.setUpdatedBy(user.getUserCode());
			leaveManagementHistory.setUpdatedDate(new Date());
		}
		return leaveManagementHistoryDao.saveLeaveManagementHistory(leaveManagementHistory);
	}

}
