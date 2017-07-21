package com.bis.sourcelead.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.Invoice;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;

@RestController
public class ProjectPlanningRestController {

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	RolesService roleService;
	
	@Autowired
	ProjectsService projectsService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	CandidateResumesService candidateResumesService;
	
	@Autowired
	ProjectPlanningRequestService projectPlanningRequestService;
	
	@Autowired
	UserService userService;
	
	 @RequestMapping(value = "/getRequestedAssociatesList", method = RequestMethod.GET)
	 public @ResponseBody String  getRequestedAssociatesList(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
			 @RequestParam(value="roles", required=false) String roles,@RequestParam(value="resourceType", required=false) String resourceType,
			 @RequestParam(value="selectedAssociates", required=false) String selectedAssociates) throws Exception{ 
		 
		String resultJson;
		
		List<Long> roleIds = null; 
		
		if(!isNullOrEmpty(roles)){
			roleIds = new ArrayList<Long>();
			 String[] rolesList = roles.split(",");
			 for(int i=0; i < rolesList.length; i++){
				 roleIds.add(Long.valueOf(rolesList[i]));
			 }
			 
		}
		
		List<Long> associateIds = null; 
		if(!isNullOrEmpty(selectedAssociates)){
			associateIds = new ArrayList<Long>();
			 String[] associateIdsList = selectedAssociates.split(",");
			 for(int i=0; i < associateIdsList.length; i++){
				 associateIds.add(Long.valueOf(associateIdsList[i]));
			 }
		}
		
		
		List<Candidate> assoicatesList = new ArrayList<Candidate>(); 
		List<Candidate> candidatesList = candidateService.getRequestedCandidatesList(orgLocationId, roleIds, resourceType,associateIds);
		 for(Candidate candidate : candidatesList){
			 Integer projectsCount = projectsService.getUserAssignedProjectsCount(candidate.getConsultantUser().getId(), orgLocationId);
			 candidate.setAssignedProjectsCount(projectsCount);
			 
			 UserRole userRole = userRoleService.getUserDetailsByUserIdAndLocationId(candidate.getConsultantUser().getId(), orgLocationId);
			 candidate.setUserRole(userRole.getRoles().getDisplayName());
			 if(userRole.getReportingManager() != null){
				 StringBuilder reportingManager = new StringBuilder();
				 if(!isNullOrEmpty(userRole.getReportingManager().getLastName())){
					 reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName()).append(" ").append(userRole.getReportingManager().getLastName());
				 }else{
					 reportingManager = reportingManager.append(userRole.getReportingManager().getFirstName());
				 }
				 candidate.setReportingManager(reportingManager.toString());
			 }else{
				 candidate.setReportingManager("----");
			 }
			 
			 CandidateResumes candidateResumes = candidateResumesService.getCandidateResumeByCandidateId(candidate.getId());
			 if(candidateResumes != null){
				 candidate.setResumeUUID(candidateResumes.getResumeUUID());
			 }
				
			 assoicatesList.add(candidate);
		 }
		 
		 resultJson = commonUtil.toJSON(assoicatesList);
		 return resultJson;
	  }
	 
	 
	 
	 @RequestMapping(value = "/getOrgLocationRoles", method = RequestMethod.GET)
	 public @ResponseBody String  getOrgLocationRoles(@RequestParam(value="orgLocations", required=false) String orgLocations) throws Exception{ 
		 
		String resultJson;
		
		List<Long> orgLocationIds = null; 
		List<Long> roleIds = null; 
		
		 String[] orgLocationId = orgLocations.split(",");
		
		 List<Roles> rolesList = roleService.getRoles(Long.valueOf(orgLocationId[0]));
		 
		 resultJson = commonUtil.toJSON(rolesList);
		 return resultJson;
	  }
	 
	 
	 @RequestMapping(value = "/getEstimatedHoursForResourceRequest", method = RequestMethod.GET)
		public @ResponseBody Integer getEstimatedHoursForResourceRequest(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate) throws Exception {
			
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			int estimatedHours = 0;
			
			try {

				Date requestStartDate =  formatter.parse(startDate);
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(requestStartDate); 
				
				Date requestEndDate =  formatter.parse(endDate);
				Calendar endCal = Calendar.getInstance();
				endCal.setTime(requestEndDate); 
				
				int workDays = 0;
				
			    if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			        startCal.setTime(requestEndDate);
			        endCal.setTime(requestStartDate);
			    }

			    do {
			        if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			            ++workDays;
			        }
			        startCal.add(Calendar.DATE, 1);
			    } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()); 

			    estimatedHours = (workDays*Constants.WORKING_HOURS);

	        } catch (ParseException e) {
	            e.printStackTrace();
	        }
			
			return estimatedHours;
		} 
	 
	 
	  @RequestMapping(value = "/saveResourceRequest")
		public @ResponseBody String saveResourceRequest(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="saveType", required=true) String saveType,HttpServletRequest request) throws Exception {
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    
		    JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		    projectPlanningRequestService.saveProjectPlanningRequest(jsonObj, user, orgLocations, projectUUID,saveType);
		   
		    return "success";
	    }
	  
	  
	  @RequestMapping(value = "/getPlanningRequestListByProjectUUID")
		public @ResponseBody String getPlanningRequestListByProjectUUID(@RequestParam(value="projectUUID", required=true) String projectUUID,HttpServletRequest request) throws Exception {
		    
		  OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		  String resultJson;
		  Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		  List<ProjectPlanningRequest> planningRequestList = new ArrayList<ProjectPlanningRequest>();
		  List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByProjectId(projects.getId());
		  for(ProjectPlanningRequest planningRequest : projectPlanningRequestList){
			  SimpleDateFormat format = orgLocations.getLocFullFormDateFormatter();
			  if(null != planningRequest.getCreatedDate()){
				  planningRequest.setRequestedDateStr(format.format(planningRequest.getCreatedDate()));
				}
			  planningRequestList.add(planningRequest);
		  }
		  
		  resultJson = commonUtil.toJSON(planningRequestList);
		  return resultJson;
	    }
	  
	  
	  
	  @RequestMapping(value = "/getResourceRequestDetailsByRequestUUID")
		 public ProjectPlanningRequest getResourceRequestDetailsByRequestUUID(@RequestParam(value="requestUUID", required=false) String requestUUID) throws Exception {
				
				return  projectPlanningRequestService.getProjectPlanningRequestByRequestUUID(requestUUID);
		 }
	  
	  
	  
	  @RequestMapping(value = "/getResourceRequestApproversList", method = RequestMethod.GET)
		public @ResponseBody String getResourceRequestApproversList(@RequestParam(value="orgLocationId", required=true) Long orgLocationId) throws Exception {
		    
		  String resultJson;
		  
	      List<User> approversList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocationId,Long.valueOf(Constants.TAB_PROJECTS));
		  
		  resultJson = commonUtil.toJSON(approversList);
		  return resultJson;
	    }
	  
	 
	 

	 
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
}
