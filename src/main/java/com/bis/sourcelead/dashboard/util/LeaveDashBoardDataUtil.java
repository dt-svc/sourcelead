package com.bis.sourcelead.dashboard.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.VendorContractsDTO;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.service.ContractsTemplateService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;


@Component
public class LeaveDashBoardDataUtil {
	
	@Autowired
	LeaveService leaveService;
	@Autowired
	TimesheetSubmissionsService	 timesheetSubmissionsService;
	@Autowired
	OrgRelationsService   orgRelationsService;
	
	@Autowired
	ContractsTemplateService  contractsTemplateService;
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



	public String getTimesheetsDashboardData(CommonDataDto commonDataDto) {
		Map<String, String> map = new HashedMap();
		try {
		String isGlobalAccess = "N";
		String timesheetCountType = "ALL";
		Boolean isAdmin = true;
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(commonDataDto.getUserId(), commonDataDto.getOrgLocation().getId(),commonDataDto.getOrgLocation().getOrgId(),timesheetCountType,isGlobalAccess,isAdmin);
		map.put("Draft",timeSheetSubmissionCount.getSavedTimesheetSubmissionCount().toString());
		map.put("Waiting",timeSheetSubmissionCount.getWaitingForApprovalTimesheetSubmissionCount().toString());
		map.put("Approved",timeSheetSubmissionCount.getApprovedTimesheetSubmissionCount().toString());
		map.put("Rejected",timeSheetSubmissionCount.getRejectedTimesheetSubmissionCount().toString());
		
		System.out.println(timeSheetSubmissionCount);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(map);
	
	}



	public String getVendorByitsContacts(CommonDataDto commonDataDto) {
		JSONArray jsonarray = new JSONArray();
		String vendorIds="";
		String invitationStatus="";
		String status="";
		 try {
			 JSONObject jsonobject = null;
			List<OrgSpoc> vandorsContactListByOrgLocationId = orgRelationsService.getAllVendorsContactFilterListByOrgLocationId(commonDataDto.getUserId(),commonDataDto.getOrgLocation().getId(), "Client",status,vendorIds,invitationStatus);
			for(OrgSpoc s:vandorsContactListByOrgLocationId) {
				jsonobject = new JSONObject();
				jsonobject.put("Name",s.getVendorUser().getFirstName());
				jsonobject.put("mobile phone",s.getVendorUser().getMobilePhone());
				jsonobject.put("EMail",s.getVendorUser().getEmail());
				jsonarray.add(jsonobject);
			}
			System.out.println(vandorsContactListByOrgLocationId);	
		 }  catch (Exception e) {
			e.printStackTrace();
			
		}
		return jsonarray.toJSONString();
	}
}
