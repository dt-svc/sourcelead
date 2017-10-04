package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface LeaveService {

	Leave saveLeave(LeaveDTO leaveDTO, OrgLocations orgLocations, User user) throws Exception;

	List<LeaveDTO> getEmployeeRequestedLeavesBylocationId(OrgLocations orgLocationId, String userId,String leaveStatus)throws Exception;

	Leave getLeaveByLeavId(String leaveId) throws Exception;

	boolean approveRequestLeave(OrgLocations orgLocations, User user, String leaveId, String leaveTypeId,String approveStatus, String numberOfDays) throws Exception;

	List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId) throws Exception;

	List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeId, Long orgLocationId, Long userId);

}
