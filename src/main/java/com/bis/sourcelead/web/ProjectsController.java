package com.bis.sourcelead.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.mysql.fabric.xmlrpc.base.Array;


@Controller
@PropertySource("${propertyLocation:app.properties}")
public class ProjectsController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	 ProjectsService projectsService;
	 
	 @Autowired
	 RolesService rolesService;
	 
	 @Autowired
	 ProjectPlanningRequestService projectPlanningRequestService;
	 
	 @Autowired
	 ProjectMembersService projectMembersService;
	 
	 @Autowired
	 CountriesService countriesService;
	 

	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	LookUpDataService lookUpDataService;
	 
	@Autowired
	ProjectTasksService projectTasksService;
	
	@Autowired
	ProjectAssignTasksService projectAssignTasksService;
	
	@Autowired
	ProjectClientService projectClientService;
	

	@Autowired
	ProjectBudgetService projectBudgetService;
	 
	 protected boolean isNullOrEmpty(String str)
	 {
		return (str == null || "".equals(str.trim()));
	 }
 

	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	 @PreAuthorize("hasRole('ROLE_CREATE_PROJECT')")
	 @RequestMapping(value = "/project")
		public ModelAndView projects(@RequestParam(value="projectUUID", required=false) String projectUUID,HttpServletRequest request) throws Exception {
		     String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.Projects);
					
					request.getSession().setAttribute("sessionData",sessionData);
				
				/*Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId(), loginUser.getId());
				for(OrgRelations ocl :orgClientList){
					if(null != ocl.getLocationId() && ocl.getLocationId().getId() != null && ocl.getLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
						if(ocl.getResponderLocationId() != null){
							ocl.setOrgName(ocl.getResponderLocationId().getOrgLocationName());
							ocl.setOrgLocationId(ocl.getResponderLocationId().getId());
						}
					}
					if(null != ocl.getResponderLocationId() && null != ocl.getResponderLocationId().getId() && ocl.getResponderLocationId().getId().longValue() == loginOrgLocations.getId().longValue()){
						if(ocl.getLocationId() != null){
							ocl.setOrgName(ocl.getLocationId().getOrgLocationName());
							ocl.setOrgLocationId(ocl.getLocationId().getId());
						}
					}
				 }
			    model.addObject("add_clientList", orgClientList);
			    
				Collection<Org> orgsList = orgService.getOrgs();
				model.addObject("add_client", orgsList);*/
				
                Collection<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(loginOrgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
				model.addObject("user_List", userList);
				
				List<LookUpData> skillsList = lookUpDataService.getLookUpDataByCategory(Constants.SKILLS);
				model.addObject("skillsList", skillsList);
				
				List<LookUpData> industriesList = lookUpDataService.getLookUpDataByCategory("Industries");
				model.addObject("industriesList", industriesList);
				
				List<LookUpData> testManagementList = lookUpDataService.getLookUpDataByCategory("Test Management");
				model.addObject("testManagementList", testManagementList);
				
				List<LookUpData> agileMethodologiesList = lookUpDataService.getLookUpDataByCategory("Agile Methodologies");
				model.addObject("agileMethodologiesList", agileMethodologiesList);
				
				
				if(!isNullOrEmpty(projectUUID)){
					Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
					if(projects != null){
						model.addObject("projectUUID", projects.getProjectUUID());
						model.addObject("savedProjectId", projects.getId());
						model.addObject("isProjectDetailsFilled", projects.getIsProjectDetailsFilled());
						model.addObject("isProjectMembersFilled", projects.getIsProjectMembersFilled());
						
						if(projects.getStartDate() != null){
							model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),projects.getStartDate()));
						}
						if(projects.getEndDate() != null){
							model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(loginOrgLocations.getDateFormat(),projects.getEndDate()));
						}
						
					}
					
				}
				
				model.setViewName("project/project");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 
	 
	 @PreAuthorize("hasRole('ROLE_CREATE_PROJECT')")
	 @RequestMapping(value = "/clientProject")
		public ModelAndView clientProject(HttpServletRequest request) throws Exception {
		    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.Projects);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					List<OrgRelations> clientsList = orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId());
					List<OrgRelations> clientList = new ArrayList<>();
					if(clientsList != null & clientsList.size() > 0) {
						for(OrgRelations orgRelations : clientsList) {
							OrgLocations clientOrgLocations = null;
							if(orgRelations.getLocationId().getId().equals(loginOrgLocations.getId())) {
								clientOrgLocations = orgRelations.getResponderLocationId();
							}else {
								clientOrgLocations = orgRelations.getLocationId();
							}
							
							if(StringUtils.isNotBlank(clientOrgLocations.getOrg().getOrgType()) && clientOrgLocations.getOrg().getOrgType().equalsIgnoreCase("Guest")) {
								clientList.add(orgRelations);
							}
							
						}
					}
					model.addObject("clientsList", clientList);
					model.addObject("sourceleadUrl", sourceleadUrl);
					model.setViewName("project/clientProject");
					return model;
	}
	 
	 
	 @RequestMapping(value = "/getProjectLevel1Approvers", method = RequestMethod.GET)
		public @ResponseBody List<User> getProjectLevel1Approvers(HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			return userList;
		}
	 
	 
	 @RequestMapping(value = "/projectSummary")
		public ModelAndView projectSummary(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,@RequestParam(value="tabType", required=false) String tabType,
				HttpServletRequest request) throws Exception {
				ModelAndView model = new ModelAndView();
				
			    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
				OrgLocations locations=orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
				OrgLocations clientOrgLocations = null;
				String isClientOrgActive = Constants.NO;
				if(projects != null){
					model.addObject("projectId", projects.getId());
					model.addObject("projectName", projects.getProjectName());
					if(projects.getStartDate() != null){
						model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),projects.getStartDate()));
					}
					if(projects.getEndDate() != null){
						model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),projects.getEndDate()));
					}
					model.addObject("projectStatus", projects.getProjectStatus());
				}
				
				if(StringUtils.isNotEmpty(projectClientUUID)){
					ProjectClients projectClients = projectClientService.getProjectClientsByUUID(projectClientUUID);
					if(projectClients != null && projectClients.getOrgRelationId() != null){
						if(projectClients.getOrgRelationId().getResponderLocationId().getId().equals(orgLocations.getId())){
							clientOrgLocations = projectClients.getOrgRelationId().getLocationId();
						}else{
							clientOrgLocations = projectClients.getOrgRelationId().getResponderLocationId();
						}
						
						if(clientOrgLocations.getStatus() != null && clientOrgLocations.getStatus().equals(Status.ACTIVE)){
							isClientOrgActive = Constants.YES;
						}
					}
					model.addObject("isClientOrgActive", isClientOrgActive);
				}
				model.addObject("projectClientUUID", projectClientUUID);
				
				if(null != tabType && !tabType.isEmpty() && tabType != ""){
					model.addObject("tabType", tabType);
				}
				
				ProjectClients projectClients = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
				Integer projectMembersCount = projectMembersService.getProjectMembersCountByProjectClientId(projectClients.getId());
				model.addObject("projectMembersCount", projectMembersCount);
				
				Integer resourceRequestsCount = projectPlanningRequestService.getProjectPlanningRequestCountByProjectId(projects.getId());
				model.addObject("resourceRequestsCount", resourceRequestsCount);
				
				List<ProjectBudget> projectBudgetList= projectBudgetService.getProjectBudgetByProjectTaskId(projects.getId(),orgLocations.getId());
				if(projectBudgetList != null) {
					model.addObject("projectBudgetCount", projectBudgetList.size());
				}else {
					model.addObject("projectBudgetCount", 0);
				}
				
				model.addObject("projectUUID", projectUUID);
				model.addObject("projectCurrency", projects.getCurrency());
				model.addObject("postedOrgLocationId", projects.getOrgLocationsId().getId());
				
				List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
				model.addObject("orgLocationsList", orgLocationsList);
				
				 List<LookUpData> taskTypeList = lookUpDataService.getLookUpDataByCategory(Constants.TASKTYPE);
				 model.addObject("taskTypeList", taskTypeList);
				 
				 List<LookUpData> taskSeverityList = lookUpDataService.getLookUpDataByCategory(Constants.TASKSEVERITY);
				 model.addObject("taskSeverityList", taskSeverityList);
				 
				 List<LookUpData> taskPriorityList = lookUpDataService.getLookUpDataByCategory(Constants.TASKPRIORITY);
				 model.addObject("taskPriorityList", taskPriorityList);
				 
				 List<LookUpData> taskStatusList = lookUpDataService.getLookUpDataByCategory(Constants.TASKSTATUS);
				 model.addObject("taskStatusList", taskStatusList);
				 
				commonUtil.highLightMenu(Constants.Projects, request);
				 
				model.setViewName("project/projectSummary");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 @RequestMapping(value = "/projectTaskSummary")
		public ModelAndView projectTaskSummary(@RequestParam(value="taskUUID", required=true) String taskUUID,@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
				ModelAndView model = new ModelAndView();
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
				
				 ProjectTasks projectTask = projectTasksService.getProjectTasksByUUID(taskUUID);
				 OrgLocations locations=orgLocationService.getOrgLocations(projectTask.getProjectId().getOrgLocationsId().getId());
				 if(projectTask.getProjectId() != null) {
					 model.addObject("projectUUID", projectTask.getProjectId().getProjectUUID());
					 model.addObject("projectId", projectTask.getProjectId().getId());
				 }
				 model.addObject("projectTask", projectTask);
				 if(projectTask.getCreatedDate() != null) {
					 model.addObject("createdDate",  dateFormat.format(projectTask.getCreatedDate()));
				 }else {
					 model.addObject("createdDate",  "--");
				 }
				 if(projectTask.getDueDate() !=null) {
					 model.addObject("dueDate",  dateFormat.format(projectTask.getDueDate()));
				 }else {
					 model.addObject("dueDate",  "--");
				 }
				 
				 if(projectTask.getProjectId() != null && projectTask.getProjectId().getStartDate() != null){
						model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),projectTask.getProjectId().getStartDate()));
					}
				 
				 if(projectTask.getTaskAssignedUsers() != null) {
					 model.addObject("assignTask", projectTask.getTaskAssignedUsers());
				 }
				 User user = userService.getUserByUserCode(projectTask.getCreatedBy());
				 if(user != null) {
					 model.addObject("createdBy", user.getFullName());
				 }
				 
				 List<ProjectAssignTask> assignedUsers = projectAssignTasksService.getProjectAssignTaskListByProjectTaskId(projectTask.getId()); 
				 for(ProjectAssignTask pat:assignedUsers) {
					 if(pat.getProjectMemberId() != null) {
						 pat.setAssignedUserName(pat.getProjectMemberId().getEmployee().getUser().getFullName());
					 }
				 }
				 model.addObject("taskTypeName", projectTask.getTaskType().getName());
				 model.addObject("assignedUsers", assignedUsers);
				 if(projectTask.getParentTaskId() != null) {
					 model.addObject("parentProjectTaskUUID", projectTask.getParentTaskId().getProjectTasksUUID());
					 model.addObject("parentGeneratedId", projectTask.getParentTaskId().getGeneratedId());
				 }
				if(projectTask.getProjectId() != null) {
					model.addObject("projectName", projectTask.getProjectId().getProjectName());
				}
				 
				 List<LookUpData> taskTypeList = lookUpDataService.getLookUpDataByCategory(Constants.TASKTYPE);
				 model.addObject("taskTypeList", taskTypeList);
				 
				 List<LookUpData> taskSeverityList = lookUpDataService.getLookUpDataByCategory(Constants.TASKSEVERITY);
				 model.addObject("taskSeverityList", taskSeverityList);
				 
				 List<LookUpData> taskPriorityList = lookUpDataService.getLookUpDataByCategory(Constants.TASKPRIORITY);
				 model.addObject("taskPriorityList", taskPriorityList);
				 
				 List<LookUpData> taskStatusList = lookUpDataService.getLookUpDataByCategory(Constants.TASKSTATUS);
				 model.addObject("taskStatusList", taskStatusList);
				 
				model.addObject("projectClientUUID", projectClientUUID);
				model.setViewName("project/projectTaskSummary");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 
	 @RequestMapping(value = "/newResourceRequest")
		public ModelAndView newResourceRequest(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
				ModelAndView model = new ModelAndView();
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
				 Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
				Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
				//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
				if(projects != null){
					model.addObject("projectId", projects.getId());
					model.addObject("projectName", projects.getProjectName());
					if(projects.getStartDate() != null){
						model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getStartDate()));
					}
					if(projects.getEndDate() != null){
						model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getEndDate()));
					}
					model.addObject("projectStatus", projects.getProjectStatus());
				}
				
				 Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
					if(null != countries){
		            model.addObject("loginCountryName", countries.getName());
		            model.addObject("currencyCode", countries.getCurrency());
					}
			    List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
			    model.addObject("rolesList", rolesList);
			    
			    List<User> approversList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_PROJECTS));
				model.addObject("approversList", approversList);
			    
				model.addObject("projectClientUUID", projectClientUUID);
				model.addObject("projectUUID", projectUUID);
				model.setViewName("project/newResourceRequest");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 
    @RequestMapping(value = "/requestAssociates")
	public ModelAndView requestAssociates(@RequestParam(value="projectUUID", required=true) String projectUUID,
			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    
	    ModelAndView model = new ModelAndView();
		Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		if(projects != null){
			model.addObject("projectId", projects.getId());
			model.addObject("projectName", projects.getProjectName());
			if(projects.getStartDate() != null){
				model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getStartDate()));
			}
			if(projects.getEndDate() != null){
				model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getEndDate()));
			}
			model.addObject("projectStatus", projects.getProjectStatus());
		}
		model.addObject("projectUUID", projectUUID);
		model.addObject("projectClientUUID", projectClientUUID);
		
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
		model.addObject("orgLocationsList", orgLocationsList);
		
		List<User> approversList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_PROJECTS));
		model.addObject("approversList", approversList);
		
		model.setViewName("project/requestAssociates");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
    }
    
    
    @RequestMapping(value = "/addNewProjectMembers")
	public ModelAndView addNewProjectMembers(@RequestParam(value="projectUUID", required=true) String projectUUID,
			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    
	    ModelAndView model = new ModelAndView();
	    
	    SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Projects);
		
		request.getSession().setAttribute("sessionData",sessionData);
	
	    
		Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		if(projects != null){
			model.addObject("projectId", projects.getId());
			model.addObject("projectName", projects.getProjectName());
			if(projects.getStartDate() != null){
				model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getStartDate()));
			}
			if(projects.getEndDate() != null){
				model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getEndDate()));
			}
			model.addObject("projectStatus", projects.getProjectStatus());
			model.addObject("projectOrgLocationId", projects.getOrgLocationsId().getId());
		}
		model.addObject("projectUUID", projectUUID);
		model.addObject("projectClientUUID",projectClientUUID);
		model.setViewName("project/addNewProjectMembers");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
    }
    
    
    
    
    @RequestMapping(value = "/editResourceRequest")
	public ModelAndView editResourceRequest(@RequestParam(value="requestUUID", required=true) String requestUUID,
			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,@RequestParam(value="projectUUID", required=true) String projectUUID,HttpServletRequest request) throws Exception {
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	  //  SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
	    ModelAndView model = new ModelAndView();
		Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		if(projects != null){
			model.addObject("projectId", projects.getId());
			model.addObject("projectName", projects.getProjectName());
			if(projects.getStartDate() != null){
				model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getStartDate()));
			}
			if(projects.getEndDate() != null){
				model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getEndDate()));
			}
			model.addObject("projectStatus", projects.getProjectStatus());
		}
		model.addObject("projectUUID", projectUUID);
		model.addObject("requestUUID",requestUUID);
		model.addObject("projectClientUUID",projectClientUUID);
		
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
		model.addObject("orgLocationsList", orgLocationsList);
		
		model.setViewName("project/requestAssociates");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
    }
    

    @RequestMapping(value = "/editNewResourceRequest")
	public ModelAndView editNewResourceRequest(@RequestParam(value="requestUUID", required=true) String requestUUID,
			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,@RequestParam(value="projectUUID", required=true) String projectUUID,HttpServletRequest request) throws Exception {
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
	    ModelAndView model = new ModelAndView();
		Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
		//SimpleDateFormat simpleDateFormat = orgLocations.getLocFullFormDateFormatter();
		if(projects != null){
			model.addObject("projectId", projects.getId());
			model.addObject("projectName", projects.getProjectName());
			if(projects.getStartDate() != null){
				model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getStartDate()));
			}
			if(projects.getEndDate() != null){
				model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(orgLocations.getDateFormat(),projects.getEndDate()));
			}
			model.addObject("projectStatus", projects.getProjectStatus());
		}
		model.addObject("projectUUID", projectUUID);
		model.addObject("requestUUID",requestUUID);
		model.addObject("projectClientUUID",projectClientUUID);
		Countries countries = countriesService.getCountriesByCountryName(orgLocations.getCountry());
		if(null != countries){
        model.addObject("loginCountryName", countries.getName());
        model.addObject("currencyCode", countries.getCurrency());
		}
		 List<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
		    model.addObject("rolesList", rolesList);
		    
		List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
		model.addObject("orgLocationsList", orgLocationsList);
		
		model.setViewName("project/newResourceRequest");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
    }

    
    @RequestMapping(value = "/viewNewResourceRequest")
   	public ModelAndView viewNewResourceRequest(@RequestParam(value="requestUUID", required=true) String requestUUID,
   			@RequestParam(value="projectUUID", required=true) String projectUUID,
   			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
   	    
   	    ModelAndView model = new ModelAndView();
   	    
   	    commonUtil.highLightMenu(Constants.Projects, request);
   	    
   		model.addObject("projectUUID", projectUUID);
   		model.addObject("requestUUID",requestUUID);
   		model.addObject("projectClientUUID",projectClientUUID);
   		model.setViewName("project/viewNewResourceRequest");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }
    
    
    @RequestMapping(value = "/viewResourceRequest")
	public ModelAndView viewResourceRequest(@RequestParam(value="requestUUID", required=true) String requestUUID,
			@RequestParam(value="projectUUID", required=true) String projectUUID,
			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
	    
	    ModelAndView model = new ModelAndView();
	    
	    commonUtil.highLightMenu(Constants.Projects, request);
	    
		model.addObject("projectUUID", projectUUID);
		model.addObject("requestUUID",requestUUID);
		model.addObject("projectClientUUID",projectClientUUID);
		model.setViewName("project/viewResourceRequest");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
    }
    
    @RequestMapping(value = "/requestedAssociatesList")
   	public ModelAndView requestedAssociatesList(@RequestParam(value="requestUUID", required=true) String requestUUID,HttpServletRequest request) throws Exception {
   	    
   	    ModelAndView model = new ModelAndView();
   		
   	    SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		
		request.getSession().setAttribute("sessionData",sessionData);
	
		model.addObject("requestUUID",requestUUID);
   		model.setViewName("requestedAssociatesList");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }

    
    @RequestMapping(value = "/viewNewResourceRequestDashborad")
   	public ModelAndView viewNewResourceRequestDashborad(@RequestParam(value="requestUUID", required=true) String requestUUID,HttpServletRequest request) throws Exception {
   	    
   	    ModelAndView model = new ModelAndView();
   		
   	    SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Dashboard);
		
		request.getSession().setAttribute("sessionData",sessionData);
	
		model.addObject("requestUUID",requestUUID);
   		model.setViewName("project/viewNewResourceRequestDashborad");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }



}
