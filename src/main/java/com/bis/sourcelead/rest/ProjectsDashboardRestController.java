package com.bis.sourcelead.rest;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.SourceleadPasswordEncoder;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Primary;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.enums.Status;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.TimesheetSubmissionsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * 
 * @author David Sammeta
 *
 */

@RestController
public class ProjectsDashboardRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardRestController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 private ProjectMembersHistoryService projectMembersHistoryService;
	 
	 @Autowired
	 public UserService userService;
	
	 @Autowired
	 public EmployeesService employeesService;
	
	 @Autowired
	 public ProjectMembersService projectMembersService;
	
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 @Autowired
	 public SubmissionApproverHierarchyService submissionApproverHierarchyService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 public RolesService rolesService;
	 
	 @Autowired
	 public UserRoleService userRoleService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 @Autowired
	 EmailScenarioService emailScenarioService;
	 
	 @Autowired
	 TimesheetSubmissionsService timesheetSubmissionsService;
	
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 @Autowired
	 private SourceleadPasswordEncoder sourceleadPasswordEncoder;
			
		/**
		 * to filter the projects based on client and project status
		 * @author David Sammeta
		 * @param values
		 * @param request
		 * @param clients
		 * @return String
		 * @throws Exception
        */
			@RequestMapping(value = "/getProjectsByStatusAndClient")
			public String getProjectsByStatusAndClient(@RequestParam(value="values", required=false) String values,HttpServletRequest request,@RequestParam(value="clients", required=false) String clients) throws Exception {

			    String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
				String resultJson = "";
				User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			    List<Projects> projectsList = projectsService.getProjectsBasedOnClientsAndStatusSelectionInFilters(user.getId(), clients, values,loginOrgLocations.getId());
			    for(Projects ps : projectsList){
			    	ps.setManagerLevel1(ps.getManagerLevel1());
			    	if(ps.getManagerLevel2() != null){
			    		ps.setManagerLevel2(ps.getManagerLevel2());	
			    	}
			    }
				 resultJson = commonUtil.toJSON(projectsList);
				 return resultJson;
			}
	
	/**
	 * get project details by project Id
	 * @author Laxman
	 * @param id
	 * @return Json
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		   String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		   User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		   
		     String ProjectDetails=projectsService.getProjectDetailsByProjectIdAndLoginOrgId(projectId, user, loginOrgLocation);
		   
			 return ProjectDetails;
	  }
	 
	 /**
	  * update the project by project ID
	  * @author Laxman
	  * @param projectId
	  * @param json
	  * @param request
	  * @return String
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/updateProjectByProjectId", method = RequestMethod.POST)
	 public String updateProjectByProjectId(@RequestParam(value="json", required=false) String json,@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 

		 json = "{"+json+"}";	
		 JSONObject jsonObj = new JSONObject(json);
		 User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user"); 
		 String ProjectDetailsUpdating = projectsService.getProjectDetailsUpdateByProjectId(projectId, user, jsonObj);
	
		 return ProjectDetailsUpdating;
	 }
	 

/**
 * update project task by task id	 
 * @author David Sammeta
 * @param taskId
 * @param fieldName
 * @param fieldValue
 * @return String
 * @throws Exception
 */
	 
	 @RequestMapping(value = "/updateProjectTaskById", method = RequestMethod.POST)
	 public String updateProjectTaskById(@RequestParam(value="taskId", required=false) String taskId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue) throws Exception{ 
		    
		    ProjectTasks projectTasks = tasksService.getTasksId(taskId);
		    if(fieldName.equalsIgnoreCase("taskName")){  
		    	projectTasks.setTaskName(fieldValue);
		    }
		    else if (fieldName.equalsIgnoreCase("description")) {
		    	projectTasks.setDescription(fieldValue);
			}     
		    tasksService.updateTasks(projectTasks);
		    return null;
	  }
	 
	 /**
	  * save the projects task
	  * @param json
	  * @param projectId
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/saveProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProjectTasks(@RequestParam(value="json", required=false) String json,
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		    json = "{"+json+"}";	
		    JSONObject jsonObj = new JSONObject(json);
		 	String saveProjectTasks = tasksService.saveProjectTasksByProjectId(projectId, jsonObj);
		 	
			 return saveProjectTasks;
	 }
	 
	/** 
	 * get projects tasks based on project id
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectTasks(
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		 	 String resultJson = null;
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
			 resultJson = commonUtil.toJSON(taskList);
			 return resultJson;
		 	
	 }
	/**
	 * Get Employees list based on project id
	 * @param projectId
	 * @return JSON
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectEmployeeListById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectEmployeeListById(@RequestParam(value="projectId", required=false) Long projectId) throws Exception{ 
		 
		 String employeeList = projectsService.getProjectEmployeeListByProjectId(projectId);
		 	
		 return employeeList;
	  }
	 
	 /**
	  * Save Employee from project summary
	  * @param json
	  * @param request
	  * @return JSON
	  * @throws Exception
	  */
	 @RequestMapping(value = "/addEmployeeForProjectDashboard",  method = RequestMethod.POST)
		public @ResponseBody  String addEmployeeForProjectDashboard(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		    json = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json);
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
		 	String addEmployeeProjectDashBoard = projectsService.storeEmployeeInProjectDashboard(loginOrgLocation, users, jsonObj); 
		 			 		
			return addEmployeeProjectDashBoard;
		}
		 
	/**
	 * Get Teammembers list by project id	
	 * @param projectId
	 * @param request
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/getTeamMembersList",  method = RequestMethod.POST)
		public @ResponseBody  String getTeamMembersList(@RequestParam(value="ProjectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
		 	String resultJson = null;
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			Collection<Employees> employeeList = employeesService.getEmployeesExcludeTeamMembers(projectId, loginOrgLocations.getId());
			resultJson = commonUtil.toJSON(employeeList);
			return resultJson;
		}
	/**
	 * Assign Employee to project by  project id	 
	 * @param projectId
	 * @param employeeId
	 * @param startDate
	 * @param endDate
	 * @param mailtoMembers
	 * @param request
	 * @return Json
	 * @throws Exception
	 */
	 @RequestMapping(value = "/storeAssignEmployee",  method = RequestMethod.POST)
		 public @ResponseBody  String storeAssignEmployee(
				@RequestParam(value="ProjectId", required=false) Long projectId,
				@RequestParam(value="employeeId", required=false) Long employeeId,
				@RequestParam(value="startDate", required=false) String startDate,
				@RequestParam(value="endDate", required=false) String endDate,
				@RequestParam(value="mailtoMembers", required=false) String mailtoMembers, HttpServletRequest request) throws Exception {
		 	
			 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
			 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			 	String storeAssignEmployee = projectMembersService.storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate(projectId, employeeId, startDate, endDate, mailtoMembers, loginOrgCode, user, loginOrgLocation);
			 
				return storeAssignEmployee;
		}
	 
	/**
	 * Checks String empty or null
	 * @param string
	 * @return boolean
	 */
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	
}
