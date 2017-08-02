package com.bis.sourcelead.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.elk.index.RequirementIndex;
import com.bis.sourcelead.elk.setup.RequirementElkUtil;
import com.bis.sourcelead.elk.setup.SourceleadELKBaseIndexer;
import com.bis.sourcelead.entity.DashboardCounts;
import com.bis.sourcelead.entity.ExpensesCount;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementApproverHierarchy;
import com.bis.sourcelead.entity.RequirementAssignTo;
import com.bis.sourcelead.entity.RequirementInterviewHistory;
import com.bis.sourcelead.entity.RequirementOffers;
import com.bis.sourcelead.entity.RequirementSubmission;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.REQUIREMENT_APPROVER_HIERARCHY_STATUS;
import com.bis.sourcelead.enums.REQUIREMENT_SUBMISSION_STATUS;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.ExpensesApproverHierarchyService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementApproverHierarchyService;
import com.bis.sourcelead.service.RequirementAssignToService;
import com.bis.sourcelead.service.RequirementInterviewHistoryService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementInterviewerService;
import com.bis.sourcelead.service.RequirementOffersService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TaskService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.LoginSessionController;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@RestController
public class DashboardRestController {

	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	public OrgLocationService orgLocationService;
	
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
	 private SourceleadELKBaseIndexer<RequirementIndex> sourceleadELKBaseIndexer;
	 
	 @Autowired
	 TimeZoneService timeZoneService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	
	 @Autowired
	 TaskService taskService;
	 
	 @Autowired
	 RequirementAssignToService requirementAssignToService;
	 
	 @Autowired
	 RequirementOffersService requirementOffersService;
	 
	 @Autowired
	 private ExpensesApproverHierarchyService expensesApproverHierarchyService;
	 
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
	 
	/****
	 * This  controller method is   for getting TimesheetSubmission Counts in dashboard Landing Page Based on User Login
	 * @author: Sammeta David
	 */
	
	@PreAuthorize("hasAnyRole('ROLE_FILL_MY_TIMESHEETS','ROLE_EXPORT_MY_TIMESHEETS','ROLE_MODIFY_MY_TIMESHEETS','ROLE_FILL_MEMBER_TIMESHEETS','ROLE_VIEW_MEMBER_TIMESHEETS','ROLE_EXPORT_MEMBER_TIMESHEETS')")
	@RequestMapping(value = "/getTimeSheetSubmissionStatus")
	public String getTimeSheetSubmissionStatus(@RequestParam(value="timesheetType", required=false) String timesheetType,HttpServletRequest request) throws Exception {

		String resultJson;

	    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		if("employes".equalsIgnoreCase(timesheetType)){
			timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(user.getId(), loginOrgLocations.getId());
		}else if("myTimesheets".equalsIgnoreCase(timesheetType)){
			timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationId(user.getId(), loginOrgLocations.getId());
		}
		resultJson = commonUtil.toJSON(timeSheetSubmissionCount);
		return resultJson;
	}
	/****
	 * 
	 * @author: Pavan Jampana 
	 * This  controller method is  to get Timesheet Submissions list based on status 
	 */
	
	@RequestMapping(value = "/totalTimesheetList/{status}")
	public @ResponseBody void tiesheetStatusList(@PathVariable("status") String status,HttpServletResponse response) throws Exception {
		 StringBuilder redirectUrl = new StringBuilder();
		 response.sendRedirect(redirectUrl.append("/sourcelead/timesheetlistByStatus?status=").append(status).toString());
	}
	
	
	@RequestMapping(value = "/dashboard/{orgLocationId}" , method = RequestMethod.GET)
	 public @ResponseBody void userSummary(@PathVariable Long orgLocationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     //to update user state in user role based on location----> Prasad Salina
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		     UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
   			    userRole.setUserState(Status.ACTIVE);
   			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
   			   
   			   
   			   
   			   //user update/save in user Elk
   			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
   		      }
				
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/sourcelead/dashboardLinks?orgLocationId=").append(orgLocationId).toString());
			
	}
	
	
	@RequestMapping(value = "/dashboard/{orgLocationId}/{switchUserType}" , method = RequestMethod.GET)
	 public @ResponseBody void userSummaryWithUserType(@PathVariable Long orgLocationId,@PathVariable String switchUserType,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     //to update user state in user role based on location----> Prasad Salina
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		     UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
  			    userRole.setUserState(Status.ACTIVE);
  			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
  			   
  			   
  			   
  			   //user update/save in user Elk
  			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
  		      }
				
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/sourcelead/dashboardLinks?orgLocationId=").append(orgLocationId).append("&switchUserType=").append(switchUserType).toString());
			
	}
	
	
	@RequestMapping(value = "/productionSupportdashboard/{orgLocationId}/{loginOrgLocatonId}" , method = RequestMethod.GET)
	 public @ResponseBody void productionSupportdashboard(@PathVariable Long orgLocationId,@PathVariable Long loginOrgLocatonId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			
		     //to update user state in user role based on location----> Prasad Salina
		     User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		     OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");

		     UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocationId);
		     if(userRole != null && userRole.getUserState() != null && userRole.getUserState().equals(Status.NOT_LOGGEDIN)){
 			    userRole.setUserState(Status.ACTIVE);
 			   UserRole userRoleElk = userRoleService.addUserRole(userRole);
 			   
 			   
 			   
 			   //user update/save in user Elk
 			  userService.saveOrUpdateUserElkDocument(userRoleElk,null,user.getUserCode());
 		      }
				
		     StringBuilder redirectUrl = new StringBuilder();
		     response.sendRedirect(redirectUrl.append("/sourcelead/dashboardLinks?orgLocationId=").append(orgLocationId).append("&loginOrgLocatonId=").append(loginOrgLocatonId).toString());
			
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
	 public String getAssignedRequirementListByOrgLocationIDAndUserId(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,HttpServletRequest request) throws Exception {

		String resultJson;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		
		String userCode = user.getUserCode();
		String userType = null;
		List<Requirement>   requirementList = null;
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
			//requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}else{
			//requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
			requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserIdSubStatusApprovedOpenNew(loginOrgLocation.getId(), userType, user.getId(), userCode);
		}
		
		//List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(), userType, user.getId(), userCode);
		for(Requirement requirement : requirementList){
			/*if(requirement.getClientId() != null){
				requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
			}*/
			//changed ny prasad salina
			if(requirement.getClientId() != null){
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Client")){
					requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrgLocationName());
				}
				if(requirement.getClientId().getResponderType().equalsIgnoreCase("Vendor")){
					requirement.setClientName(requirement.getClientId().getLocationId().getOrgLocationName());
				}
			}
			
			
			int applicantsCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "APPLICANTS");
			int shortlistedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "SHORTLISTED");
			int submittedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "SUBMITTED");
			int interviewedCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "INTERVIEWED");
			int offeredCount = requirementSubmissionService.getAllSubmissionCountByRequirementIdAndSubmissionStage(loginOrgLocation.getId(), requirement.getId(), "OFFERED");
			
			requirement.setNoOfApplicants(String.valueOf(applicantsCount));
			requirement.setNoOfShortlistedApplicants(String.valueOf(shortlistedCount));
			requirement.setNoOfSubmittedApplicants(String.valueOf(submittedCount));
			requirement.setNoOfInterviewedApplicants(String.valueOf(interviewedCount));
			requirement.setNoOfOfferedApplicants(String.valueOf(offeredCount));
			
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
		
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
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
	 public String  getRequirementSubmissionsCount(@RequestParam(value="daterange", required=false) String daterange,HttpServletRequest request) throws Exception {
		
		String resultJson;
	    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		
		DashboardCounts dc= new DashboardCounts();
		Timezone timezone = null;
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		if(loginOrgLocations.getTimezone() != null){
			timezone = timeZoneService.getTimezone(loginOrgLocations.getTimezone().getId());
		}
		String userType = null;
		Date startUTCDate = null;
		Date endUTCDate = null;
		DateFormat TZ_DF_TS = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		if("today".equals(daterange)){
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 DateTimeFormatter outputFormatter1 = DateTimeFormat.forPattern("yyyy-MM-dd'T'23:59:59");
			 String startDate = outputFormatter.print(zoned);
			 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
			 startUTCDate = TZ_DF_TS.parse(startDateString);
			 String endDate = outputFormatter1.print(zoned);
			 String endDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, endDate);
			 endUTCDate = TZ_DF_TS.parse(endDateString);
		}else{
			 DateTime zoned = new DateTime(DateTimeZone.forID(timezone.getTimeZoneId()));
			 DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'00:00:00");
			 String startDate = outputFormatter.print(zoned);
			 String startDateString = CommonUtil.convertTimeZones(timezone.getTimeZoneId(),Constants.UTC_TIMEZONE, startDate);
			 startUTCDate = TZ_DF_TS.parse(startDateString);
		}
		
		if(Constants.ROLE_ADMIN.equalsIgnoreCase(userRole) || Constants.SOURCELEAD_ADMIN.equalsIgnoreCase(userRole)){
			userType = Constants.ADMIN;
		} 
		
		int toDayReqSubmissionscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMIT_TO_CLIENT",daterange,userType,user,startUTCDate,endUTCDate);
		//int previousReqSubmissionscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION","previous");
		
		int toDayReqApplicantscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION",daterange,userType,user,startUTCDate,endUTCDate);
		//int previousReqApplicantscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION","previous");
	   
		int toDayReqInterviewscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"INTERVIEW",daterange,userType,user,startUTCDate,endUTCDate);
	    //  int previousReqInterviewscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"INTERVIEW","previous");
	   
	    int toDayReqOfferscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"OFFERMADE",daterange,userType,user,startUTCDate,endUTCDate);
	    // int previousReqOfferscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"OFFERMADE","previous");
		
	    dc.setSubmissionCount(toDayReqSubmissionscount);
	    //dc.setSubmissionCount(previousReqSubmissionscount);
	    
	    dc.setApplicantsCount(toDayReqApplicantscount);
	   // dc.setApplicantsCount(previousReqApplicantscount);
	    
	    dc.setInterviewsCount(toDayReqInterviewscount);
	   // dc.setInterviewsCount(previousReqInterviewscount);
	    
	    dc.setOffersCount(toDayReqOfferscount);
	   // dc.setOffersCount(previousReqOfferscount);
	   
	    resultJson = commonUtil.toJSON(dc);
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
	 public String getApproverRequirementApproverListByOrgLocationIDAndUserId(@RequestParam(value="statusType", required=false) String statusType,HttpServletRequest request) throws Exception {

		String resultJson;
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
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
		resultJson = commonUtil.toJSON(newList);
		return resultJson;
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
				Date startTime =  LoginSessionController.DATEFORMAT.parse(dateRange);
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
		public @ResponseBody String requirementsAcceptOrReject(@RequestParam(value = "requirementId", required = false) Long requirementId,@RequestParam(value = "status", required = false)  String status, @RequestParam(value = "reason", required = false) String reason, HttpServletRequest request) throws Exception {
			
		 
			User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			
			Requirement requirement = requirementService.getRequirementByRequirementId(requirementId);
			RequirementApproverHierarchy requirementApproverHierarchy = requirementApproverHierarchyService.getRequirementApproverHierarchyByRequirementId(requirementId, loginUser.getId());
			if(status.equalsIgnoreCase("REJECTED")  &&  !reason.equalsIgnoreCase("") && !reason.isEmpty()){
		    	requirementApproverHierarchy.setRejectedReason(reason);
		    	requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
		    }
			/*// to get  RequirementApproverHierarchy with maximum Level
			Integer maxLevel = requirementApproverHierarchyService.getRequirementApproverHierarachyWithHighestLevel(requirementId);

			if(status.equalsIgnoreCase("APPROVED")){ 
			    if(maxLevel!=null){
			    	 if(maxLevel == requirementApproverHierarchy.getLevel()){
			    		 requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.OPEN);
					   }else{
					    	List<RequirementApproverHierarchy> list1  =  requirementApproverHierarchyService.getRequirementApproverHierarchyListByRequirementId(requirementId);
					    	for(RequirementApproverHierarchy rah :list1){
					    		if(maxLevel.equals(rah.getLevel())){
									User approverUser = userService.getUserById(rah.getApproverUserId().getId());
									requirementService.approverTwoEmailScenario(loginUser, approverUser,requirement,null,orgLocation);
								   } 
					    	} 
			    }
	  }
			    
				requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.APPROVED);
				User intiatorUser = userService.getUserByUserCode(requirement.getCreatedBy());
				requirementService.acceptEmailScenario(loginUser, intiatorUser, requirement,orgLocation);
				
			}
			if(status.equalsIgnoreCase("REJECTED")){
				 if(maxLevel!=null){
			    	 if(maxLevel == requirementApproverHierarchy.getLevel()){
			    		 requirement.setRequirementSubmissionStatus(REQUIREMENT_SUBMISSION_STATUS.REJECTED);
					   }
			    }
				 
				requirementApproverHierarchy.setRequirementApproverStatus(REQUIREMENT_APPROVER_HIERARCHY_STATUS.REJECTED);
				requirementApproverHierarchy.setRejectedReason(reason);
				
				User initiatorUser = userService.getUserByUserCode(requirement.getCreatedBy()); 
				requirementService.rejectedEmailScenario(loginUser, initiatorUser, requirement,null,reason,orgLocation); 
			}
			requirementService.addRequirement(requirement);
			requirementApproverHierarchyService.addRequirementApproverHierarchy(requirementApproverHierarchy);
			*/
			
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
								requirementApproverHierarchyService.addRequirementApproverHierarchy(rah);
							}
						// this is for sending email to requirement initiator
						if (status.equalsIgnoreCase("APPROVED")) {
							requirementService.acceptEmailScenario(loginUser, requirement.getCreatedBy(), requirement,location, orgLocation);
						} else if (status.equalsIgnoreCase("REJECTED")) {
							requirementService.rejectedEmailScenario(loginUser, requirement.getCreatedBy(), requirement, location, reason,orgLocation);
						}
						
						
						 /*This logic is to send mail to assigned team members after approver accepted the requirement request(parallel approver)*/
						RequirementApproverHierarchy hierarchy = requirementApproverHierarchyService.getApproverHierarchyByRequirementId(requirementId);
				    	 List<RequirementAssignTo> reqAssignTo = requirementAssignToService.getAssignedToMembersByRequirementIdAndOrgLocationId(requirementId,orgLocation.getId());
			    			if(null != hierarchy && "APPROVED".equals(hierarchy.getRequirementApproverStatus().toString())){
			    				for(RequirementAssignTo reqAssign:reqAssignTo ){
										if(null != reqAssign.getAssignUserId() && !loginUser.getId().equals(reqAssign.getAssignUserId().getId())){
											requirementService.AssignToTeamMembersEmail(loginUser, reqAssign.getAssignUserId(), requirement,new StringBuilder(),orgLocation);
										}
			    				}
			    		}
						
						
				}else{

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
			requirementElkUtil.saveElkRequirement(requirement,new JSONObject());
			return "success";
			
		}
		
		@RequestMapping(value = "/getApplicantsByStatus", method = RequestMethod.POST)
		public String getApplicantsByStatus(@RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws Exception {

		 String resultJson;
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 List<RequirementSubmission> requirementSubmissionList = requirementSubmissionService.getApplicantsByStatus(loginOrgLocations.getId(), user,status);
		 resultJson = commonUtil.toJSON(requirementSubmissionList);
		 return resultJson;

		}
		
		@RequestMapping(value = "/getInterviewsandTasks")
		public String getInterviewsandTasks(String dateRange, HttpServletRequest request) throws Exception {
			
			String resultJson;
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<RequirementInterviewHistory> requirementInterviewList = null;
			
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
			Date startTime =  LoginSessionController.DATEFORMAT.parse(dateRange);
			
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
				Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(dateRange);
				taskCurrentTimeStr = Constants.TZ_DF.format(date1);
				taskCurrentDateInTimezone= CommonUtil.convertTimeZones(timezone.getTimeZoneId(), Constants.UTC_TIMEZONE, taskCurrentTimeStr);
				converteDate = TZ_DF_TS.parse(taskCurrentDateInTimezone);
				taskCurrentDateInTimezone = LoginSessionController.DATEFORMAT.format(converteDate);
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
			
			resultJson = commonUtil.toJSON(reqInterviewHistory);
			return resultJson;
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
		 public String  getExpensesCount(@RequestParam(value="expenseType", required=false) String expenseType,HttpServletRequest request) throws Exception {
			
			 String resultJson;

			    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				ExpensesCount expensesCount = new ExpensesCount();
				if("employeeExpenses".equalsIgnoreCase(expenseType)){
					expensesCount = expensesApproverHierarchyService.getExpensesCountByUserIdAndOrgLocationIdForApprover(user.getId(), loginOrgLocations.getId());
				}else if("myExpenses".equalsIgnoreCase(expenseType)){
					expensesCount = expensesApproverHierarchyService.getExpensesCountByUserIdAndOrgLocationId(user.getId(), loginOrgLocations.getId());
				}
				resultJson = commonUtil.toJSON(expensesCount);
				return resultJson;
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
		
}
