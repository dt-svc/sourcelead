package com.bis.sourcelead.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.EmailAttachment;
import com.bis.sourcelead.entity.EmailMessage;
import com.bis.sourcelead.entity.EmailRecipient;
import com.bis.sourcelead.entity.EmailScenario;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.Constants;
import com.bis.sourcelead.util.EmailScenerioUtil;

@RestController
@RequestMapping(value = "/projects")
public class ProjectsController {
	 @Autowired
	 private ProjectsService projectsService; 
	 
	 @Autowired
	 EmailScenerioUtil emailScenerioUtil;
	 
	 
	 /**
	  * This method for get project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
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
	 @RequestMapping(value = "/getAll", method = RequestMethod.GET)
	 public Collection<Projects> getAll() throws Exception { 
		 Collection<Projects> projectsList = projectsService.getAllProjects();
		 for(Projects projects : projectsList){
			 System.out.println(projects.getProjectManagerUser().getId());
			 System.out.println(projects.getOrg().getId());
		 }
		 return projectsList;
	  }
	
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
	 * This method for test email  
	 * @throws Exception
	 */
	 @RequestMapping(value = "/email", method = RequestMethod.GET,headers="Accept=application/json")
	 public void getEmail() throws Exception{
			
			EmailMessage emailMessage = new EmailMessage();
			
			
			 emailMessage.setFirstName("Prasad");
			 emailMessage.setLastName("Salina");
			 emailMessage.setFromEmail("peopleaxisindia@gmail.com");
			 
			 Map<String, String> globalVarsMap = new HashMap<String, String>();
			 globalVarsMap.put("firstName", emailMessage.getFirstName());
			 globalVarsMap.put("lastName",emailMessage.getLastName());
				
			
			ArrayList<EmailRecipient> emailRecipientList = new ArrayList<EmailRecipient>();
			EmailRecipient emailRecipient = new EmailRecipient();
			 emailMessage.setToEmail("prasad.salina@gmail.com");
			emailRecipient.setEmail(emailMessage.getToEmail());
			emailRecipient.setName(emailMessage.getFirstName()+" "+emailMessage.getLastName());
			emailRecipientList.add(emailRecipient);
			
			
			Org org = new Org();
			List<EmailAttachment> attachmentList = new ArrayList<EmailAttachment>();
			
			EmailScenario emailScenario =  emailScenerioUtil.validateEmailscenerioExists(Constants.ORG_REGISTRATION,"sourcelead.com" , "in.sourcelead.com" , emailMessage , "userCode");
			
			String subject = emailScenerioUtil.bulidSubject(emailScenario.getSubject(),globalVarsMap);
			String body = emailScenerioUtil.buildBody(emailScenario.getBody(),globalVarsMap);
			String signature = emailScenerioUtil.buildSignature(emailScenario.getSignature(),globalVarsMap);
			String url = null;
			
			String emailTemplate = emailScenerioUtil.AssembleEmailMessage(emailScenario,emailMessage, subject, body, signature, url, org);
			emailMessage.setSubject(subject);
			emailMessage.setMessageBody(emailTemplate);
			emailScenerioUtil.sendEmail(emailMessage,emailRecipientList,attachmentList);
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
