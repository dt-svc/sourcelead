package com.bis.sourcelead.web;

import java.io.Writer;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.SourceLeadSessionManager;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

@Controller
@RestController
@PropertySource(value = { "classpath:config/application.properties" })
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private ProjectsService projectsService;
	
	@Autowired
	private ProjectApproverHierarchyService projectApproverHierarchyService;
	
	
	@RequestMapping(value = "/usersList")
	public ModelAndView userRegistration() {
		ModelAndView model = new ModelAndView();
		model.setViewName("users/usersList");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	
	@RequestMapping(value = "/userCreation")
	public ModelAndView userCreation(HttpServletRequest request) throws Exception {
		OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
	   
		ModelAndView model = new ModelAndView();
	    Collection<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
	    model.addObject("org_projectsList", projectsList);
	    
	    Collection<User> userList = userService.getUsersByUserRoles();
		model.addObject("user_List", userList);
	    
		model.setViewName("users/userCreation");
		model.addObject("sourceleadUrl", sourceleadUrl);
		return model;

	}
	
	@RequestMapping(value = "/addProject" , method = RequestMethod.POST)
	 public @ResponseBody  String saveProject(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		
		    OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	
	    	Projects projects = new Projects();
	    	OrgLocations ol = new OrgLocations();
			ol.setId(orgLocations.getId());
			projects.setOrgLocationsId(ol);
			
	    	projects.setProjectSequeneNumber(jsonObj.getString("ProjectSequeneNumber"));
	    	projects.setProjectName(jsonObj.getString("ProjectName"));
	    	projects.setStartDate(new Date());
	    	projects.setEndDate(new Date());
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
	    	
			
		 Collection<Projects> projectsList = projectsService.getProjectByOrgLocationsId(orgLocations.getId());
		
		 resultJson = toJSON(projectsList);
		 return resultJson;
	   }

	  
	 @RequestMapping(value = "/createUser" , method = RequestMethod.POST)
	 public @ResponseBody  ModelAndView createUser(@RequestParam(value="json", required=false) String json, HttpServletRequest request) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
		 	
		 	String resultJson = null;
		 	User user = (User) SourceLeadSessionManager.getSessionAttribute(request, "user");
		 	OrgLocations orgLocations = (OrgLocations) SourceLeadSessionManager.getSessionAttribute(request, "orgLocations");
		 	
		 	//For Saving of User Details
			userService.createUser(jsonObj,user.getUserCode(),orgLocations.getId());
			ModelAndView model = new ModelAndView();
			model.setViewName("users/userCreation");
			model.addObject("sourceleadUrl", sourceleadUrl);
			return model;
	   }
	 
	 //to get the Project Manager Name by using projectId
	 @RequestMapping(value = "/getProjectManager" , method = RequestMethod.POST)
	 public @ResponseBody  String getProjectManager(@RequestParam(value="projectId", required=false) String projectId, HttpServletRequest request) throws Exception {
		 	
		 	String resultJson = null;
		 	ProjectApproverHierarchy ph = null;
		 	if(projectId != null && !"".equals(projectId)){
		 		ph = projectApproverHierarchyService.getLevelOneProjectApproverByProjectId(Long.parseLong(projectId));
		 	}
		 	resultJson = toJSON(ph);
			return resultJson;
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
