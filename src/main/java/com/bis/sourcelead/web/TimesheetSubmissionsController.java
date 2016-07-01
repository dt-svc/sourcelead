package com.bis.sourcelead.web;


import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

@RestController 
@RequestMapping("/TimesheetSubmissionsController")
public class TimesheetSubmissionsController {
	
	 
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;

	
	
	@RequestMapping(value = "/addTimesheetSubmissions", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions addTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 4);
		timesheetSubmissions.setStatus("Active");
		timesheetSubmissions.setCreatedBy("prasad");
		timesheetSubmissions.setUpdatedBy("vemket");
		
		TimesheetSubmissions savedTimesheetSubmissionss = timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
		return savedTimesheetSubmissionss; 
	}
	
	
	@RequestMapping(value = "/updateTimesheetSubmissions", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions updateTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 4);
		timesheetSubmissions.setStatus("reject");
		timesheetSubmissions.setCreatedBy("CIB");
		timesheetSubmissions.setUpdatedBy("BBBB");
		
		TimesheetSubmissions updtaeTimesheetSubmissionss = timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		return updtaeTimesheetSubmissionss; 
	}
	
	
/*	@RequestMapping(value = "/getTimesheetEntries", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@RequestParam String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}*/
	
	@RequestMapping(value = "/deleteTimesheetSubmissions/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetSubmissions(@PathVariable String id) throws Exception
			 
	{
		Boolean status = timesheetSubmissionsService.deleteTimesheetSubmissions(id);
		return status; 
	}
	
	
	@RequestMapping(value = "/getAllTimesheetSubmissions", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetSubmissions> getAllTimesheetSubmissions() throws Exception
			 
	{
		  Collection<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissions();
		  return timesheetSubmissionsList;
	}
     
	
 
}
