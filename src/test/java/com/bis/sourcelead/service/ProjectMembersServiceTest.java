package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.impl.ProjectMembersServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class ProjectMembersServiceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectMembersServiceTest.class);
	
	
	@Autowired
	ProjectMembersService projectMembersService;
	
	@Test
	public void saveProjectMember() throws Exception{
		ProjectMembers projectMembers = new ProjectMembers();
		
		Projects projects = new Projects();
		projects.setId(1L);
		projectMembers.setProjects(projects);
		
		Employees employee = new Employees();
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
		
		projectMembersService.saveProjectMember(projectMembers);
		logger.info("saveProjectMember added successfully");
	}
	
	@Test
	public void updateProjectMember() throws Exception{
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1L);
		
		Projects projects = new Projects();
		projects.setId(1L);
		projectMembers.setProjects(projects);
		
		Employees employee = new Employees();
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
		
		projectMembersService.updateProjectMember(projectMembers);
		logger.info("updateProjectMember added successfully");

	}
	
	@Test
	public void getProjectMemberById() throws Exception{
		ProjectMembers memberid = 	projectMembersService.getProjectMemberById(1L);
		logger.info("submissionApproverHierarchy added successfully"+memberid);
	}
	
	@Test
	public Boolean deleteProjectMemberById() throws Exception{
		Boolean isDeleted =  projectMembersService.deleteProjectMemberById(6L);
		logger.info("getDeleteProjectMember added successfully");
		return isDeleted;
		
	}
	
	@Test
	public void getAllProjectMembers() throws Exception{
		Collection<ProjectMembers> allprojectmembers = projectMembersService.getAllProjectMembers();
		logger.info("getAllProjectMembers added successfully"+allprojectmembers.size());
	}
	@Test
	public void getAllProjectMembersByProjectId() throws Exception{
		Collection<ProjectMembers> AllProjectMembersByProjectId = projectMembersService.getAllProjectMembersByProjectId(1l);
		logger.info("getAllProjectMembersByProjectId added successfully"+AllProjectMembersByProjectId.size());

	}
	@Test
	public void getAllProjectMembersIdsByemployeeId() throws Exception{
		List<Long> AllProjectMembersIdsByemployeeId = projectMembersService.getAllProjectMembersIdsByemployeeId(1l);
		logger.info("getAllProjectMembersIdsByemployeeId added successfully"+AllProjectMembersIdsByemployeeId.size());
	}
	@Test
	public void getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId() throws Exception{
		List<ProjectMembers> getAllProjectMembersIdsByTimesheet = projectMembersService.getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(1l);
		logger.info("getAllProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId added successfully"+getAllProjectMembersIdsByTimesheet.size());
	}
	@Test
	public void getAllProjectsByUserId() throws Exception{
		List<ProjectMembers> getAllProjectsByUserId = projectMembersService.getAllProjectsByUserId(1l);
		  logger.info("getAllProjectsByUserId added successfully"+getAllProjectsByUserId.size());
	}
	@Test
	public void getAllProjectMembersCountByProjectId() throws Exception{
		Integer getAllProjectMembersCount  = projectMembersService.getAllProjectMembersCountByProjectId(1l);
		logger.info("getAllProjectMembersCountByProjectId added successfully"+getAllProjectMembersCount.intValue());
	}
	@Test
	public void updateProjectMemberDeletedReason() throws Exception{
		Integer updateProjectMemberDeleted = projectMembersService.updateProjectMemberDeletedReason(1l,"hi hello DeleteReason");
		logger.info("updateProjectMemberDeletedReason added successfully"+updateProjectMemberDeleted.intValue());
	}
	@Test
	public void getAllProjectsByUserIdAndStatus() throws Exception{
		List<ProjectMembers> getAllProjectsByUserIdAndStatus = projectMembersService.getAllProjectsByUserIdAndStatus(1l,"INACTIVE");
		logger.info("getAllProjectsByUserIdAndStatus added successfully"+getAllProjectsByUserIdAndStatus.size());
	}
	@Test
	public void updateEndDateOfProjectMemberId() throws Exception{
		Integer updateEndDateOfProject = projectMembersService.updateEndDateOfProjectMemberId(new Date(),1l);
		logger.info("updateEndDateOfProjectMemberId added successfully"+updateEndDateOfProject.intValue());
	}
	@Test
	public void getEmployeeAssignedProjectsCount() throws Exception{
		Integer getEmployeeAssignedCount = projectMembersService.getEmployeeAssignedProjectsCount(1l,1l);
		logger.info("getEmployeeAssignedProjectsCount added successfully"+getEmployeeAssignedCount.intValue());
	}
}
