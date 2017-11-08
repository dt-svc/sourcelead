package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.entity.LeaveType;

public interface LeaveTypeService {
	
	public LeaveType addLeaveType(LeaveTypeDTO LeaveTypeDTO) throws Exception;

	public List<LeaveType> getLeaveTypeListByLocationId(String orgLocationId);

	public LeaveType getLeaveTypeById(String leaveTypeId) throws Exception;

	List<LeaveType> getLeaveTypeListByLocationIdAndStatus(String orgLocationId);

}