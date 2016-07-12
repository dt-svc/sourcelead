package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;



@RestController
public class ProjectsDashboardController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsDashboardController.class);
	
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
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	
	@RequestMapping(value = "/projectDashboard")
	public ModelAndView projectDashboard() throws Exception {

		ModelAndView model = new ModelAndView();
		model.setViewName("project/projectDashboard");
		Collection<Projects> projectsList = projectsService.getAllProjects();
		model.addObject(projectsList);
		return model;
    }
	
	/**
	 * get project details by Id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value = "/getProjectById", method = RequestMethod.POST)
	 public Projects getProjectById(@RequestParam(value="ProjectId", required=false) Long projectId) throws Exception{ 
		    Projects projects = projectsService.getProjectById(projectId);
		    return projects;
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
	 public @ResponseBody String getProjectEmployeeListById(@RequestParam(value="ProjectId", required=false) Long projectId) throws Exception{ 
		 String resultJson = null;
		 Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembersByProjectId(projectId);
		 resultJson = toJSON(projectMembersList);
		 return resultJson;
	  }
	 
	 @RequestMapping(value = "/addEmployeeForProjectDashboard",  method = RequestMethod.POST)
		public @ResponseBody  String addEmployeeForProjectDashboard(@RequestParam(value="json", required=false) String json) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
			
			Employees employee  = new Employees();
			User user= new User();
			
			user.setFirstName(jsonObj.getString("employeeFirstName"));
			user.setLastName(jsonObj.getString("employeeLastName"));
			user.setEmail(jsonObj.getString("employeeEmail"));
			user.setCreatedDate(new Date());
			User  usr=	userService.addUser(user);
			
			employee.setUser(user);
			employee.setInvoiceFrequency(jsonObj.getString("invoiceFrequency"));
			employee.setCreatedDate(new Date());
			Employees  emp=	employeesService.addEmployee(employee);
			
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
