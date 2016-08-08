package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.dao.impl.ProjectsDaoImpl;
import com.bis.sourcelead.entity.Org;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.impl.ProjectsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ProjectsServiceTest {
	
	@Configuration
	static class ProjectServiceTestContextConfiguration {
		@Bean
		public ProjectsService projectsService() {
			return new ProjectsServiceImpl();
		}
	}
	
	@Autowired
	ProjectsService projectsService;
	
	/**
	 * this method for save project
	 * @throws Exception
	 */
//	@Test
	public void saveProject() throws Exception{
		
		Projects projects = new Projects();
		
		User superiorUser = new User();
		superiorUser.setId(1L);
		projects.setSuperiorUser(superiorUser);
		
		projects.setProjectName("XYZ");
		projects.setProjectStatus(Project_Status.getNameByValue("Started"));
		projects.setDescription("sourcelead desc");
		projects.setStatus("0");
		projects.setStartDate(new Date());
		projects.setEndDate(new Date());
		projects.setTimesheetFrequency("Monthly");
		projects.setCreatedDate(new Date());
		projects.setCreatedBy("prasad");
		projects.setUpdatedDate(new Date());
		projects.setUpdatedBy("Raj");
		
		projectsService.saveProject(projects);
	}

	/**
	 * this method for updateProject
	 * @throws Exception
	 */
	//@Test
	public void updateProject() throws Exception{
		Projects projects = new Projects();
		projects.setId(9L);
		User superiorUser = new User();
		superiorUser.setId(1L);
		projects.setSuperiorUser(superiorUser);
		
		projects.setProjectName("XYZ Updated");
		projects.setDescription("project desc");
		projects.setProjectStatus(Project_Status.getNameByValue("Started"));
		projects.setStatus("0");
		projects.setStartDate(new Date());
		projects.setEndDate(new Date());
		projects.setTimesheetFrequency("Monthly");
		projects.setCreatedDate(new Date());
		projects.setCreatedBy("prasad");
		projects.setUpdatedDate(new Date());
		projects.setUpdatedBy("Yaswanth");
		
		projectsService.updateProject(projects);
	}
	
	/**
	 * this method for get project by id
	 * @throws Exception
	 */
	//@Test
	public void getProjectById() throws Exception{
		projectsService.getProjectById(1L);
	}
	
	/**
	 * this method for delete project by id
	 * @return
	 * @throws Exception
	 */
	//@Test
	public Boolean getDeleteProject() throws Exception{
		Boolean isDeleted = projectsService.deleteProjectById(9L);
		return isDeleted;
	}
	
	/**
	 * this method for get all projects
	 * @throws Exception
	 */
	//@Test
	public void getProjects() throws Exception{
		projectsService.getAllProjects();
	}
	/**
	 * test case Weekly base Emails and Notification
	 * @throws Exception
	 */
	@Test
	public void getAllUserTimeSheetSubmissionStatus() throws Exception{
		List<User> usrList = projectsService.getAllUesrTimeSheetSubmissionStatus();
		for(User user : usrList){
			projectsService.userEmailNotificationForTimeShhetSubmission(user);
		}
	}
}
