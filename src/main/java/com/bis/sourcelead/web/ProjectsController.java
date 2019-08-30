package com.bis.sourcelead.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.bis.sourcelead.dto.CustomFormFieldDTO;
import com.bis.sourcelead.dto.OrgRelationDTO;
import com.bis.sourcelead.dto.OrglocationFormFieldsDTO;
import com.bis.sourcelead.dto.SourceleadSectionDTO;
import com.bis.sourcelead.entity.Countries;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectAssignTask;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.SourceleadActions;
import com.bis.sourcelead.entity.Team;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.enums.TagEntites;
import com.bis.sourcelead.service.CountriesService;
import com.bis.sourcelead.service.LookUpDataService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectAssignTasksService;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectClientService;
import com.bis.sourcelead.service.ProjectDocumentsService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectPlanningRequestService;
import com.bis.sourcelead.service.ProjectTaskCustomFieldDataService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SourceleadActionsService;
import com.bis.sourcelead.service.SourceleadFormSectionsService;
import com.bis.sourcelead.service.SourceleadTagsService;
import com.bis.sourcelead.service.TeamService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;


@Controller
@PropertySource("${propertyLocation:app.properties}")
public class ProjectsController {
	
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Value("${static.path}")
	private String staticPath;
	
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
	 ProjectDocumentsService projectDocumentsService;

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
	 
	@Autowired
	TeamService teamService;
	
	@Autowired
	private SourceleadFormSectionsService sourceleadFormSectionsService;
	
	@Autowired
	private SourceleadActionsService sourceleadActionsService;
	
	@Autowired
	private ProjectTaskCustomFieldDataService projectTaskCustomFieldDataService;
	
	@Autowired
	UserRoleService userRoleService;
	
	 @Autowired
	 private SourceleadTagsService sourceleadTagsService;
	 
	
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
		public ModelAndView projects(@RequestParam(value="projectUUID", required=false) String projectUUID,
				@RequestParam(value="projectType", required=false) String projectType,HttpServletRequest request) throws Exception {
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
				
				if(StringUtils.isNotBlank(projectType) && projectType.equalsIgnoreCase("sharedProject")) {
					List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(loginOrgLocations.getId());
				    model.addObject("clientsList", orgClientList);
				}
				 model.addObject("projectType", projectType);
			    
				/*Collection<Org> orgsList = orgService.getOrgs();
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
				
				SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_PROJECT);
			     model.addObject("actionId",action.getId());
			     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),loginOrgLocations.getId());
				 model.addObject("sourceleadSectionList",sections);
				
				model.setViewName("project/project");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 
	 
	 @PreAuthorize("hasRole('ROLE_CREATE_PROJECT')")
	 @RequestMapping(value = "/clientProject")
		public ModelAndView clientProject(HttpServletRequest request) throws Exception {
		    OrgLocations loginOrgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
		    
				ModelAndView model = new ModelAndView();
				
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.Projects);
					
					request.getSession().setAttribute("sessionData",sessionData);
					
					List<OrgRelationDTO> orgClientList = orgRelationsService.getAllClientsListOrgLocationId(loginOrgLocations.getId());
				    model.addObject("clientsList", orgClientList);
					
					model.addObject("sourceleadUrl", sourceleadUrl);
					model.setViewName("project/clientProject");
					return model;
	}
	 
	 
	 @RequestMapping(value = "/getProjectLevel1Approvers", method = RequestMethod.GET)
		public @ResponseBody List<User> getProjectLevel1Approvers(HttpServletRequest request) throws Exception {
			OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
			Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request,"org");
			List<User> level1ApproversList = new CopyOnWriteArrayList<>();
			List<User> userList = userService.getTabAccessOwnersByOrgLocationIdAndTabId(orgLocations.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			List<User> globalUserList = userService.getTabAccessGlobalOwnersByOrgIdAndTabId(orgLocations.getId(),org.getId(),Long.valueOf(Constants.TAB_TIMESHEET_APPROVAL));
			if(userList != null && !userList.isEmpty()) {
				level1ApproversList.addAll(userList);
			}
			if(globalUserList != null && !globalUserList.isEmpty()) {
				level1ApproversList.addAll(globalUserList);
			}
			
			HashMap<Long,User> userMap = new HashMap<>();
			for (User user : level1ApproversList) {
				User tempUser = userMap.get(user.getId());
				if(tempUser == null) {
					userMap.put(user.getId(),user);
				}else {
					level1ApproversList.remove(user);
					continue;
				}
				UserRole userRole = userRoleService.getUserRoleByUserIdAndLocationId(user.getId(), orgLocations.getId());
				if(userRole != null){
					user.setUserType(userRole.getUserType());
				}else {
					UserRole primaryRole = userRoleService.getLoginUserPrimaryRole(user.getId());
					user.setUserType(primaryRole.getUserType());
				}
			}
			
			return level1ApproversList;
		}
	 
	 
	 @RequestMapping(value = "/projectSummary")
		public ModelAndView projectSummary(@RequestParam(value="projectUUID", required=true) String projectUUID,
				@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,@RequestParam(value="tabType", required=false) String tabType,
				HttpServletRequest request) throws Exception {
				ModelAndView model = new ModelAndView();
				
				
			    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
			    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,"orgLocations");
				String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
				String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
				ProjectClients projectClients = null;
				boolean flag = commonUtil.isProjectAccessableToUser(projectUUID, projectClientUUID, loginUser, orgLocations, loginUserRole,candidateId,isGlobalAccess);
				
				if(flag == true) {
					Projects projects = projectsService.getProjectByProjectUUID(projectUUID);
					OrgLocations locations=orgLocationService.getOrgLocations(projects.getOrgLocationsId().getId());
					OrgLocations clientOrgLocations = null;
					String isClientOrgActive = "";
					if(projects != null){
						model.addObject("projectId", projects.getId());
						model.addObject("projectName", projects.getProjectName());
						if(projects.getStartDate() != null){
							model.addObject("projectStartDate", commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),projects.getStartDate()));
						}
						if(projects.getEndDate() != null){
							model.addObject("projectEndDate", commonUtil.dateStrFromOrgDateFormatter(locations.getDateFormat(),projects.getEndDate()));
						}
						List<ProjectMembers> projectAssignMemberList = projectsService.getProjectEmployeeListByProjectIdAndStatus(projects.getId(),"All",orgLocations,"","",isGlobalAccess);
						model.addObject("projectAssignMemberList", projectAssignMemberList);
						model.addObject("projectStatus", projects.getProjectStatus());
						
					}
					
					
					
					if(StringUtils.isNotEmpty(projectClientUUID)){
						 projectClients = projectClientService.getProjectClientsByUUID(projectClientUUID);
						 if(projectClients != null && projectClients.getOrgLocationsId().getId().equals(orgLocations.getId())) {
							 if(projectClients != null && projectClients.getOrgRelationId() != null){
									if(projectClients.getOrgRelationId().getResponderLocationId().getId().equals(orgLocations.getId())){
										clientOrgLocations = projectClients.getOrgRelationId().getLocationId();
									}else{
										clientOrgLocations = projectClients.getOrgRelationId().getResponderLocationId();
									}
									
									if(clientOrgLocations.getStatus() != null && clientOrgLocations.getStatus().equals(Status.ACTIVE)){
										isClientOrgActive = Constants.YES;
									}else {
										isClientOrgActive = Constants.NO;
									}
						 }
						
						}
						
						
						
						model.addObject("isClientOrgActive", isClientOrgActive);
					}
					model.addObject("projectClientUUID", projectClientUUID);
					
					if(null != tabType && !tabType.isEmpty() && tabType != ""){
						model.addObject("tabType", tabType);
					}
					
					
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
					
					Long roleHierarchyId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "roleHierarchyId");
					Collection<Roles> rolesList = rolesService.getUserRolesListByModuleAndLoginUserRoleHierarchy(org,orgLocations.getId(),roleHierarchyId);
					if(rolesList != null) {
						model.addObject("rolesList", rolesList);
					}
					
					List<Team> teamList = teamService.getTeamListByOrgLocationID(orgLocations.getId());
					model.addObject("teamList", teamList);
					
					model.addObject("projectUUID", projectUUID);
					model.addObject("projectCurrency", projects.getCurrency());
					model.addObject("postedOrgLocationId", projects.getOrgLocationsId().getId());
					
					if(StringUtils.isNotBlank(projects.getIsProjectShared()) && projects.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
						ProjectClients pc = projectClientService.getProjectClientsByProjectIdAndOrgLocationId(projects.getId(), orgLocations.getId());
						if(pc != null && StringUtils.isNotBlank(pc.getIsProjectShared()) && pc.getIsProjectShared().equalsIgnoreCase(Constants.YES)) {
							model.addObject("postedSharedProjectOrgLocationId", orgLocations.getId());
						}
								
					}
					
					List<OrgLocations> orgLocationsList = orgLocationService.getAllOrgLocationsByOrgId(org.getId());
					model.addObject("orgLocationsList", orgLocationsList);
					
					
					 List<LookUpData> taskTypeList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKTYPE,orgLocations.getId());
					 model.addObject("taskTypeList", taskTypeList);
					 
					 List<LookUpData> taskSeverityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSEVERITY,orgLocations.getId());
					 model.addObject("taskSeverityList", taskSeverityList);
					 
					 List<LookUpData> taskPriorityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKPRIORITY,orgLocations.getId());
					 model.addObject("taskPriorityList", taskPriorityList);
					 
					 List<LookUpData> taskStatusList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSTATUS,orgLocations.getId());
					 model.addObject("taskStatusList", taskStatusList);
					 
					commonUtil.highLightMenu(Constants.Projects, request);
					 
					 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_PROJECT);
				     model.addObject("actionId",action.getId());
				     List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
					 model.addObject("sourceleadSectionList",sections);
					
					 SourceleadActions actionIDProjectTask = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_PROJECT_TASKS);
				     model.addObject("actionIDProjectTask",actionIDProjectTask.getId());
				     List<SourceleadSectionDTO> sectionsProjectTsk  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionIDProjectTask.getId(),orgLocations.getId());
					 model.addObject("sourceleadSectionListForProjectTask",sectionsProjectTsk);
					 
					 List<String> taskTagsList = sourceleadTagsService.sourceleadTagsListByEntitypeAndOrgloactionId(TagEntites.TASKS.toString(), orgLocations.getId());
				     model.addObject("taskTagsList", taskTagsList);
				        
					model.setViewName("project/projectSummary");
				}else {
					model.setViewName("registration/persmissionDenied");
				}
				
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 @RequestMapping(value = "/projectTaskSummary")
		public ModelAndView projectTaskSummary(@RequestParam(value="taskUUID", required=true) String taskUUID,@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
				ModelAndView model = new ModelAndView();
				OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
				SimpleDateFormat dateFormat = orgLocations.getLocFullFormDateFormatter();
				Long orgId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "orgId"); 
				 SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
					if(sessionData == null)
					{
						sessionData = new SessionMaintainanceData();
					}
					sessionData.setNav(Constants.Projects);
					
					request.getSession().setAttribute("sessionData",sessionData);
				
				 ProjectTasks projectTask = projectTasksService.getProjectTasksByUUID(taskUUID);
				 
				 ProjectClients projectClient = projectClientService.getProjectClientsByUUID(projectClientUUID);
				 OrgLocations locations=orgLocationService.getOrgLocations(projectClient.getOrgLocationsId().getId());
					 
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
					 model.addObject("dueDate",  "");
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
					 if(pat.getProjectMemberId() != null && pat.getProjectMemberId().getEmployee() != null && pat.getProjectMemberId().getEmployee().getUser() != null) {
						 pat.setAssignedUserName(pat.getProjectMemberId().getEmployee().getUser().getFullName());
					 }else{
						 pat.setAssignedUserName(pat.getProjectMemberId().getProjectMemberCandidteId().getFullName());
					 }
				 }
				 model.addObject("taskTypeName", projectTask.getTaskType().getName());
				 model.addObject("assignedUsers", assignedUsers);
				 if(projectTask.getParentTaskId() != null) {
					 if(projectTask.getParentTaskId().getProjectTasksUUID() !=  null) {
						 model.addObject("parentProjectTaskUUID", projectTask.getParentTaskId().getProjectTasksUUID());
					 }
					 model.addObject("parentGeneratedId", projectTask.getParentTaskId().getGeneratedId());
					 model.addObject("parentTaskId", projectTask.getParentTaskId().getId());
				 }
				if(projectTask.getProjectId() != null) {
					model.addObject("projectName", projectTask.getProjectId().getProjectName());
					model.addObject("projectStartDate",  commonUtil.dateStrFromOrgDateFormatter(projectTask.getProjectId().getOrgLocationsId().getDateFormat(), projectTask.getProjectId().getStartDate()));
				}
				 
				 List<LookUpData> taskTypeList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKTYPE,orgLocations.getId());
				 model.addObject("taskTypeList", taskTypeList);
				 
				 List<LookUpData> taskSeverityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSEVERITY,orgLocations.getId());
				 model.addObject("taskSeverityList", taskSeverityList);
				 
				 List<LookUpData> taskPriorityList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKPRIORITY,orgLocations.getId());
				 model.addObject("taskPriorityList", taskPriorityList);
				 
				 List<LookUpData> taskStatusList = lookUpDataService.getLookUpdataByCategoryAndOrgLocationId(Constants.TASKSTATUS,orgLocations.getId());
				 model.addObject("taskStatusList", taskStatusList);
				 
				 
				 SourceleadActions action = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_PROJECT_TASKS);
				 List<SourceleadSectionDTO> sections  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(action.getId(),orgLocations.getId());
				 List<CustomFormFieldDTO> formFieldId = new ArrayList<>();
				 if(sections != null && !sections.isEmpty()) {
					 for(SourceleadSectionDTO sec:sections) {
						 for(OrglocationFormFieldsDTO customFields:sec.getCustomFieldsList()) {
							 CustomFormFieldDTO custom = new CustomFormFieldDTO();
							 custom.setCustomFormFieldId(customFields.getId());
							 formFieldId.add(custom);
						 }
					 }
					 if(formFieldId != null && !formFieldId.isEmpty() && projectTask != null && projectTask.getId() != null) {
					 Map<Long, String[]> projectTaskData = projectTaskCustomFieldDataService.getProjectTaskCustomFieldDataByUserId(orgId,formFieldId,projectTask.getId());
					 Map<Long,String> dataMap = new HashMap<>();
					 if(projectTaskData!= null && !projectTaskData.isEmpty()) {
						 for(Entry<Long, String[]> projectTaskCustomData:projectTaskData.entrySet()) {
							String labelName = projectTaskCustomData.getValue()[0];
							 String labelData = projectTaskCustomData.getValue()[1];
							 Long id = projectTaskCustomData.getKey();
							 StringBuilder keyValue =  new StringBuilder();
							 keyValue.append(labelName).append("$").append(labelData);
							 dataMap.put(id, keyValue.toString());
							 model.addObject("projectTaskCustomFieldlabelNameAndValue",dataMap);
							 //assetDTO.setProjectTaskCustomFieldlabelNameAndValue(dataMap);
						 	}
					 	}
					 }
				 }
				 
				 SourceleadActions actionIDProjectTask = sourceleadActionsService.getSourceleadActionsByActionRole(Constants.ROLE_CREATE_PROJECT_TASKS);
			     model.addObject("actionIDProjectTask",actionIDProjectTask.getId());
			     List<SourceleadSectionDTO> sectionsProjectTsk  = sourceleadFormSectionsService.getSourceleadFormSectionsByActionId(actionIDProjectTask.getId(),orgLocations.getId());
				 model.addObject("sourceleadSectionListForProjectTask",sectionsProjectTsk);
				 
				LookUpData taskStatus = lookUpDataService.getRequirementExperienceLevelyUniqueKey(projectTask.getTaskType().getUniqueKey());
				if(taskStatus !=null) {
					model.addObject("taskStatus", taskStatus.getUniqueKey());
				}
				
				model.addObject("taskUUID", taskUUID);
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
				
				 User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
				 String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
				 String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
				 
				 Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
				 boolean flag = commonUtil.isProjectAccessableToUser(projectUUID, projectClientUUID, loginUser, orgLocations, loginUserRole,candidateId,isGlobalAccess);
				 
				 if(flag == true) {
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
				 }else {
					 model.setViewName("registration/persmissionDenied");
				 }
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 
    @RequestMapping(value = "/requestAssociates")
	public ModelAndView requestAssociates(@RequestParam(value="projectUUID", required=true) String projectUUID,
			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		 
	    ModelAndView model = new ModelAndView();
		
	    Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		boolean flag = commonUtil.isProjectAccessableToUser(projectUUID, projectClientUUID, loginUser, orgLocations, loginUserRole,candidateId,isGlobalAccess);
	    
	    if(flag == true) {
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
	    }else {
	    	 model.setViewName("registration/persmissionDenied");
	    }
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;
    }
    
    
    @RequestMapping(value = "/addNewProjectMembers")
	public ModelAndView addNewProjectMembers(@RequestParam(value="projectUUID", required=true) String projectUUID,
			@RequestParam(value="projectClientUUID", required=true) String projectClientUUID,HttpServletRequest request) throws Exception {
	    Org org = (Org) SourceLeadSessionManager.getSessionAttribute(request, "org");
	    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	    User loginUser = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String loginUserRole = (String) SourceLeadSessionManager.getSessionAttribute(request, "loginUserRole");
		String isGlobalAccess = (String) SourceLeadSessionManager.getSessionAttribute(request, "isGlobalAccess");
		
	    ModelAndView model = new ModelAndView();
	    
	    SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
		if(sessionData == null)
		{
			sessionData = new SessionMaintainanceData();
		}
		sessionData.setNav(Constants.Projects);
		
		request.getSession().setAttribute("sessionData",sessionData);
		
		Long candidateId = (Long) SourceLeadSessionManager.getSessionAttribute(request, "candidateId");
		 boolean flag = commonUtil.isProjectAccessableToUser(projectUUID, projectClientUUID, loginUser, orgLocations, loginUserRole,candidateId,isGlobalAccess);
	
	    if(flag == true) {
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
	    }else {
	    	model.setViewName("registration/persmissionDenied");
	    }
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

    
    @RequestMapping(value = "/projectAttributes")
   	public ModelAndView pojrctAttributes(HttpServletRequest request) throws Exception {
   	    ModelAndView model = new ModelAndView();
   	    List<LookUpData> referTypeList = lookUpDataService.getReferLookUpDataByCategory(Constants.TASKTYPE);
		model.addObject("referTypeList", referTypeList);
		model.setViewName("project/projectAttributesTaskType");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }
    
    @RequestMapping(value = "/projectAttributePriority")
   	public ModelAndView pojrctAttributePriority(HttpServletRequest request) throws Exception {
   	    ModelAndView model = new ModelAndView();
   	    List<LookUpData> referTypeList = lookUpDataService.getReferLookUpDataByCategory(Constants.TASKPRIORITY);
		model.addObject("referTypeList", referTypeList);
   		model.setViewName("project/projectAttributePriority");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }
    
    
    @RequestMapping(value = "/projectAttributeStatus")
   	public ModelAndView pojrctAttributeStatus(HttpServletRequest request) throws Exception {
   	    ModelAndView model = new ModelAndView();
   	    List<LookUpData> referTypeList = lookUpDataService.getReferLookUpDataByCategory(Constants.TASKSTATUS);
		model.addObject("referTypeList", referTypeList);
		model.setViewName("project/projectAttributeStatus");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }

    @RequestMapping(value = "/projectAttributeSeverity")
   	public ModelAndView pojrctAttributeSeverity(HttpServletRequest request) throws Exception {
   	    ModelAndView model = new ModelAndView();
   	    List<LookUpData> referTypeList = lookUpDataService.getReferLookUpDataByCategory(Constants.TASKSEVERITY);
		model.addObject("referTypeList", referTypeList);
   		model.setViewName("project/projectAttributeSeverity");
   		model.addObject("sourceleadUrl", sourceleadUrl);
   		return model;
       }
    
}
