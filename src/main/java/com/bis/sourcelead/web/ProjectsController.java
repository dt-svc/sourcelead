package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.Date;

import org.json.JSONException;
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

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.form.Registration;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.EmailScenerioUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class ProjectsController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectsController.class);
	
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 
	 @RequestMapping(value = "/project")
		public ModelAndView projects() {

				ModelAndView model = new ModelAndView();
				model.setViewName("project/project");
				return model;
	}
	 
	 @RequestMapping(value = "/saveProject" , method = RequestMethod.GET)
	 public @ResponseBody  void saveProject(@RequestParam(value="json", required=false) String json) throws Exception {
		 	JSONObject jsonObj = new JSONObject(json);
	    	Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    	//Projects projects = gson.fromJson(json, Projects.class);
	    	Projects projects = new Projects();
	    	projects.setProjectName(jsonObj.getString("Project Name"));
	    	projects.setDescription(jsonObj.getString("Project Description"));
	    	
	    	
	    	projectsService.saveProject(projects);
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
			
			User projectManagerUser = new User();
			projectManagerUser.setId(2L);
			projects.setProjectManagerUser(projectManagerUser);
			
			Org org = new Org();
			org.setId(1L);
			projects.setOrg(org);
			
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
			
			User projectManagerUser = new User();
			projectManagerUser.setId(2L);
			projects.setProjectManagerUser(projectManagerUser);
			
			Org org = new Org();
			org.setId(1L);
			projects.setOrg(org);
			
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
}
