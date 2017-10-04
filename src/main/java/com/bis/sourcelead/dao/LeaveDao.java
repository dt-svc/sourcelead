package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.OrgLocations;

public interface LeaveDao {

	Leave saveLeave(Leave leave) throws Exception;

	List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(Long orgLocationId, String userId, String leaveStatus);

	List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId);

	Leave getLeaveById(String leaveId) throws Exception;

	int getNumberOfLeavesTakenByUser(Long userId) throws Exception;

	int getThisMonthLeavesTakenByUser(Long userId);

	ResponseList<LeaveDTO> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception;

}
