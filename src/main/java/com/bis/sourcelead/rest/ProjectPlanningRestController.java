package com.bis.sourcelead.rest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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

import com.bis.sourcelead.annotations.Validator;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dto.AssociateRequestDTO;
import com.bis.sourcelead.dto.CandidateDTO;
import com.bis.sourcelead.dto.ProjectAssignedMembersDTO;
import com.bis.sourcelead.dto.ProjectBudgetRequestDTO;
import com.bis.sourcelead.dto.ProjectMembersDTO;
import com.bis.sourcelead.dto.ProjectPlanningNewResourceRequestDTO;
import com.bis.sourcelead.dto.ProjectPlanningResourceRequestDTO;
import com.bis.sourcelead.dto.ProjectResourceRequestDTO;
import com.bis.sourcelead.dto.ResourceRequestDTO;
import com.bis.sourcelead.dto.ResourcesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.MasterCandidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.OrgSpoc;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Result;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CandidateResumesService;
import com.bis.sourcelead.service.CandidateService;
import com.bis.sourcelead.service.ClientService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgSpocService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectPlanningNewResourceRequestService;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.service.ProjectPlanningResourceRequestService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.bis.sourcelead.web.RegistrationController;

@RestController
@RequestMapping("/app")
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
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	OrgRelationsService orgRelationsService;
	
	@Autowired
	RegistrationController registrationController;
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	OrgSpocService orgSpocService;
	
	@Autowired
	EmployeesDao employeeDao;
	
	@Autowired
	ProjectClientService projectClientService;
	
	@Autowired
	ProjectMembersHistoryService projectMembersHistoryService;
	
	 @RequestMapping(value = "/getRequestedAssociatesList", method = RequestMethod.GET)
	 public @ResponseBody ResponseList<CandidateDTO>  getRequestedAssociatesList(CandidateDTO candidateDTO) throws Exception{ 
		 
		
		List<Long> roleIds = null; 
		List<Long> teamIds = null;
		Projects projects = null;
		
		
		String[] tableColumns = {"ischeck","name", "primarySkill", "rate", "employmentType", "manager", "resume", "assignedProjectsCount"};
	    int sortColumnIndex = 1; //by default sorting using updated date
		String sortColumnOrder = "desc"; //by default referContactsDTO order
		if(candidateDTO.getOrder() != null && !candidateDTO.getOrder().isEmpty()) {
			sortColumnIndex = Integer.parseInt(candidateDTO.getOrder().get(0).get("column"));
			sortColumnOrder = candidateDTO.getOrder().get(0).get("dir");
		}
		candidateDTO.setSortColumn(tableColumns[sortColumnIndex]);
		candidateDTO.setSortOrder(sortColumnOrder);
		
		if(!isNullOrEmpty(candidateDTO.getRoles())){
			roleIds = new ArrayList<Long>();
			 String[] rolesList = candidateDTO.getRoles().split(",");
			 for(int i=0; i < rolesList.length; i++){
				 roleIds.add(Long.valueOf(rolesList[i]));
			 }
			 
		}
		
		
		if(!isNullOrEmpty(candidateDTO.getTeams())){
			teamIds = new ArrayList<Long>();
			 String[] teamsList = candidateDTO.getTeams().split(",");
			 for(int i=0; i < teamsList.length; i++){
				 teamIds.add(Long.valueOf(teamsList[i]));
			 }
			 
		}
		
		List<Long> associateIds = null; 
		if(!isNullOrEmpty(candidateDTO.getSelectedAssociates())){
			associateIds = new ArrayList<Long>();
			 String[] associateIdsList = candidateDTO.getSelectedAssociates().split(",");
			 for(int i=0; i < associateIdsList.length; i++){
				 associateIds.add(Long.valueOf(associateIdsList[i]));
			 }
		}
		
		if(!isNullOrEmpty(candidateDTO.getProjectUUID())){
			projects = projectsService.getProjectByProjectUUID(candidateDTO.getProjectUUID());
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
		ResponseList<CandidateDTO> candidateDTOList = candidateService.getRequestedCandidatesList(candidateDTO,candidateDTO.getOrgLocationId(), roleIds,teamIds, candidateDTO.getResourceType(),associateIds,projects.getId());
		return candidateDTOList;
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
		public @ResponseBody Response<String> saveResourceRequest(ProjectResourceRequestDTO resourceRequestDTO) throws Exception {
		    Response<String> response = new Response<>();
		     User user = userService.getUserById(Long.valueOf(resourceRequestDTO.getLoginuserId()));
		     if(user != null) {
		    	 try {
		    		 outerLoop:{
						if (Validator.validate(resourceRequestDTO) && resourceRequestDTO.getAssociateResources() != null && !resourceRequestDTO.getAssociateResources().isEmpty()) {
							 for(AssociateRequestDTO associateRequestDTO :resourceRequestDTO.getAssociateResources()) {
								if(StringUtils.isEmpty(associateRequestDTO.getValidateRow()))
									 continue;
								if(!Validator.validate(associateRequestDTO)) {
									response.setObject("DataMissing");
									response.setResponse(Result.INVALID_DATA.name());
									response.setResponseMessage(Result.INVALID_DATA.getValue());
									break outerLoop;
								}
							  }
							 OrgLocations orgLocations = orgLocationService.getOrgLocations(Long.valueOf(resourceRequestDTO.getLoginOrglocationId()));
						     projectPlanningRequestService.saveProjectPlanningRequest(resourceRequestDTO, user, orgLocations);
							 response.setResponse(Result.SUCCESS.name());
							 response.setResponseMessage(Result.SUCCESS.getValue());
						} else {
							response.setObject("DataMissing");
							response.setResponse(Result.INVALID_DATA.name());
							response.setResponseMessage(Result.INVALID_DATA.getValue());
						}
				     }
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
	  
	  @RequestMapping(value = "/saveNewResourceRequest")
	  public @ResponseBody Response<String> saveNewResourceRequest(ResourceRequestDTO  resourceRequestDTO ,HttpServletRequest request) throws Exception {
		    Response<String> response = new Response<>();
		    User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    if (user != null) {
				try {
					outerLoop:{
						if (Validator.validate(resourceRequestDTO) &&resourceRequestDTO.getPlanningResources() != null && !resourceRequestDTO.getPlanningResources().isEmpty()) {
							 for(ResourcesDTO resourcesDTO :resourceRequestDTO.getPlanningResources()) {
								if(StringUtils.isEmpty(resourcesDTO.getValidateRow()))
									 continue;
								if(!Validator.validate(resourcesDTO)) {
									response.setObject("DataMissing");
									response.setResponse(Result.INVALID_DATA.name());
									response.setResponseMessage(Result.INVALID_DATA.getValue());
									break outerLoop;
								}
							}
								    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
								    projectPlanningRequestService.saveProjectNewResourceRequest(resourceRequestDTO, user, orgLocations);
									response.setResponse(Result.SUCCESS.name());
									response.setResponseMessage(Result.SUCCESS.getValue());
							
						} else {
							response.setObject("DataMissing");
							response.setResponse(Result.INVALID_DATA.name());
							response.setResponseMessage(Result.INVALID_DATA.getValue());
						}
				     }
				}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
		    } else {
				response.setResponse(Result.SESSION_TIMEDOUT.name());
				response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
			}
			return response;
      }
	  
	  @RequestMapping(value = "/getPlanningRequestListByProjectUUID")
		public @ResponseBody ResponseList<ProjectPlanningRequest> getPlanningRequestListByProjectUUID(@RequestParam(value="projectUUID", required=true) String projectUUID) throws Exception {
		    ResponseList<ProjectPlanningRequest> response = new ResponseList<>();
		    try {
		    	 Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		    	 if(projects != null) {
		    		 List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByProjectId(projects.getId());
					   response.setData(projectPlanningRequestList);
					   response.setResponse(Result.SUCCESS.name());
					   response.setResponseMessage(Result.SUCCESS.getValue());
		    	 }else {
		    		 response.setResponse(Result.INVALID_DATA.name());
					 response.setResponseMessage(Result.INVALID_DATA.getValue());
		    	 }
		    }catch (Exception e) {
				response.setResponse(Result.AWKWARD.name());
				response.setResponseMessage(Result.AWKWARD.getValue());
			}
		  
		  return response;
	    }
	  
	  
	  
	  @RequestMapping(value = "/getResourceRequestDetailsByRequestUUID")
		 public ProjectPlanningRequest getResourceRequestDetailsByRequestUUID(@RequestParam(value="requestUUID", required=false) String requestUUID) throws Exception {
		  ProjectPlanningRequest projectPlanningRequest =   projectPlanningRequestService.getProjectPlanningRequestByRequestUUID(requestUUID);
		 
		  Set<ProjectPlanningResourceRequest> planningResourceRequestList = projectPlanningRequest.getProjectPlanningResourceRequestList();
		  if(planningResourceRequestList != null && planningResourceRequestList.size() > 0) {
			  List<ProjectPlanningResourceRequest> resourceRequestList = new ArrayList<>();
			  for(ProjectPlanningResourceRequest resourceRequest : planningResourceRequestList) {
				  resourceRequest.setRequestedUser(resourceRequest.getProjectPlanningRequestId().getRequestedUser());
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
		    
	      List<User> approversList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocationId,Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
		  return approversList;
	    }
	  
	     
	  @RequestMapping(value = "/getSourceLeadActionApproversList", method = RequestMethod.GET)
	  public @ResponseBody List<UserDTO>  getSourceLeadActionApproversList(@RequestParam(value="orgLocationId", required=true) Long orgLocationId,@RequestParam(value="actionType", required=true) String actionType) throws Exception {
	    
	      List<UserDTO> approversList = commonUtil.getRoleActionUsers(actionType, orgLocationId);
		  return approversList;
      }
	  
	  
	  
	  
	  @RequestMapping(value = "/getResourceRequestsCounts", method = RequestMethod.GET)
		public @ResponseBody ProjectPlanningRequest getResourceRequestsCounts(@RequestParam(value="approverUserId", required=true) Long approverUserId,
				@RequestParam(value="orgLocationId", required=true) Long orgLocationId,HttpServletRequest request) throws Exception {
		   
		  String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		  List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByApproverIdAndOrgLocationId(approverUserId, orgLocationId,userRoleName);
		 
		  ProjectPlanningRequest projectPlanningRequest = new ProjectPlanningRequest();
		  
		  Integer waitingCount = 0;
		  Integer approvedCount = 0;
		  Integer rejectedCount = 0;
		  Integer onholdCount = 0;
		  
		  if(projectPlanningRequestList != null && projectPlanningRequestList.size() > 0) {
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
		  }
		  
		  projectPlanningRequest.setWaitingCount(waitingCount);
		  projectPlanningRequest.setApprovedCount(approvedCount);
		  projectPlanningRequest.setRejectedCount(rejectedCount);
		  projectPlanningRequest.setOnholdCount(onholdCount);
		  
		  return projectPlanningRequest;
		  
	    }
	  
	  
	    @RequestMapping(value = "/getPlanningRequestListByApproverIdAndOrgLocationId", method = RequestMethod.GET)
		public @ResponseBody List<ProjectPlanningRequest> getPlanningRequestListByApproverIdAndOrgLocationId(@RequestParam(value="approverUserId", required=true) Long approverUserId,
				@RequestParam(value="orgLocationId", required=true) Long orgLocationId,@RequestParam(value="requestType", required=true) String requestType, HttpServletRequest request) throws Exception {
		    
	      String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		  List<ProjectPlanningRequest> projectPlanningRequestList = projectPlanningRequestService.getProjectPlanningRequestByApproverId(approverUserId, orgLocationId, requestType,userRoleName);
		  
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
	    		 resourceRequest.setUpdatedBy(user);
	    		 
	    		 /*ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestById(resourceRequest.getProjectPlanningRequestId().getId());
	    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_REJECTED);*/
	    		 
	    		 projectPlanningResourceRequestService.saveProjectPlanningResourceRequest(resourceRequest);
	    		/* projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);*/
	    		 
	    		 //Sending emil to Approver
	    		 projectPlanningResourceRequestService.sendMailToAcceptAndRejectSingleResourceRequestApprover(resourceRequest, user, orgLocations);
	    	 }
		  
		     return "sucess";
		  
	    }
	    
	    
	    
	    @RequestMapping(value = "/onholdRequestedAssociate", method = RequestMethod.POST)
		public @ResponseBody String onholdRequestedAssociate(@RequestParam("json") String jsonPayload,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
        	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
	    	User user = userService.getUserById(userId); 
		  
	    	 JSONObject jsonObj = new JSONObject(jsonPayload);
	    	 if(jsonObj.has("requestUUID") && !isNullOrEmpty(jsonObj.getString("requestUUID"))){
	    		 ProjectPlanningResourceRequest resourceRequest = projectPlanningResourceRequestService.getProjectPlanningResourceRequestByRequestUUID(jsonObj.getString("requestUUID"));
	    		 StringBuilder rejectReason = new StringBuilder();
	    		 rejectReason = rejectReason.append(jsonObj.getString("rejectedReason"));
	    		 resourceRequest.setRejectedReason(rejectReason.toString());
	    		 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_ON_HOLD);
	    		 
	    		/* ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestById(resourceRequest.getProjectPlanningRequestId().getId());
	    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_REJECTED);*/
	    		 
	    		 projectPlanningResourceRequestService.saveProjectPlanningResourceRequest(resourceRequest);
	    		 /*projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);*/
	    		 
	    		 //Sending emil to Approver
	    		 projectPlanningResourceRequestService.sendMailToAcceptAndRejectSingleResourceRequestApprover(resourceRequest, user, orgLocations);
	    	 }
		  
		     return "sucess";
		  
	    }
	    
	    
	    
	    
	    @RequestMapping(value = "/onholdRequestedNewAssociate", method = RequestMethod.POST)
			public @ResponseBody String onholdRequestedNewAssociate(@RequestParam("json") String jsonPayload,@RequestParam("userId") Long userId,@RequestParam("orglocationId") Long orglocationId) throws Exception {
	        	OrgLocations orgLocations = orgLocationService.getOrgLocations(orglocationId);
		    	User user = userService.getUserById(userId); 
			  
		    	 JSONObject jsonObj = new JSONObject(jsonPayload);
		    	 if(jsonObj.has("requestUUID") && !isNullOrEmpty(jsonObj.getString("requestUUID"))){
		    		 ProjectPlanningNewResourceRequest resourceRequest = projectPlanningNewResourceRequestService.getProjectPlanningNewResourceRequestByRequestUUID(jsonObj.getString("requestUUID"));
		    		 StringBuilder rejectReason = new StringBuilder();
		    		 rejectReason = rejectReason.append(jsonObj.getString("onholdReason"));
		    		 resourceRequest.setRejectedReason(rejectReason.toString());
		    		 resourceRequest.setResourceStatus(Constants.RESOURCE_REQUEST_ON_HOLD);
		    		 projectPlanningNewResourceRequestService.saveProjectPlanningNewResourceRequest(resourceRequest);
		    		 //Sending emil to Approver
		    		 projectPlanningNewResourceRequestService.sendMailToAcceptAndRejectSingleNewResourceRequestApprover(resourceRequest, user, orgLocations);
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
	    		 projectPlanningResourceRequest.setUpdatedBy(user);
	    		 ProjectPlanningRequest projectPlanningRequest = projectPlanningResourceRequest.getProjectPlanningRequestId();
	    		 Integer allocatedResouresCount = projectPlanningRequest.getAllocatedResources()+1;
	    		 projectPlanningRequest.setAllocatedResources(allocatedResouresCount);
	    		 
	    		 Candidate vendorCandidate = null;
	    		 OrgRelations orgRelations = null;
	    		 Candidate candidate = candidateService.getCandidateByCandidateId(projectPlanningResourceRequest.getCandidateId().getId());
	    		 if(candidate != null && !candidate.getOrgLocationId().getId().equals(projectPlanningResourceRequest.getOrgLocationId().getId())) {
	    			
					 OrgLocations vendorOrgLocation = candidate.getOrgLocationId();
		    		 orgRelations = orgRelationsService.getOrgRelationByResponderLocationIdAndLocationId(vendorOrgLocation.getId(),orgLocations.getId());
		    		 OrgSpoc orgSpoc = null;
		    		 if(orgRelations == null) {

 						    orgRelations = saveOrgRelations(orgLocations,vendorOrgLocation,user);
			 				orgRelations = orgRelationsService.addOrgRelations(orgRelations);
			 			
							String modules = vendorOrgLocation.getModulesId();
							List<Integer> moduleIds = new ArrayList<Integer>();
							 for (String s : vendorOrgLocation.getModulesId().split(",")){
								 moduleIds.add(Integer.parseInt(s));
							 }
							 boolean flag = moduleIds.contains(Constants.MODULE_VMS);
							 if(flag == false){
							     modules = modules+","+Constants.MODULE_VMS;
							 }
							 
							 boolean tsflag = moduleIds.contains(Constants.MODULE_TIMESHEET);
							 if(tsflag == false) {
				    				modules = modules+","+Constants.MODULE_TIMESHEET;
				    			}
							 registrationController.saveUserRoleActions(vendorOrgLocation);
							
							 
							 vendorOrgLocation.setModulesId(modules);
							 orgLocationService.updateOrgLocations(vendorOrgLocation);
                           							 
							 
							   String clientModules = orgLocations.getModulesId();
								List<Integer> clientModuleIds = new ArrayList<Integer>();
								 for (String s : orgLocations.getModulesId().split(",")){
									 clientModuleIds.add(Integer.parseInt(s));
								 }
								 boolean flag1 = moduleIds.contains(Constants.MODULE_ATS);
								 if(flag1 == false){
									 clientModules = clientModules+","+Constants.MODULE_ATS;
								     registrationController.saveVendorUserRoleActions(orgLocations);
								 }
								 
								 orgLocations.setModulesId(clientModules);
								 orgLocationService.updateOrgLocations(orgLocations);
							 
							 Set<OrgSpoc> orgSpocList = orgRelations.getOrgSpocList();
							 for(OrgSpoc spoc : orgSpocList){
								 orgSpoc =  spoc;
							 }
							 
							 clientService.saveOrUpdateClientVendorElkDocument(null, vendorOrgLocation, "Vendor", orgSpoc, orgRelations,null, user, orgLocations);
							 orgSpocService.addClientVendorContactsELK(orgSpoc);
 					
						}
		    		 
		    		   
						vendorCandidate = candidateService.findByCandidateEmail(candidate.getEmail(), projectPlanningRequest.getOrgLocationId().getId());
						if(vendorCandidate == null) {
							vendorCandidate = new Candidate();
							MasterCandidate masterCandidate = new MasterCandidate();
							masterCandidate.setId(candidate.getMasterCandidateId().getId());
							vendorCandidate.setMasterCandidateId(masterCandidate);
							vendorCandidate.setParentCandidateId(candidate);
							vendorCandidate.setConsultantUser(candidate.getConsultantUser());
							vendorCandidate.setOrgRelationId(orgRelations);
							 Set<OrgSpoc> orgSpocList = orgRelations.getOrgSpocList();
							 for(OrgSpoc spoc : orgSpocList){
								 orgSpoc =  spoc;
							 }
							vendorCandidate.setSpocId(orgSpoc);
							vendorCandidate.setCandidateType("Candidate");
							vendorCandidate.setFirstName(candidate.getFirstName());
							vendorCandidate.setLastName(candidate.getLastName());
							vendorCandidate.setEmail(candidate.getEmail());
							vendorCandidate.setCountry(candidate.getCountry());
							vendorCandidate.setOrgLocationId(projectPlanningRequest.getOrgLocationId());
							vendorCandidate.setIsAssociate(Constants.NO);
							vendorCandidate.setDesignationType("Offshore C2C");
							
							if(projectPlanningResourceRequest.getBillRate() != null){
								vendorCandidate.setCurrentRate(projectPlanningResourceRequest.getBillRate().toString());
							}
							if(StringUtils.isNotBlank(projectPlanningResourceRequest.getBillRateType())){
								vendorCandidate.setCurrentRateType(projectPlanningResourceRequest.getBillRateType());
							}
							
							if(StringUtils.isNotBlank(projectPlanningResourceRequest.getBillRateCurrency())){
								vendorCandidate.setCurrentRateCurrency(projectPlanningResourceRequest.getBillRateCurrency());
							}
							
							vendorCandidate.setStatus(1);
							vendorCandidate.setCandidateUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
							vendorCandidate.setCreatedBy(user.getUserCode());
							vendorCandidate.setCreatedDate(new Date());
							vendorCandidate.setUpdatedDate(new Date());
							
							Candidate savedCandidate = candidateService.addCandidate(vendorCandidate);
	    					candidateService.saveOrUpdateCandidateElkDocument(savedCandidate);
						}
	    		 }
	    		 
	    		 Projects projects = projectsService.getProjectById(projectPlanningResourceRequest.getProjectPlanningRequestId().getProjectId().getId());
	    		 List<ProjectMembersDTO> projectMembersDTOList = new ArrayList<>();
	    		 ProjectMembersDTO projectMembersDTO = new ProjectMembersDTO();
	    		 projectMembersDTO.setCandidateId(projectPlanningResourceRequest.getCandidateId().getId());
	    		 projectMembersDTO.setPayRate(projectPlanningResourceRequest.getPayRate());
	    		 projectMembersDTO.setPayRateCurrency(projectPlanningResourceRequest.getPayRateCurrency());
	    		 projectMembersDTO.setPayRateRateType(projectPlanningResourceRequest.getPayRateType());
	    		 projectMembersDTO.setBillRate(projectPlanningResourceRequest.getBillRate());
	    		 projectMembersDTO.setBillRateCurrency(projectPlanningResourceRequest.getBillRateCurrency());
	    		 projectMembersDTO.setBillRateRateType(projectPlanningResourceRequest.getBillRateType());
	    		
	    		 if(projectPlanningResourceRequest.getStartDate() != null) {
	    			 projectMembersDTO.setMemberStartDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projectPlanningResourceRequest.getStartDate()));
	    		 }
	    		
	    		 if(projectPlanningResourceRequest.getEndDate() != null) {
	    			 projectMembersDTO.setMemberEndDate(commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projectPlanningResourceRequest.getEndDate()));
	    		 }
	    		 projectMembersDTO.setEstHours(projectPlanningResourceRequest.getEstimatedHours());
	    		 projectMembersDTO.setEstBudget(projectPlanningResourceRequest.getEstimatedBudget());
	    		 projectMembersDTOList.add(projectMembersDTO);
	    		
	    		 ProjectAssignedMembersDTO projectAssignedMembersDTO = new ProjectAssignedMembersDTO();
	    		 projectAssignedMembersDTO.setProjectMembers(projectMembersDTOList);
	 		   
	    		 if(candidate != null && candidate.getOrgLocationId().getId().equals(projectPlanningResourceRequest.getOrgLocationId().getId())) {
	    			 projectMembersService.saveAssignedProjectMembers(projectAssignedMembersDTO, projects.getProjectUUID(), user, orgLocations);
	    		 }else {
	    			 ProjectClients orgProjectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), projectPlanningRequest.getOrgLocationId().getId());
	    			 if(orgProjectClients != null) {
							ProjectMembers projectMembers = saveProjectMembers(projects,vendorCandidate,projectPlanningResourceRequest,user,orgProjectClients,orgLocations);
							saveProjectMembersHistory(projectMembers, user, orgProjectClients);
	    			 }
	    			 
	    			 ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), candidate.getOrgLocationId().getId());
						if(projectClients == null){
							projectClients = new ProjectClients();
							projectClients.setProjectId(projects);
							projectClients.setOrgRelationId(orgRelations);
							projectClients.setOrgLocationsId(candidate.getOrgLocationId());
							projectClients.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
							projectClients.setStatus(1);
							int level = 1;
						
							projectClients.setLevel(level);
							projectClients.setCreatedDate(new Date());
							projectClients.setCreatedBy(user);
							projectClients.setUpdatedDate(new Date());
							projectClients.setUpdatedBy(user);
							
							//prepare ProjectApproverHierarchy
							Set<ProjectApproverHierarchy> projectApproverHierarchyList = new HashSet<ProjectApproverHierarchy>();
							ProjectApproverHierarchy pah = new ProjectApproverHierarchy();
							pah.setProjectId(projects);
							pah.setProjectClientsId(projectClients);
							pah.setApproverUserId(candidate.getOrgLocationId().getUser());
							
							pah.setLevel(1);
							pah.setStatus("1");
							projectApproverHierarchyList.add(pah);
							projectClients.setProjectApproverHierarchyList(projectApproverHierarchyList);
							
							projectClientService.storeProjectClients(projectClients);
						}
						
	    			    
						ProjectMembers projectMembers = saveProjectMembers(projects,candidate,projectPlanningResourceRequest,user,projectClients,orgLocations);
						saveProjectMembersHistory(projectMembers, user, projectClients);
	    		 }
	    		 
	    		 projectPlanningResourceRequestService.saveProjectPlanningResourceRequest(projectPlanningResourceRequest);
	    		 projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);
	    		 //Sending emil To Aprover
	    		 projectPlanningResourceRequestService.sendMailToAcceptAndRejectSingleResourceRequestApprover(projectPlanningResourceRequest, user, orgLocations);
	    	 }
		     return "sucess";
	    }
	    
	    
	    public ProjectMembers saveProjectMembers(Projects projects, Candidate candidate,ProjectPlanningResourceRequest projectPlanningResourceRequest,User user,ProjectClients projectClients,OrgLocations orgLocations) throws Exception {
	    	
	    	 ProjectMembers projectMembers = new ProjectMembers();
				projectMembers.setProjects(projects);
				projectMembers.setProjectMemberCandidteId(candidate);
				projectMembers.setMasterCandidateId(candidate.getMasterCandidateId());
				
				if(projectPlanningResourceRequest.getPayRate() != null){
					projectMembers.setPayRate(projectPlanningResourceRequest.getPayRate());
					projectMembers.setPayRateCurrency(projectPlanningResourceRequest.getPayRateCurrency());
					projectMembers.setPayRateType(projectPlanningResourceRequest.getPayRateType());
				}
				
				if(projectPlanningResourceRequest.getBillRate() != null){
					projectMembers.setCurrentBillRate(projectPlanningResourceRequest.getBillRate());
					projectMembers.setBillRateCurrency(projectPlanningResourceRequest.getBillRateCurrency());
					projectMembers.setRateType(projectPlanningResourceRequest.getBillRateType());
					
				}
				
				if(projectPlanningResourceRequest.getStartDateStr() != null && !projectPlanningResourceRequest.getStartDateStr().isEmpty()){
					Date projectAssignedStartDate = commonUtil.orgLocationDateFormat(projectPlanningResourceRequest.getOrgLocationId().getDateFormat(),projectPlanningResourceRequest.getStartDateStr());
					projectMembers.setProjectAssignedDate(projectAssignedStartDate);
				}
				
				if(projectPlanningResourceRequest.getEndDateStr() != null && !projectPlanningResourceRequest.getEndDateStr().isEmpty()){
					Date projectAssignedEndDate = commonUtil.orgLocationDateFormat(projectPlanningResourceRequest.getOrgLocationId().getDateFormat(),projectPlanningResourceRequest.getEndDateStr());
					projectMembers.setProjectEndDate(projectAssignedEndDate);
				}
				
				if(projectPlanningResourceRequest.getEstimatedHours() != null){
					projectMembers.setEstimatedHours(projectPlanningResourceRequest.getEstimatedHours());
				}
				
				if(projectPlanningResourceRequest.getEstimatedBudget() != null){
					projectMembers.setEstimatedBudget(projectPlanningResourceRequest.getEstimatedBudget());
				}
				
				projectMembers.setProjects(projects);
				projectMembers.setStatus("1");
				projectMembers.setCreatedBy(user.getUserCode());
				projectMembers.setUpdatedBy(user.getUserCode());
				projectMembers.setCreatedDate(new Date());
				projectMembers.setUpdatedDate(new Date());
				projectMembers.setProjectClientsId(projectClients);
				projectMembersService.saveProjectMember(projectMembers);
				return projectMembers;
	    }
	    
	    
	    public void saveProjectMembersHistory(ProjectMembers projectMembers,User user,ProjectClients projectClients) throws Exception{
			
			ProjectMembersHistory projectMembersHistory = null;
			if(projectMembers.getId() != null){
				projectMembersHistory = projectMembersHistoryService.getProjectMembersOpenHistoryByProjectClientsId(projectClients.getId(),projectMembers.getId());
			}
			
			if(projectMembersHistory == null){
				projectMembersHistory = new ProjectMembersHistory();
			}
			
			projectMembersHistory.setProjectMemberId(projectMembers);
			//projectMembersHistory.setProjectClientsId(projectClients);
			
			if(projectMembers.getProjectAssignedDate() != null){
				projectMembersHistory.setProjectAssignedDate(projectMembers.getProjectAssignedDate());
			}else{
				projectMembersHistory.setProjectAssignedDate(null);
			}
			
			if(projectMembers.getProjectEndDate() != null){
				projectMembersHistory.setProjectTerminationDate(projectMembers.getProjectEndDate());
			}else{
				projectMembersHistory.setProjectTerminationDate(null);
			}
			
			/*Candidate candidate =  candidateService.findByCandidateEmail(projectMembers.getProjectMemberCandidteId().getEmail(), projectClients.getOrgLocationsId().getId());
			if(candidate != null){
				projectMembersHistory.setCandidateId(candidate);
			}*/
			projectMembersHistory.setStatus("OPEN");
			projectMembersHistory.setCreatedBy(user.getUserCode());
			projectMembersHistory.setUpdatedBy(user.getUserCode());
			projectMembersHistory.setCreatedDate(new Date());
			projectMembersHistory.setUpdatedDate(new Date());
			projectMembersHistoryService.updateProjectMembersHistory(projectMembersHistory);
			
		}
	    
	    
	    public OrgRelations saveOrgRelations(OrgLocations orglocations,OrgLocations vendorOrgLocation,User user){
			
			OrgRelations orgRelations = new OrgRelations();
				orgRelations.setLocationId(orglocations);
				orgRelations.setResponderLocationId(vendorOrgLocation);
				orgRelations.setInitiatorType("Client");
				orgRelations.setResponderType("Vendor");
				orgRelations.setInitiatorLevel("Tier-5");
				orgRelations.setResponderLevel("Tier-5");
				orgRelations.setVendorRelationStatus("Sent");
				orgRelations.setStatus("ACTIVE");
				orgRelations.setCreatedBy(user.getUserCode());
				orgRelations.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				orgRelations.setCreatedDate(new Date());
				orgRelations.setUpdatedBy(user.getUserCode());
				orgRelations.setUpdatedDate(new Date());
				
				Set<OrgSpoc> orgSpocSet = new HashSet<>();
				OrgSpoc orgSpoc = new OrgSpoc();
				orgSpoc.setOrgRelations(orgRelations);
				orgSpoc.setClientUser(user);
				orgSpoc.setVendorUser(vendorOrgLocation.getUser());
				orgSpoc.setInvitationStatus(Constants.MAIL_NEW);
				orgSpoc.setClientLocationId(orglocations);
				orgSpoc.setVendorLocationId(vendorOrgLocation);
				orgSpoc.setUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
				orgSpoc.setSpocStatus("Active");
				orgSpoc.setStatus(Constants.MEMBER_ACTIVE);
				orgSpoc.setOwnerUser(user);
				orgSpoc.setCreatedDate(new Date());
				orgSpoc.setCreatedBy(user.getUserCode());
				orgSpoc.setUpdatedDate(new Date());
				orgSpoc.setUpdatedBy(user.getUserCode());
				orgSpocSet.add(orgSpoc);
				orgRelations.setOrgSpocList(orgSpocSet);
				return orgRelations;
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
		    		 
		    		 /*ProjectPlanningRequest projectPlanningRequest = projectPlanningRequestService.getProjectPlanningRequestById(projectPlanningNewResourceRequest.getProjectPlanningRequestId().getId());
		    		 projectPlanningRequest.setRequestStatus(Constants.RESOURCE_REQUEST_REJECTED);*/
		    		 
		    		 projectPlanningNewResourceRequestService.saveProjectPlanningNewResourceRequest(projectPlanningNewResourceRequest);
		    		 /*projectPlanningRequestService.storeProjectPlanningRequest(projectPlanningRequest);*/
		    		 
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
			public @ResponseBody ResponseList<ProjectBudget> getBudgetRequestListByProjectUUID(@RequestParam(value="projectUUID", required=true) String projectUUID,HttpServletRequest request) throws Exception {
	        	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	        	ResponseList<ProjectBudget> response = new ResponseList<>();
	        	try {
	        		Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		        	if(projects != null) {
		        		List<ProjectBudget> projectBudgetList= projectBudgetService.getProjectBudgetByProjectTaskId(projects.getId(),loginOrgLocation.getId());
		        		response.setData(projectBudgetList);
					    response.setResponse(Result.SUCCESS.name());
						response.setResponseMessage(Result.SUCCESS.getValue());
		        	}else {
						response.setResponse(Result.INVALID_DATA.name());
						response.setResponseMessage(Result.INVALID_DATA.getValue());
		        	}
	        	}catch (Exception e) {
					response.setResponse(Result.AWKWARD.name());
					response.setResponseMessage(Result.AWKWARD.getValue());
				}
	        	
			    
			  return response;
		    }
	        
		 /**
		  * @author Laxman
		  * @param projectBudgetRequestDTO
		  * @return success
		  * @throws Exception
		  */
	        @RequestMapping(value = "/saveBudgetRequest",method = RequestMethod.POST)
			public @ResponseBody Response<String> saveBudgetRequest(ProjectBudgetRequestDTO projectBudgetRequestDTO) throws Exception {
			       User user = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getLoginUserid()));
			       Response<String> response = new Response<>();
			       if(user != null) {
			    	   try {
			    		   if(Validator.validate(projectBudgetRequestDTO)) {
			    			   projectBudgetService.saveProjectBudgetRequset(projectBudgetRequestDTO, user);
			    			   response.setResponse(Result.SUCCESS.name());
							   response.setResponseMessage(Result.SUCCESS.getValue());
							}else {
								response.setObject("DataMissing");
								response.setResponse(Result.INVALID_DATA.name());
								response.setResponseMessage(Result.INVALID_DATA.getValue());
							}
			    	   }catch (Exception e) {
							response.setResponse(Result.AWKWARD.name());
							response.setResponseMessage(Result.AWKWARD.getValue());
						}
			       } else {
						response.setResponse(Result.SESSION_TIMEDOUT.name());
						response.setResponseMessage(Result.SESSION_TIMEDOUT.getValue());
					}
			    return response;
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
			public @ResponseBody List<ProjectBudget> getBudgetRequsetListByBudgetStatusAndOrgLocationId(@RequestParam(value="approverUserId", required=true) Long approverUserId,@RequestParam(value="orgLocationId", required=true) Long orgLocationId,
					@RequestParam(value="requestType", required=true) String requestType,HttpServletRequest request) throws Exception {
	        	String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	        	List<ProjectBudget> projectBudgetList= projectBudgetService.getProjectBudgetListByOrgLocationIdAndBudgetStatus(approverUserId,orgLocationId, requestType,userRoleName);
			  return projectBudgetList;
			  
		    }
	        
	        
	        /**'
	         * @author Laxman
	         * @param orgLocationId
	         * @return projectBudget
	         * @throws Exception
	         */
	        @RequestMapping(value = "/getBudgetRequestListCount", method = RequestMethod.GET)
			public @ResponseBody ProjectBudget getBudgetRequestListCount(@RequestParam(value="approverUserId", required=true) Long approverUserId,
					@RequestParam(value="orgLocationId", required=true) Long orgLocationId,HttpServletRequest request) throws Exception {
	        	String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	        	List<ProjectBudget> projectBudgetList= projectBudgetService.getProjectBudgetListByOrgLocationId(approverUserId,orgLocationId, userRoleName);
	        	  ProjectBudget projectBudget = new ProjectBudget();
				  Integer waitingCount = 0;
				  Integer approvedCount = 0;
				  Integer rejectedCount = 0;
				  Integer onholdCount = 0;
				  
				  if(projectBudgetList != null && projectBudgetList.size() > 0) {
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
				  }
				  
				  projectBudget.setWaitingCount(waitingCount);
				  projectBudget.setApprovedCount(approvedCount);
				  projectBudget.setRejectedCount(rejectedCount);
				  projectBudget.setOnholdCount(onholdCount);
				  
				  return projectBudget;
			  
		    }
	        
	        
	        
	        @RequestMapping(value = "/getProjectPlanningResourceRequestList", method = RequestMethod.GET)
	      	public ResponseList<ProjectPlanningResourceRequestDTO> getProjectPlanningResourceRequestList(ProjectPlanningResourceRequestDTO resourceRequestDTO,HttpServletRequest request) throws Exception {

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
	        
	        
	        
	        
	        
	        
	        @RequestMapping(value = "/getProjectPlanningNewResourceRequestList", method = RequestMethod.GET)
	      	public ResponseList<ProjectPlanningNewResourceRequestDTO> getProjectPlanningNewResourceRequestList(ProjectPlanningNewResourceRequestDTO resourceRequestDTO,HttpServletRequest request) throws Exception {

	      		OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	      		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
	      		 String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
	      		 
	      		String[] tableColumns = {"projectPlanningRequestId","projectName", "roleName", "requestedUser", "approverName","consultingType", "noOfResource"};
			    int sortColumnIndex = 1; //by default sorting using updated date
				String sortColumnOrder = "desc"; //by default referContactsDTO order
				if(resourceRequestDTO.getOrder() != null && !resourceRequestDTO.getOrder().isEmpty()) {
					sortColumnIndex = Integer.parseInt(resourceRequestDTO.getOrder().get(0).get("column"));
					sortColumnOrder = resourceRequestDTO.getOrder().get(0).get("dir");
				}
				resourceRequestDTO.setSortColumn(tableColumns[sortColumnIndex]);
				resourceRequestDTO.setSortOrder(sortColumnOrder);
	      		
				ResponseList<ProjectPlanningNewResourceRequestDTO> requestDTOList = projectPlanningNewResourceRequestService.getNewResourceRequestListByApproverIdAndOrgLocationId(resourceRequestDTO,user.getId(), orgLocation.getId(),userRoleName);
	      		
	      		return requestDTOList;
	    	}
	        
	        
	        
	        @RequestMapping(value = "/getProjectNewResourceRequestsCounts", method = RequestMethod.GET)
			public @ResponseBody ProjectPlanningNewResourceRequestDTO getProjectNewResourceRequestsCounts(HttpServletRequest request) throws Exception {
			   
        	  OrgLocations orgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
      		  User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			  String userRoleName = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
			  List<ProjectPlanningNewResourceRequest> projectPlanningRequestList = projectPlanningNewResourceRequestService.getNewResourceRequestListByApproverIdAndOrgLocationIdAndStatus(user.getId(), orgLocation.getId(), userRoleName);
			 
			  ProjectPlanningNewResourceRequestDTO projectPlanningNewResourceRequestDTO = new ProjectPlanningNewResourceRequestDTO();
			  
			  Integer waitingCount = 0;
			  Integer approvedCount = 0;
			  Integer rejectedCount = 0;
			  Integer onholdCount = 0;
			  
			  if(projectPlanningRequestList != null && projectPlanningRequestList.size() > 0) {
				  for(ProjectPlanningNewResourceRequest planningRequest : projectPlanningRequestList){
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
			  projectPlanningNewResourceRequestDTO.setWaitingCount(waitingCount);
			  projectPlanningNewResourceRequestDTO.setApprovedCount(approvedCount);
			  projectPlanningNewResourceRequestDTO.setRejectedCount(rejectedCount);
			  projectPlanningNewResourceRequestDTO.setOnholdCount(onholdCount);
			  
			  return projectPlanningNewResourceRequestDTO;
			  
		    }
	        
	        
	 protected static boolean isNullOrEmpty(String str) {
			return (str == null || "".equals(str.trim()));
		}
}
