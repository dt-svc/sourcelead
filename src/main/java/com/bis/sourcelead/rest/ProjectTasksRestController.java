package com.bis.sourcelead.rest;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.service.ProjectTasksService;
@RestController 
@RequestMapping("/ProjectTasks")
public class ProjectTasksRestController {
	@Autowired
	public ProjectTasksService tasksService;
	/**
	 * @author David sammeta
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks addTasks() throws Exception
	{
		ProjectTasks  tasks  = new ProjectTasks();
		return tasksService.addTasks(tasks);
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public @ResponseBody  ProjectTasks updateTasks() throws Exception
			 
	{
		ProjectTasks  tasks  = new ProjectTasks();
		return tasksService.updateTasks(tasks);
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
