package com.bis.sourcelead.web;


import java.util.Collection;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.entity.TimesheetEntries;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.TasksService;
import com.bis.sourcelead.service.TimesheetEntriesService;

@RestController 
public class TimesheetEntriesController {
	
	 
	@Autowired
	public TimesheetEntriesService timesheetEntriesService;
	
	@Autowired
	public ProjectsService projectsService;
	
	@Autowired
	public TasksService tasksService;
	
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;


	@RequestMapping(value = "/timesheet")
	public ModelAndView registration() throws Exception {
			ModelAndView model = new ModelAndView();
			Collection<Projects> projectsList =   projectsService.getAllProjects();
			model.addObject("sourcelead_projectsList", projectsList);
			
			Collection<Tasks> tasksList = tasksService.getAllTasks();
			model.addObject("sourcelead_tasksList", tasksList);
			
			model.setViewName("timesheet");
			return model;

	}
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries addTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		
		Tasks task = new Tasks();
		task.setId(1l);
		timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		//timesheetEntries.setId((long) 4);
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("X");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad k");
		timesheetEntries.setUpdatedBy("naidu l");
		timesheetEntries.setUpdatedDate(new Date());
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.addTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries updateTimesheetEntries() throws Exception
			 
	{
		TimesheetEntries timesheetEntries = new TimesheetEntries();
		TimesheetSubmissions timesheetSubmissions = new TimesheetSubmissions();
		timesheetSubmissions.setId(1l);
		timesheetEntries.setTimesheetSubmissions(timesheetSubmissions);
		timesheetEntries.setId(1l);
		
		Tasks task = new Tasks();
		task.setId(1l);
		
		timesheetEntries.setStartDate(new Date());
		timesheetEntries.setEndDate(new Date());
		timesheetEntries.setTotalHours(5);
		timesheetEntries.setSubmittedHours(10);
		timesheetEntries.setEntryStatus("A");
		timesheetEntries.setMonTask(task);
		timesheetEntries.setMonHours(15);
		timesheetEntries.setMoncomments("some how");
		timesheetEntries.setTueTask(task);
		timesheetEntries.setTueHours(5);
		timesheetEntries.setTuecomments("nothing");
		timesheetEntries.setWedTask(task);
		timesheetEntries.setWedHours(10);
		timesheetEntries.setWedcomments("some alone");
		timesheetEntries.setThuTask(task);
		timesheetEntries.setThuHours(12);
		timesheetEntries.setThucomments("every day");
		timesheetEntries.setFriTask(task);
		timesheetEntries.setFriHours(15);
		timesheetEntries.setFricomments("all");
		timesheetEntries.setSatTask(task);
		timesheetEntries.setSatHours(16);
		timesheetEntries.setSatcomments("saturady no comments");
		timesheetEntries.setSunTask(task);
		timesheetEntries.setSunHours(10);
		timesheetEntries.setSuncomments("sleep");
		timesheetEntries.setEntryType("open");
		timesheetEntries.setStatus("rejected");
		timesheetEntries.setCreatedDate(new Date());
		timesheetEntries.setCreatedBy("prsad");
		timesheetEntries.setUpdatedBy("naidu");
		timesheetEntries.setUpdatedDate(new Date());
		
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.updateTimesheetEntries(timesheetEntries);
		return savedTimesheetEntries; 
	}
	
	
	@RequestMapping(value = "/getTimesheetEntries/{id}", method = RequestMethod.GET)
	public @ResponseBody  TimesheetEntries getTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		TimesheetEntries savedTimesheetEntries = timesheetEntriesService.getTimesheetEntries(id);
		return savedTimesheetEntries; 
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTimesheetEntries(@PathVariable String id) throws Exception
			 
	{
		
		Boolean status = timesheetEntriesService.deleteTimesheetEntries(id);
		return status; 
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> getAllTimesheetEntries() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
	
	
	@RequestMapping(value = "/saveAll", method = RequestMethod.GET)
	public @ResponseBody Collection<TimesheetEntries> saveAll() throws Exception
			 
	{
		  Collection<TimesheetEntries> timesheetEntriesList = timesheetEntriesService.getAllTimesheetEntries();
		  return timesheetEntriesList;
	}
     
	
	 
	@RequestMapping(value = "/getSum", method = RequestMethod.GET)
	public @ResponseBody String getDetails(@RequestParam String valueOne)
			 
	{
		 System.out.println("hlo1");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

	
	@RequestMapping(value = "/getSum", method = RequestMethod.POST)
	public @ResponseBody String getDetailss(@RequestBody String valueOne)
			 
	{
		 System.out.println("hlo2");
		 Integer sum = 0;
		 String[] tokens = valueOne.split(",");
         for(String s:tokens){
            sum = sum + Integer.parseInt(s);
         }
		 Integer result = sum ;
		 System.out.println("result"+result);
 	     return result.toString();
	}

	
	 
 
}
