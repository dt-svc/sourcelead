package com.bis.sourcelead.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.DashboardCounts;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.RequirementInterview;
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.RequirementInterviewService;
import com.bis.sourcelead.service.RequirementService;
import com.bis.sourcelead.service.RequirementSubmissionService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

/**
 * This class is used for...
 * 
 * @author: Venkat Kesav
 * @date: 15-July-2016
 */

@RestController
public class DashboardRestController {

	private static final Logger logger = LoggerFactory.getLogger(DashboardRestController.class);

	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	public OrgLocationService orgLocationService;
	
	 @Autowired
	 RequirementService requirementService;
	 
	 @Autowired
	 RequirementSubmissionService requirementSubmissionService;
	 
	 @Autowired
	 RequirementInterviewService requirementInterviewService;
	
	@Autowired
	CommonUtil commonUtil;
	/****
	 * This  controller method is   for getting TimesheetSubmission Counts in dashboard Landing Page Based on User Login
	 * @author: Sammeta David
	 */
	
	@RequestMapping(value = "/getTimeSheetSubmissionStatus")
	public String getTimeSheetSubmissionStatus(HttpServletRequest request) throws Exception {

		String resultJson;
		String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

	    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		TimeSheetSubmissionCount timeSheetSubmissionCount = new TimeSheetSubmissionCount();
		
		if("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_APPROVER".equalsIgnoreCase(userRole)  ||  "ROLE_TEAM_LEAD".equalsIgnoreCase(userRole)  ){
			timeSheetSubmissionCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(user.getId(), loginOrgLocations.getId());
		}else if("ROLE_USER".equalsIgnoreCase(userRole)){
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
		response.sendRedirect("/sourcelead/timesheetlistByStatus?status=" + status);
	}
	
	
	@RequestMapping(value = "/dashboard/{orgLocationId}" , method = RequestMethod.GET)
	 public @ResponseBody void userSummary(@PathVariable Long orgLocationId,HttpServletRequest request,HttpServletResponse response) throws Exception {
			response.sendRedirect("/sourcelead/dashboardLinks?orgLocationId="+orgLocationId);
			
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
	 
	 @RequestMapping(value = "/getAssignedRequirementListByOrgLocationIDAndUserId")
	 public String getAssignedRequirementListByOrgLocationIDAndUserId(@RequestParam(value="status", required=false) String status,@RequestParam(value="clients", required=false) String clients,HttpServletRequest request) throws Exception {

		String resultJson;
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(),user.getId());
		for(Requirement requirement : requirementList){
			if(requirement.getClientId() != null){
				requirement.setClientName(requirement.getClientId().getResponderLocationId().getOrg().getOrgName());
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
		int toDayReqSubmissionscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION",daterange);
		//int previousReqSubmissionscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION","previous");
		
		int toDayReqApplicantscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION",daterange);
		//int previousReqApplicantscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"SUBMISSION","previous");
	   
		int toDayReqInterviewscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"INTERVIEW",daterange);
	  //  int previousReqInterviewscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"INTERVIEW","previous");
	   
	    int toDayReqOfferscount = requirementSubmissionService.getRequirementSubmissionsCountByOrgLocationsIdAndSubmissionStatus(loginOrgLocations.getId(),"OFFERMADE",daterange);
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
		Org   org = (Org)SourceLeadSessionManager.getSessionAttribute(request, "org");
		User  user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		List<Requirement>   requirementList = requirementService.getAssignedRequirementListByOrgLocationIDAndUserId(loginOrgLocation.getId(),user.getId());
		resultJson = commonUtil.toJSON(requirementList);
		return resultJson;
	 }
	
	 /**
		 * 
		 * @author: Venkat Kesav
		 * This  controller method is  to get Interviews list 
		 */
		@RequestMapping(value = "/getInterviews")
		public String getInterviews(String dateRange, HttpServletRequest request) throws Exception {
			
			

			String resultJson;
			String userRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");

		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
			List<RequirementInterview> requirementInterviewList = null;
			
			String currentTime = null;
			String userType = null;
			String userCode = user.getUserCode();
			Long userId = user.getId();
			
			
			if("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_SOURCELEAD_ADMIN".equalsIgnoreCase(userRole)){
				userType = "ADMIN";
				requirementInterviewList = requirementInterviewService.getAllInterviewAssignedToLoginUser(loginOrgLocations.getId(), dateRange, currentTime, userType, userId, userCode);
			}else{
				requirementInterviewList = requirementInterviewService.getAllInterviewAssignedToLoginUser(loginOrgLocations.getId(), dateRange, currentTime, userType, userId, userCode);
			}
			
			for(RequirementInterview requirementInterview : requirementInterviewList){
				String candidateFirstName = "";
				String candidateLastName = "";
				String requirementTitle = "";
				if(requirementInterview.getSubmissionId() != null && requirementInterview.getSubmissionId().getCandidate() != null){
					if(requirementInterview.getSubmissionId().getCandidate().getFirstName() != null){
						
						candidateFirstName = requirementInterview.getSubmissionId().getCandidate().getFirstName();
					}
					if(requirementInterview.getSubmissionId().getCandidate().getLastName() != null){
						
						candidateLastName = requirementInterview.getSubmissionId().getCandidate().getLastName();
					}
				}
				
				if(requirementInterview.getSubmissionId() != null && requirementInterview.getSubmissionId().getRequirement().getJobTitle() != null){
					requirementTitle = requirementInterview.getSubmissionId().getRequirement().getJobTitle();
				}
				
				requirementInterview.setCandidateName(candidateFirstName+" "+candidateLastName);
				
				requirementInterview.setRequirementTitle(requirementTitle);
				
				requirementInterview.setRequirementId(requirementInterview.getSubmissionId().getRequirement().getId().toString());
				
				requirementInterview.setInterviewStartDateTimeStr(requirementInterview.getStartTime().toString());
				
				requirementInterview.setClientName(requirementInterview.getSubmissionId().getRequirement().getClientId().getResponderLocationId().getOrg().getOrgName());
				
			}
			
			
			resultJson = commonUtil.toJSON(requirementInterviewList);
			return resultJson;
		}

}
