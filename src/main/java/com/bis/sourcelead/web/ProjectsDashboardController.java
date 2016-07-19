package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgRelationsService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SessionMaintainanceData;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;



@RestController
@PropertySource(value = { "classpath:config/application.properties" })
public class ProjectsDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardController.class);
	
	 @Value("${sourceleadUrl}")
	 private String sourceleadUrl;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 public UserService userService;
	
	 @Autowired
	 public EmployeesService employeesService;
	
	 @Autowired
	 public ProjectMembersService projectMembersService;
	
	
	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	
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
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	
	@RequestMapping(value = "/projectDashboard")
	public ModelAndView projectDashboard(HttpServletRequest request) throws Exception {

		User user = (User)SourceLeadSessionManager.getSessionAttribute(request, "user");
		ModelAndView model = new ModelAndView();
		model.setViewName("project/projectDashboard");
		
		SessionMaintainanceData sessionData = (SessionMaintainanceData)request.getSession().getAttribute("sessionData");
        if(sessionData == null)
        {
            sessionData = new SessionMaintainanceData();
        }
        sessionData.setNav(com.bis.sourcelead.util.Constants.Projects);
        
        request.getSession().setAttribute("sessionData",sessionData);

        
        Collection<Projects> projectsList = projectsService.getAllProjectsAndManagersByLoginUserId(user.getId());
        for(Projects projects: projectsList){
            if(projects.getOrgRelationId()!=null) {
                projects.setClient(projects.getOrgRelationId().getClientLocationId().getOrg().getOrgName());
            }
        }
		model.addObject(projectsList);
		model.addObject("sourceleadUrl", sourceleadUrl);

		return model;
		
    }
	
	/**
	 * get project details by Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,HttpServletRequest request) throws Exception{ 
		 Projects projects = projectsService.getProjectById(projectId);
		   String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		   OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			
		    projects.setCreatedDateStr(Constants.DF_STD.format(projects.getCreatedDate()));
		    projects.setStartDateStr(Constants.DF_STD.format(projects.getStartDate()));
		    if(projects.getEndDate() != null){
		    	 projects.setEndDateStr(Constants.DF_STD.format(projects.getEndDate()));
		    }
		    projects.setUpdatedDateStr(Constants.DF_STD.format(projects.getUpdatedDate()));
		    projects.setCreatedBy(user.getFirstName()+" "+user.getLastName());
		    Collection<ProjectApproverHierarchy> projectApproverHierarchies = projectApproverHierarchyService.getApproverUsersByProjectId(projectId);
		    String[] approvers = new String[projectApproverHierarchies.size()];
		    int i = 0;
		    for(ProjectApproverHierarchy php : projectApproverHierarchies){
		    	approvers[i] = php.getApproverUserId().getFirstName()+"&"+php.getApproverUserId().getId();
		    	i++;
		    }
		    projects.setProjectManagerName(approvers);
		    
		    
		    Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
		    String[] clientList = new String[orgClientList.size()];
		    int j = 0;
			for(OrgRelations ocl :orgClientList){
				clientList[j] = ocl.getClientLocationId().getOrg().getOrgName()+"&"+ocl.getClientLocationId().getOrg().getId();
				j++;
			 }
			projects.setClientName(clientList);
			
		    ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(projects);
			return jsonInString;
	  }
	 
	 @RequestMapping(value = "/updateProjectById", method = RequestMethod.POST)
	 public String updateProjectById(@RequestParam(value="ProjectId", required=false) Long projectId,@RequestParam(value="fieldName", required=false) String fieldName,@RequestParam(value="fieldValue", required=false) String fieldValue) throws Exception{ 
		    Projects projects = projectsService.getProjectById(projectId);
		    if(fieldName.equalsIgnoreCase("projectName")){
		    	projects.setProjectName(fieldValue);
		    }
		    else if (fieldName.equalsIgnoreCase("description")) {
		    	projects.setDescription(fieldValue);
			}     
		    projectsService.updateProject(projects);
		    return null;
	  }
	 
	 
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
	 
	 
	 @RequestMapping(value = "/saveProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProjectTasks(@RequestParam(value="json", required=false) String json,
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			
			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				tasks.setTaskName(jsonObj.getString(c+"taskName"));
				tasks.setDescription(jsonObj.getString(c+"taskDescription"));
				tasks.setCreatedDate(new Date());
				tasks.setUpdatedDate(new Date());
				Projects projects = projectsService.getProjectById(projectId);
				tasks.setProjectId(projects);	
				
				ProjectTasks savedtasks = tasksService.addTasks(tasks);
			}
			List<ProjectTasks> taskList=tasksService.getAllTasks();
		 	
			 resultJson = toJSON(taskList);
			 return resultJson;
		 	
	 }
	 
	 
	 @RequestMapping(value = "/getProjectTasks" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectTasks(
			 @RequestParam(value="projectId", required=false) Long projectId) throws Exception {
		 	
		 	 String resultJson = null;
			 Collection<ProjectTasks> taskList=tasksService.getAllTasksByProjectId(projectId);
			 resultJson = toJSON(taskList);
			 return resultJson;
		 	
	 }
	  
	 @RequestMapping(value = "/getProjectEmployeeListById", method = RequestMethod.POST)
	 public @ResponseBody String getProjectEmployeeListById(@RequestParam(value="projectId", required=false) Long projectId) throws Exception{ 
		 
		 Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
		 ArrayList<ProjectMembers> projectMembersArrList = new ArrayList<ProjectMembers>();
		 for(ProjectMembers pm : projectMembersList){
			 pm.setEmpName(pm.getEmployee().getUser().getFirstName()+" "+pm.getEmployee().getUser().getLastName());
			 
			 if(pm.getProjectAssignedDate()!=null){
				 pm.setProjectAssignedDateStr(Constants.DF_DMY.format(pm.getProjectAssignedDate()));
             }
        	 if(pm.getProjectEndDate()!=null){
        		pm.setProjectEndDateStr(Constants.DF_DMY.format(pm.getProjectEndDate()));
        	 }
	        	
			 projectMembersArrList.add(pm);
		 }
		 ObjectMapper mapper = new ObjectMapper();
		 String jsonInString = mapper.writeValueAsString(projectMembersArrList);
		 
		 return jsonInString;
	  }
	 
	 @RequestMapping(value = "/addEmployeeForProjectDashboard",  method = RequestMethod.POST)
		public @ResponseBody  String addEmployeeForProjectDashboard(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
			
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Employees employee  = new Employees();
			
			// User saving
			User user= new User();
			user.setFirstName(jsonObj.getString("employeeFirstName"));
			user.setLastName(jsonObj.getString("employeeLastName"));
			user.setEmail(jsonObj.getString("EmailId"));
			user.setUserType("Employee");
			user.setCreatedDate(new Date());
			user.setCreatedBy(users.getUserCode());
			User  usr=	userService.addUser(user);
			
			//Employee Saving
			employee.setUser(user);
			if(!jsonObj.getString("invoiceFrequency").isEmpty()){
				employee.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
			}
			employee.setOrgLocations(loginOrgLocation);
			employee.setCreatedBy(users.getUserCode());
			employee.setCreatedDate(new Date());
			Employees  emp=	employeesService.addEmployee(employee);
			
			
			// UserRoles saving
			String roleName = "ROLE_USER";
			Roles roles = rolesService.getRolesByRoleName(roleName);
			UserRole userRole = new UserRole();
			userRole.setRoles(roles);
			userRole.setUser(usr);
			userRole.setOrgLocations(loginOrgLocation);
			userRole.setIsPrimary(0);
			userRole.setCreatedBy(users.getUserCode());
			userRole.setCreatedDate(new Date());
			UserRole userRolesSaving = userRoleService.addUserRole(userRole);
			
			return null;
		}
	 
	 @RequestMapping(value = "/getTeamMembersList",  method = RequestMethod.POST)
		public @ResponseBody  String getTeamMembersList(@RequestParam(value="ProjectId", required=false) Long projectId, HttpServletRequest request) throws Exception {
		 	String resultJson = null;
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
			OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
			Collection<Employees> employeeList = employeesService.getEmployeesExcludeTeamMembers(projectId, loginOrgLocations.getId());
			resultJson = toJSON(employeeList);
			return resultJson;
		}
	 
	 @RequestMapping(value = "/storeAssignEmployee",  method = RequestMethod.POST)
		public @ResponseBody  String storeAssignEmployee(
				@RequestParam(value="ProjectId", required=false) Long projectId,
				@RequestParam(value="employeeId", required=false) Long employeeId,
				@RequestParam(value="startDate", required=false) String startDate,
				@RequestParam(value="endDate", required=false) String endDate) throws Exception {
		 	
			 	ProjectMembers pm = new ProjectMembers();
			 	
			  if(projectId != null){
				  
					Projects projects = projectsService.getProjectById(projectId);
					Employees emp = employeesService.getEmployee(employeeId.toString());
				 	pm.setEmployee(emp);
					pm.setProjects(projects);
					String pattern = "yyyy-MM-dd";
		            SimpleDateFormat format = new SimpleDateFormat(pattern);
		            if(StringUtils.isNotEmpty(startDate)){
		            	Date currentStartDate = format.parse(startDate);
		            	  pm.setProjectAssignedDate(currentStartDate);
		            }
		            else{
		            	 pm.setProjectAssignedDate(projects.getStartDate());
		            }
		            if(StringUtils.isNotEmpty(endDate)){
		            	Date currentEndDate = format.parse(endDate);
		            	pm.setProjectEndDate(currentEndDate);
		            }
		            
					pm.setCreatedDate(new Date());
					pm.setUpdatedDate(new Date());
					projectMembersService.saveProjectMember(pm);
					commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
			  }
				return null;
		}
	 
	private String toJSON(Object obj){
	        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
	            public HierarchicalStreamWriter createWriter(Writer writer) {
	                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
	            }
	        });
	        return xstream.toXML(obj);
	    }
}
