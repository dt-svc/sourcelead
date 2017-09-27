package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface LeaveService {

	Leave saveLeave(LeaveDTO leaveDTO, OrgLocations orgLocations, User user) throws Exception;

	List<LeaveDTO> getEmployeeRequestedLeavesBylocationId(Long orgLocationId, String userId)throws Exception;

}
