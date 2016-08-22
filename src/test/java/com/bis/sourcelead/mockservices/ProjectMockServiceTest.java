package com.bis.sourcelead.mockservices;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.service.SubmissionApproverHierarchyService;
import com.bis.sourcelead.service.impl.ProjectMembersServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ProjectMockServiceTest {

	
	private static final Long DummayValue = 3L;

	
	@Mock
	ProjectMembersDao projectMembersDao;
	
	@Mock
	SubmissionApproverHierarchyService submissionApproverHierarchyService;
	
	@Mock
	ProjectMembersHistoryService projectMembersHistoryService;
	
	@InjectMocks
	ProjectMembersService projectMembersService = new ProjectMembersServiceImpl();
	
	
	@Before
	  public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void getAllProjectsByUserIdAndStatus() throws Exception {
		
		ProjectMembers projectMembers=new ProjectMembers();
		Date date = new Date("27/5/2016");
		
		Projects projects = new Projects();
		projects.setEndDate(date);
		projects.setId(1L);
		
		Employees employee = new Employees();
		employee.setId(1L);
		
		
		projectMembers.setProjects(projects);
		projectMembers.setEmployee(employee);
		projectMembers.setId(1L);
		projectMembers.setTimesheetFrequency("weekly");
		projectMembers.setProjectAssignedDate(date);
		projectMembers.setCurrentBillRate(100.0F);
		projectMembers.setRateType("Monthly");
		projectMembers.setStatus("1");
		projectMembers.setStatus("ACTIVE");
		projectMembers.setCreatedDate(date);
		projectMembers.setCreatedBy("prasad");
		projectMembers.setUpdatedDate(date);
		
		
		
		ProjectMembers projectMembers1=new ProjectMembers();
		Date date1 = new Date("27/5/1991");
		
		Projects projects1 = new Projects();
		projects1.setEndDate(date);
		projects1.setId(2L);
		
		Employees employee1 = new Employees();
		employee1.setId(1L);
		
		
		projectMembers1.setProjects(projects1);
		projectMembers1.setEmployee(employee1);
		projectMembers1.setId(1L);
		projectMembers1.setTimesheetFrequency("weekly");
		projectMembers1.setProjectAssignedDate(date);
		projectMembers1.setCurrentBillRate(100.0F);
		projectMembers1.setRateType("Monthly");
		projectMembers1.setStatus("1");
		projectMembers1.setStatus("IN-ACTIVE");
		projectMembers1.setCreatedDate(date);
		projectMembers1.setCreatedBy("prasad");
		projectMembers1.setUpdatedDate(date);
		
		
		
		List<ProjectMembers> projectsListForAll = new ArrayList<ProjectMembers>();
		List<ProjectMembers> projectsListForInActive = new ArrayList<ProjectMembers>();
		List<ProjectMembers> projectsListForActive = new ArrayList<ProjectMembers>();
		
		projectsListForAll.add(projectMembers);
		projectsListForAll.add(projectMembers1);
		
		projectsListForActive.add(projectMembers);
		
		projectsListForInActive.add(projectMembers1);
		
		when(projectMembersDao.getAllProjectsByUserId(1l)).thenReturn(projectsListForAll);
		when(projectMembersDao.getAllProjectsByUserIdAndStatus(1l,"ACTIVE")).thenReturn(projectsListForActive);	
		when(projectMembersDao.getAllProjectsByUserIdAndStatus(1l,"IN-ACTIVE")).thenReturn(projectsListForInActive);
		
		
		when(submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForEmployee(anyLong(), anyLong(), anyString())).thenReturn(40.1f);
		when(submissionApproverHierarchyService.getTotalDraftAndSubmittedHoursByuserIdAndProjectIdForApprover(anyLong(), anyLong(), anyString())).thenReturn(20.1f);
		List<ProjectMembers> allProjectsList4 = projectMembersService.getAllProjectsByUserIdAndStatus(1l,"All");
		
		//assertSame(60.2f, allProjectsList4.get(0).getDraftHrs());
		
		
		verify(projectMembersDao,times(1)).getAllProjectsByUserId(1l);
		
		//verify(projectMembersDao,times(1)).getAllProjectsByUserIdAndStatus(1l,"ACTIVE");
		
		//assertSame(1L, allProjectsList4.get(0).getId());
		

	}
}
