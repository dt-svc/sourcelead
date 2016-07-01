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
import com.bis.sourcelead.dao.TimesheetEntriesDao;
import com.bis.sourcelead.dao.impl.TimesheetEntriesDaoImpl;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.service.impl.TimesheetEntriesServiceImpl;

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
		@Bean
		public TimesheetEntriesDao timesheetEntriesDao() {
			return new TimesheetEntriesDaoImpl();
		}	

	}
	
	@Autowired
	TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	TimesheetEntriesDao timesheetEntriesDao;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Test
	public void addTimesheetSubmissions() throws Exception{
		
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		timesheetEntries.setId((long) 1);
		timesheetEntries.setStatus("agredd");
		timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		System.out.println("Successfully Added!!!!");
	}
	
	@Test
	public void updateTimesheetEntries() throws Exception{
		
		
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		timesheetEntries.setId((long) 1);
		timesheetEntries.setStatus("updated");
		timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		System.out.println("Successfully Added!!!!");
	}
	
	@Test
	public void getTimesheetEntries() throws Exception{
		
		String id = "1";
		TimesheetEntries timesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		System.out.println("Successfully retrived!!!!"+timesheetEntries.getStatus());
	}
	 
	
	@Test
	public void getAllTimesheetEntries() throws Exception{
		
		Collection<TimesheetEntries> timesheetEntries = timesheetEntriesService.getAllTimesheetEntries();
		for(TimesheetEntries  tm : timesheetEntries ){
			System.out.println("Successfully retrived!!!!"+tm.getStatus());
		}
		
	}
	

	@Test
	public void deleteTimesheetEntries() throws Exception{
		String id = "1";  
		timesheetEntriesService.deleteTimesheetEntries(id);
	}
	
	
	
}
