package com.bis.sourcelead.dashboard.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bis.sourcelead.clockinout.service.ClockInOutConfigService;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dashboard.dto.CommonDataDto;
import com.bis.sourcelead.dto.JobFairDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ExpensesCount;
import com.bis.sourcelead.entity.JobFair;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesApproverHierarchyService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.ibm.icu.util.Calendar;

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
	
	@Autowired
	RequirementService requirementService;
	
	@Autowired
	ExpensesApproverHierarchyService expensesApproverHierarchyService;

	@Autowired
	RequirementSubmissionService requirementSubmissionService;
	
	@Autowired
	ClockInOutConfigService clockInOutConfigService;
	
	@Autowired
	RequirementAssignToService requirementAssignToService;
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
	
	@SuppressWarnings("unchecked")
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
			  
			  
			   for(RequirementSubmissionHistory req : subList) {
				   JSONObject jsonObj = new JSONObject();
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

	@SuppressWarnings("unchecked")
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
			  
			   for(RequirementSubmissionHistory req : subList) {
				   JSONObject jsonObj  = new JSONObject();
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
	public String getExpensesDashBoardData(CommonDataDto commonDataDto) {
		Map<String, String> map = new HashedMap();
		try {
		ExpensesCount expensesCount = new ExpensesCount();
		expensesCount = expensesApproverHierarchyService.getExpensesCountByUserIdAndOrgLocationIdForApprover(commonDataDto.getUserId(), commonDataDto.getOrgLocation().getId(),"isGlobalAccess");
		map.put("Approved", expensesCount.getEmpExpensesApprovedCount().toString());
		map.put("Rejected", expensesCount.getEmpExpensesRejectedCount().toString());
		map.put("Waiting", expensesCount.getEmpExpensesSubmittedForApprovalCount().toString());
		}catch (Exception e) {
			e.printStackTrace();
		}
		 return getJsonString(map);
	}
	
	
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

	@SuppressWarnings("unchecked")
	public String getJobFairDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		
		try {
			JobFairDTO jobFairDTO = new JobFairDTO();
			User user = userService.getUserById(commonDataDto.getUserId());
			String[] tableColumns = {"generatedId" ,"name", "startDate", "endDate", "noOfJobs", "", "", "", "status", "id"};
			/*jobFairDTO.setSortColumn("id");
			jobFairDTO.setSortOrder("desc");*/
			
			int sortColumnIndex = 9; //by default sorting using id
			boolean sortColumnOrder = false; //by default descending order
			if(jobFairDTO.getOrder() != null && !jobFairDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(jobFairDTO.getOrder().get(0).get("column"));
				sortColumnOrder = !jobFairDTO.getOrder().get(0).get("dir").equalsIgnoreCase("desc");					
			}
			jobFairDTO.setSortColumn(tableColumns[sortColumnIndex]);
			jobFairDTO.setSortOrder(sortColumnOrder ? "asc" : "desc");
			
			jobFairDTO.setUserId(user.getId());
			jobFairDTO.setOrgLocationId(commonDataDto.getOrgLocation().getId());
			
			if("Admin".equalsIgnoreCase(Constants.ADMIN)) {
				jobFairDTO.setAdminUser(true);
			}
			
			ResponseList<JobFairDTO> response = requirementService.getJobFairsList(jobFairDTO, false);
			   
			   for(JobFairDTO job  : response.getData()) {
			   JSONObject jsonObj = new JSONObject();
			   jsonObj.put("Title",job.getName());
			   jsonObj.put("offered", job.getOfferedCount());
			   jsonObj.put("Applicants", job.getApplicantCount());
			   jsonArray.add(jsonObj);
				   
		     }

		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}

	@SuppressWarnings("unchecked")
	public String getApplicantPremissionDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		try {
			User user = userService.getUserById(commonDataDto.getUserId());
			String requestSent= "requestSent";
			List<RequirementSubmission>  requirementSubmissionList = requirementSubmissionService.getApplicantsByStatus(commonDataDto.getOrgLocation().getId(), user,requestSent);
			for(RequirementSubmission req  : requirementSubmissionList) {
				JSONObject jsonObj = new JSONObject();
				   jsonObj.put("Applicant",req.getCandidateName());
				   jsonObj.put("Job", req.getRequirement().getJobTitle());
				   jsonObj.put("Team member", req.getCreatedUser());
				   jsonArray.add(jsonObj);
					   
			     }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}

	@SuppressWarnings("unchecked")
	public String getAssignedJobsDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		try {
		User user = userService.getUserById(commonDataDto.getUserId());
		String userType = null;
		List<Requirement> requirementList = null;
		if (Constants.ROLE_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())
				|| Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())) {
			userType = Constants.ADMIN;
			requirementList = clockInOutConfigService.getAssignedRequirementListByOrgLocationIDAndUserId(commonDataDto.getOrgLocation().getId(), userType, user.getId());
		}
		else {
			requirementList = clockInOutConfigService.getAssignedRequirementListByOrgLocationIDAndUserId(commonDataDto.getOrgLocation().getId(), userType, user.getId());
		}
		for(Requirement assign :requirementList) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("Job", assign.getJobTitle());
			jsonObj.put("Applicants", assign.getTotalApplicants().toString());
			jsonObj.put("Generated Id", assign.getGeneratedId());
			jsonArray.add(jsonObj);
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	}
	@SuppressWarnings("unchecked")
	public String getJobsPipeLineDashBoardData(CommonDataDto commonDataDto) {
		JSONArray jsonArray = new JSONArray();
		try {
			User user = userService.getUserById(commonDataDto.getUserId());
			if(user != null) {
					String userCode = user.getUserCode();
					String userType = null;
					List<Requirement>   requirementList = null;
					String dayString = "";
					int walkInDay = -1;
					int selectedDay = walkInDay;
					Boolean isWalkIn = false;
					if(isWalkIn) {
						Calendar c = Calendar.getInstance();
						if(walkInDay >= 0) {
							selectedDay = walkInDay;
							c.set(Calendar.DAY_OF_WEEK, walkInDay);
						} else {
							selectedDay = c.get(Calendar.DAY_OF_WEEK);
						}
						dayString = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(c.getTime());
					}
				
					if(Constants.ROLE_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole()) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(commonDataDto.getLoginUserRole())){
						userType = Constants.ADMIN;
						requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(commonDataDto.getOrgLocation().getId(), userType, user.getId(), userCode, isWalkIn, dayString);
					}else{
						requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(commonDataDto.getOrgLocation().getId(), userType, user.getId(), userCode, isWalkIn, dayString);
					}
					// your code here...
					 
					for(Requirement requirement : requirementList){
						 RequirementSubmissionsCountDTO rsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirement.getId(), user, commonDataDto.getOrgLocation(), "ALL");						;
			    		JSONObject jsonObj = new JSONObject();
			    			jsonObj.put("Job", requirement.getJobTitle());
			    			jsonObj.put("GeneratedId", requirement.getGeneratedId());
			    			jsonObj.put("Applicants", rsCount.getApplicantsCount());
			    			jsonObj.put("Shortlist",rsCount.getShortlistCount());
			    			jsonObj.put("Interview",rsCount.getInterviewCount());
			    			jsonObj.put("Submitted", rsCount.getSubmitToClientCount());
			    			jsonObj.put("Offered", rsCount.getOffersCount());
			    			jsonObj.put("Joined", rsCount.getJoinedCount());
			    			jsonArray.add(jsonObj);
						}
						
					
		
		}
		}
			catch(Exception e) {
			e.printStackTrace();
		}
		return jsonArray.toString();

}
}
