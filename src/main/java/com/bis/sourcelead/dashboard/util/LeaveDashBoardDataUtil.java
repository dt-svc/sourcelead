package com.bis.sourcelead.dashboard.util;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.service.LeaveService;

@Component
public class LeaveDashBoardDataUtil {
	
	@Autowired
	LeaveService leaveService;

	
	/**
	 * Leave DashBoard Data 
	 * approved,rejected,pending and canceled leav count
	 * 
	 * @param commonDto
	 * @return
	 */
	public Map<String, String> getLeaveDashBoardData(CommonDataDto commonDto) {
		Map<String, String> map = new HashedMap();
		try {
			LeaveDTO leaveDto = leaveService.getAllEmployeesLeavesCounts(commonDto.getOrgLocation().getId(), commonDto.getUserId(), commonDto.getLoginUserRole(),commonDto.getOrgId());
			map.put("Requested", leaveDto.getEmployeeRequestedCounts());
			map.put("Rejected", leaveDto.getEmployeeRejectedCounts());
			map.put("Cancelled", leaveDto.getEmployeeCancelledCounts());
			map.put("Approved", leaveDto.getEmployeeApprovedCounts());
		}catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	

}
