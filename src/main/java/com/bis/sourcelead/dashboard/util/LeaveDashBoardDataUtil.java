package com.bis.sourcelead.dashboard.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.itextpdf.text.List;

@Component
public class LeaveDashBoardDataUtil {
	
	@Autowired
	LeaveService leaveService;
	
	@Autowired
	 private RequirementSubmissionDao requirementSubmissionDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	UserService userService;

	
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
	
	public String getSubmitToClientCandidatesDashBoardData(CommonDataDto commonDto) {
		 JSONArray jsonArray = new JSONArray();
		try {
		RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO = new RequirementSubmissionHistoryDTO();
		User user = userService.getUserById(commonDto.getUserId());
		user.setId(commonDto.getUserId());
		requirementSubmissionHistoryDTO.setSubmissionStatus("SUBMIT_TO_CLIENT");
		requirementSubmissionHistoryDTO.setTabType("previous");
		requirementSubmissionHistoryDTO.setSearchValue("");
		requirementSubmissionHistoryDTO.setSearchType("");
			DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String currentDate = commonUtil.dateStrFromDate(new Date());
			Date endTime =  commonUtil.orgLocationDateFormat(commonDto.getOrgLocation().getDateFormat(),currentDate);
			String customEndTimeStr = TZ_DF_CED.format(endTime);  
			Date endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
			String userType = null;
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(commonDto.getLoginUserRole()) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDto.getLoginUserRole())){
				userType = Constants.ADMIN;
			} 
		
			   ResponseList<RequirementSubmissionHistory> toDayReqApplicantscount =requirementSubmissionDao.getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, commonDto.getOrgLocation().getId(),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,null,endUTCDate,commonDto.getOrgLocation(),Constants.Dashboard);
			  
			   ArrayList<RequirementSubmissionHistory> subList =  (ArrayList<RequirementSubmissionHistory>) toDayReqApplicantscount.getData();
			  
			   System.out.println(subList);
			  
			   JSONObject jsonObj = null;
			   for(RequirementSubmissionHistory req : subList) {
				   jsonObj = new JSONObject();
				   jsonObj.put("Applicant Name",req.getSubmissionId().getCandidate().getFirstName());
				   jsonObj.put("Job", req.getSubmissionId().getRequirement().getJobTitle());
				   jsonObj.put("Client", req.getSubmissionId().getRequirement().getClientName());
				   jsonObj.put("Source",req.getSubmissionId().getCreatedUser());
				   jsonArray.add(jsonObj);
		     }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toJSONString();
	}

	public String getInterviewDashBoardData(CommonDataDto commonDataDto) {
		 JSONArray jsonArray = new JSONArray();
		try {
		RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO = new RequirementSubmissionHistoryDTO();
		User user = userService.getUserById(commonDataDto.getUserId());
		user.setId(commonDataDto.getUserId());
		requirementSubmissionHistoryDTO.setSubmissionStatus("INTERVIEW");
		requirementSubmissionHistoryDTO.setTabType("previous");
		requirementSubmissionHistoryDTO.setSearchValue("");
		requirementSubmissionHistoryDTO.setSearchType("");
			DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String currentDate = commonUtil.dateStrFromDate(new Date());
			Date endTime =  commonUtil.orgLocationDateFormat(commonDataDto.getOrgLocation().getDateFormat(),currentDate);
			String customEndTimeStr = TZ_DF_CED.format(endTime);  
			Date endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
			String userType = null;
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole()) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())){
				userType = Constants.ADMIN;
			} 
		
			   ResponseList<RequirementSubmissionHistory> toDayReqApplicantscount =requirementSubmissionDao.getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, commonDataDto.getOrgLocation().getId(),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,null,endUTCDate,commonDataDto.getOrgLocation(),Constants.Dashboard);
			  
			   ArrayList<RequirementSubmissionHistory> subList =  (ArrayList<RequirementSubmissionHistory>) toDayReqApplicantscount.getData();
			  
			   System.out.println(subList);
			  
			   JSONObject jsonObj = null;
			   for(RequirementSubmissionHistory req : subList) {
				   jsonObj = new JSONObject();
				   jsonObj.put("Applicant Name",req.getSubmissionId().getCandidate().getFirstName());
				   jsonObj.put("Job", req.getSubmissionId().getRequirement().getJobTitle());
				   jsonObj.put("Client", req.getSubmissionId().getRequirement().getClientName());
				   jsonObj.put("Source",req.getSubmissionId().getCreatedUser());
				   jsonArray.add(jsonObj);
				   
		     }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toJSONString();
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
