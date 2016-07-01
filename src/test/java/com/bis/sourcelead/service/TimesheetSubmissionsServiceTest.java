package com.bis.sourcelead.service;

import java.util.Collection;

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
import com.bis.sourcelead.dao.TimesheetSubmissionsDao;
import com.bis.sourcelead.dao.impl.TimesheetSubmissionsDaoImpl;
import com.bis.sourcelead.entity.TimesheetSubmissions;
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
		@Bean
		public TimesheetSubmissionsDao timesheetSubmissionsDao() {
			return new TimesheetSubmissionsDaoImpl();
		}	

	}
	
	@Autowired
	TimesheetSubmissionsService timesheetSubmissionsService;
	
	@Autowired
	TimesheetSubmissionsDao timesheetSubmissionsDao;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Test
	public void addTimesheetSubmissions() throws Exception{
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 1);
		timesheetSubmissions.setStatus("agredd");
		timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
		System.out.println("Successfully Added!!!!");
	}
	
	 
	
	@Test
	public void updateTimesheetSubmissions() throws Exception{
		
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 1);
		timesheetSubmissions.setStatus("updated");
		timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		System.out.println("Successfully Added!!!!");
	}
	
	@Test
	public void getTimesheetSubmissions() throws Exception{
		
		String id = "1";
		TimesheetSubmissions timesheetSubmissions = timesheetSubmissionsService.getTimesheetSubmissions(id);
		System.out.println("Successfully retrived!!!!"+timesheetSubmissions.getStatus());
	}
	 
	
	@Test
	public void getAllTimesheetSubmissions() throws Exception{
		
		Collection<TimesheetSubmissions> timesheetSubmissions = timesheetSubmissionsService.getAllTimesheetSubmissions();
		for(TimesheetSubmissions  ts : timesheetSubmissions ){
			System.out.println("Successfully retrived!!!!"+ts.getStatus());
		}
		
	}
	

	@Test
	public void deleteTimesheetSubmissions() throws Exception{
		String id = "1";  
		timesheetSubmissionsService.deleteTimesheetSubmissions(id);
	}
	
	
	
	
	
	
}
