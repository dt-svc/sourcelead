package com.bis.sourcelead.web;


import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.TimesheetSubmissionsService;

@RestController 
@RequestMapping("/TimesheetSubmissions")
public class TimesheetSubmissionsController {
	
	 
	@Autowired
	public TimesheetSubmissionsService timesheetSubmissionsService;
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;

	
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions addTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		timesheetSubmissions.setSubmissionStatus("ok");
		timesheetSubmissions.setStatus("Active");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("prasad");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("venket");
		
		TimesheetSubmissions savedTimesheetSubmissionss = timesheetSubmissionsService.addTimesheetSubmissions(timesheetSubmissions);
		return savedTimesheetSubmissionss; 
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetSubmissions updateTimesheetSubmissions() throws Exception
			 
	{
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId((long) 1);
		ProjectMembers projectMembers = new ProjectMembers();
		projectMembers.setId(1l);
		timesheetSubmissions.setProjectMembers(projectMembers);
		timesheetSubmissions.setStartDate(new Date());
		timesheetSubmissions.setEndDate(new Date());
		timesheetSubmissions.setSubmissionStatus("False");
		timesheetSubmissions.setStatus("Reject");
		timesheetSubmissions.setCreatedDate(new Date());
		timesheetSubmissions.setCreatedBy("Sri");
		timesheetSubmissions.setUpdatedDate(new Date());
		timesheetSubmissions.setUpdatedBy("Sai");
		
		TimesheetSubmissions updtaeTimesheetSubmissionss = timesheetSubmissionsService.updateTimesheetSubmissions(timesheetSubmissions);
		return updtaeTimesheetSubmissionss; 
	}
	
	
/*	@RequestMapping(value = "/getTimesheetEntries", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@RequestParam String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}*/
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetSubmissions(@PathVariable String id) throws Exception
			 
	{
		Boolean status = timesheetSubmissionsService.deleteTimesheetSubmissions(id);
		return status; 
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetSubmissions> getAllTimesheetSubmissions() throws Exception
			 
	{
		  Collection<TimesheetSubmissions> timesheetSubmissionsList = timesheetSubmissionsService.getAllTimesheetSubmissions();
		  return timesheetSubmissionsList;
	}
     
	
 
}
