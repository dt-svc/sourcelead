package com.bis.sourcelead.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Leave;
import com.bis.sourcelead.entity.LeaveApproveHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LeaveService;

@RestController
@RequestMapping("/app")
public class UserLeaveLinkUpWithTimeSheetOldData {

	 @Autowired
	 private LeaveService leaveService;
	
	 @Autowired
	 private TimesheetRestController timesheetRestController;
	 
	@RequestMapping(value ="/ws/userLeaveOldDateToTimesheet")
	public String userLeaveOldDateToTimesheet() throws Exception {
		List<Leave> leaves = leaveService.getAllLeave();
		for(Leave leave:leaves) {
			User leaveUserId = null; 
			for(LeaveApproveHierarchy approverUser:leave.getApproverHierarchyList()) {
				leaveUserId =  approverUser.getApproverUserId();
			}
			if(leaveUserId != null && leave != null) {
				timesheetRestController.linkLeaveTypeWithTimeSheet(leaveUserId,leave.getOrgLocations(), leave.getLeaveType().getId(), leave);
			}
		}
		return "success";
		
	} 
}
