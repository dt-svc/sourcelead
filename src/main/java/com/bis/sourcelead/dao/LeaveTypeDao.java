package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.LeaveType;

public interface LeaveTypeDao {
	LeaveType addLeaveType(LeaveType leaveType) throws Exception;
	
	List<LeaveType> getLeaveTypeListByLocationId(String orgLocationId);

	LeaveType getLeaveTypeById(String leaveTypeId);

	List<LeaveType> getLeaveTypeListByLocationIdAndStatus(String orgLocationId);
}