package com.bis.sourcelead.service;

import java.util.Date;
import java.util.HashSet;

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
import com.bis.sourcelead.dao.ProjectMembersDao;
import com.bis.sourcelead.dao.impl.ProjectMembersDaoImpl;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.impl.ProjectMembersServiceImpl;
import com.bis.sourcelead.util.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ProjectMembersServiceTest {
	
	@Configuration
	static class ProjectMembersServiceTestContextConfiguration {
		
		@Bean
		public ProjectMembersService projectMembersService() {
			return new ProjectMembersServiceImpl();
		}
		
	}
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	//@Test
	public void saveProjectMember() throws Exception{
		ProjectMembers projectMembers = new ProjectMembers();
		
		Projects projects = new Projects();
		projects.setId(1L);
		projectMembers.setProjects(projects);
		
		Employees employee = new Employees();
		employee.setId(1L);
		projectMembers.setEmployee(employee);
		
		projectMembers.setTimesheetFrequency("weekly");
		
		HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
		ProjectMembersHistory pmh = new ProjectMembersHistory();
		pmh.setProjectMemberId(projectMembers);
		pmh.setProjectAssignedDate(new Date());
		pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
		pmh.setCreatedDate(new Date());
		pmh.setCreatedBy("prasad");
		pmh.setUpdatedDate(new Date());
		pmh.setUpdatedBy("Raj");
		pmhList.add(pmh);
		
		projectMembers.setCurrentBillRate(100.0F);
		projectMembers.setRateType("Monthly");
		projectMembers.setStatus("1");
		projectMembers.setCreatedDate(new Date());
		projectMembers.setCreatedBy("prasad");
		projectMembers.setUpdatedDate(new Date());
		projectMembers.setUpdatedBy("Raj");
		projectMembers.setMemberHistoryList(pmhList);
		
		projectMembersService.saveProjectMember(projectMembers);
	}
	
	//@Test
	public void updateProjectMember() throws Exception{
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(7L);
		
		Projects projects = new Projects();
		projects.setId(1L);
		projectMembers.setProjects(projects);
		
		Employees employee = new Employees();
		employee.setId(1L);
		projectMembers.setEmployee(employee);
		
		HashSet<ProjectMembersHistory> pmhList = new HashSet<ProjectMembersHistory>();
		ProjectMembersHistory pmh = new ProjectMembersHistory();
		pmh.setProjectMemberId(projectMembers);
		pmh.setProjectAssignedDate(new Date());
		pmh.setStatus(Constants.PROJECT_MEMBER_HISTORY_OPEN);
		pmh.setCreatedDate(new Date());
		pmh.setCreatedBy("prasad");
		pmh.setUpdatedDate(new Date());
		pmh.setUpdatedBy("Raj");
		pmhList.add(pmh);
		
		
		projectMembers.setTimesheetFrequency("weekly");
		projectMembers.setCurrentBillRate(500.0F);
		projectMembers.setRateType("Monthly");
		projectMembers.setStatus("1");
		projectMembers.setCreatedDate(new Date());
		projectMembers.setCreatedBy("yaswanth");
		projectMembers.setUpdatedDate(new Date());
		projectMembers.setUpdatedBy("Raj");
		projectMembers.setMemberHistoryList(pmhList);
		
		projectMembersService.updateProjectMember(projectMembers);
	}
	
	@Test
	public void getProjectMemberById() throws Exception{
		projectMembersService.getProjectMemberById(1L);
	}
	
	//@Test
	public Boolean getDeleteProjectMember() throws Exception{
		Boolean isDeleted =  projectMembersService.deleteProjectMemberById(6L);
		return isDeleted;
	}
	
	//@Test
	public void getProjectMembers() throws Exception{
		projectMembersService.getAllProjectMembers();
	}

}
