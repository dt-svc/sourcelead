package com.bis.sourcelead.mockservices;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bis.sourcelead.dao.ProjectsDao;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Project_Status;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.impl.ProjectsServiceImpl;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SampleMockTest {
	 
	    @Autowired
	    @Mock
		ProjectsDao projectsDao;
		
		@InjectMocks
		ProjectsService projectsService = new ProjectsServiceImpl();
		
		@Before
		  public void setUp(){
			MockitoAnnotations.initMocks(this);
		}

	     @PersistenceContext
	     EntityManager entityManager;
	     
	    @Test
	    @Transactional
	    public void save() throws Exception { 
	    	
	    	
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
			projectsDao.saveProject(projects);
			assertNotNull(projects);
	    	
			when(projectsDao.saveProject(any(Projects.class))).thenReturn(projects);
		    //doNothing().when(projectsService).notifyOfNewAccount(projectId);
			 
			Projects projects2 = flushAndGet(projects.getId());
		    assertEquals(projects.getId(), projects2.getId());
		    assertEquals(projects.getProjectName(), projects2.getProjectName());

	    }
	     
	    private Projects flushAndGet(long id) {
	         entityManager.flush();
	         return entityManager.find(Projects.class, id);
	    }

}
