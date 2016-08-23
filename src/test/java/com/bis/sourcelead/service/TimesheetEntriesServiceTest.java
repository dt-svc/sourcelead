package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.impl.TimesheetEntriesServiceImpl;
import com.bis.sourcelead.util.CommonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class TimesheetEntriesServiceTest {

	
	@Configuration
	static class TimesheetEntriesServiceConfigTest {
		@Bean
		public TimesheetEntriesService timesheetEntriesService() {
			return new TimesheetEntriesServiceImpl();
		}

	}
	
	@Autowired
	TimesheetEntriesService timesheetEntriesService;
	
	
	@Autowired
	CommonUtil commonUtil;

	private static final Logger logger = LoggerFactory.getLogger(TimesheetEntriesServiceTest.class);

	//@Test
	public void addTimesheetSubmissions() throws Exception{
		
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		ProjectTasks projectTask = new ProjectTasks();
		//timesheetEntries.setId((long) 1);
		timesheetEntries.setStatus("agredd");
		timesheetEntries.setCreatedBy("akhil");
		projectTask.setId(1l);
		timesheetEntries.setProjectTask(projectTask);
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setDocFile(new byte[123]);
		timesheetSubmissions.setId(2l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		//timesheetEntries.setEntryStatus("");
		timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		
		logger.info("registration added successfully");
	}
	
	@Test
	public void updateTimesheetEntries() throws Exception{
		
		
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		ProjectTasks projectTask = new ProjectTasks();
		timesheetEntries.setId(1l);
		timesheetEntries.setStatus("abcd");
		timesheetEntries.setCreatedBy("akhil_kumar");
		projectTask.setId(1l);
		timesheetEntries.setProjectTask(projectTask);
		timesheetEntries.setDocFile(new byte[123]);
		timesheetSubmissions.setId(2l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		timesheetEntriesService.updateTimesheetEntries(timesheetEntries);
		logger.info("Successfully Added!!!!");
	}
	
	//@Test
	public void getTimesheetEntries() throws Exception{
		
		String id = "1";
		TimesheetEntries timesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		logger.info("Successfully retrived!!!!"+timesheetEntries.getStatus());
	}
	 
	
	//@Test
	public void getAllTimesheetEntries() throws Exception{
		
		Collection<TimesheetEntries> timesheetEntries = timesheetEntriesService.getAllTimesheetEntries();
		for(TimesheetEntries  tm : timesheetEntries ){
			logger.info("Successfully retrived!!!!"+tm.getStatus());
		}
		
	}
	
	//@Test
	public void getAllTimesheetEntriesBySubmissionId() throws Exception	{
		Collection<TimesheetEntries> AllTimesheetEntries = timesheetEntriesService.getAllTimesheetEntriesBySubmissionId(1l);
		for(TimesheetEntries  tm : AllTimesheetEntries ){
			logger.info("Successfully retrived!!!!"+tm.getStatus());
		}
	}
	//@Test
	public void deleteTimesheetEntries() throws Exception{
		String id = "1";  
		Boolean deleteTimesheet = timesheetEntriesService.deleteTimesheetEntries(id);
		logger.info("Deleted Successfully!!!!"+deleteTimesheet);
	}
}