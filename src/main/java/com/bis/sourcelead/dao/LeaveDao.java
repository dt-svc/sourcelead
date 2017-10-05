package com.bis.sourcelead.dao;

import java.text.ParseException;
import java.util.List;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.OrgLocations;

public interface LeaveDao {

	Leave saveLeave(Leave leave) throws Exception;

	ResponseList<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(LeaveDTO leaveDTO, OrgLocations orgLocations) throws ParseException;

	List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId);

	Leave getLeaveById(String leaveId) throws Exception;

	List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeId, Long orgLocationId, Long userId);
	
	int getNumberOfLeavesTakenByUser(Long userId) throws Exception;

	int getThisMonthLeavesTakenByUser(Long userId);

	ResponseList<LeaveDTO> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception;

	List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationIdAndUserIdAndStatus(Long locationId, String userId, String status);

}
