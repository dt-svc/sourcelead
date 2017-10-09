package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.LeaveReportsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface LeaveService {

	Leave saveLeave(LeaveDTO leaveDTO, OrgLocations orgLocations, User user) throws Exception;

	List<LeaveDTO> getEmployeeRequestedLeavesBylocationId(OrgLocations orgLocationId, String userId,String leaveStatus)throws Exception;

	Leave getLeaveByLeavId(String leaveId) throws Exception;

	boolean approveRequestLeave(OrgLocations orgLocations, User user, String leaveId, String leaveTypeId,String approveStatus, String numberOfDays, String reason) throws Exception;

	ResponseList<LeaveDTO> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId) throws Exception;

	List<LeaveReportsDTO> getEmployeeLeavesByApproverIdAndStatus(OrgLocations orgLocations, String userId, String status, String department, String leaveDate) throws Exception;
	
	List<Leave> getLeavesListByTypeIdAndLocationIdAndUserId(String leaveTypeId, Long orgLocationId, Long userId);

	ResponseList<Leave> getTopLeavetakers(OrgLocations orgLocation, LeaveDTO leaveDTO) throws Exception;

	List<LeaveDTO> getLeaveTakersBarChat(OrgLocations orgLocation, LeaveDTO leaveDTO)throws Exception;

	boolean getWithdrawLeave(String leaveId, String reason, Long orgLocationId) throws Exception;

}
