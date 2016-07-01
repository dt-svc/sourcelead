package com.bis.sourcelead.web;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.Employee;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ProjectMembersService;

@RestController
@RequestMapping(value = "/projectMembers")
public class ProjectMembersController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMembersController.class);
	
	 @Autowired
	 private ProjectMembersService projectMembersService; 
	 
	 /**
	  * This method for get project member by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public ProjectMembers getProjectById(@PathVariable Long id) throws Exception { 
		    ProjectMembers projectMembers = projectMembersService.getProjectMemberById(id);
		    return projectMembers;
	  }
	 
	 /**
	  * This method for get all project members
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/getAll", method = RequestMethod.GET)
	 public Collection<ProjectMembers> getAll() throws Exception { 
		 Collection<ProjectMembers> projectMembersList = projectMembersService.getAllProjectMembers();
		 return projectMembersList;
	  }
	
	 
	 /**
	  * This method for save project member
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/save", method = RequestMethod.GET,headers="Accept=application/json")
	 public ProjectMembers save() throws Exception { 
		 ProjectMembers projectMembers = new ProjectMembers();
			
			Projects projects = new Projects();
			projects.setId(13L);
			projectMembers.setProjects(projects);
			
			Employee employee = new Employee();
			employee.setId(1L);
			projectMembers.setEmployee(employee);
			
			projectMembers.setTimesheetFrequency("weekly");
			projectMembers.setProjectAssignedDate(new Date());
			projectMembers.setCurrentBillRate(100.0F);
			projectMembers.setRateType("Monthly");
			projectMembers.setStatus("1");
			projectMembers.setCreatedDate(new Date());
			projectMembers.setCreatedBy("prasad");
			projectMembers.setUpdatedDate(new Date());
			projectMembers.setUpdatedBy("Raj");
			
			return projectMembersService.saveProjectMember(projectMembers);
	  }
	 
	 /**
	  * This method for update project by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/update/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public ProjectMembers update(@PathVariable Long id) throws Exception { 
		 ProjectMembers projectMembers = new ProjectMembers();
			projectMembers.setId(id);
			
			Projects projects = new Projects();
			projects.setId(13L);
			projectMembers.setProjects(projects);
			
			Employee employee = new Employee();
			employee.setId(1L);
			projectMembers.setEmployee(employee);
			
			projectMembers.setTimesheetFrequency("weekly");
			projectMembers.setProjectAssignedDate(new Date());
			projectMembers.setCurrentBillRate(500.0F);
			projectMembers.setRateType("Monthly");
			projectMembers.setStatus("1");
			projectMembers.setCreatedDate(new Date());
			projectMembers.setCreatedBy("yaswanth");
			projectMembers.setUpdatedDate(new Date());
			projectMembers.setUpdatedBy("Raj");

		    return projectMembersService.updateProjectMember(projectMembers);
	  }
	 
	 /**
	  * This method for delete project member by id
	  * @param id
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	 public Boolean delete(@PathVariable Long id) throws Exception { 
		Boolean isDeleted = projectMembersService.deleteProjectMemberById(id);
		return isDeleted;
	  }
}
