package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Primary;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
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
		logger.info("saveProjectMember"+projectMembers.getCreatedBy());
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
		logger.info("updateProjectMember"+projectMembers.getId());

	}
	@Test
	public void getAllTerminatedProjectMembersByProjectIdAndterminatedStatus() throws Exception{
		List<ProjectMembers> getAllTerminatedProjectMembersByProjectIdAndterminatedStatus = projectMembersService.getAllTerminatedProjectMembersByProjectIdAndterminatedStatus(1l,Primary.NO);
	
		logger.info("getAllTerminatedProjectMembersByProjectIdAndterminatedStatus"+getAllTerminatedProjectMembersByProjectIdAndterminatedStatus.size());
	}
	
	@Test
	public void getProjectMemberById() throws Exception{
		ProjectMembers memberid = 	projectMembersService.getProjectMemberById(1L);
		logger.info("submissionApproverHierarchy added successfully"+memberid);
	}
	
	@Test
	public void deleteProjectMemberById() throws Exception{
		Boolean isDeleted =  projectMembersService.deleteProjectMemberById(1L);
		if(isDeleted !=null){
		logger.info("successfully deleted "+isDeleted);
		}
		//return isDeleted;
		
		
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
	public void getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId() throws Exception{
		List<ProjectMembers> getAllTerminatedProjectmembesId = projectMembersService.getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId(3l);
		logger.info("getAllTerminatedProjectMembersIdsByTimesheetApproverUserIdOrMemberUserId "+getAllTerminatedProjectmembesId.size());
	}
	@Test
	public void getAllProjectsByUserId() throws Exception{
		List<ProjectMembers> getAllProjectsByUserId = projectMembersService.getAllProjectsByUserId(1l);
		  logger.info("getAllProjectsByUserId added successfully"+getAllProjectsByUserId.size());
	}
	@Test
	public void getAllProjectMembersCountByProjectId() throws Exception{
		Integer getAllProjectMembersCount  = projectMembersService.getAllProjectMembersCountByProjectId(1l);
		logger.info("getAllProjectMembersCountByProjectId added successfully"+getAllProjectMembersCount);
	}
	@Test
	public void updateProjectMemberDeletedReason() throws Exception{
		ProjectMembers projectmemebers = new ProjectMembers();
		projectmemebers.setId(3l);
		projectmemebers.setStatus("MEMBER_IN_ACTIVE");
		Employees employees = new Employees();
		employees.setId(2l);
		employees.setEmployeeId("3l");
		projectmemebers.setEmployee(employees);
		Integer updateProjectMemberDeleted = projectMembersService.updateProjectMemberDeletedReason(2l,"hi hello DeleteReason");
		
		logger.info("updateProjectMemberDeletedReason added successfully"+updateProjectMemberDeleted);
		
	}
	@Test
	public void getAllProjectsByUserIdAndStatus() throws Exception{
		List<ProjectMembers> getAllProjectsByUserIdAndStatus = projectMembersService.getAllProjectsByUserIdAndStatus(1l,"In-Active");
		logger.info("getAllProjectsByUserIdAndStatus added successfully"+getAllProjectsByUserIdAndStatus.size());
	}
	@Test
	public void updateEndDateOfProjectMemberId() throws Exception{
		Integer updateEndDateOfProject = projectMembersService.updateEndDateOfProjectMemberId(new Date(),1l);
		logger.info("updateEndDateOfProjectMemberId added successfully"+updateEndDateOfProject);
	}
	@Test
	public void getEmployeeAssignedProjectsCount() throws Exception{
		Integer getEmployeeAssignedCount = projectMembersService.getEmployeeAssignedProjectsCount(1l,1l,1l);
		logger.info("getEmployeeAssignedProjectsCount added successfully"+getEmployeeAssignedCount);
	}
	@Test
	public void storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate() throws Exception{
		Long projectId=1l;
		Long employeeId=1l;
		String startDate="1991-02-02";
		String endDate="1991-10-02";
		String mailtoMembers="N";
		String loginOrgCode="SLADMIN";
		User user=new User();
		user.setId(1l);
		OrgLocations loginOrgLocation= new OrgLocations();
		projectMembersService.storeAssignEmployeeByProjectIdEmployeeIdStartDateEndaDate(projectId, employeeId, startDate, endDate, mailtoMembers, loginOrgCode, user, loginOrgLocation);
		logger.info("getEmployeeAssignedProjectsCount added successfully");
	}
		
	@Test
	public void prepareProjectMembersListByJson() throws Exception{
		JSONObject jsonObj= new JSONObject();
		jsonObj.put("employeeMaxCount", 10);
		for(int i=1;i<=10;i++){
		jsonObj.put("employeeId"+i,"1");
		}
		jsonObj.put("employeestartDate"+1,"1991-02-02");
		jsonObj.put("employeeendDate"+1,"1991-10-02");
		User user= new User();
		user.setId(1l);
		Projects projects= new Projects();
		projects.setId(1l);
		Set<ProjectMembers> prepareProjectMember = projectMembersService.prepareProjectMembersListByJson(jsonObj, user, projects);
		for(ProjectMembers pm:prepareProjectMember){
			pm.getId();
		}
		logger.info("getEmployeeAssignedProjectsCount added successfully");
	}
}
