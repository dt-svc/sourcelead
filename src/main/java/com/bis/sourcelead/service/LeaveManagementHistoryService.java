package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveManagementHistory;
import com.bis.sourcelead.entity.User;

public interface LeaveManagementHistoryService {

	LeaveManagementHistory saveLeaveManagementHistory(Object leave, User user, LeaveDTO leaveDTO) throws Exception;

}
