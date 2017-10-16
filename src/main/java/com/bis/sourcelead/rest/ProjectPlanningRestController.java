package com.bis.sourcelead.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dto.ProjectBudgetRequestDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateResumes;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectPlanningNewResourceRequestService;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.service.ProjectPlanningResourceRequestService;
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
	ProjectPlanningResourceRequestService projectPlanningResourceRequestService;
	
	@Autowired
	ProjectPlanningNewResourceRequestService projectPlanningNewResourceRequestService; 
	
	@Autowired
	ProjectBudgetService projectBudgetService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrgLocationService orgLocationService;
	
	 @RequestMapping(value = "/getRequestedAssociatesList", method = RequestMethod.GET)
	 public @ResponseBody String  getRequestedAssociatesList(@RequestParam(value="orgLocationId", required=false) Long orgLocationId,
			 @RequestParam(value="roles", required=false) String roles,@RequestParam(value="resourceType", required=false) String resourceType,
			 @RequestParam(value="selectedAssociates", required=false) String selectedAssociates,
			 @RequestParam(value="projectUUID", required=false) String projectUUID) throws Exception{ 
		 
		String resultJson;
		
		List<Long> roleIds = null; 
		Projects projects = null;
		
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
		
		if(!isNullOrEmpty(projectUUID)){
			projects = projectsService.getProjectByProjectUUID(projectUUID);
			List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByProjectId(projects.getId());
		    if(projectPlanningRequestList != null && projectPlanningRequestList.size() > 0){
		    	if(null == associateIds){
		    		associateIds = new ArrayList<Long>();
		    	}
		    	for(ProjectPlanningRequest planningRequest : projectPlanningRequestList){
		    		Set<ProjectPlanningResourceRequest> projectPlanningResourceRequestList = planningRequest.getProjectPlanningResourceRequestList();
		    		for(ProjectPlanningResourceRequest resourceRequest : projectPlanningResourceRequestList){
		    			associateIds.add(resourceRequest.getCandidateId().getId());
		    		}
		    	}
		    }
		}
		
		
		List<Candidate> assoicatesList = new ArrayList<Candidate>(); 
		List<Candidate> candidatesList = candidateService.getRequestedCandidatesList(orgLocationId, roleIds, resourceType,associateIds,projects.getId());
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
	 public @ResponseBody List<Roles>  getOrgLocationRoles(@RequestParam(value="orgLocations", required=false) String orgLocations) throws Exception{ 
		 
		 List<Roles> rolesList = roleService.getRoles(Long.valueOf(orgLocations));
		 return rolesList;
	  }
	 
	 
	 @RequestMapping(value = "/getEstimatedHoursForResourceRequest", method = RequestMethod.GET)
		public @ResponseBody Integer getEstimatedHoursForResourceRequest(@RequestParam(value = "startDate", required = false) String startDate,
				@RequestParam(value = "endDate", required = false) String endDate,@RequestParam(value = "averageWorkingHours", required = false) Integer averageWorkingHours,@RequestParam(value = "locationId", required = false) Long locationId) throws Exception {
			
			OrgLocations orgLocations = orgLocationService.getOrgLocations(locationId);

			int estimatedHours = 0;
			
			try {

				Date requestStartDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),startDate);
				Calendar startCal = Calendar.getInstance();
				startCal.setTime(requestStartDate); 
				
				Date requestEndDate =  commonUtil.orgLocationDateFormat(orgLocations.getDateFormat(),endDate);
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

			    estimatedHours = (workDays*averageWorkingHours);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			return estimatedHours;
		} 
	 
	 
	  @RequestMapping(value = "/saveResourceRequest")
		public @ResponseBody String saveResourceRequest(@RequestParam("json") String jsonPayload,@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="saveType", required=true) String saveType,@RequestParam(value="", required=true) Long userId,
				@RequestParam(value="orgLocationId", required=true) Long orgLocationId) throws Exception {
		    
		    User user = userService.getUserById(userId);
		    OrgLocations orgLocations = orgLocationService.getOrgLocations(orgLocationId);
		    
		    JSONObject jsonObj = new JSONObject(jsonPayload);
		    projectPlanningRequestService.saveProjectPlanningRequest(jsonObj, user, orgLocations, projectUUID,saveType);
		   
		    return "success";
	    }
	  
	  @RequestMapping(value = "/saveNewResourceRequest")
	  public @ResponseBody String saveNewResourceRequest(@RequestParam(value="projectUUID", required=true) String projectUUID,
			@RequestParam(value="saveType", required=true) String saveType,HttpServletRequest request) throws Exception {
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		    JSONObject jsonObj = new JSONObject(request.getParameter("json"));
		    projectPlanningRequestService.saveProjectNewResourceRequest(jsonObj, user, orgLocations, projectUUID,saveType);
		   
		    return "success";
      }
	  
	  @RequestMapping(value = "/getPlanningRequestListByProjectUUID")
		public @ResponseBody List<ProjectPlanningRequest> getPlanningRequestListByProjectUUID(@RequestParam(value="projectUUID", required=true) String projectUUID) throws Exception {
		    
		  Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		  List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByProjectId(projects.getId());
		  return projectPlanningRequestList;
	    }
	  
	  
	  
	  @RequestMapping(value = "/getResourceRequestDetailsByRequestUUID")
		 public ProjectPlanningRequest getResourceRequestDetailsByRequestUUID(@RequestParam(value="requestUUID", required=false) String requestUUID) throws Exception {
		  ProjectPlanningRequest projectPlanningRequest =   projectPlanningRequestService.getProjectPlanningRequestByRequestUUID(requestUUID);
		 
		  Set<ProjectPlanningResourceRequest> planningResourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
		  if(planningResourceRequestList != null && planningResourceRequestList.size() > 0) {
			  List<ProjectPlanningResourceRequest> resourceRequestList = new ArrayList<>();
			  for(ProjectPlanningResourceRequest resourceRequest : planningResourceRequestList) {
				  resourceRequestList.add(resourceRequest);
			  }
			  projectPlanningRequest.setResourceRequestList(resourceRequestList);
		  }
		  
		  Set<ProjectPlanningNewResourceRequest> newRequestRessourceList = projectPlanningRequest.getProjectPlanningNewResourceRequestList();
		  if(newRequestRessourceList != null && newRequestRessourceList.size() > 0) {
			  List<ProjectPlanningNewResourceRequest> newResourceRequestList = new ArrayList<>();
			  for(ProjectPlanningNewResourceRequest requestList : newRequestRessourceList) {
				  newResourceRequestList.add(requestList);
			  }
			  projectPlanningRequest.setNewResourceRequestList(newResourceRequestList);
		  }
		 
		  return  projectPlanningRequest;
		 }
	  
	  
	  
	  @RequestMapping(value = "/getResourceRequestApproversList", method = RequestMethod.GET)
		public @ResponseBody List<User>  getResourceRequestApproversList(@RequestParam(value="orgLocationId", required=true) Long orgLocationId) throws Exception {
		    
	      List<User> approversList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocationId,Long.valueOf(Constants.TAB_PROJECTS));
		  return approversList;
	    }
	  
	  
	  @RequestMapping(value = "/getResourceRequestsCounts", method = RequestMethod.GET)
		public @ResponseBody ProjectPlanningRequest getResourceRequestsCounts(@RequestParam(value="approverUserId", required=true) Long approverUserId,
				@RequestParam(value="orgLocationId", required=true) Long orgLocationId) throws Exception {
		    
		  List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByApproverIdAndOrgLocationId(approverUserId, orgLocationId);
		 
		  ProjectPlanningRequest projectPlanningRequest = new ProjectPlanningRequest();
		  
		  Integer waitingCount = 0;
		  Integer approvedCount = 0;
		  Integer rejectedCount = 0;
		  Integer onholdCount = 0;
		  
		  for(ProjectPlanningRequest planningRequest : projectPlanningRequestList){
			  if(planningRequest.getRequestStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_APPROVED)){
				  approvedCount = approvedCount + 1;
			  }else if(planningRequest.getRequestStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_WAITING_FOR_APPROVAL)){
				  waitingCount = waitingCount + 1;
			  }else if(planningRequest.getRequestStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_REJECTED)){
				  rejectedCount = rejectedCount + 1;
			  }else if(planningRequest.getRequestStatus().equalsIgnoreCase(Constants.RESOURCE_REQUEST_ON_HOLD)){
				  onholdCount = onholdCount + 1;
			  }
		  }
		  
		  projectPlanningRequest.setWaitingCount(waitingCount);
		  projectPlanningRequest.setApprovedCount(approvedCount);
		  projectPlanningRequest.setRejectedCount(rejectedCount);
		  projectPlanningRequest.setOnholdCount(onholdCount);
		  
		  return projectPlanningRequest;
		  
	    }
	  
	  
	    @RequestMapping(value = "/getPlanningRequestListByApproverIdAndOrgLocationId", method = RequestMethod.GET)
		public @ResponseBody List<ProjectPlanningRequest> getPlanningRequestListByApproverIdAndOrgLocationId(@RequestParam(value="approverUserId", required=true) Long approverUserId,
				@RequestParam(value="orgLocationId", required=true) Long orgLocationId,@RequestParam(value="requestType", required=true) String requestType) throws Exception {
		    
		  
		  List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByApproverId(approverUserId, orgLocationId, requestType);
		  
		  return projectPlanningRequestList;
		  
	    }
	    
	   
	    @RequestMapping(value = "/rejectRequestedAssociate", method = RequestMethod.POST)
		public @ResponseBody String rejectRequestedAssociate(@RequestParam("json") String jsonPayload,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
        	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
	    	User user = userService.getUserById(userId); 
		  
	    	 JSONObject jsonObj = new JSONObject(jsonPayload);
	    	 if(jsonObj.has("requestUUID") && !isNullOrEmpty(jsonObj.getString("requestUUID"))){
	    		 ProjectPlanningResourceRequest resourceRequest = projectPlanningResourceRequestService.getProjectPlanningResourceRequestByRequestUUID(jsonObj.getString("requestUUID"));
	    		 StringBuilder rejectReason = new StringBuilder();
	    		 rejectReason = rejectReason.append(jsonObj.getString("rejectedReason"));
	    		 resourceRequest.setRejectedReason(rejectReason.toString());
	    		 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_REJECTED);
	    		 
	    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestById(resourceRequest.getProjectPlanningRequestId().getId());
	    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_REJECTED);
	    		 
	    		 projectPlanningResourceRequestService.saveProjectPlanningResourceRequest(resourceRequest);
	    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);
	    		 
	    		 //Sending emil to Approver
	    		 projectPlanningResourceRequestService.sendMailToAcceptAndRejectSingleResourceRequestApprover(resourceRequest, user, orgLocations);
	    	 }
		  
		     return "sucess";
		  
	    }
	    
	    
	    
	    @RequestMapping(value = "/rejectAssociatesRequest", method = RequestMethod.POST)
		public @ResponseBody String rejectAssociatesRequest(@RequestParam("json") String jsonPayload,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
	    	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
	    	User user = userService.getUserById(userId); 
	    	 JSONObject jsonObj = new JSONObject(jsonPayload);
	    	 if(jsonObj.has("requestUUID") && !isNullOrEmpty(jsonObj.getString("requestUUID"))){
	    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestByRequestUUID(jsonObj.getString("requestUUID"));
	    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_REJECTED);
	    		 projectPlanningRequest.setRejectedReason(jsonObj.getString("rejectedReason"));
	    		 if(projectPlanningRequest.getRequestType().equalsIgnoreCase(Constants.RESOURCE_REQUEST_ASSOCIATE_REQUEST)){
	    			 Set<ProjectPlanningResourceRequest> resourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
		    		 for(ProjectPlanningResourceRequest resourceRequest : resourceRequestList){
		    			 if(isNullOrEmpty(resourceRequest.getResourceStatus())){
		    				 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_REJECTED);
			    			 projectPlanningResourceRequestService.saveProjectPlanningResourceRequest(resourceRequest);
		    			 }
		    		 }
		    		 projectPlanningResourceRequestService.sendMailToAcceptAndRejectBulkResourceRequestApprover(projectPlanningRequest, user, orgLocations);
	    		 }else{
	    			 Set<ProjectPlanningNewResourceRequest> newresourceRequestList = projectPlanningRequest.getProjectPlanningNewResourceRequestList();
		    		 for(ProjectPlanningNewResourceRequest resourceRequest : newresourceRequestList){
		    				 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_REJECTED);
			    			 projectPlanningNewResourceRequestService.saveProjectPlanningNewResourceRequest(resourceRequest);
		    		 }
		    		 projectPlanningRequest.setAllocatedResources(newresourceRequestList.size());
		    		 projectPlanningNewResourceRequestService.sendMailToAcceptAndRejectBulkNewResourceRequestApprover(projectPlanningRequest,user,orgLocations);
	    		 }
	    		
	    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);
	    	 }
		  
		     return "sucess";
		  
	    }
	 
	    
	    @RequestMapping(value = "/onholdAssociateRequest", method = RequestMethod.POST)
		public @ResponseBody String onholdAssociateRequest(@RequestParam("requestUUID") String requestUUID,@RequestParam("json") String jsonPayload,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
	    	JSONObject jsonObj = new JSONObject(jsonPayload);
	    	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
	    	User user = userService.getUserById(userId); 
	    	 if(!isNullOrEmpty(requestUUID)){
	    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestByRequestUUID(requestUUID);
	    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_ON_HOLD);
	    		 projectPlanningRequest.setRejectedReason(jsonObj.getString("rejectedReason"));
	    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);
	    		 if(projectPlanningRequest.getRequestType().equalsIgnoreCase(Constants.RESOURCE_REQUEST_ASSOCIATE_REQUEST)){
	    			 projectPlanningResourceRequestService.sendMailToOnHoldBulkResourceRequestApprover(projectPlanningRequest, user, orgLocations);
	    		 }else{
	    			 projectPlanningNewResourceRequestService.sendMailToOnHoldBulkNewResourceRequestApprover(projectPlanningRequest, user, orgLocations);
	    		 }
	    	 }
		     return "sucess";
	    }
	    
	    @RequestMapping(value = "/acceptRequestedAssociate", method = RequestMethod.POST)
		public @ResponseBody String acceptRequestedAssociate(@RequestParam("requestUUID") String requestUUID,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
        	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
	    	User user = userService.getUserById(userId); 
		  
	    	 if(!isNullOrEmpty(requestUUID)){
	    		 ProjectPlanningResourceRequest projectPlanningResourceRequest = projectPlanningResourceRequestService.getProjectPlanningResourceRequestByRequestUUID(requestUUID);
	    		 projectPlanningResourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_APPROVED);
	    		 projectPlanningResourceRequestService.saveProjectPlanningResourceRequest(projectPlanningResourceRequest);
	    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningResourceRequest.getProjectPlanningRequestId();
	    		 Integer allocatedResouresCount = projectPlanningRequest.getAllocatedResources()+1;
	    		 projectPlanningRequest.setAllocatedResources(allocatedResouresCount);
	    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);

	    		 //Sending emil To Aprover
	    		 projectPlanningResourceRequestService.sendMailToAcceptAndRejectSingleResourceRequestApprover(projectPlanningResourceRequest, user, orgLocations);
	    	 }
		     return "sucess";
	    }
	    
	    @RequestMapping(value = "/acceptAssociateRequest", method = RequestMethod.POST)
		public @ResponseBody String acceptAssociateRequest(@RequestParam("requestUUID") String requestUUID,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
	    	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
	    	User user = userService.getUserById(userId); 
	    	 if(!isNullOrEmpty(requestUUID)){
	    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestByRequestUUID(requestUUID);
	    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_APPROVED);
	    		
	    		if(projectPlanningRequest.getRequestType().equalsIgnoreCase(Constants.RESOURCE_REQUEST_ASSOCIATE_REQUEST)){
	    			Set<ProjectPlanningResourceRequest> resourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
		    		 for(ProjectPlanningResourceRequest resourceRequest : resourceRequestList){
		    				 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_APPROVED);
			    			 projectPlanningResourceRequestService.saveProjectPlanningResourceRequest(resourceRequest);
		    		 }
		    		 projectPlanningRequest.setAllocatedResources(resourceRequestList.size());
		    		 projectPlanningResourceRequestService.sendMailToAcceptAndRejectBulkResourceRequestApprover(projectPlanningRequest, user, orgLocations);
	    		}else{
	    			Set<ProjectPlanningNewResourceRequest> newresourceRequestList = projectPlanningRequest.getProjectPlanningNewResourceRequestList();
		    		 for(ProjectPlanningNewResourceRequest resourceRequest : newresourceRequestList){
		    				 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_APPROVED);
			    			 projectPlanningNewResourceRequestService.saveProjectPlanningNewResourceRequest(resourceRequest);
		    		 }
		    		 projectPlanningRequest.setAllocatedResources(newresourceRequestList.size());
		    		 //Send Email To Approver
		    		 projectPlanningNewResourceRequestService.sendMailToAcceptAndRejectBulkNewResourceRequestApprover(projectPlanningRequest,user,orgLocations);
	    		}
	    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);
	    	 }
		     return "sucess";
	    }
	 
	    
	    /**
	     * @author Laxman 
	     * this method used for accepting new resource request in dashboard 
	     * @param requestUUID
	     * @return sucess
	     * @throws Exception
	     */
	        @RequestMapping(value = "/acceptNewRequestedResource", method = RequestMethod.POST)
	  		public @ResponseBody String acceptNewRequestedResource(@RequestParam("requestUUID") String requestUUID,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
	        	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
		    	User user = userService.getUserById(userId); 
	  	    	 if(!isNullOrEmpty(requestUUID)){
	  	    		 
	  	    		 ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest = projectPlanningNewResourceRequestService.getProjectPlanningNewResourceRequestByRequestUUID(requestUUID);
	  	    		 projectPlanningNewResourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_APPROVED);
	  	    		 projectPlanningNewResourceRequestService.saveProjectPlanningNewResourceRequest(projectPlanningNewResourceRequest);
	  	    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningNewResourceRequest.getProjectPlanningRequestId();
	  	    		 Integer allocatedResouresCount = projectPlanningRequest.getAllocatedResources()+1;
	  	    		 projectPlanningRequest.setAllocatedResources(allocatedResouresCount);
	  	    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);
	  	    		 //Send Email Accepting for  Approver 
	  	    		projectPlanningNewResourceRequestService.sendMailToAcceptAndRejectSingleNewResourceRequestApprover(projectPlanningNewResourceRequest, user, orgLocations);
	  	    	 }
	  		     return "sucess";
	  	    }
	    
	    
	        /**
		     * @author Laxman 
		     * this method used for reject new resource request in dashboard 
		     * @param requestUUID
		     * @return sucess
		     * @throws Exception
		     */
	        @RequestMapping(value = "/rejectNewRequestedResource", method = RequestMethod.POST)
			public @ResponseBody String rejectNewRequestedResource(@RequestParam("json") String jsonPayload,@RequestParam("userId") String userId,@RequestParam("orglocationId") String orglocationId) throws Exception {
	        	OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(orglocationId));
		    	User user = userService.getUserById(Long.valueOf(userId)); 
		    	 JSONObject jsonObj = new JSONObject(jsonPayload);
		    	 if(jsonObj.has("requestUUID") && !isNullOrEmpty(jsonObj.getString("requestUUID"))){
		    		 ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest = projectPlanningNewResourceRequestService.getProjectPlanningNewResourceRequestByRequestUUID(jsonObj.getString("requestUUID"));
		    		 StringBuilder rejectReason = new StringBuilder();
		    		 rejectReason = rejectReason.append("Reason for ").append(" : ").append(jsonObj.getString("rejectedReason"));
		    		 projectPlanningNewResourceRequest.setRejectedReason(rejectReason.toString());
		    		 projectPlanningNewResourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_REJECTED);
		    		 
		    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestById(projectPlanningNewResourceRequest.getProjectPlanningRequestId().getId());
		    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_REJECTED);
		    		 
		    		 projectPlanningNewResourceRequestService.saveProjectPlanningNewResourceRequest(projectPlanningNewResourceRequest);
		    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);
		    		 
		    		 projectPlanningNewResourceRequestService.sendMailToAcceptAndRejectSingleNewResourceRequestApprover(projectPlanningNewResourceRequest, user, orgLocations);
		    	 }
			  
			     return "sucess";
			  
		    }

	        /**
	         * @author Laxman
	         * @param projectUUID
	         * @return projectBudgetList
	         * @throws Exception
	         */
	        @RequestMapping(value = "/getBudgetRequestListByProjectUUID")
			public @ResponseBody List<ProjectBudget> getBudgetRequestListByProjectUUID(@RequestParam(value="projectUUID", required=true) String projectUUID) throws Exception {
			  Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
			  List<ProjectBudget> projectBudgetList= projectBudgetService.getProjectBudgetByProjectTaskId(projects.getId());
			  return projectBudgetList;
		    }
	        
		 /**
		  * @author Laxman
		  * @param projectBudgetRequestDTO
		  * @return success
		  * @throws Exception
		  */
	        @RequestMapping(value = "/saveBudgetRequest",method = RequestMethod.POST)
			public @ResponseBody String saveBudgetRequest(ProjectBudgetRequestDTO projectBudgetRequestDTO) throws Exception {
			       User user = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getLoginUserid()));
			       projectBudgetService.saveProjectBudgetRequset(projectBudgetRequestDTO, user);
			    return "success";
		    }
	        
	        
	        /**
	         * @author Laxman
	         * @param projectBudgetRequestDTO
	         * @return success
	         * @throws Exception
	         */
	        @RequestMapping(value = "/saveBudgetRequestReason",method = RequestMethod.POST)
			public @ResponseBody String saveBudgetRequestReason(ProjectBudgetRequestDTO projectBudgetRequestDTO) throws Exception {
			       if(StringUtils.isNotBlank(projectBudgetRequestDTO.getBudgetRequestedId())) {
			    	   User user = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getLoginUserid()));
			    	   ProjectBudget projectBudget =  projectBudgetService.getProjectBudgetsById(Long.valueOf(projectBudgetRequestDTO.getBudgetRequestedId()));
				       if(projectBudget != null) {
				    	   projectBudget.setRejectedReason(projectBudgetRequestDTO.getReasonForProjectBudget());
				    	   projectBudget.setUpdateUserId(user);
				    	   projectBudget.setBudgetStatus(Constants.BUDGET_REQUEST_REJECTED);
				    	   projectBudget.setUpdatedDate(new Date());
				    	   projectBudgetService.saveProjectBudget(projectBudget);
				    	   if(projectBudget.getApproverUserId() != null && user.getId() != projectBudget.getApproverUserId().getId()) {
				    		   projectBudgetService.sendEmailForBugetRequestApproversByAcceptAndRejectAndOnHold(user,projectBudget);
				    	   }
				       }
			       }
			    return "success";
		    }
	        
	        
	        /**
	         * @author Laxman
	         * @param budgetRequestedId
	         * @param userId
	         * @return success
	         * @throws Exception
	         */
	        @RequestMapping(value = "/acceptBudgetRequest",method = RequestMethod.POST)
			public @ResponseBody String acceptBudgetRequest(@RequestParam("budgetRequestedId") Long budgetRequestedId,@RequestParam("userId") Long userId) throws Exception {
			    	   User user = userService.getUserById(userId);
			    	   ProjectBudget projectBudget =  projectBudgetService.getProjectBudgetsById(budgetRequestedId);
				       if(projectBudget != null) {
				    	   projectBudget.setUpdateUserId(user);
				    	   projectBudget.setBudgetStatus(Constants.BUDGET_REQUEST_APPROVED);
				    	   projectBudget.setUpdatedDate(new Date());
				    	   projectBudgetService.saveProjectBudget(projectBudget);
				    	   if(user.getId() != projectBudget.getApproverUserId().getId()) {
				    		   projectBudgetService.sendEmailForBugetRequestApproversByAcceptAndRejectAndOnHold(user,projectBudget);
				    	   }
				       }
			    return "success";
		    }
	        
	        
	        /**
	         * @author Laxman
	         * @param budgetRequestedId
	         * @param userId
	         * @return success
	         * @throws Exception
	         */
	        @RequestMapping(value = "/onHoldBudgetRequest",method = RequestMethod.POST)
			public @ResponseBody String onHoldBudgetRequest(ProjectBudgetRequestDTO projectBudgetRequestDTO) throws Exception {
				       if(StringUtils.isNotBlank(projectBudgetRequestDTO.getBudgetRequestedId())) {
				    	   User user = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getLoginUserid()));
				    	   ProjectBudget projectBudget =  projectBudgetService.getProjectBudgetsById(Long.valueOf(projectBudgetRequestDTO.getBudgetRequestedId()));
					       if(projectBudget != null) {
					    	   projectBudget.setRejectedReason(projectBudgetRequestDTO.getReasonForProjectBudget());
					    	   projectBudget.setUpdateUserId(user);
					    	   projectBudget.setBudgetStatus(Constants.BUDGET_REQUEST_ON_HOLD);
					    	   projectBudget.setUpdatedDate(new Date());
					    	   projectBudgetService.saveProjectBudget(projectBudget);
					    	   if(user.getId() != projectBudget.getApproverUserId().getId()) {
					    		   projectBudgetService.sendEmailForBugetRequestApproversByAcceptAndRejectAndOnHold(user,projectBudget);
					    	   }
					       }
				       }
				       
			    return "success";
		    }
	        
	        
	        /**
	         * @author Laxman
	         * @param budgetRequestedId
	         * @return projectBudget
	         * @throws Exception
	         */
	        @RequestMapping(value = "/getBudgetRequestDetailsById",method = RequestMethod.POST)
			public @ResponseBody ProjectBudget getBudgetRequestDetailsById(@RequestParam("budgetRequestedId") Long budgetRequestedId) throws Exception {
			    	   ProjectBudget projectBudget =  projectBudgetService.getProjectBudgetsById(budgetRequestedId);
			    return projectBudget;
		    }
	        
	        
	        /**
	         * @author Laxman
	         * @param orgLocationId
	         * @param requestType
	         * @return projectBudgetList
	         * @throws Exception
	         */
	        @RequestMapping(value = "/getBudgetRequsetListByBudgetStatusAndOrgLocationId", method = RequestMethod.GET)
			public @ResponseBody List<ProjectBudget> getBudgetRequsetListByBudgetStatusAndOrgLocationId(@RequestParam(value="orgLocationId", required=true) Long orgLocationId,@RequestParam(value="requestType", required=true) String requestType) throws Exception {
	        	List<ProjectBudget> projectBudgetList= projectBudgetService.getProjectBudgetListByOrgLocationIdAndBudgetStatus(orgLocationId, requestType);
			  return projectBudgetList;
			  
		    }
	        
	        
	        /**'
	         * @author Laxman
	         * @param orgLocationId
	         * @return projectBudget
	         * @throws Exception
	         */
	        @RequestMapping(value = "/getBudgetRequestListCount", method = RequestMethod.GET)
			public @ResponseBody ProjectBudget getBudgetRequestListCount(@RequestParam(value="orgLocationId", required=true) Long orgLocationId) throws Exception {
	        	List<ProjectBudget> projectBudgetList= projectBudgetService.getProjectBudgetListByOrgLocationId(orgLocationId);
	        	  ProjectBudget projectBudget = new ProjectBudget();
				  Integer waitingCount = 0;
				  Integer approvedCount = 0;
				  Integer rejectedCount = 0;
				  Integer onholdCount = 0;
				  
				  for(ProjectBudget budget : projectBudgetList){
					  if(budget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_APPROVED)){
						  approvedCount = approvedCount + 1;
					  }else if(budget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_WAITING_FOR_APPROVAL)){
						  waitingCount = waitingCount + 1;
					  }else if(budget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_REJECTED)){
						  rejectedCount = rejectedCount + 1;
					  }else if(budget.getBudgetStatus().equalsIgnoreCase(Constants.BUDGET_REQUEST_ON_HOLD)){
						  onholdCount = onholdCount + 1;
					  }
				  }
				  
				  projectBudget.setWaitingCount(waitingCount);
				  projectBudget.setApprovedCount(approvedCount);
				  projectBudget.setRejectedCount(rejectedCount);
				  projectBudget.setOnholdCount(onholdCount);
				  
				  return projectBudget;
			  
		    }
	        
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
}
