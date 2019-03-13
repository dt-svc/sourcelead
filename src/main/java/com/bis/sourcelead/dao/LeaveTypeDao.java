package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.LeaveType;
import com.bis.sourcelead.entity.LeaveTypeHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;

public interface LeaveTypeDao {
	
	LeaveType addLeaveType(LeaveType leaveType) throws Exception;
	
	List<LeaveType> getLeaveTypeListByLocationId(String orgLocationId);

	LeaveType getLeaveTypeById(Long leaveTypeId);

	List<LeaveType> getLeaveTypeListByLocationIdAndStatus(String orgLocationId);

	LeaveType getLeaveTypeByUUID(String leaveTypeUUID);

	List<ProjectTasks> getProjectTaskByorgLocationIdAndLeaveTypeId(Long orgLocationId, Long leaveTypeId);

	List<LeaveType> getLeaveTypesByTypeList(List<String> typeList, OrgLocations orgLocation);

	LeaveType getLeaveTypeByLeaveTypeName(String leaveTypeName, OrgLocations orgLocation);
	
	List<String> getLeaveTypeNameListByLocationId(Long orgLocationId);

	void storeLeaveTypeHistory(LeaveTypeHistory lt);
}