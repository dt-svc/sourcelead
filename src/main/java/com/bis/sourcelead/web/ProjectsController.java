package com.bis.sourcelead.web;

import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Roles;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.EmailScenarioService;
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
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;


@RestController
@PropertySource(value = { "classpath:config/application.properties" })
public class ProjectsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	 @Autowired
	 EmailScenarioService emailScenarioService;
	
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 private OrgService orgService;
	 
	 @Autowired
	 private OrgRelationsService orgRelationsService;
	 
	 @Autowired
	 private OrgLocationService orgLocationService;
	 
	 @Autowired
	 private ProjectTasksService tasksService;
	 
	 @Autowired
	 public UserService userService;
	
	 @Autowired
	 public EmployeesService employeesService;
	 
	 @Autowired
	 public UserRoleService userRoleService;

	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 @Autowired
	 public ProjectMembersService projectMembersService;
	
	 @Autowired
	 public RolesService rolesService;
	 
	 @Autowired
	 CommonUtil commonUtil;
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/project")
		public ModelAndView projects(HttpServletRequest request) throws Exception {
		    String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		   
				ModelAndView model = new ModelAndView();
				
				Collection<OrgRelations> orgClientList =orgRelationsService.getAllClientsByOrgLocationId(loginOrgLocations.getId()); 
				for(OrgRelations ocl :orgClientList){
					ocl.setOrgName(ocl.getClientLocationId().getOrg().getOrgName());
					ocl.setOrgid(ocl.getClientLocationId().getOrg().getId());
				 }
			    model.addObject("add_clientList", orgClientList);
			    
				Collection<Org> orgsList = orgService.getOrgs();
				model.addObject("add_client", orgsList);
				
                Collection<User> userList = userService.getUsersByUserRoles();
				model.addObject("user_List", userList);
				
				model.setViewName("project/project");
				model.addObject("sourceleadUrl", sourceleadUrl);
				return model;
	}
	 
	 @RequestMapping(value = "/saveProject" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProject(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	
	    	Projects projects = new Projects();
	    	projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
	    	projects.setProjectName(jsonObj.getString("ProjectName"));
	    	projects.setDescription(jsonObj.getString("ProjectDescription"));
	    	projects.setStartDate(new Date());
	    	projects.setEndDate(new Date());
	    	projects.setProjectStatus(Project_Status.getNameByValue((jsonObj.getString("Satatus"))));
	    	projects.setCreatedDate(new Date());
	    	projects.setUpdatedDate(new Date());
	    	projects.setCreatedBy(user.getUserCode());
	    	projects.setOrgLocationsId(loginOrgLocation);
	    	
	    	// Saving of Client by bringing OrgRelation id based on Client OrgId
		    String clientOrgCode = jsonObj.getString("selectClient");	
		    if(!clientOrgCode.isEmpty() && clientOrgCode!=null){
		    	  OrgLocations clientOrgLocations = orgLocationService.getOrgLocationsByOrgCode(clientOrgCode);
		    	  if(clientOrgLocations!=null){
		    		  OrgRelations  orgRelations = orgRelationsService.getOrgRelationIdByOrgLocationIdAndClientLocationId(loginOrgLocation.getId(), clientOrgLocations.getId());
			    		  if(orgRelations!=null){
			    			  projects.setOrgRelationId(orgRelations);
			    		  }
		    	  }
		    	  
		    }
	    	
	    	Projects updatedProjects = projectsService.saveProject(projects);
	    	
	    	ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
	    	pah1.setProjectId(updatedProjects);
	    	User approverUserId1 = new User();
	    	approverUserId1.setId(Long.parseLong(jsonObj.getString("level1Manager")));
	    	pah1.setApproverUserId(approverUserId1);
	    	pah1.setLevel(1);
	    	ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
	    	
		if(!jsonObj.getString("level2Manager").isEmpty()){
			ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
			pah2.setProjectId(updatedProjects);
			User approverUserId2 = new User();
			approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
			pah2.setApproverUserId(approverUserId2);
			pah2.setLevel(2);
			ProjectApproverHierarchy ProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
		}
	    	
		
	    	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);

			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				if(jsonObj.has(c+"taskName")){
					if(jsonObj.has(c+"taskDescription")){
						tasks.setDescription(jsonObj.getString(c+"taskDescription"));
					}
						tasks.setTaskName(jsonObj.getString(c+"taskName"));
						tasks.setCreatedDate(new Date());
						tasks.setUpdatedDate(new Date());
						tasks.setProjectId(projects);
				ProjectTasks savedtasks = tasksService.addTasks(tasks);
			  }
			}
			
			if (jsonObj.has("employeeMaxCount")){
				String employeeMaxCount = jsonObj.getString("employeeMaxCount");
				if(!jsonObj.getString("employeeMaxCount").isEmpty()){
						int empMaxCount = Integer.parseInt(employeeMaxCount);
						for (int c = 1; c < empMaxCount; c++) {
							
							ProjectMembers pm = new ProjectMembers();
							if(StringUtils.isNotEmpty(jsonObj.getString("employeeId"+c))){
								Employees emp = employeesService.getEmployee(jsonObj.getString("employeeId"+c));
							    if(emp != null){
							    	pm.setEmployee(emp);
						    	    
							    	String pattern = "yyyy-MM-dd";
						            SimpleDateFormat format = new SimpleDateFormat(pattern);
						            if(StringUtils.isNotEmpty(jsonObj.getString("employeestartDate"+c))){
						            	Date currentStartDate = format.parse(jsonObj.getString("employeestartDate"+c));
						            	  pm.setProjectAssignedDate(currentStartDate);
						            }
						            if(StringUtils.isNotEmpty(jsonObj.getString("employeeendDate"+c))){
						            	Date currentEndDate = format.parse(jsonObj.getString("employeeendDate"+c));
						            	pm.setProjectEndDate(currentEndDate);
						            }
									pm.setProjects(projects);
									pm.setCreatedDate(new Date());
									pm.setUpdatedDate(new Date());
									projectMembersService.saveProjectMember(pm);
									
									commonUtil.storeTimesheetSubmissionRecords(emp.getUser());
							    }
							}
					    }
				 }
		    }
			
			
			
			Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
			
			 resultJson = toJSON(tasksList);
			 
			 
			 return "projectDashboard";
			 
			 
	   }
	 
	 
    @RequestMapping(value = "/validateProjectId",  method = RequestMethod.POST)
    public @ResponseBody String validateJobSequenceNumber(@RequestParam(value="ProjectId", required=false) Long projectId) throws Exception {
	    	String result = "";
			String resultJson = "";
			Integer projectIdCount = projectsService.projectIdValidation(projectId);
			if(projectIdCount>0){
				result = "duplicate";
			}
			else{
				result = "unique";
			}
			resultJson = toJSON(result);
			return resultJson;
	    }
		


	@RequestMapping(value = "/sourceLeadTasks")
			public String sourceLeadTasks() throws Exception {
	
					ModelAndView model = new ModelAndView();
					String result = "";
					String resultJson = "";
					
					Collection<ProjectTasks>  sourceleadProjectTasks = tasksService.getAllSourceleadTasks();
					model.addObject("Tasks_List", sourceleadProjectTasks);
					
					resultJson = toJSON(sourceleadProjectTasks);
					return resultJson;
		}
	 
	 @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public Projects getProjectById(@PathVariable Long id) throws Exception { 
		    Projects projects = projectsService.getProjectById(id);
		    return projects;
	  }
	 
	 /**
	  * This method for get all projects
	  * @return
	  * @throws Exception
	  */
/*	 @RequestMapping(value = "/getAll", method = RequestMethod.GET)
	 public Collection<Projects> getAll() throws Exception { 
		 Collection<Projects> projectsList = projectsService.getAllProjects();
		 for(Projects projects : projectsList){
			 System.out.println(projects.getProjectManagerUser().getId());
			 System.out.println(projects.getOrg().getId());
		 }
		 return projectsList;
	  }*/
	
	 /**
	  * This method for save project
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/save", method = RequestMethod.GET,headers="Accept=application/json")
	 public Projects save() throws Exception { 
		    Projects projects = new Projects();
			User superiorUser = new User();
			superiorUser.setId(1L);
			projects.setSuperiorUser(superiorUser);
			
			projects.setProjectName("VADALU");
			projects.setProjectStatus(Project_Status.getNameByValue("Started"));
			projects.setDescription("sourcelead desc");
			projects.setStatus("1");
			projects.setStartDate(new Date());
			projects.setEndDate(new Date());
			projects.setTimesheetFrequency("Monthly");
			projects.setCreatedDate(new Date());
			projects.setCreatedBy("OfferTab");
			projects.setUpdatedDate(new Date());
			projects.setUpdatedBy("Idly");

		    return projectsService.saveProject(projects);
	  }
	 
	 /**
	  * This method for update project
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/update/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public Projects update(@PathVariable Long id) throws Exception { 
		    Projects projects = new Projects();
		    projects.setId(id);
		    
			User superiorUser = new User();
			superiorUser.setId(1L);
			projects.setSuperiorUser(superiorUser);
			
			projects.setProjectName("SOURCELEAD");
			projects.setProjectStatus(Project_Status.getNameByValue("Started"));
			projects.setDescription("sourcelead desc");
			projects.setStatus("1");
			projects.setStartDate(new Date());
			projects.setEndDate(new Date());
			projects.setTimesheetFrequency("Monthly");
			projects.setCreatedDate(new Date());
			projects.setCreatedBy("Prasad");
			projects.setUpdatedDate(new Date());
			projects.setUpdatedBy("Yaswanth");

		    return projectsService.updateProject(projects);
	  }
	 
	 /**
	  * This method for delete project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public Boolean delete(@PathVariable Long id) throws Exception { 
		Boolean isDeleted = projectsService.deleteProjectById(id);
		return isDeleted;
	  }
	  
	  @RequestMapping(value = "/addNewEmployee",  method = RequestMethod.POST)
		public @ResponseBody  String addNewEmployee(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
			
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
			Employees employee  = new Employees();
			
			// User saving
			User user= new User();
			user.setFirstName(jsonObj.getString("employeeFirstName"));
			user.setLastName(jsonObj.getString("employeeLastName"));
			user.setEmail(jsonObj.getString("EmailId"));
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
	  
	  @RequestMapping(value = "/getEmployeesList",  method = RequestMethod.POST)
		public @ResponseBody  String getEmployeesList(HttpServletRequest request) throws Exception {
		 	String resultJson = null;
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		 	
		    Collection<Employees> employeList=employeesService.getAllEmployeesByOrgLocationId(loginOrgLocations.getId());
			//Collection<Employees> employeeList = employeesService.getAllEmployees();
			resultJson = toJSON(employeList);
			return resultJson;
	  }
	 
 
	 @RequestMapping(value = "/notification", method = RequestMethod.GET,headers="Accept=application/json")
	 public void getNotification() throws Exception{
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIME_SHEET_SUBMISSION,Constants.ENTITY_TYPE_NOTIFICATION);
		 Map<String, String> globalVarsMap = new HashMap<String, String>();
		    globalVarsMap.put("firstName", "Vinesh");
		    globalVarsMap.put("lastName","Kammari");
			String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
			emailScenario.setBody(body);
			String url = null;
			String actionLink = "";
			User user = new User();
			emailScenerioUtil.sendNotification(actionLink, user, emailScenario);
	}
	 
	 //to  validate user email
	 @RequestMapping(value = "/getEmailVerification" , method = RequestMethod.POST)
	 public @ResponseBody  String validateUserEmail(@RequestParam(value="EmailId", required=false) String EmailId) throws Exception {
		 	
		 	String result = null;
		 	User user = userService.findByEmail(EmailId);
		 	
		 	if (user != null) {
				result = "true";
			}else{
				result = "false";
			}
			return result;
	   }

	//to validate Project SequeneNumber
		 @RequestMapping(value = "/validateProjectSequeneNumberById" , method = RequestMethod.POST)
		 public @ResponseBody  Boolean validateProjectSequeneNumber(@RequestParam(value="ProjectSequeneNumber", required=false) String ProjectSequeneNumber, HttpServletRequest request) throws Exception {
			    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
			 	Boolean flag = projectsService.validateProjectSequeneNumber(ProjectSequeneNumber,orgLocations.getId());
			 	
				return flag;
		   }

	//Validate for Cilent Website
	 @RequestMapping(value = "/ws/validateClientWebsite", method = RequestMethod.GET)
	   public @ResponseBody String validateOrgWebsite(
	   		@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites) {
		
			String status = null;
		    String orgInfo = orgService.websiteValidation(website,sourceleadBLockedwebsites);
			if (orgInfo != null) {
				status = "success";
			}
			else{
				status = "failure";
			}
			
	    return status;
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
