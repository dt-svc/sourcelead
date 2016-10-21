package com.bis.sourcelead.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
import com.bis.sourcelead.entity.TimeSheetSubmissionCount;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.TimesheetSubmissionsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class TimesheetSubmissionsServiceTest {

	
	@Configuration
	static class TimesheetSubmissionsServiceConfigTest {
		@Bean
		public TimesheetSubmissionsService timesheetSubmissionsService() {
			return new TimesheetSubmissionsServiceImpl();
		}

	}
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	//TimeSheetSubmissionCount timeSheetSubmissionsCount;
	
	private static final Logger logger = LoggerFactory.getLogger(TimesheetSubmissionsServiceTest.class);

	//@Test
	public void addTimesheetSubmissions() throws Exception{
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		ProjectMembers projectMemberId = new ProjectMembers();
		User user = new User();
		//timesheetSubmissions.setId((long) 1);
		timesheetSubmissions.setStatus("APPROVED");
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setDocFile(new byte[4096]);
		timesheetSubmissions.setEndDate(new Date());
		timesheetSubmissions.setTotalHours("20");
		projectMemberId.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMemberId);
		timesheetSubmissions.setRejectedReason("not logedin"); 
		user.setId(3l);
		timesheetSubmissions.setUserId("3");
		timesheetSubmissions.setApproverUserId(user);
		assertEquals(timesheetSubmissions,timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions));
	}
	
	 
	
	//@Test
	public void updateTimesheetSubmissions() throws Exception{
		
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		ProjectMembers projectMemberId = new ProjectMembers();
		User user = new User();
		timesheetSubmissions.setId((long) 1);
		timesheetSubmissions.setStatus("APPROVED");
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setDocFile(new byte[4096]);
		timesheetSubmissions.setEndDate(new Date());
		timesheetSubmissions.setTotalHours("20");
		projectMemberId.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMemberId);
		timesheetSubmissions.setRejectedReason("Registered");
		user.setId(3l);
		timesheetSubmissions.setUserId("1");
		timesheetSubmissions.setApproverUserId(user);
		assertEquals(timesheetSubmissions,timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions));
	}
	
	//@Test
	public void getTimesheetSubmissions() throws Exception{
		String id = "1";
		TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(id);
		assertEquals(timesheetSubmissions,timesheetSubmissionsService.getTimesheetSubmissions(id));
	}
	 
	
	//@Test
	public void getAllTimesheetSubmissions() throws Exception{
		
		List<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetSubmissions.setFullName("AJITH");
		User approverUserId = new User();
		approverUserId.setId(3l);
		timesheetSubmissions.setApproverUserId(approverUserId);
		ProjectMembers projectMembers =  new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
		}
		
	
	

	//@Test
	public void deleteTimesheetSubmissions() throws Exception{
		String id = "1";  
		timesheetSubmissionsService.deleteTimesheetSubmissions(id);
	}
	
	//@Test
	public void getAllEmployeeTimesheetSubmissions() throws Exception{
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		Employees employees = new Employees();
		timesheetSubmissions.setEmployeeId("1");
		employees.setEmployeeId("1");
		Date startDate = new Date();
		Date endDate = new Date();  
		Long  userId = 2l;
		
		List<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getAllEmployeeTimesheetSubmissions(startDate,endDate,userId);
		assertNotNull(timesheetSubmissionsList);
	}
	
	//@Test
	public void getAllApprovalEmployeeTimesheetSubmissions() throws Exception{
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		Date startDate = new Date();
		Date endDate = new Date();
		timesheetSubmissions.setStartDate(startDate);
		timesheetSubmissions.setEndDate(endDate);
		Long  userId = 2l;
		timesheetSubmissions.setId(userId);
		User user = new User();
		user.setId(1l);
		timesheetSubmissions.setApproverUserId(user);
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		assertEquals(timesheetSubmissions,timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissions(startDate,endDate,userId));
	}
	
	
	//@Test
	public void getAllApprovalEmployeeTimesheetSubmissionsByProjectMemberId() throws Exception{
		
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		Date startDate = new Date();
		Date endDate = new Date();
		Long  userId = 2l;
		Long  memberUserId = (long) 1;
		Long employeeId = (long) 2;
		timesheetSubmissions.setProjectMembers(projectMembers);
		
		assertEquals(timesheetSubmissions,timesheetSubmissionsService.getAllApprovalEmployeeTimesheetSubmissionsByEmployeeUserId(startDate,endDate,userId,memberUserId,employeeId));
	}
	
	//@Test
	public void getMinAndMaxTimesheetSubmissionDatesForEmployee() throws Exception{
		
		HashMap<String,Date> minAndMaxTimesheetSubmissionDatesForEmployee = new LinkedHashMap<>();
		Long userId = (long) 4;
		
		minAndMaxTimesheetSubmissionDatesForEmployee = timesheetSubmissionsService.getMinAndMaxTimesheetSubmissionDatesForEmployee(userId);
		assertNotNull(minAndMaxTimesheetSubmissionDatesForEmployee);
	}
	
	//@Test
	public void getApprovalTimesheetSubmissions() throws Exception{
		Long userId = 3l;
		Collection<TimesheetSubmissions> timesheetSubmissionslist = timesheetSubmissionsService.getApprovalTimesheetSubmissions(userId);
		assertNotNull(timesheetSubmissionslist);		
	}
	
	//@Test
	public void getTimesheetSubmissionsByAproverId() throws Exception{
		Long projectMemberId = (long) 4;
		Long userId = 3l;
		Date startDate = new Date();
		Collection<TimesheetSubmissions> timesheetSubmissionslist=timesheetSubmissionsService.getTimesheetSubmissionsByAproverId(projectMemberId,userId,startDate);
		assertNotNull(timesheetSubmissionslist);
	}
	
	//@Test
	public void saveAllTimesheetSubmissions() throws Exception{
		
		List<TimesheetSubmissions> timesheetSubmissionsList = new ArrayList<TimesheetSubmissions>();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetSubmissions.setFullName("AJITH");
		User approverUserId = new User();
		approverUserId.setId(1l);
		timesheetSubmissions.setApproverUserId(approverUserId);
		ProjectMembers projectMembers =  new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissionsService.saveAllTimesheetSubmissions(timesheetSubmissionsList);
	}
	//@Test
	public void getNextMondayByProjectMemberId() throws Exception{
		 Date date = timesheetSubmissionsService.getNextMondayByProjectMemberId(1l);
	}
	/*@Test
	public void	getTimeSheetSubmissionsCountBasedOnStatus() throws Exception{
		Integer getTimeSheetSubmissionsCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountBasedOnStatus(3l,"APPROVED");
		 logger.info(" getTimeSheetSubmissionsCountBasedOnStatus successfully updated "+ getTimeSheetSubmissionsCount);
	}*/
	/*@Test
	public void getTotalApprovedOrRejectedHoursByuserId() throws Exception{
		Float getTimeSheetSubmissionsCount = timesheetSubmissionsService.getTotalApprovedOrRejectedHoursByuserId(4l, "Approved");
	 logger.info(" getTotalApprovedOrRejectedHoursByuserId "+getTimeSheetSubmissionsCount);
	}*/
	
	/*@Test
	public void getTimeSheetSubmissionsListByStatus() throws Exception{
		User user = new User();
		user.setId(3l);
		Collection<TimesheetSubmissions> timesheetSubmissionslist = timesheetSubmissionsService.getTimeSheetSubmissionsListByStatus(user.getId(),"NEW");
		logger.info(" getTimeSheetSubmissionsListByStatus"+timesheetSubmissionslist.size());
	}*/
	
	//@Test
	public void getTimeSheetSubmissionsCountByUserId() throws Exception{
		TimeSheetSubmissionCount timesheetSubmissioncount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserId(3l);
	    assertNotNull(timesheetSubmissioncount);
	}
	
	/*@Test
	public void getTotalDraftAndSubmittedHoursByuserId() throws Exception{
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setStatus("APPROVED");
		Float timeSheetSubmissionsCount = timesheetSubmissionsService.getTotalDraftAndSubmittedHoursByuserId(3l, "Approved");
		 logger.info(" getTotalDraftAndSubmittedHoursByuserId "+timeSheetSubmissionsCount);
	}*/
	
	/*@Test
	public void getTimeSheetSubmissionsHoursCountByUserId() throws Exception{
		
		TimeSheetSubmissionCount timeSheetSubmissionsCount = new TimeSheetSubmissionCount();
		
		//timeSheetSubmissionsCount.setSubmittedTimesheetsSubmissionsHoursCount("10");
		timeSheetSubmissionsCount = timesheetSubmissionsService.getTimeSheetSubmissionsHoursCountByUserId(2l);
		logger.info(" getTotalDraftAndSubmittedHoursByuserId "+timeSheetSubmissionsCount.getApprovalTimesheetsSubmissionsHoursCount());
		
	}*/
	
	//@Test
	public void getProjectsBasedOnUserIdAndStatusAndClientForFilters() throws Exception{
	
		Collection<TimesheetSubmissions> timesheetSubmission = timesheetSubmissionsService.getProjectsBasedOnUserIdAndStatusAndClientForFilters(2l,"1","All");
		assertFalse(timesheetSubmission.isEmpty());
	}
	
	/*@Test
	public void getUsersTimeSheetSubmissionsCountBasedOnStatus() throws Exception{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions(); 
		
		Integer timeSheetSubmissionsCount = timesheetSubmissionsService.getUsersTimeSheetSubmissionsCountBasedOnStatus(5l,"NEW",3l);
		
		logger.info(" getTotalDraftAndSubmittedHoursByuserId "+timeSheetSubmissionsCount);
	}*/
	
	
	//@Test
	public void getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover() throws Exception{
		TimeSheetSubmissionCount getTimeSheetSubmissionsCount = timesheetSubmissionsService.getTimeSheetSubmissionsCountByUserIdAndOrgLocationIdForApprover(3l, 3l);
		assertNotNull(getTimeSheetSubmissionsCount);
	}
	
	//@Test
	public void getSubmissionApproverHierarchyForEmployeeList() throws Exception{
		List<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getSubmissionApproverHierarchyForEmployeeList(3l);
		assertNotNull(timesheetSubmissions);
	}
	
	@Test 
	public void getWeekFrequencyTest() throws Exception{
	      HashSet<TimesheetSubmissions> tms = timesheetSubmissionsService.getWeekFrequency(32l);
		assertNotNull(tms);
	}
	
}