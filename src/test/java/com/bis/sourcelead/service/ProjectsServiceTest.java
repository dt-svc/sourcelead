package com.bis.sourcelead.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ProjectsServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ProjectsServiceTest.class);

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
		superiorUser.setId(3L);
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
		logger.info("saveProject project added successfully");
	}

	/**
	 * this method for updateProject
	 * @throws Exception
	 */
	//@Test
	public void updateProject() throws Exception{
		Projects projects = new Projects();
		projects.setId(1L);
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
		Projects getProjectbyid = projectsService.getProjectById(1L);
		logger.info("getProjectById added successfully"+getProjectbyid);
	}
	
	
	/**
	 * this method for get all projects
	 * @throws Exception
	 */
	
	//@Test
	public void getallProjects() throws Exception{
		Collection<Projects> allprojects = projectsService.getAllProjects();
		logger.info("getProjects added successfully"+allprojects.size());
	}
	
	//@Test
    public void getPrimaryProjectByOrgLocationsId() throws Exception{
        System.out.println("sadasdsadasdsadsadsa");
        Long orgLocationsId= 3l;
        projectsService.getPrimaryProjectByOrgLocationsId(orgLocationsId);
        logger.info("getPrimaryProjectByOrgLocationsId added successfully");
        
    }
    
   // @Test
    public void getProjectByOrgLocationsId() throws Exception{
        Long orgLocationsId= 3l;
        List<Projects> projects = projectsService.getProjectByOrgLocationsId(orgLocationsId);
        logger.info("project list in getProjectByOrgLocationsId"+projects.size());
    }
	
   // @Test
	public void getAllLoginUserProjectsList() throws Exception{
		List<Long> projectIds=  new ArrayList<Long>();
		projectIds.add(1L);
		projectIds.add(2l);
		Long orgLocationId = 3L;
		List<Projects> projectsList = projectsService.getAllLoginUserProjectsList(projectIds,orgLocationId);
		
		logger.info("projects list in getAllLoginUserProjectsList"+projectsList.size());
	}
	//@Test
	public void getAllClientProjectsByOrgRelationId() throws Exception{
		Collection<Projects> AllClientProjects = projectsService.getAllClientProjectsByOrgRelationId(1l,1l);
		logger.info("projects list in getAllLoginUserProjectsList"+AllClientProjects.size());
	}
	//@Test
	public void updateStartDateOfProjectId() throws Exception{
		Integer updateStartDate = projectsService.updateStartDateOfProjectId(new Date(),1l);
		logger.info("updateStartDate addded successfully"+updateStartDate.intValue());
	}
	//@Test
	public void updateEndDateOfProjectId() throws Exception{
		Integer updateEndDate = projectsService.updateEndDateOfProjectId(new Date(),1l);
		logger.info("updateEndDate addded successfully"+updateEndDate.intValue());
	}

	//@Test
	public void getApprovarAssignedProjects() throws Exception{
		List<Long> updateEndDate = projectsService.getApprovarAssignedProjects(1l,1l);
		logger.info("getApprovarAssignedProjects addded successfully"+updateEndDate.size());
	}
	//@Test
	public void getProjectByProjectSequenceNumber() throws Exception{
		Projects updateEndDate = projectsService.getProjectByProjectSequenceNumber("301");
		logger.info("getProjectByProjectSequenceNumber addded successfully"+updateEndDate);
	}
//	@Test
	public void getProjectsBasedOnClientsAndStatusSelectionInFilters() throws Exception{
		Long userId = 1l;
		String clients ="1";
		String status ="STARTED";
		Long orgLocationId =3l;
		List<Projects> ProjectsBasedOnClients = projectsService.getProjectsBasedOnClientsAndStatusSelectionInFilters(userId,clients,status,orgLocationId);
		logger.info("getProjectsBasedOnClientsAndStatusSelectionInFilters addded successfully"+ProjectsBasedOnClients);
	}
	//@Test
	public void getProjectByOrgLocationsIdAndUserId() throws Exception{
		List<Projects> ProjectByOrgLocationsId = projectsService.getProjectByOrgLocationsIdAndUserId(1l,1l);
		logger.info("getProjectByOrgLocationsIdAndUserId addded successfully"+ProjectByOrgLocationsId.size());
	}
	//@Test
	public void getProjectByIdAndLoginOrgId() throws Exception{
		Projects ProjectByIdAndLogin = projectsService.getProjectByIdAndLoginOrgId(1l,1l);
		logger.info("getProjectByIdAndLoginOrgId addded successfully"+ProjectByIdAndLogin);
	}
	//@Test
	public void getUserAssignedProjectsCount() throws Exception{
		Integer UserAssignedProjectsCount = projectsService.getUserAssignedProjectsCount(1l,1l);
		logger.info("getUserAssignedProjectsCount addded successfully"+UserAssignedProjectsCount.intValue());
	}
	

	/**
	 * test case Weekly base Emails and Notification
	 * @throws Exception
	 */
	@Test
	public void getAllUserTimeSheetSubmissionStatus() throws Exception{
		List<User> usrList = projectsService.getAllUserTimeSheetSubmissionStatus();
		for(User user : usrList){
			TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
			projectsService.userEmailNotificationForTimeSheetSubmission(user,timesheetSubmissions);
			logger.info("getPreviousWeekTimeShhetSubmissionStatus addded successfully"+usrList.size());
			
		}
	}

	//@Test
	public void getPreviousWeekTimeShhetSubmissionStatus() throws Exception{
		List<User> usrList  = projectsService.getAllUserPrevoiusWeekTimeSheetSubmissionStatus();
		logger.info("getPreviousWeekTimeShhetSubmissionStatus addded successfully"+usrList.size());
		
	}
	/**
	 * this method for delete project by id
	 * @return
	 * @throws Exception
	 */
	//@Test
	public void getDeleteProject() throws Exception{
		Boolean isDeleted = projectsService.deleteProjectById(1L);
		logger.info("getDeleteProject "+isDeleted.booleanValue());
	}
	

}
