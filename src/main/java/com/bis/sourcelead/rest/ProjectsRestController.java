package com.bis.sourcelead.rest;

import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmailScenarioService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgLocationService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.RolesService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;


@RestController
@PropertySource("${propertyLocation:app.properties}")
public class ProjectsRestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsRestController.class);
	
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
	  * This method for save the  project by json
	  * @param json
	  * @param request
	  * @return value is Project pojo
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/storeProject" , method = RequestMethod.POST)
	 public @ResponseBody  String storeProject(@RequestParam(value="json", required=false) String json) throws Exception {
		   
		    String json1 = "{"+json+"}";
		    JSONObject jsonObj = new JSONObject(json1);
		    String outPut =  projectsService.storeProject(jsonObj);
		    return outPut;		
			 
	   }
	 
	 @RequestMapping(value = "/saveProject" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProject(@RequestParam(value="json", required=false) String json,HttpServletRequest request) throws Exception {
		   
		String json1 = "{" + json + "}";
		JSONObject jsonObj = new JSONObject(json1);

		OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request,
				"orgLocations");
		User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		String loginOrgCode = (String) SourceLeadSessionManager.getSessionAttribute(request, "orgCode");
		projectsService.saveProjectByUserOrgLocationsJson(user, loginOrgLocation, loginOrgCode, jsonObj);
		SourceLeadSessionManager.setSessionAttribute(request, "projectCreateMsg",
				"Successfully you have created a project");
		return "projectDashboard";
			 
	   }
	 
	 
    @RequestMapping(value = "/validateProjectId",  method = RequestMethod.POST)
    public @ResponseBody String validateJobSequenceNumber(@RequestParam(value="ProjectId", required=false) Long projectId) throws Exception {
	    	String result;
			String resultJson;
			Integer projectIdCount = projectsService.projectIdValidation(projectId);
			if(projectIdCount>0){
				result = "duplicate";
			}
			else{
				result = "unique";
			}
			resultJson = commonUtil.toJSON(result);
			return resultJson;
	    }
		


	@RequestMapping(value = "/sourceLeadTasks")
	public String sourceLeadTasks() throws Exception {
			ModelAndView model = new ModelAndView();
			Collection<ProjectTasks>  sourceleadProjectTasks = tasksService.getAllSourceleadTasks();
			model.addObject("Tasks_List", sourceleadProjectTasks);
			return commonUtil.toJSON(sourceleadProjectTasks);
		}
	 
	 @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public Projects getProjectById(@PathVariable Long id) throws Exception { 
		    Projects projects = projectsService.getProjectById(id);
		    return projects;
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
		    
		    String json1 = "{"+json+"}";	
		    JSONObject jsonObj = new JSONObject(json1);
		 	OrgLocations loginOrgLocation = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	User users = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	userService.storeUser(jsonObj,users,loginOrgLocation,"PROJECTS","",new Org());
			return "SUCCESS";
		}
	  
	  /**
		  * This method for getEmployeesList by by excluding  Level1 and Level2 Managers 
		  * @param id
		  * @return
		  * @throws Exception
		  */
	  @RequestMapping(value = "/getEmployeesListByExcludingManagers",  method = RequestMethod.POST)
		public @ResponseBody  String getEmployeesListByExcludingManagers(HttpServletRequest request,
			   @RequestParam(value="level1Manager", required=false) Long level1Manager,
			   @RequestParam(value="level2Manager", required=false) Long level2Manager,
			   @RequestParam(value="searchByName", required=false) String searchByName) throws Exception {
		 	String loginOrgLocation = (String) SourceLeadSessionManager.getSessionAttribute(request, "locationCode");
		    OrgLocations loginOrgLocations = orgLocationService.getOrgLocationsByLocationCode(loginOrgLocation);
		    Collection<Employees> employeList=employeesService.getAllEmployeesByOrgLocationIdAndSearchByName(loginOrgLocations.getId(),level1Manager,level2Manager,searchByName);
		    return  commonUtil.toJSON(employeList);
			
	  }
	 
 
	/* @RequestMapping(value = "/notification", method = RequestMethod.GET,headers="Accept=application/json")
	 public void getNotification() throws Exception{
		 EmailScenario  emailScenario = emailScenarioService.getEmailScenarioByUniqueName(Constants.TIME_SHEET_SUBMISSION,Constants.ENTITY_TYPE_NOTIFICATION);
		 Map<String, String> globalVarsMap = new HashMap<>();
		    globalVarsMap.put("firstName", "Vinesh");
		    globalVarsMap.put("lastName","Kammari");
			String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
			emailScenario.setBody(body);
			String actionLink = "";
			User user = new User();
			OrgLocations loginOrgLocation = new OrgLocations();
			emailScenerioUtil.sendNotification(actionLink, user, user, loginOrgLocation, emailScenario);
	}*/
	 
	 //to  validate user email
	 @RequestMapping(value = "/getEmailVerification" , method = RequestMethod.POST)
	 public @ResponseBody  String validateUserEmail(@RequestParam(value="EmailId", required=false) String EmailId) throws Exception {
		 	
		 	String result;
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
			    return projectsService.validateProjectSequeneNumber(ProjectSequeneNumber,orgLocations.getId());
		   }

	//Validate for Cilent Website
	 @RequestMapping(value = "/ws/validateClientWebsite", method = RequestMethod.GET)
	   public @ResponseBody String validateOrgWebsite(
	   		@RequestParam(value="website", required=false) String website,
	   		@RequestParam(value="sourceleadBLockedwebsites", required=false) String sourceleadBLockedwebsites) {
			String status = "success";
            String orgInfo = orgService.websiteValidation(website,sourceleadBLockedwebsites);
            if(orgInfo !=null && !"".equalsIgnoreCase(orgInfo)){
            	 if ("Website Duplicated".equalsIgnoreCase(orgInfo)) {
                     status = "Website Duplicated";
                 }else if("Invalid Website".equalsIgnoreCase(orgInfo)){
                     status = "Invalid Website";
                 }
            }
	    return status;
	   }
	   protected static boolean isNullOrEmpty(String str) {
			return str == null || "".equals(str.trim());
		}
}
