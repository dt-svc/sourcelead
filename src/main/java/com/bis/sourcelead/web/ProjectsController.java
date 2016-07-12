package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.Collection;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserRole;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.OrgService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserRoleService;
import com.bis.sourcelead.service.UserService;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;



@RestController
public class ProjectsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
	
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
	 public UserRoleService userRoleService;

	 @Autowired
	 public ProjectApproverHierarchyService projectApproverHierarchyService;
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/project")
		public ModelAndView projects() throws Exception {

				ModelAndView model = new ModelAndView();
				
				Collection<Org> orgsList = orgService.getOrgs();
				model.addObject("add_client", orgsList);
				
				Collection<UserRole> userRoleList = userRoleService.getUsersBasedOnUserRoles();
				model.addObject("user_RoleList", userRoleList);
				
				model.setViewName("project/project");
				return model;
	}
	 
	 @RequestMapping(value = "/saveProject" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProject(@RequestParam(value="json", required=false) String json) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	
	    	Projects projects = new Projects();
	    	projects.setProjectSequeneNumber(jsonObj.getString("ProjectId"));
	    	projects.setProjectName(jsonObj.getString("ProjectName"));
	    	projects.setDescription(jsonObj.getString("ProjectDescription"));
	    	projects.setStartDate(new Date());
	    	projects.setEndDate(new Date());
	    	projects.setProjectStatus(jsonObj.getString("Satatus"));
	    	projects.setCreatedDate(new Date());
	    	projects.setUpdatedDate(new Date());
	    	Projects updatedProjects = projectsService.saveProject(projects);
	    	
	    	
	    	ProjectApproverHierarchy pah1 = new ProjectApproverHierarchy();
	    	pah1.setProjectId(updatedProjects);
	    	User approverUserId1 = new User();
	    	approverUserId1.setId(Long.parseLong(jsonObj.getString("level1Manager")));
	    	pah1.setApproverUserId(approverUserId1);
	    	pah1.setLevel("Level1");
	    	ProjectApproverHierarchy saveProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah1);
	    	
		if(!jsonObj.getString("level2Manager").isEmpty()){
			ProjectApproverHierarchy pah2 = new ProjectApproverHierarchy();
			pah2.setProjectId(updatedProjects);
			User approverUserId2 = new User();
			approverUserId2.setId(Long.parseLong(jsonObj.getString("level2Manager")));
			pah2.setApproverUserId(approverUserId2);
			pah2.setLevel("Level2");
			ProjectApproverHierarchy ProjectApproverHierarchy = projectApproverHierarchyService.saveProjectApproverHierarchy(pah2);
		}
	    	
	    	
	    	
	    	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
			
			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				tasks.setTaskName(jsonObj.getString(c+"taskName"));
				tasks.setDescription(jsonObj.getString(c+"taskDescription"));
				tasks.setCreatedDate(new Date());
				tasks.setUpdatedDate(new Date());
				tasks.setProjectId(projects);
				
				ProjectTasks savedtasks = tasksService.addTasks(tasks);
			}
			
			Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
			
			 resultJson = toJSON(tasksList);
			 return resultJson;
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
			projects.setProjectStatus("Open");
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
			projects.setProjectStatus("Open");
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
		public @ResponseBody  String addNewEmployee(@RequestParam(value="json", required=false) String json) throws Exception {
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
