package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.Date;

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
import com.bis.sourcelead.dao.TasksDao;
import com.bis.sourcelead.dao.impl.TasksDaoImpl;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.service.impl.TasksServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfig.class, loader = AnnotationConfigWebContextLoader.class)
public class TasksServiceTest {

	
	@Configuration
	static class TasksServiceConfigTest {
		@Bean
		public TasksService tasksService() {
			return new TasksServiceImpl();
		}
		@Bean
		public TasksDao tasksDao() {
			return new TasksDaoImpl();
		}	

	}
	
	@Autowired
	TasksService tasksService;
	
	@Autowired
	TasksDao tasksDao;

	private static final Logger logger = LoggerFactory.getLogger(TasksServiceTest.class);

	@Test
	public void addTasks() throws Exception{
		Tasks tasks = new Tasks();
		tasks.setId(3l);
		tasks.setTaskName("timesheet task");
		tasks.setDescription("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		tasks.setStatus("Reject");
		tasks.setCreatedBy("Balu");
		tasks.setCreatedDate(new Date());
		tasks.setUpdatedBy("santhu");
		tasks.setUpdatedDate(new Date());
		tasksService.addTasks(tasks);
		logger.info("Successfully Added!!!!");
	}
	
	//@Test
	public void updateTimesheetEntries() throws Exception{
		
		
		Tasks tasks = new Tasks();
		tasks.setId(2l);
		tasks.setTaskName("timesheet task");
		tasks.setDescription("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		tasks.setStatus("Reject");
		tasks.setCreatedBy("Balu");
		tasks.setCreatedDate(new Date());
		tasks.setUpdatedBy("santhu");
		tasks.setUpdatedDate(new Date());
		tasksService.updateTasks(tasks);
		System.out.println("Successfully Updated!!!!");
	}
	
	//@Test
	public void getTimesheetEntries() throws Exception{
		
		String id = "1";
		Tasks tasks = tasksService.getTasksId(id);
		System.out.println("Successfully retrived!!!!"+tasks.getStatus());
	}
	 
	
	//@Test
	public void getAllTasks() throws Exception{
		
		Collection<Tasks> tasks = tasksService.getAllTasks();
		for(Tasks  task : tasks ){
			System.out.println("Successfully retrived!!!!"+task.getStatus());
		}
		
	}
	

	//@Test
	public void deleteTasks() throws Exception{
		String id = "1";  
		tasksService.deleteTask(id);
	}
	
	
	
}
