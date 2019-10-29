package com.bis.sourcelead.dashboard.util;

import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
	public String getLeaveDashBoardData(CommonDataDto commonDto) {
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
		return getJsonString(map);
	}

	
	
	/**
	 * converting map to String
	 * @param map
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	private String getJsonString(Map<String, String> map) {
		String convertedString ="";
		try {
			JSONObject jsonObj = new JSONObject();
			map.forEach((key, value) -> {
				jsonObj.put(key, value);
			});
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(jsonObj);
			 convertedString = jsonArray.toString().replace("\"", "\\\"");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return convertedString;
		
	}
	
	

}
