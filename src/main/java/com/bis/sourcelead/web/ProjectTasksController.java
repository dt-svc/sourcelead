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

import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.service.ProjectTasksService;

@RestController 
@RequestMapping("/ProjectTasks")
public class ProjectTasksController {
	
	 
	@Autowired
	public ProjectTasksService tasksService;
	
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks addTasks() throws Exception
			 
	{
		ProjectTasks  tasks  = new ProjectTasks();
		tasks.setTaskName("task1");
		tasks.setDescription("hi this is first description");
		tasks.setStatus("accept");
		tasks.setCreatedBy("raju");
		tasks.setCreatedDate(new Date());
		tasks.setUpdatedBy("prasad");
		tasks.setUpdatedDate(new Date());
		ProjectTasks savedtasks = tasksService.addTasks(tasks);
		return savedtasks; 
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks updateTasks() throws Exception
			 
	{
		ProjectTasks  tasks  = new ProjectTasks();
		tasks.setId(1l);
		tasks.setTaskName("task2");
		tasks.setDescription("hi this is first description");
		tasks.setStatus("accept");
		tasks.setCreatedBy("david");
		tasks.setCreatedDate(new Date());
		tasks.setUpdatedBy("laxman");
		tasks.setUpdatedDate(new Date());
		
		ProjectTasks updatetasks = tasksService.updateTasks(tasks);
		return updatetasks; 
	}
	
	
	@RequestMapping(value = "/getTasks/{id}", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks getTasksId(@PathVariable String id) throws Exception
			 
	{
		ProjectTasks tasks = tasksService.getTasksId(id);
		return tasks; 
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public @ResponseBody  Boolean deleteTask(@PathVariable String id) throws Exception
			 
	{
		
		Boolean status = tasksService.deleteTask(id);
		return status; 
	}
	
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public @ResponseBody Collection<ProjectTasks> getAllTasks() throws Exception
			 
	{
		  Collection<ProjectTasks> tasksList = tasksService.getAllTasks();
		  return tasksList;
	}
}
