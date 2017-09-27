package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;

public interface LeaveDao {

	Leave saveLeave(Leave leave) throws Exception;

	List<LeaveApproveHierarchy> getEmployeeRequestedLeavesBylocationId(Long orgLocationId, String userId);

	List<Leave> getLeavesListByLocationIdAndStatus(Long orgLocationId, String status, Long userId);

	Leave getLeaveById(String leaveId) throws Exception;

}
