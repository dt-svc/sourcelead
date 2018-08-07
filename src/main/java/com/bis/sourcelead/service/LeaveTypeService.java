package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.LeaveTypeDTO;
import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;

public interface LeaveTypeService {
	
	public LeaveType addLeaveType(LeaveTypeDTO LeaveTypeDTO) throws Exception;

	public List<LeaveType> getLeaveTypeListByLocationId(String orgLocationId);

	public LeaveType getLeaveTypeById(Long leaveTypeId) throws Exception;

	List<LeaveType> getLeaveTypeListByLocationIdAndStatus(String orgLocationId);

	public LeaveType getLeaveTypeByUUID(String leaveTypeUUID) throws Exception;

	public List<ProjectTasks> getProjectTaskByorgLocationIdAndLeaveTypeId(Long id, Long leaveTypeId);

	public List<LeaveType> getLeaveTypesByTypeList(List<String> typeList, OrgLocations orgLocation);

	public LeaveType getLeaveTypeByLeaveTypeName(String leaveTypeName, OrgLocations orgLocation);
	
	List<String> getLeaveTypeNameListByLocationId(Long orgLocationId);

}