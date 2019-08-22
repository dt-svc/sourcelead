package com.bis.sourcelead.rest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.couchbase.UserAccountsDTO;
import com.bis.sourcelead.couchbase.util.UserStatsCacheHelper;
import com.bis.sourcelead.dao.RequirementSubmissionDao;
import com.bis.sourcelead.dto.DashboardCountsDTO;
import com.bis.sourcelead.dto.DashboardItemsDTO;
import com.bis.sourcelead.dto.EventsDTO;
import com.bis.sourcelead.dto.ExpensesAndTimesheetDashBordCountDTO;
import com.bis.sourcelead.dto.InvoiceDTO;
import com.bis.sourcelead.dto.LeaveDTO;
import com.bis.sourcelead.dto.ProjectPlanningResourceRequestDTO;
import com.bis.sourcelead.dto.RequirementDTO;
import com.bis.sourcelead.dto.RequirementSubmissionHistoryDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsCountDTO;
import com.bis.sourcelead.dto.RequirementSubmissionsDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.TimesheetExpenseEntryDetailsUtilDTO;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.DashboardCounts;
import com.bis.sourcelead.entity.ExpensesCount;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.RequirementSubmissionHistory;
import com.bis.sourcelead.entity.SubmissionTemplate;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ExpensesApproverHierarchyService;
import com.bis.sourcelead.service.InvoiceService;
import com.bis.sourcelead.service.LeaveService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectPlanningResourceRequestService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementLocationsService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.SubmissionTemplateService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.timesheet.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.ConversionUtil;
import com.bis.sourcelead.util.ReportsUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.util.UserLoginHelper;
import com.bis.sourcelead.util.dynamicReport.DynamicReport;
import com.bis.sourcelead.web.DashboardController;
import com.ibm.icu.util.Calendar;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@RestController
@RequestMapping("/app")
public class DashboardRestController {

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	public OrgLocationService orgLocationService;
	
	 @Autowired
	 private CandidateService candidateService;
	
	 @Autowired
	 RequirementService requirementService;
	 
	 @Autowired
	 RequirementInterviewerService requirementInterviewerService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 RequirementInterviewService requirementInterviewService;
	 
	 @Autowired
	 RequirementApproverHierarchyService requirementApproverHierarchyService;
	 
	 @Autowired
	 RequirementInterviewHistoryService requirementInterviewHistoryService;
	 
	 @Autowired
	 UserService userService;
	 
	 @Autowired
     UserRoleService userRoleService;
	 
	 @Autowired
	 private RequirementElkUtil requirementElkUtil;
	 
	 @Autowired
	 private ConversionUtil conversionUtil;
	 
	 @Autowired
	 private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 ReportsUtil reportsUtil;
	
	 @Autowired
	 TaskService taskService;
	 
	 @Autowired
	 RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 RequirementOffersService requirementOffersService;
	 
	 @Autowired
	 private ExpensesApproverHierarchyService expensesApproverHierarchyService;
	 
	 @Autowired
	 DashboardController dashboardController;
	 
	 @Autowired
	 RequirementSubmissionDao requirementSubmissionDao;
	 
	 @Autowired
	 OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 RequirementLocationsService requirementLocationsService;
	 
	 @Autowired
	 TeamService teamService;
	 
	 @Autowired
	 private InvoiceService invoiceService;
	 
	 @Autowired 
	 UserLoginHelper userLoginHelper;
	 
	 @Autowired
	 UserStatsCacheHelper userStatsCacheHelper;
	 
	 @Autowired
	 LeaveService leaveService;
	 
	 @Autowired
	 ProjectPlanningResourceRequestService projectPlanningResourceRequestService;
	 
	 @Autowired
	 DynamicReport dynamicReport;
	 
	 
	 @Autowired
	 private SubmissionTemplateService submissionTemplateService;
	 
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	/****
	 * This  controller method is   for getting TimesheetSubmission Counts in dashboard Landing Page Based on User Login
	 * @author: Sammeta David
	 */
    @PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/getTimeSheetSubmissionStatus")
	public TimeSheetSubmissionCount getTimeSheetSubmissionStatus(@RequestParam(value="timesheetType", required=false) String timesheetType,@RequestParam(value="timesheetCountType", required=false) String timesheetCountType,HttpServletRequest request) throws Exception {

	    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Candidate candidate =(Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		Long masterCandidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "masterCandidateId");
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		if(StringUtils.isEmpty(timesheetCountType)) {
			timesheetCountType = "All";
		}
		if(loginOrgLocations != null && (loginOrgLocations.getOrg() == null || loginOrgLocations.getOrg() != null && loginOrgLocations.getOrg().getId() == null)) {
			loginOrgLocations = orgLocationService.getOrgLocations(loginOrgLocations.getId());
		}
		if("employes".equalsIgnoreCase(timesheetType)){
			 Boolean isAdmin = (Boolean) SourceLeadSessionManager.getSessionAttribute(request, "isAdmin");
			 timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(user.getId(), loginOrgLocations.getId(),loginOrgLocations.getOrg().getId(),timesheetCountType,isGlobalAccess,isAdmin);
		}else if("myTimesheets".equalsIgnoreCase(timesheetType)){
			timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByMasterCandidateIdAndOrgLocationId(masterCandidateId, loginOrgLocations.getId(),loginOrgLocations.getOrg().getId(),timesheetCountType);
		}
		
		return timeSheetSubmissionCount;
	}
		
	
	
	
	//@RequestMapping(value = "/rest/getexpensesAndTimeSheetCount")
	public ExpensesAndTimesheetDashBordCountDTO getExpensesAndTimeSheetCount(TimesheetExpenseEntryDetailsUtilDTO tedDTO,HttpServletRequest request) throws Exception {

		ExpensesAndTimesheetDashBordCountDTO expensesAndTimesheetDashBordCountDTO = new ExpensesAndTimesheetDashBordCountDTO();
		User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginUser == null && tedDTO.getUserId() != null){
			loginUser = userService.getUserById(tedDTO.getUserId());
		}
		OrgLocations orgLoc = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		Long masterCandidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "masterCandidateId");
		if(orgLoc == null && StringUtils.isNotEmpty(tedDTO.getLocationCode())){
			orgLoc = orgLocationService.getOrgLocationsByLocationCode(tedDTO.getLocationCode());
		}else if(orgLoc != null && (orgLoc.getOrg() == null || orgLoc.getOrg() != null && orgLoc.getOrg().getId() == null)) {
			orgLoc = orgLocationService.getOrgLocations(orgLoc.getId());
		}
		
		Candidate candidate =(Candidate) SourceLeadSessionManager.getSessionAttribute(request, "candidate");
		if(loginUser != null &&  orgLoc != null) {
			 candidate = candidateService.getCandidateByConsultantUserId(loginUser.getId(), orgLoc.getId());
		}
		if(candidate != null) {
			String timesheetFor = tedDTO.getTimeSheetsFor();
			if(StringUtils.isEmpty(timesheetFor)) {
				timesheetFor = "myTimesheets";
			}
			String expenseFor = tedDTO.getTimeSheetsFor();
			if(StringUtils.isEmpty(expenseFor)) {
				expenseFor = "myExpenses";
			}
			 expensesAndTimesheetDashBordCountDTO = new ExpensesAndTimesheetDashBordCountDTO();
			TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
			
			if("employes".equalsIgnoreCase(timesheetFor)){
				 Boolean isAdmin = userRoleService.isUserAdminByOrgLocationId(loginUser.getId(),orgLoc.getId());
			     timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(loginUser.getId(), orgLoc.getId(),orgLoc.getOrg().getId(),"All",isGlobalAccess,isAdmin);
			}else if("myTimesheets".equalsIgnoreCase(timesheetFor)){
				timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByMasterCandidateIdAndOrgLocationId(candidate.getMasterCandidateId().getId(), orgLoc.getId(),orgLoc.getOrg().getId(),"All");
			}
			
			ExpensesCount expensesCount = new ExpensesCount();
			if("employes".equalsIgnoreCase(expenseFor)){
				expensesCount = expensesApproverHierarchyService.getExpensesCountByUserIdAndOrgLocationIdForApprover(loginUser.getId(), orgLoc.getId(),isGlobalAccess);
			}else if("myExpenses".equalsIgnoreCase(expenseFor) && candidate != null ){
				expensesCount = expensesApproverHierarchyService.getExpensesCountByMasterCandidateId(candidate.getMasterCandidateId().getId());
			}
			
			if(timeSheetSubmissionCount != null) {
				expensesAndTimesheetDashBordCountDTO.setApprovedTimesheetCount(timeSheetSubmissionCount.getApprovedTimesheetSubmissionCount());
				expensesAndTimesheetDashBordCountDTO.setRejectedTimesheetCount(timeSheetSubmissionCount.getRejectedTimesheetSubmissionCount());
				expensesAndTimesheetDashBordCountDTO.setWaitingForApprovalTimesheetCount(timeSheetSubmissionCount.getWaitingForApprovalTimesheetSubmissionCount());
				expensesAndTimesheetDashBordCountDTO.setSavedTimesheetCount(timeSheetSubmissionCount.getSavedTimesheetSubmissionCount());
			}
			
			if(expensesCount != null) {
				if(expensesCount.getExpensesApprovedCount() != null) {
					expensesAndTimesheetDashBordCountDTO.setExpensesApprovedCount((float)expensesCount.getExpensesApprovedCount());
				}else if(expensesCount.getEmpExpensesApprovedCount() != null) {
					expensesAndTimesheetDashBordCountDTO.setExpensesApprovedCount((float)expensesCount.getEmpExpensesApprovedCount());	
				}
				if(expensesCount.getExpensesDraftCount() != null) {
					expensesAndTimesheetDashBordCountDTO.setExpensesDraftCount((float)expensesCount.getExpensesDraftCount());
				}
				if(expensesCount.getExpensesSubmittedForApprovalCount() != null) {
					expensesAndTimesheetDashBordCountDTO.setExpensesSubmittedForApprovalCount((float)expensesCount.getExpensesSubmittedForApprovalCount());
				}
				if(expensesCount.getExpensesRejectCount() != null) {
					expensesAndTimesheetDashBordCountDTO.setExpensesRejectCount((float)expensesCount.getExpensesRejectCount());
				}else if(expensesCount.getEmpExpensesRejectedCount() != null) {
					expensesAndTimesheetDashBordCountDTO.setExpensesRejectCount((float)expensesCount.getEmpExpensesRejectedCount());
				}
				if(expensesCount.getEmpExpensesReopenCount() != null) {
					expensesAndTimesheetDashBordCountDTO.setEmpExpensesReopenCount((float)expensesCount.getEmpExpensesReopenCount());
				}else if(expensesCount.getEmpExpensesReopenCount()!= null) {
					expensesAndTimesheetDashBordCountDTO.setEmpExpensesReopenCount((float)expensesCount.getEmpExpensesReopenCount());
				}
			}
		}
		return expensesAndTimesheetDashBordCountDTO;
	}
	
	
	
	
	
	@RequestMapping(value = "/dashboard/{orgLocationId}/{userId}" , method = RequestMethod.GET)
	 public @ResponseBody void userSummary(@PathVariable Long orgLocationId,@PathVariable Long userId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		   
		     User user = userService.getUserById(userId);

		     UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
   			    userRole.setUserState(Status.ACTIVE);
   			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
   			   //user update/save in user Elk
   			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
   		      }
		     
		      Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		      String authToken[] = auth.getName().split("##");
		      String uname = user.getUserName()+"##"+userRole.getId()+"##"+authToken[2];
		      String actionUUID = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionUUID");
		  	  Long actionId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"actionId");
		      userStatsCacheHelper.saveLoginUserSessionStoreToCouchbase(authToken[2],userRole,actionUUID,actionId,request);
				 
		     SecurityContextHolder.clearContext();
			 SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(request, uname, null, null));
				
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/dashboard").toString());
			
	}
	
	
	
	@RequestMapping(value = "/switchLocation" , method = RequestMethod.GET)
	 public @ResponseBody void userSummary(@RequestParam(value="userRoleId") Long userRoleId,HttpServletRequest request,HttpServletResponse response) throws Exception {
		      UserRole userRole = userRoleService.getUserRoleById(userRoleId);
		      User user = userRole.getUser();
		      Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		      String authToken[] = auth.getName().split("##");
		      String uname = user.getUserName()+"##"+userRole.getId()+"##"+authToken[2];
		      String actionUUID = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionUUID");
		  	  Long actionId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"actionId");
		      userStatsCacheHelper.saveLoginUserSessionStoreToCouchbase(authToken[2],userRole,actionUUID,actionId,request);
				
		     SecurityContextHolder.clearContext();
			 SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(request, uname, null, null));
				
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/dashboard").toString());
			
	}
	
	@RequestMapping(value = "/productionSupportDashboard/{orgLocationId}/{userId}/{productionLoginUserId}" , method = RequestMethod.GET)
	 public @ResponseBody void productionSupportDashboard(@PathVariable Long orgLocationId,@PathVariable Long userId,@PathVariable Long productionLoginUserId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     
		     User user = userService.getUserById(userId);

		     UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
  			    userRole.setUserState(Status.ACTIVE);
  			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
  			   
  			   //user update/save in user Elk
  			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
  		      }
		     
		      Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		      String authToken[] = auth.getName().split("##");
		      String uname = user.getUserName()+"##"+userRole.getId()+"##"+authToken[2];
		      String actionUUID = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionUUID");
		  	  Long actionId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"actionId");
		      userStatsCacheHelper.saveLoginUserSessionStoreToCouchbase(authToken[2],userRole,actionUUID,actionId,request);
				
		     SourceLeadSessionManager.setSessionAttribute(request, "productionLoginUserId", productionLoginUserId);
		     SecurityContextHolder.clearContext();
			 SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(request, uname, null, null));
				
		     
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/dashboard").toString());
			
	}
	
	@RequestMapping(value = "/professionalDashboard/{orgLocationId}/{switchUserType}" , method = RequestMethod.GET)
	 public @ResponseBody void professionalUserSummary(@PathVariable Long orgLocationId,@PathVariable String switchUserType,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		 
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     UserRole userRole = null;
		    
		     if(!isNullOrEmpty(switchUserType) && switchUserType.equalsIgnoreCase("Professional") && orgLocationId == 1L) {
		    	 userRole =  userRoleService.getUserDetailsByUserIdAndLocationIdAndUserType(user.getId(), orgLocationId, switchUserType);
		     }else {
		    	 userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     }
		     
		     
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
  			    userRole.setUserState(Status.ACTIVE);
  			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
  			   
  			   //user update/save in user Elk
  			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
  		      }
		     
		      Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		      String authToken[] = auth.getName().split("##");
		      String uname = user.getUserName()+"##"+userRole.getId()+"##"+authToken[2];
		      String actionUUID = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionUUID");
		      Long actionId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"actionId");
		      userStatsCacheHelper.saveLoginUserSessionStoreToCouchbase(authToken[2],userRole,actionUUID,actionId,request);
		     
		     SecurityContextHolder.clearContext();
			 SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(request, uname, null, null));
		     
		     SourceLeadSessionManager.setSessionAttribute(request, "userRoleId", userRole.getId());
		     SourceLeadSessionManager.setSessionAttribute(request, "switchUserType", switchUserType);
		     
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/dashboard").toString());
			
	}
	
	@RequestMapping(value = "/recruiterDashboard/{orgLocationId}/{switchUserType}" , method = RequestMethod.GET)
	 public @ResponseBody void recRuturUserSummary(@PathVariable Long orgLocationId,@PathVariable String switchUserType,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		    
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     UserRole userRole = null;
		     
		     if(!isNullOrEmpty(switchUserType) && switchUserType.equalsIgnoreCase("Recruiter") && orgLocationId == 1L) {
		    	 userRole =  userRoleService.getUserDetailsByUserIdAndLocationIdAndUserType(user.getId(), orgLocationId, switchUserType);
		     }else {
		    	 userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     }
		     
		    
		     
		     
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
 			    userRole.setUserState(Status.ACTIVE);
 			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
 			   
 			   
 			   
 			   //user update/save in user Elk
 			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
 		      }
		     
		      Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		      String authToken[] = auth.getName().split("##");
		      String uname = user.getUserName()+"##"+userRole.getId()+"##"+authToken[2];
		      String actionUUID = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionUUID");
		      Long actionId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"actionId");
		      userStatsCacheHelper.saveLoginUserSessionStoreToCouchbase(authToken[2],userRole,actionUUID,actionId,request);
		     
		     SecurityContextHolder.clearContext();
			 SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(request, uname, null, null));
				
		     SourceLeadSessionManager.setSessionAttribute(request, "userRoleId", userRole.getId());
		     SourceLeadSessionManager.setSessionAttribute(request, "switchUserType", switchUserType);
		     
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/dashboard").toString());
			
			
	}
	
	
	@RequestMapping(value = "/dashboard/{orgLocationId}/{switchUserType}/{userId}" , method = RequestMethod.GET)
	 public @ResponseBody void userSummaryWithUserType(@PathVariable Long orgLocationId,@PathVariable String switchUserType,@PathVariable Long userId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     //to update user state in user role based on location----> Prasad Salina
		     User user = userService.getUserById(userId);
		     UserRole userRole = null;
		     if(!isNullOrEmpty(switchUserType) && (switchUserType.equalsIgnoreCase("Professional") || switchUserType.equalsIgnoreCase("Recruiter")) && orgLocationId == 1L){
		    	 userRole =  userRoleService.getUserDetailsByUserIdAndLocationIdAndUserType(user.getId(), orgLocationId, switchUserType);
		     }else {
		    	 userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     }
		     
		     
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
  			    userRole.setUserState(Status.ACTIVE);
  			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
  			   
  			   
  			   
  			   //user update/save in user Elk
  			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
  		      }
		     
		      Authentication auth = SecurityContextHolder.getContext().getAuthentication();	
		      String authToken[] = auth.getName().split("##");
		      String uname = user.getUserName()+"##"+userRole.getId()+"##"+authToken[2];
		      String actionUUID = (String) SourceLeadSessionManager.getSessionAttribute(request, "actionUUID");
		      Long actionId = (Long) SourceLeadSessionManager.getSessionAttribute(request,"actionId");
		      userStatsCacheHelper.saveLoginUserSessionStoreToCouchbase(authToken[2],userRole,actionUUID,actionId,request);
		     
		     SecurityContextHolder.clearContext();
			 SecurityContextHolder.getContext().setAuthentication(userLoginHelper.redirectToDashboard(request, uname, null, null));
				
		     SourceLeadSessionManager.setSessionAttribute(request, "userRoleId", userRole.getId());
		     SourceLeadSessionManager.setSessionAttribute(request, "switchUserType", switchUserType);
		     
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/dashboard").toString());
			
	}
	
	
	@RequestMapping(value = "/productionSupportdashboard/{orgLocationId}/{loginOrgLocatonId}" , method = RequestMethod.GET)
	 public @ResponseBody void productionSupportdashboard(@PathVariable Long orgLocationId,@PathVariable Long loginOrgLocatonId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     //to update user state in user role based on location----> Prasad Salina
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

		     UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
 			    userRole.setUserState(Status.ACTIVE);
 			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
 			   
 			   
 			   
 			   //user update/save in user Elk
 			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
 		      }
				
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/app/dashboard?orgLocationId=").append(orgLocationId).append("&loginOrgLocatonId=").append(loginOrgLocatonId).toString());
			
	}
	
	
	@RequestMapping(value = "/dashboardPipelineJobsCount" , method = RequestMethod.GET)
	 public @ResponseBody RequirementSubmissionsCountDTO dashboardPipelineJobsCount(@RequestParam(value = "requirementId", required = true) Long requirementId,@RequestParam(value = "submissionType", required = false) String submissionType, HttpServletRequest request) throws Exception {
			
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		     RequirementSubmissionsCountDTO rsCount = requirementSubmissionService.getAllPipeLineCountsByRequirementIdAndSubmissionStage(requirementId, user, orgLocation, "ALL");
		     return rsCount;
	}
	
	
	
	 /**
	   * @author sarath kumar
	   * @since  aug 30 2016
	   * @version 0.0.1
	   * 
	   * This method is used to for getting CandidatesList By OrgLocationID
	   * this method is called at onload when click on timesheet left menu
	   * 
	   * @param user This is the first parameter to getCandidatesListByOrgLocationID method
	   * @param navigation This is the second parameter to getCandidatesListByOrgLocationID method
	   * @param startDate This is the third parameter to getCandidatesListByOrgLocationID method
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @PreAuthorize("hasRole('ROLE_VIEW_REQUIREMENT')")
	 @RequestMapping(value = "/getAssignedRequirementListByOrgLocationIDAndUserId")
	 public ResponseList<Requirement> getAssignedRequirementListByOrgLocationIDAndUserId(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,@RequestParam(defaultValue="false") boolean isWalkIn, @RequestParam(defaultValue="-1") int walkInDay, HttpServletRequest request) throws Exception {
		ResponseList<Requirement> response = new ResponseList<>();
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				 
				 
				 String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				 Long orgLocationId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
				
				String userCode = user.getUserCode();
				String userType = null;
				List<Requirement>   requirementList = null;
				String dayString = "";
				String selectedDayString = "";
				int selectedDay = walkInDay;
				if(isWalkIn) {
					Calendar c = Calendar.getInstance();
					if(walkInDay >= 0) {
						selectedDay = walkInDay;
						c.set(Calendar.DAY_OF_WEEK, walkInDay);
					} else {
						selectedDay = c.get(Calendar.DAY_OF_WEEK);
					}
					dayString = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(c.getTime());
					selectedDayString = new SimpleDateFormat("EEEE (dd/MM/yyyy)", Locale.ENGLISH).format(c.getTime());
				}
			
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
					requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(orgLocationId, userType, user.getId(), userCode, isWalkIn, dayString);
				}else{
					requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(orgLocationId, userType, user.getId(), userCode, isWalkIn, dayString);
				}
				// your code here...
				 
				for(Requirement requirement : requirementList){
					 
					//changed ny prasad salina
					if(requirement.getClientId() != null){
						if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
							requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
							requirement.setOrgRelationUUID(requirement.getClientId().getUUID());
						}
						if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
							requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
							requirement.setOrgRelationUUID(requirement.getClientId().getUUID());
						}
					}
					
					Integer requirementAssignToListCount = requirementAssignToService.getRequirementAssignToListCountByRequirementId(requirement.getId());
		    		requirement.setAssignedMembersCount(requirementAssignToListCount.toString());

		    		
					
					if(null != requirement.getReleasedBy() && !"".equalsIgnoreCase(requirement.getReleasedBy())){
						List<Long> userId = new ArrayList<Long>();
						for (String s : requirement.getReleasedBy().split(",")){
							userId.add(Long.parseLong(s));
						 }
						boolean flag = userId.contains(user.getId());
			     		if(flag == true){
			     			requirement.setIsRequirementReleasedByuser("Y");
			     		}else{
			     			requirement.setIsRequirementReleasedByuser("N");
			     		}
			     		
			     		
			     		if(userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_SOURCELEAD_ADMIN")){
		         			requirement.setIsRequirementReleasedByuser("Y");
		         		}
					}
					
				}
				response.setData(requirementList);
				
			    response.setResponse(Result.SUCCESS.name());
			    if(isWalkIn)
			    	response.setResponseMessage(Result.SUCCESS.getValue() + ":" + selectedDay +":"+selectedDayString);
			    else
			    	response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	 }
	 
	 /**
	  * This method is used for getting all Submissions,Applicat's,Interviews,Offers counts and Show in Dashboard page.
	  * 
	  * @author prasad K
	  * @param daterange
	  * @return String
	  * @throws NullPointer
	  */
	
	 @RequestMapping(value = "/getRequirementSubmissionsCount" , method = RequestMethod.GET)
	 public Response<DashboardCounts>  getRequirementSubmissionsCount(@RequestParam(value="daterange", required=false) String daterange,@RequestParam(value="orgLocationId", required=false) Long orgLocationId,@RequestParam(value="userId", required=false) Long userId,HttpServletRequest request) throws Exception {
		 Response<DashboardCounts> response = new Response<>();
		 OrgLocations loginOrgLocations = orgLocationService.getOrgLocations(orgLocationId);
			DashboardCounts dc= new DashboardCounts();
			Timezone timezone = null;

			UserRole userRole = userRoleService.getUserByUserId(userId);

		if(userRole != null) {
			try {
				if(loginOrgLocations.getTimezone() != null){
					timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
				}
				String userType = null;
				Date startUTCDate = null;
				Date endUTCDate = null;
				DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
				 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd 00:00:00");
				 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd 23:59:59");
				if("today".equals(daterange)){
					 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
					 String customStartTimeStr = outputFormatter.print(zoned);
					 startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
					 String endDate = outputFormatter1.print(zoned);
					 Date endDateString = TZ_DF_TS.parse(endDate);
					 endUTCDate = endDateString;
					  
				}else{

					DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
					String startDate = outputFormatter.print(zoned);
					Date startDateUTC = TZ_DF_TS.parse(startDate);
					
					DateTime oneMonthZoned = zoned.minusDays(30);
					String oneMonthDate = outputFormatter.print(oneMonthZoned);
					Date oneMonthDateUTC = TZ_DF_TS.parse(oneMonthDate);
					
					Date[] dates = new Date[2];
					dates[0] = oneMonthDateUTC;
					dates[1] = startDateUTC;
					startUTCDate = dates[0]; 
					endUTCDate = dates[1];

					 
				}
				
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole.getUserType()) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole.getUserType())){
					userType = Constants.ADMIN;
				} 
				
				Integer[] requirementSubmissionHistoryCounts = requirementSubmissionService.getRequirementSubmissionsListsByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),daterange, userRole.getUserType(), userRole.getUser(), startUTCDate, endUTCDate);
				
			    dc.setApplicantsCount(requirementSubmissionHistoryCounts[0]); //0 index is applicant count as per QueryResult
			    
			    dc.setInterviewsCount(requirementSubmissionHistoryCounts[1]); //1 index is interview count as per QueryResult
			    
			    dc.setSubmissionCount(requirementSubmissionHistoryCounts[2]); //2 index is  submission count as per QueryResult

			    dc.setOffersCount(requirementSubmissionHistoryCounts[3]); //3 index is offer count as per QueryResult
			   
			    response.setObject(dc);
			    response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	   }

	 
	 /**
	   * @author sarath kumar
	   * @since  oct 26 2016
	   * @version 0.0.1
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @RequestMapping(value = "/getAssignedRequirementApproverListByOrgLocationIDAndUserId")
	 public String getAssignedRequirementApproverListByOrgLocationIDAndUserId(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,HttpServletRequest request) throws Exception {

		String resultJson;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		String userCode = user.getUserCode();
		String userType = null;
		List<Requirement>   requirementList = null;
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}else{
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}
		
		//List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(),user.getId());
		for(Requirement requirement : requirementList){
			// to get the RequirementApproverHierarchy to set the Status
			RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirement.getId(), user.getId());
			if(requirementApproverHierarchy!=null){
				requirement.setRequirementApproverHierarchyStatus(requirementApproverHierarchy.getRequirementApproverStatus().toString());
			}
		}	
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	 }
	 
	 /**
	   * @author sarath kumar
	   * @since  oct 26 2016
	   * @version 0.0.1
	   * 
	   *  
	   * @return string 
	   * 
	   * @throws NullPointer
	   */
	 
	 @RequestMapping(value = "/getApproverRequirementApproverListByOrgLocationIDAndUserId",  method = RequestMethod.GET)
	 public ResponseList<Requirement> getApproverRequirementApproverListByOrgLocationIDAndUserId(@RequestParam(value="statusType", required=false) String statusType,HttpServletRequest request) throws Exception {
		 ResponseList<Requirement> response = new ResponseList<>();
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(user != null) {
			try {
				OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				
				String userCode = user.getUserCode();
				String userType = null;
				List<Requirement>   requirementList = null;
				ArrayList<Requirement>   newList = new ArrayList<Requirement>();
				
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
					requirementList = requirementService.getApproverRequirementApproverListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode,statusType);
				}else{
					requirementList = requirementService.getApproverRequirementApproverListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode,statusType);
				}
				
				//List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(),user.getId());
				for(Requirement requirement : requirementList){
					// to get the RequirementApproverHierarchy to set the Status
					RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirement.getId(), user.getId());
					if(null != requirementApproverHierarchy ){
						       requirement.setRequirementApproverHierarchyStatus(requirementApproverHierarchy.getRequirementApproverStatus().toString());
						       // to set the approved/rejected by user
						       User approvedUser = userService.getUserByUserCode(requirementApproverHierarchy.getUpdatedBy());
					                    if(approvedUser !=null ){
					                    	StringBuilder sb = new StringBuilder();
											requirement.setApprovedBy(sb.append(approvedUser.getFirstName()).append(" ").append(approvedUser.getLastName()).toString());
					                    }
					                    newList.add(requirement);
					}else if(!"SUBMITTED".equalsIgnoreCase(statusType) && !"".equalsIgnoreCase(statusType)){
						
						 List<RequirementApproverHierarchy>  rahlist = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirement.getId());
					                if(rahlist !=null ){
											 for(RequirementApproverHierarchy rah :rahlist ){
												  requirement.setRequirementApproverHierarchyStatus(rah.getRequirementApproverStatus().toString());
								            	  if("APPROVED".equalsIgnoreCase(rah.getRequirementApproverStatus().toString()) || "REJECTED".equalsIgnoreCase(rah.getRequirementApproverStatus().toString())  || "OPEN".equalsIgnoreCase(requirement.getRequirementSubmissionStatus().toString())){
								            		  User approvedUser = userService.getUserByUserCode(rah.getUpdatedBy());
								            		  if(approvedUser !=null ){
									                    	StringBuilder sb = new StringBuilder();
															requirement.setApprovedBy(sb.append(approvedUser.getFirstName()).append(" ").append(approvedUser.getLastName()).toString());
									                    }
								            		 
								            	  }
							              }
									 newList.add(requirement);
					                }
					}
				}
				response.setData(newList);
			    response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			}catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		}else {
			response.setResponse(Result.SESSION_TIMEDOUT.name());
			response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
		}
		return response;
	 }
	 
	
	 /**
		 * 
		 * @author: Venkat Kesav
		 * This  controller method is  to get Interviews list 
		 */
		@PreAuthorize("hasRole('ROLE_VIEW_INTERVIEW')")
		@RequestMapping(value = "/getInterviews")
		public String getInterviews(String dateRange, HttpServletRequest request) throws Exception {
			
			

			String resultJson;
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<RequirementInterviewHistory> requirementInterviewList = null;
			//SimpleDateFormat simpleDateFormat = loginOrgLocations.getLocFullFormDateFormatter();
			String currentTime = null;
			String userType = null;
			Timezone timezone = null;
			String userCode = user.getUserCode();
			Long userId = user.getId();
			if(loginOrgLocations.getTimezone() != null){
				timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
			}
			
			Date startUTCDate = null;
			Date endUTCDate = null;
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			if(dateRange != null && !"today".equals(dateRange) && !"previous".equals(dateRange) && !"upcoming".equals(dateRange)){
				DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
				Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),dateRange);
				String startTimeTS = Constants.TZ_DF.format(startTime); 
				String endTimeTS = END_TZ_DF.format(startTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),"UTC", startTimeTS);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),"UTC", endTimeTS);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
				 endUTCDate = TZ_DF_TS.parse(endDateString);
			}
			else if("today".equals(dateRange)){
				 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
				 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
				 String startDate = outputFormatter.print(zoned);
				 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
				 String endDate = outputFormatter1.print(zoned);
				 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
				 endUTCDate = TZ_DF_TS.parse(endDateString);
			}
			else{
				 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
				 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
				 String startDate = outputFormatter.print(zoned);
				 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
				 startUTCDate = TZ_DF_TS.parse(startDateString);
			}
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
				userType = Constants.ADMIN;
				requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
				}else{
				requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
			}
			
			
			
			
			resultJson = commonUtil.toJSON(requirementInterviewList);
			return resultJson;
		}
		
		/**
		 * @author sarath kumaar
		 * @since  oct 18 2016
		 * @version 0.0.1
		 * 
		 * This method is used to accept/Reject Requirement in Requirement table.
		 *  
		 * @param jsonObj This is the first paramter to storeRequirement method
		 * @param orgLocations This is the second paramter to storeRequirement method
		 * @param user This is the third paramter to storeRequirement method
		 * @param multiFile This is the fourth paramter to storeRequirement method
		 * 
		 * @return success msg  
		 * 
		 * @throws NullPointer
		*/
		
		@RequestMapping(value = "/requirementsAcceptOrReject", method = RequestMethod.POST)
		public @ResponseBody Response<String> requirementsAcceptOrReject(@RequestParam(value = "requirementId", required = false) Long requirementId,@RequestParam(value = "status", required = false)  String status, @RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception {
			Response<String> response = new Response<>();
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(loginUser != null) {
				try {
					OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
					RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());
					if(status.equalsIgnoreCase("REJECTED")  &&  !reason.equalsIgnoreCase("") && !reason.isEmpty()){
				    	requirementApproverHierarchy.setRejectedReason(reason);
				    	requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
				    }
					
				//  This for the Requirement Location 
							StringBuilder location = new StringBuilder();
							if(null != requirement.getCity() && !requirement.getCity().equalsIgnoreCase("")){
								location.append(requirement.getCity()).append(" ,");
							 }
							if(null != requirement.getState() && !requirement.getState().equalsIgnoreCase("")){
								location.append(requirement.getState()).append(" ,");
							 }
							
							 location.append(requirement.getCountry()).append(" ");
						
					// requirement created user
		
					// to get RequirementApproverHierarchy with maximum Level
					Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);
		
					if (maxLevel != null) {
						if (maxLevel.equals(requirementApproverHierarchy.getLevel())) {
								if (status.equalsIgnoreCase("APPROVED")) {
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
								} else if (status.equalsIgnoreCase("REJECTED")) {
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
								}
									requirement.setUpdatedBy(loginUser.getUserCode());
									requirement.setUpdatedDate(commonUtil.currentDate());
									requirementService.addRequirement(requirement);
									List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
									String approverArray[] = new String[list1.size()];
									int i = 0;
									for (RequirementApproverHierarchy rah : list1) {
										if (status.equalsIgnoreCase("APPROVED")) {
											rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
											requirement.setReqlevel2ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED.toString());
										} else if (status.equalsIgnoreCase("REJECTED")) {
											//rah.setRejectedReason(reason);
											rah.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
											requirement.setReqlevel2ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
										}
										rah.setUpdatedBy(loginUser.getUserCode());
										rah.setUpdatedDate(commonUtil.currentDate());
										approverArray[i++] = rah.getApproverUserId().getId() + "";
										requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
									}
									requirement.setLevel1ApproverUserId(approverArray);
								// this is for sending email to requirement initiator
								if (status.equalsIgnoreCase("APPROVED")) {
									requirementService.acceptEmailScenario(loginUser, requirement.getCreatedBy(), requirement,location, orgLocation);
								} else if (status.equalsIgnoreCase("REJECTED")) {
									requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
								}
								// for URL redirections from mail
								String redirectURL = "/requirementSummary-params-requirementUUID="+requirement.getRequirementUUID();
								Long redirectOrgLocationId = orgLocation.getId();
								 /*This logic is to send mail to assigned team members after approver accepted the requirement request(parallel approver)*/
								RequirementApproverHierarchy hierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
						    	 List<RequirementAssignTo> reqAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(requirementId,orgLocation.getId());
					    			if(null != hierarchy && "APPROVED".equals(hierarchy.getRequirementApproverStatus().toString())){
					    				for(RequirementAssignTo reqAssign:reqAssignTo ){
												if(null != reqAssign.getAssignUserId() && !loginUser.getId().equals(reqAssign.getAssignUserId().getId())){
													requirementService.AssignToTeamMembersEmail(loginUser, reqAssign.getAssignUserId(), requirement,new StringBuilder(),orgLocation,redirectURL,redirectOrgLocationId);
												}
					    				}
					    		}
								
						}else{
							List<RequirementApproverHierarchy> list1 = requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
							if(list1 != null && !list1.isEmpty()) {
								for (RequirementApproverHierarchy rah : list1) {
									if(rah.getLevel() == 1) {
										String[] levelUserIds = new String[1];
										levelUserIds[0] = rah.getApproverUserId().getId() + "";
										requirement.setLevel1ApproverUserId(levelUserIds);
									}
									if(rah.getLevel() == 2) {
										String[] levelUserIds = new String[1];
										levelUserIds[0] = rah.getApproverUserId().getId() + "";
										requirement.setLevel2ApproverUserId(levelUserIds);
									}
								}
							}
							    RequirementApproverHierarchy level1approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdAndMaximumLevel(requirementId);
								User approverUser = userService.getUserById(level1approver.getApproverUserId().getId());
								if (status.equalsIgnoreCase("APPROVED")) {
									if (null != level1approver) {
										// this is for sending email to second level approver
										requirementService.approverTwoEmailScenario(loginUser, approverUser, requirement, location,	orgLocation);
									}
									requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
									requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED.toString());
								} else if (status.equalsIgnoreCase("REJECTED")) {
									// this is for sending email to requirement initiator
									requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
									requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
									requirementApproverHierarchy.setRejectedReason(reason);
									requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
									requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
									
									// This is  to update  the level2 Approver Record For RejectedS
								    RequirementApproverHierarchy level2approver = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementIdExcludingApproverHierarchyId(requirementApproverHierarchy.getId(), requirementId);
								    level2approver.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
								    requirement.setReqlevel1ApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED.toString());
								    level2approver.setUpdatedBy(loginUser.getUserCode());
								    level2approver.setUpdatedDate(commonUtil.currentDate());
									requirementApproverHierarchyService.addRequirementApproverHierarchy(level2approver);
			
								}
								requirementApproverHierarchy.setUpdatedBy(loginUser.getUserCode());
								requirementApproverHierarchy.setUpdatedDate(commonUtil.currentDate());
								requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
								requirement.setUpdatedBy(loginUser.getUserCode());
								requirement.setUpdatedDate(commonUtil.currentDate());
								requirementService.addRequirement(requirement);
						}
					}
					
					// pass dto as parameter
					RequirementDTO requirementDTO = commonUtil.getRequirementDTO(requirement);
					requirementElkUtil.saveElkRequirement(requirement,requirementDTO);
					response.setObject("success");
				    response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			return response;
		}
		
		@RequestMapping(value = "/getApplicantsByStatus", method = RequestMethod.POST)
		public ResponseList<RequirementSubmission> getApplicantsByStatus(@RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception {
			ResponseList<RequirementSubmission> response = new ResponseList<>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				try {
					OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					List<RequirementSubmission> requirementSubmissionList = requirementSubmissionService.getApplicantsByStatus(loginOrgLocations.getId(), user,status);
				 	response.setData(requirementSubmissionList);
				    response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			return response;
		}
		
	@RequestMapping(value = "/getJobFairDates")
	public ResponseList<String> getJobFairDates(String dateRange, HttpServletRequest request) throws Exception {
		ResponseList<String> response = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				String userType = null;
				String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
				}
				List<String> datesList = requirementService.getJobFairDaysListByOrgLocationIDAndUserId(loginOrgLocations.getId(), userType, user.getId());
				response.setData(datesList);
				response.setResponse(Result.SUCCESS.name());
				response.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
		
		
		@RequestMapping(value = "/getInterviewsTaskDates")
		public ResponseList<String> getInterviewsTaskDates(String dateRange, HttpServletRequest request) throws Exception {
			ResponseList<String> response = new ResponseList<>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				try {
					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations loginOrgLocations = orgLocationService.getOrgLocationById(orgLocId);
					List<Date> datesList = new ArrayList<>();
					List<String> interviewDatesList = new ArrayList<>();
					datesList = requirementInterviewService.getInterviewDatesForCalendarEvent(user.getId(), loginOrgLocations.getId());
					Timezone timezone = null;
					if(loginOrgLocations.getTimezone() != null){
						timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
					}
					if(datesList != null) {
						for(Date date : datesList) {
							    String converted_dateStr =  CalendarUtil.getDateStrForGivenTimeZone(timezone.getTimeZoneId(), date, "yyyy/MM/dd");
							    if(StringUtils.isNotEmpty(converted_dateStr)) {
							    	 interviewDatesList.add(converted_dateStr);
							    }
						}
					}
					response.setData(interviewDatesList);
					response.setResponse(Result.SUCCESS.name());
					response.setResponseMessage(Result.SUCCESS.getValue());
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			return response;
		}
		
		/**
		   * @author Venkat Kesav
		   * @since  feb 22 2018
		   * @version 0.0.1
		   * 
		   * This method is used for getting Interviews and TasksList By login User for mobile App
		   *  
		   * @return EventsDTO 
		   * 
		   * @throws NullPointer
		   */
		@RequestMapping(value = "/rest/getInterviewsAndTasksList", method = RequestMethod.POST )
		public EventsDTO getInterviewsAndTasksList(EventsDTO eventsDTO, HttpServletRequest request) throws Exception {
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<RequirementInterviewHistory> requirementInterviewList = new ArrayList<RequirementInterviewHistory>();
			List<Tasks> taklist = new ArrayList<Tasks>();
			String currentTime = null;
			String userType = null;
			Timezone timezone = null;
			String userCode = user.getUserCode();
			Long userId = user.getId();
			if(loginOrgLocations.getTimezone() != null){
				timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
			}
			
			Date startUTCDate = null;
			Date endUTCDate = null;
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			if(!StringUtils.isNotBlank(eventsDTO.getStartDate())) {
				Date startDateWithTime = new Date();
				String startTimeTS = Constants.TZ_DF.format(startDateWithTime);
				String startDateString = CommonUtil.convertTimeUTC(Constants.UTC_TIMEZONE, startTimeTS);
				startUTCDate = TZ_DF_TS.parse(startDateString);
				/*if(!StringUtils.isNotBlank(eventsDTO.getEndDate())) {
					DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
					String endTimeTS = END_TZ_DF.format(startDateWithTime);
					String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
					endUTCDate = TZ_DF_TS.parse(endDateString);
				}*/
			}else if(StringUtils.isNotBlank(eventsDTO.getStartDate())) {
				Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),eventsDTO.getStartDate());
				String startTimeTS = Constants.TZ_DF.format(startTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startTimeTS);
				startUTCDate = TZ_DF_TS.parse(startDateString);
			}
			
			if(StringUtils.isNotBlank(eventsDTO.getEndDate())) {
				Date endTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),eventsDTO.getEndDate());
				String endTimeTS = Constants.TZ_DF.format(endTime);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
				endUTCDate = TZ_DF_TS.parse(endDateString);
			}
				
			if(eventsDTO.getEventType().equalsIgnoreCase("interviews")) {
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
						requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, eventsDTO.getDateRange(), currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
					}else{
						requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, eventsDTO.getDateRange(), currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
				}
			}else if(eventsDTO.getEventType().equalsIgnoreCase("tasks")) {
				String status =  "myTask";
				Date converteDate = null;
				taklist = taskService.tasksListByIdAndOrgLocationIdAssignedUserId(loginOrgLocations.getId(),user.getId(),status,eventsDTO.getDateRange(),converteDate,timezone);
			}
			
			eventsDTO.setInterviewerList(requirementInterviewList);
			eventsDTO.setTasksList(taklist);
			return eventsDTO;
		}
		
		
		/**
		   * @author Venkat Kesav
		   * @since  March 06 2018
		   * @version 0.0.1
		   * 
		   * This method is used for getting Dashboard Counts By login User for mobile App
		   *  
		   * @return DashboardDTO 
		   * 
		   * @throws NullPointer
		   */
		@RequestMapping(value = "/rest/getDashboardCompact", method = RequestMethod.POST )
		public DashboardCountsDTO getDashboardCompact(DashboardCountsDTO dashboardDTO, HttpServletRequest request) throws Exception {
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			long interviewCount = 0;
			long taskCount = 0;
			String userType = null;
			
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
				userType = Constants.ADMIN;
			}
			Long totalInterviewCount = requirementInterviewHistoryService.getCountOfAllInterviewAssignedToLoginUser(loginOrgLocations, userType, user.getId(), user.getUserCode());
			if(totalInterviewCount != null) {
				interviewCount = totalInterviewCount;
			}
			
			String status =  "myTask";
			Long totalTaskCount = taskService.taskCountByIdAndOrgLocationIdAssignedUserId(loginOrgLocations.getId(), user.getId(), status);
			if(totalTaskCount != null) {
				taskCount = totalTaskCount;
			}
			dashboardDTO.setInterviewCount(interviewCount);
			dashboardDTO.setTaskCount(taskCount);
			TimesheetExpenseEntryDetailsUtilDTO tedDTO = new TimesheetExpenseEntryDetailsUtilDTO();
			ExpensesAndTimesheetDashBordCountDTO timesheetExpenses = getExpensesAndTimeSheetCount(tedDTO, request);			
			dashboardDTO.setApprovedTimesheetCount(timesheetExpenses.getApprovedTimesheetCount());
			dashboardDTO.setRejectedTimesheetCount(timesheetExpenses.getRejectedTimesheetCount());
			dashboardDTO.setSavedTimesheetCount(timesheetExpenses.getSavedTimesheetCount());
			dashboardDTO.setWaitingForApprovalTimesheetCount(timesheetExpenses.getWaitingForApprovalTimesheetCount());
			dashboardDTO.setExpensesApprovedCount(timesheetExpenses.getExpensesApprovedCount());
			dashboardDTO.setEmpExpensesReopenCount(timesheetExpenses.getEmpExpensesReopenCount());
			dashboardDTO.setExpensesDraftCount(timesheetExpenses.getExpensesDraftCount());
			dashboardDTO.setExpensesRejectCount(timesheetExpenses.getExpensesRejectCount());
			dashboardDTO.setExpensesSubmittedForApprovalCount(timesheetExpenses.getExpensesSubmittedForApprovalCount());
			return dashboardDTO;
		}
		
		
		@RequestMapping(value = "/getInterviewsandTasks")
		public Response<RequirementInterviewHistory> getInterviewsandTasks(String dateRange, HttpServletRequest request) throws Exception {
			Response<RequirementInterviewHistory> response = new Response<>();
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if(user != null) {
				try {
					String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
					Long orgLocId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgLocationId");
					OrgLocations loginOrgLocations = orgLocationService.getOrgLocationById(orgLocId);
					List<RequirementInterviewHistory> requirementInterviewList = null;
					//SimpleDateFormat simpleDateFormat = loginOrgLocations.getLocFullFormDateFormatter();
					String currentTime = null;
					String userType = null;
					Timezone timezone = null;
					String userCode = user.getUserCode();
					Long userId = user.getId();
					if(loginOrgLocations.getTimezone() != null){
						timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
					}
					
					Date startUTCDate = null;
					Date endUTCDate = null;
					DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
					DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
					if(StringUtils.isNotBlank(dateRange)) {
						Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),dateRange);
						
						String startTimeTS = Constants.TZ_DF.format(startTime); 
						String endTimeTS = END_TZ_DF.format(startTime);
						String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startTimeTS);
						String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
							 startUTCDate = TZ_DF_TS.parse(startDateString);
							 endUTCDate = TZ_DF_TS.parse(endDateString);
						if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
							userType = Constants.ADMIN;
								requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
							}else{
								requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, dateRange, currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
						}
						
						// tasks
						String taskCurrentTimeStr = "";
						String taskCurrentDateInTimezone ="";
						Date converteDate = null;
						
						String status =  "myTask";
						if(!"".equalsIgnoreCase(dateRange)){
							Date date1 = commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),dateRange);
							taskCurrentTimeStr = Constants.TZ_DF.format(date1);
							taskCurrentDateInTimezone= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, taskCurrentTimeStr);
							converteDate = TZ_DF_TS.parse(taskCurrentDateInTimezone);
							taskCurrentDateInTimezone = commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),converteDate);
						}
						
						List<Tasks> taklist = taskService.tasksListByIdAndOrgLocationIdAssignedUserId(loginOrgLocations.getId(),user.getId(),status,dateRange,converteDate,timezone);
						
						DateFormat week_Day = new SimpleDateFormat ("EEEE");
						
						String dayOfweek = week_Day.format(startTime);
						SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
						String selectedDate  = formatter.format(startTime);
						RequirementInterviewHistory reqInterviewHistory =  new RequirementInterviewHistory();
						
						
						reqInterviewHistory.setInterviewerList(requirementInterviewList);
						reqInterviewHistory.setTasksList(taklist);
						
						reqInterviewHistory.setDayOfweek(dayOfweek);
						reqInterviewHistory.setSelectedDate(selectedDate);
						response.setObject(reqInterviewHistory);
						response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
					}
				}catch(Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
			}else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			return response;
		}
		
		
		@PreAuthorize("hasRole('ROLE_VIEW_REQUIREMENT')")
		@RequestMapping(value = "/getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate")
		public String getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(@RequestParam(value="postedDateRange", required=false) String postedDateRange, HttpServletRequest request) throws Exception {
			
			String numberOfDays;
			int Days;
			 switch (postedDateRange) {
			 	 case "today" :
			 		 numberOfDays = "1";
			 		 break;
			 	 case "yesterday" :
			 		 numberOfDays = "2";
			 		 break;
			 	 case "week" :
					 numberOfDays = "7";
				      break;
				 case "twoweeks" :
					 Days  = (2 * 7);
					 numberOfDays = Integer.toString(Days);
				     break;
				 case "month" :
					 numberOfDays = "30";
				     break;
				 case "twomonths" :
					 Days  = (2 * 30);
					 numberOfDays = Integer.toString(Days);
				     break;
				 default:
					 numberOfDays = "1";
			 }
			 
			String resultJson;
			User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			String userCode = user.getUserCode();
			String userType = null;
			List<Requirement>   requirementList = null;
			if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
				userType = Constants.ADMIN;
				requirementList = requirementService.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(loginOrgLocation.getId(), userType, user.getId(), userCode, numberOfDays);
			}else{
				requirementList = requirementService.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(loginOrgLocation.getId(), userType, user.getId(), userCode, numberOfDays);
			}
			resultJson = commonUtil.toJSON(requirementList);
			return resultJson;
		}
		
		 /**
		  * This method is used for getting all Expenses Stages(Draft,Submitted,Approved,Rejected) counts and Show in Dashboard page.
		  * 
		  * @author prasad K
		  * @param expenseType
		  * @return String
		  * @throws NullPointer
		  */
		
		 @RequestMapping(value = "/getExpensesCount" , method = RequestMethod.GET)
		 public ExpensesCount  getExpensesCount(@RequestParam(value="expenseType", required=false) String expenseType,HttpServletRequest request) throws Exception {
			
			    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
				Long masterCandidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "masterCandidateId");
				ExpensesCount expensesCount = new ExpensesCount();
				if("employeeExpenses".equalsIgnoreCase(expenseType)){
					expensesCount = expensesApproverHierarchyService.getExpensesCountByUserIdAndOrgLocationIdForApprover(user.getId(), loginOrgLocations.getId(),isGlobalAccess);
				}else if("myExpenses".equalsIgnoreCase(expenseType)){
					expensesCount = expensesApproverHierarchyService.getExpensesCountByMasterCandidateId(masterCandidateId);
				}
				return expensesCount;
		   }
		 
		 /**
		  * @author Pavan jampana
		  * this method is for dashboard contracts List
		  * @param status
		  * @param request
		  * @return contract list
		  * @throws Exception
		  */
	@RequestMapping(value = "/getDashboardContracts", method = RequestMethod.POST)
	public List<RequirementOffers> getDashboardContracts(@RequestParam(value = "locationId", required = false) Long locationId) throws Exception
	{
		List<RequirementOffers> offerList = requirementOffersService.getOfferAcceptedVendorCandidatesByOrgLocationId(locationId);
		return offerList;
	}
	
	@RequestMapping(value = "/checkForOldrecords", method = RequestMethod.POST)
	public boolean checkForOldrecords(@RequestParam(value = "orglocationId", required = false) Long orglocationId) throws Exception
	{
		boolean generationIdStatus  = requirementService.checkForOldrecordsUsingOrgLocationid(orglocationId);
		return generationIdStatus;
	}
	
	
	@RequestMapping(value = "/getAllRequirementByOrgLocationIdAndUserIdAndPostedDate")
	public String getAllRequirementByOrgLocationIdAndUserIdAndPostedDate(@RequestParam(value="postedDateRange", required=false) String postedDateRange, HttpServletRequest request) throws Exception {
		
		String numberOfDays;
		int Days;
		 switch (postedDateRange) {
		 	 case "today" :
		 		 numberOfDays = "1";
		 		 break;
		 	 case "yesterday" :
		 		 numberOfDays = "2";
		 		 break;
		 	 case "week" :
				 numberOfDays = "7";
			      break;
			 case "twoweeks" :
				 Days  = (2 * 7);
				 numberOfDays = Integer.toString(Days);
			     break;
			 case "month" :
				 numberOfDays = "30";
			     break;
			 case "twomonths" :
				 Days  = (2 * 30);
				 numberOfDays = Integer.toString(Days);
			     break;
			 default:
				 numberOfDays = "1";
		 }
		String resultJson;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String userCode = user.getUserCode();
		String userType = null;
		List<Requirement>   requirementList = null;
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(loginOrgLocation.getId(), userType, user.getId(), userCode, numberOfDays);
		}else{
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDate(loginOrgLocation.getId(), userType, user.getId(), userCode, numberOfDays);
		}
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	}
	
	@RequestMapping(value = "/getAllRequirementByOrgLocationIdAndUserIdAndPostedDateWithCustomDates")
	public List<Requirement> getAllRequirementByOrgLocationIdAndUserIdAndPostedDateWithCustomDates(@RequestParam(value="postedDateRange", required=false) String postedDateRange, @RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,@RequestParam(value="managerId", required=false) Long managerId,HttpServletRequest request) throws Exception {
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String numberOfDays;
		int Days;
		 switch (postedDateRange) {
		 	 case "today" :
		 		 numberOfDays = "1";
		 		 break;
		 	 case "yesterday" :
		 		 numberOfDays = "2";
		 		 break;
		 	 case "week" :
				 numberOfDays = "7";
			      break;
			 case "twoweeks" :
				 Days  = (2 * 7);
				 numberOfDays = Integer.toString(Days);
			     break;
			 case "month" :
				 numberOfDays = "30";
			     break;
			 case "twomonths" :
				 Days  = (2 * 30);
				 numberOfDays = Integer.toString(Days);
			     break;
			 case "custome" :
				 numberOfDays = "0";
				 if(startDate != null && startDate != "" && endDate != null && endDate !="") {
					 Date startDates = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), startDate);
					 Date endDates = commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), endDate);
					 Long customeDays =  CalendarUtil.getNumberOfDaysCount(startDates,endDates);
					 numberOfDays = String.valueOf(customeDays);
				 } 
			     break;
			 default:
				 numberOfDays = "1";
		 }
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String userCode = user.getUserCode();
		String userType = null;
		List<Requirement>   requirementList = null;
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDateAndManagerId(loginOrgLocation.getId(), userType, user.getId(), userCode, numberOfDays,managerId);
		}else{
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIdAndUserIdAndPostedDateAndManagerId(loginOrgLocation.getId(), userType, user.getId(), userCode, numberOfDays,managerId);
		}
		return requirementList;
	}
	
	@RequestMapping(value = "/getAllRequirementsForJobSharing")
	public List<RequirementDTO> getAllRequirementsForJobSharing(@RequestParam(value="postedDateRange", required=false) String postedDateRange, @RequestParam(value="startDate", required=false) String startDate,
			@RequestParam(value="endDate", required=false) String endDate,@RequestParam(value="managerId", required=false) Long managerId,HttpServletRequest request) throws Exception {
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		
		
		Date[] searchDates = null;
		List<RequirementDTO> requirementDTOList = null;
		if(StringUtils.isNotBlank(postedDateRange)) {
			if(postedDateRange.equalsIgnoreCase("custom") && StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
				Date fromDate =  commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), startDate);
	    	    Date toDate =  commonUtil.orgLocationDateFormat(loginOrgLocation.getDateFormat(), endDate);
	    	   
	    	    Timezone timezone = timeZoneService.getTimezone(loginOrgLocation.getTimezone().getId());
	    	    DateFormat TZ_DF_TS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	    DateFormat TZ_DF_CSD = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00");
	    	    DateFormat TZ_DF_CED = new SimpleDateFormat("yyyy-MM-dd'T'23:59:59");
	    	    String customStartTimeStr = TZ_DF_CSD.format(fromDate);
	    	    String customEndTimeStr = TZ_DF_CED.format(toDate);
	    	    String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customStartTimeStr);
	    	    String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, customEndTimeStr);
	    	    fromDate = TZ_DF_TS.parse(startDateString);
	    	    toDate = TZ_DF_TS.parse(endDateString);
	    	    
	    	    searchDates = new Date[2];
	    	    searchDates[0] = fromDate;
	    	    searchDates[1] = toDate;
			}else if (!postedDateRange.equalsIgnoreCase("custom")){
				searchDates = reportsUtil.getJobFilterDate(postedDateRange,loginOrgLocation);
			}
		}
		
		if(searchDates != null) {
			requirementDTOList = requirementService.getAllManagerRequirementForJobSharing(searchDates, user.getId(), loginOrgLocation.getId());
		}
		
		return requirementDTOList;
	}
	
	/**
	 * @author Mahaveer Manoj
	 * @param request
	 * @param dashboardItemsDTO
	 * @return
	 */
	@RequestMapping(value = "/saveDashboardItems", method = RequestMethod.POST)
	public Response<String> saveDashboardItems(HttpServletRequest request, DashboardItemsDTO dashboardItemsDTO) {
		Response<String> response = new Response<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			if (user != null) {
				JSONArray jsonArray = new JSONArray(dashboardItemsDTO.getJsonData());
				if(jsonArray!=null) {
					for(int i=0;i<jsonArray.length();i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						if (!jsonObject.has("name") || !jsonObject.has("checked") || !jsonObject.has("order") || !jsonObject.has("size")) {
							response.setResponse(Result.INVALID_DATA.name());
							response.setResponseMessage(Result.INVALID_DATA.getValue());
							return response;
						}
					}
				}
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				response = userService.saveDashboardItems(dashboardItemsDTO, user, orgLocations);
			} else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setResponse(Result.AWKWARD.name());
			response.setResponseMessage(Result.AWKWARD.getValue());
		}
		return response;
	}
	
	
	/**
	 * @author Laxman
	 * @param requirementSubmissionHistoryDTO
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/applicantListExport")
	public void applicantListExport(RequirementSubmissionHistoryDTO requirementSubmissionHistoryDTO, HttpServletRequest request,HttpServletResponse response)throws Exception
   {
		OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		Timezone timezone = null;
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginOrgLocations.getTimezone() != null){
			timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
		}
		Date[] freqDates = new Date[2];
		String userType = null;
		Date startUTCDate = null;
		Date endUTCDate = null;
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		DateFormat TZ_DF_CSD = new SimpleDateFormat ("yyyy-MM-dd 00:00:00");
		DateFormat TZ_DF_CED = new SimpleDateFormat ("yyyy-MM-dd 23:59:59");
		if("Custom".equalsIgnoreCase(requirementSubmissionHistoryDTO.getFrequency())){
			Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),requirementSubmissionHistoryDTO.getFromDate());
			Date endTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),requirementSubmissionHistoryDTO.getToDate());
			String customStartTimeStr = TZ_DF_CSD.format(startTime);  
			String customEndTimeStr = TZ_DF_CED.format(endTime);  
			startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
			endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
		}else{
			freqDates = reportsUtil.getJobFilterDate(requirementSubmissionHistoryDTO.getFrequency(),loginOrgLocations);
			String customStartTimeStr = TZ_DF_CSD.format(freqDates[0]);  
			String customEndTimeStr = TZ_DF_CED.format(freqDates[1]);  
			startUTCDate = TZ_DF_TS.parse(customStartTimeStr);
			endUTCDate = TZ_DF_TS.parse(customEndTimeStr);
		}
		  
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
		} 
		
		
	   ResponseList<RequirementSubmissionHistory> dashboardJobsCountlist =requirementSubmissionDao.getRequirementSubmissionsListWithDTOByOrgLocationsIdAndSubmissionStatus(requirementSubmissionHistoryDTO, loginOrgLocations.getId(),requirementSubmissionHistoryDTO.getSubmissionStatus(),requirementSubmissionHistoryDTO.getTabType(),userType,user,startUTCDate,endUTCDate,loginOrgLocations);

	   
		 Workbook createExcelworkbook =  new XSSFWorkbook();
		 StringBuilder excelSB = new StringBuilder();
		 Sheet createExcelsheet = null;
		 if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("SUBMISSION")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("applicant").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("SUBMIT_TO_CLIENT")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("submission").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("INTERVIEW")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("interview").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("OFFERMADE")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("offer").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("REJECTED")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("rejected").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("JOINED")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("joined").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("BACKOUT")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("backout").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("SHORTLIST")) {
	 		  createExcelsheet = createExcelworkbook.createSheet(excelSB.append("shortlist").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString());
		 }

 		File file = null;
 		if(!"pdf".equalsIgnoreCase(requirementSubmissionHistoryDTO.getExportType())){
 			createExcelsheet = requirementSubmissionService.getExcelFile(createExcelsheet,createExcelworkbook,dashboardJobsCountlist,loginOrgLocations,requirementSubmissionHistoryDTO.getSubmissionStatus());
 			 StringBuilder templateSB = new StringBuilder();
	 		 if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("SUBMISSION")) {
		 		  file = File.createTempFile(templateSB.append("applicant_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("SUBMIT_TO_CLIENT")) {
		 		  file = File.createTempFile(templateSB.append("submission_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("INTERVIEW")) {
		 		  file = File.createTempFile(templateSB.append("interview_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("OFFERMADE")) {
		 		  file = File.createTempFile(templateSB.append("offer_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("REJECTED")) {
		 		  file = File.createTempFile(templateSB.append("rejected_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("JOINED")) {
		 		  file = File.createTempFile(templateSB.append("joind_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("BACKOUT")) {
		 		  file = File.createTempFile(templateSB.append("backout_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls");
	 		 }else if(requirementSubmissionHistoryDTO.getSubmissionStatus().equalsIgnoreCase("SHORTLIST")) {
	 			file = File.createTempFile(templateSB.append("shortlist_").append(commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),new Date())).toString(),".xls"); 
			 }
	 		  file.setReadable(true);
	 		  
			 FileOutputStream fos = new FileOutputStream(file);
			 try {
				createExcelworkbook.write(fos);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	 	
 		}else {
 			file = requirementSubmissionService.getPdfFile(dashboardJobsCountlist,loginOrgLocations);
 			
 		}
 		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}
		response.setContentType(mimeType);
		StringBuilder fileNameSB = new StringBuilder();
		response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		inputStream.close();
		response.getOutputStream().close();
		response.getOutputStream().flush();	
		if(file!= null){
			file.delete();
		}
   }

	/**
	 * @author ajith
	 * @param request
	 * @return RequirementSubmissionsDTO list
	 * @throws Exception
	 * This method is used to retrieve candidate for Invoice list in Dashboard 
	 */
	@RequestMapping(value = "/getCandidateInvoiceForDashboard", method = RequestMethod.POST)
	public List<RequirementSubmissionsDTO> getCandidateInvoiceForDashboard(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		 List<RequirementSubmissionsDTO > dtoList = requirementSubmissionService.getCandidateForInvoiceDashBoard(orgLocations,user);
		return dtoList; 
	}
	
	@RequestMapping(value = "/generateInvoiceForJobClosureDashboard", method = RequestMethod.POST)
	@ResponseBody
	public Response<InvoiceDTO> generateInvoiceForJobClosureDashboard(@RequestParam(value = "invoiceType", required = false) String invoiceType, 
			@RequestParam(value = "contractType", required = false) String contractType,
			@RequestParam(value = "orgRelationId", required = false) Long orgRelationId,
			@RequestParam(value = "submissionId", required = false) Long submissionId,
			@RequestParam(value = "candidateId", required = false) Long candidateId,
			HttpServletRequest request) throws Exception
	{
		Response<InvoiceDTO> response = new Response<>();
		InvoiceDTO invoiceDTO = new InvoiceDTO();
		
		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		String loginOrgCurrencyCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "currencyCode");
		
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		Integer savedInvoiceCount = invoiceService.generateInvoiceJobClosureFromDashboard(orgLocation, loginOrgCurrencyCode, invoiceType, user,contractType,orgRelationId,submissionId,candidateId);
		invoiceDTO.setCount(String.valueOf(savedInvoiceCount));
		response.setObject(invoiceDTO);
		return response;
	}
	
	
	@RequestMapping(value = "/getMarketplaceCartCountAndPrice")
	public Object[] getMarketplaceCartCountAndPrice( HttpServletRequest request)throws Exception{
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		Object[] countAndSum = null;
		if(user!= null) {
			if(StringUtils.isNotBlank(userRoleName)) {
				OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
					countAndSum = candidateService.updateMarketplaceCartCountAndPrice(orgLocation.getId(), userRoleName, user);
			}
		}
		return countAndSum;
	}
	
	@RequestMapping(value = { "/getCountsForSelfLeaves","/rest/getCountsForSelfLeaves" }, method = RequestMethod.GET)
	@ResponseBody
	public LeaveDTO getCountsForSelfLeaves(
			@RequestParam(value = "orgLocationId", required = false) Long orgLocationId,
			@RequestParam(value = "userId", required = false) Long userId) throws Exception {
		return leaveService.getAllSelfLeavesCounts(orgLocationId, userId);

	}
	
	
	@RequestMapping(value="/getAllJobFairDatesForCalendar")
	public ResponseList<String> getAllJobFairDatesForCalendar(HttpServletRequest request){
		ResponseList<String> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
			if(user !=null) {
				OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				boolean adminFlag = false;
				if("Admin".equalsIgnoreCase(roleName)) {
					adminFlag = true;	
				}
				
				responseList.setData(requirementService.getJobFairDatesForCalendar(user.getId(), loginOrgLocations.getId(), loginOrgLocations.getDateFormat(), adminFlag));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(Result.SUCCESS.getValue());
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	
	
	@RequestMapping(value = "/getAllJobFairRequirements")
	public ResponseList<Requirement> getAllJobFairRequirements(HttpServletRequest request, @RequestParam(defaultValue="") String date) {
		ResponseList<Requirement> responseList = new ResponseList<>();
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String roleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "displayName");
			OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			if (user != null) {
				Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(), date);
				DateFormat week_Day = new SimpleDateFormat ("EEEE");
				String dayOfweek = week_Day.format(startTime);
				SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
				String selectedDate  = formatter.format(startTime);
				
				boolean adminFlag = false;
				if("Admin".equalsIgnoreCase(roleName)) {
					adminFlag = true;
				}
				
				responseList.setData(requirementService.getJobFairRequirementsByDate(user.getId(), loginOrgLocations.getId(), loginOrgLocations.getDateFormat(), date, adminFlag));
				responseList.setResponse(Result.SUCCESS.name());
				responseList.setResponseMessage(dayOfweek + "<>" + selectedDate);
			} else {
				responseList.setResponse(Result.SESSION_TIMEDOUT.name());
				responseList.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseList.setResponse(Result.AWKWARD.name());
			responseList.setResponseMessage(Result.AWKWARD.getValue());
		}
		return responseList;
	}
	
	
	
	  @RequestMapping(value = "/getAllProjectResourceRequestsCounts", method = RequestMethod.GET)
		public @ResponseBody ProjectPlanningResourceRequestDTO getAllProjectResourceRequestsCounts(HttpServletRequest request) throws Exception {
		   
  	  OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		  String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		  List<ProjectPlanningResourceRequest> projectPlanningRequestList = projectPlanningResourceRequestService.getResourceRequestListByApproverIdAndOrgLocationIdAndStatus(user.getId(), orgLocation.getId(), userRoleName);
		 
		  ProjectPlanningResourceRequestDTO projectPlanningResourceRequestDTO = new ProjectPlanningResourceRequestDTO();
		  
		  Integer waitingCount = 0;
		  Integer approvedCount = 0;
		  Integer rejectedCount = 0;
		  Integer onholdCount = 0;
		  
		  if(projectPlanningRequestList != null && projectPlanningRequestList.size() > 0) {
			  for(ProjectPlanningResourceRequest planningRequest : projectPlanningRequestList){
				  if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
					  approvedCount = approvedCount + 1;
				  }else if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL)){
					  waitingCount = waitingCount + 1;
				  }else if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_REJECTED)){
					  rejectedCount = rejectedCount + 1;
				  }else if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_ON_HOLD)){
					  onholdCount = onholdCount + 1;
				  }
			  }
		  }
		  
		  projectPlanningResourceRequestDTO.setWaitingCount(waitingCount);
		  projectPlanningResourceRequestDTO.setApprovedCount(approvedCount);
		  projectPlanningResourceRequestDTO.setRejectedCount(rejectedCount);
		  projectPlanningResourceRequestDTO.setOnholdCount(onholdCount);
		  
		  return projectPlanningResourceRequestDTO;
		  
	    }
      
      @RequestMapping(value = "/getAllProjectPlanningResourceRequestList", method = RequestMethod.GET)
    	public ResponseList<ProjectPlanningResourceRequestDTO> getAllProjectPlanningResourceRequestList(ProjectPlanningResourceRequestDTO resourceRequestDTO,HttpServletRequest request) throws Exception {

    		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
    		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
    		 String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
    		 
    		String[] tableColumns = {"projectPlanningRequestId","projectName", "candidateName", "requestedUser", "approverName", "consultingType"};
		    int sortColumnIndex = 1; //by default sorting using updated date
			String sortColumnOrder = "desc"; //by default referContactsDTO order
			if(resourceRequestDTO.getOrder() != null && !resourceRequestDTO.getOrder().isEmpty()) {
				sortColumnIndex = Integer.parseInt(resourceRequestDTO.getOrder().get(0).get("column"));
				sortColumnOrder = resourceRequestDTO.getOrder().get(0).get("dir");
			}
			resourceRequestDTO.setSortColumn(tableColumns[sortColumnIndex]);
			resourceRequestDTO.setSortOrder(sortColumnOrder);
    		
			ResponseList<ProjectPlanningResourceRequestDTO> requestDTOList = projectPlanningResourceRequestService.getResourceRequestListByApproverIdAndOrgLocationId(resourceRequestDTO,user.getId(), orgLocation.getId(),userRoleName);
    		
    		return requestDTOList;
  		}
      
      
      
      
      @RequestMapping(value = "/getOrgSubmissionTemplatesList", method = RequestMethod.POST)
  	@ResponseBody
  	String getOrgSubmissionTemplatesList(HttpServletRequest request) throws Exception {
  		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
  				"orgLocations");
  		List<SubmissionTemplate> submissiontTemplateList = submissionTemplateService
  				.getOrgSubmissionTemplatesByOrgLocationIdAndTemplateType(orgLocations.getId(), "Submission");

  		return commonUtil.toJSON(submissiontTemplateList);
  	}

  	@RequestMapping(value = "/saveOrgSubmissionTemplate", method = RequestMethod.POST)
  	public String saveClientSubmissionTemplate(@RequestParam(value = "jsonData", required = false) String json,
  			@RequestParam(value = "templateId", required = false) String templateId,
  			@RequestParam(value = "jsonStringData", required = false) String jsonStringData, HttpServletRequest request)
  			throws Exception {

  		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
  				"orgLocations");
  		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");

  		String json1 = "{" + json + "}";
  		JSONObject jsonObj = new JSONObject(json1);

  		try {
  			SubmissionTemplate submissionTemplate = null;
  			if (!isNullOrEmpty(templateId)) {
  				submissionTemplate = submissionTemplateService.getSubmissionTemplateById(Long.parseLong(templateId));
  			} else {
  				submissionTemplate = new SubmissionTemplate();
  				submissionTemplate.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
  			}

  			submissionTemplate.setTemplateName(jsonObj.getString("templateName").trim());
  			submissionTemplate.setDescription(jsonObj.getString("description"));
  			submissionTemplate.setOrgLocationId(orgLocations);
  			submissionTemplate.setJsonString(jsonStringData);
  			submissionTemplate.setTemplateType("Submission");
  			submissionTemplate.setStatus(Constants.CANDIDATE_ACTIVE);
  			submissionTemplate.setCreatedBy(user.getUserCode());
  			submissionTemplate.setCreatedDate(new Date());
  			submissionTemplate.setUpdatedBy(user.getUserCode());
  			submissionTemplate.setUpdatedDate(new Date());
  			submissionTemplateService.addSubmissionTemplate(submissionTemplate);
  			SourceLeadSessionManager.setSessionAttribute(request, "SubmissionTemplateCreateMsg", "Success!! New Submission Template created");

  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  		return "home";

  	}
      
 	 @RequestMapping(value = "/ws/dumpUUIDForUserRoles", method = RequestMethod.GET)
 	 public void dumpUUIDForUserRoles() throws Exception {
 		
 		 List<UserRole> userRolesList =  userRoleService.getAllUserRoles();
 		 
 		 if(userRolesList != null && !userRolesList.isEmpty()) {
 			 System.out.println("dumping uuid for userroles starts......");
 			 for(UserRole userRole : userRolesList) {
 				userRole.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
 				userRoleService.addUserRole(userRole);
 			 }
 			 System.out.println("dumping uuid for userroles ends");
 		 }
 	 }
 	 
 	 
 	@RequestMapping(value = "/getLoginUserMultiAccountsList")
	public @ResponseBody List<UserAccountsDTO> getLoginUserMultiAccountsList() throws Exception {
		
 		List<UserAccountsDTO> accountsList = userLoginHelper.getUserAccountsList();
 		return accountsList;
	}
 	
	@RequestMapping(value = "/getInterviewListExportList")
	public void getInterviewListExportList(EventsDTO eventsDTO,HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="exportType", required=false) String exportType) {
		try {
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			List<RequirementInterviewHistory> requirementInterviewList = new ArrayList<RequirementInterviewHistory>();
			String currentTime = null;
			String userType = null;
			Timezone timezone = null;
			String userCode = user.getUserCode();
			Long userId = user.getId();
			if(loginOrgLocations.getTimeZoneId() != null){
				timezone = timeZoneService.getTimezone(Long.valueOf(loginOrgLocations.getTimeZoneId()));
			}
			
			Date startUTCDate = null;
			Date endUTCDate = null;
			DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			DateFormat END_TZ_DF = new SimpleDateFormat ("yyyy-MM-dd'T'23:59:59");
			if(StringUtils.isNotBlank(eventsDTO.getDateRange())) {
				Date startTime =  commonUtil.orgLocationDateFormat(loginOrgLocations.getDateFormat(),eventsDTO.getDateRange());
				
				String startTimeTS = Constants.TZ_DF.format(startTime); 
				String endTimeTS = END_TZ_DF.format(startTime);
				String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startTimeTS);
				String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endTimeTS);
					 startUTCDate = TZ_DF_TS.parse(startDateString);
					 endUTCDate = TZ_DF_TS.parse(endDateString);
				if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
					userType = Constants.ADMIN;
						requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, eventsDTO.getDateRange(), currentTime, userType, userId, userCode,startUTCDate,endUTCDate,timezone,user);
				} 
				  else{ 
					  requirementInterviewList = requirementInterviewHistoryService.getAllInterviewAssignedToLoginUser(loginOrgLocations, eventsDTO.getDateRange(), currentTime, userType, userId,userCode,startUTCDate,endUTCDate,timezone,user); 
					  }
					 
				File file = null;
				if (exportType.equalsIgnoreCase("pdf")) {
					file = dynamicReport.interviewListPdfExport(requirementInterviewList, loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName());
				} else {
					file = dynamicReport.interviewListExcelExport(requirementInterviewList,loginOrgLocations.getDateFormat(),loginOrgLocations.getOrgLocationName());
				}
				
				String mimeType = URLConnection.guessContentTypeFromName(file.getName());
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}
				response.setContentType(mimeType);
				StringBuilder fileNameSB = new StringBuilder();
				response.setHeader("Content-Disposition", fileNameSB.append("attachment; filename=\"").append(file.getName()).append("\"").toString());
				response.setContentLength((int) file.length());
				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				inputStream.close();
				response.getOutputStream().close();
				response.getOutputStream().flush();	
				if(file!= null){
					file.delete();
				}
	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 	
 	
 	@RequestMapping(value = "/getProjectResourceRequestsCounts", method = RequestMethod.GET)
	public @ResponseBody ProjectPlanningResourceRequestDTO getProjectResourceRequestsCounts(HttpServletRequest request) throws Exception {
	   
	  OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	  String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	  List<ProjectPlanningResourceRequest> projectPlanningRequestList = projectPlanningResourceRequestService.getResourceRequestListByApproverIdAndOrgLocationIdAndStatus(user.getId(), orgLocation.getId(), userRoleName);
	 
	  ProjectPlanningResourceRequestDTO projectPlanningResourceRequestDTO = new ProjectPlanningResourceRequestDTO();
	  
	  Integer waitingCount = 0;
	  Integer approvedCount = 0;
	  Integer rejectedCount = 0;
	  Integer onholdCount = 0;
	  
	  if(projectPlanningRequestList != null && projectPlanningRequestList.size() > 0) {
		  for(ProjectPlanningResourceRequest planningRequest : projectPlanningRequestList){
			  if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
				  approvedCount = approvedCount + 1;
			  }else if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL)){
				  waitingCount = waitingCount + 1;
			  }else if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_REJECTED)){
				  rejectedCount = rejectedCount + 1;
			  }else if(planningRequest.getResourceStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_ON_HOLD)){
				  onholdCount = onholdCount + 1;
			  }
		  }
	  }
	  
	  projectPlanningResourceRequestDTO.setWaitingCount(waitingCount);
	  projectPlanningResourceRequestDTO.setApprovedCount(approvedCount);
	  projectPlanningResourceRequestDTO.setRejectedCount(rejectedCount);
	  projectPlanningResourceRequestDTO.setOnholdCount(onholdCount);
	  
	  return projectPlanningResourceRequestDTO;
	  
    }
    
      
}
