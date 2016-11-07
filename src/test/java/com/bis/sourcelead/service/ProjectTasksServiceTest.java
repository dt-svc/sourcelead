package com.bis.sourcelead.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bis.sourcelead.WebAppConfig;
import com.bis.sourcelead.SecurityConfig;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebAppConfig.class, SecurityConfig.class}, loader = AnnotationConfigWebContextLoader.class)
public class ProjectTasksServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ProjectTasksServiceTest.class);
	
	@Autowired
	ProjectTasksService projectTasksService;
	
		@Test
		public void addTasks() throws Exception	{
			ProjectTasks projectTask = new ProjectTasks();
			projectTask.setId(1l);		
			Projects project = new Projects();
			project.setId(1l);
			projectTask.setProjectId(project);
			
			projectTask.setTaskName("BusinessIntelliTask");
			projectTask.setCreatedBy("shivayogi");
			projectTask.setDescription("Description");
			projectTask.setCreatedDate(new Date());
			projectTask.setUpdatedBy("shiv");
			projectTask.setUpdatedDate(new Date());
			projectTask.setStatus("Active");
			projectTasksService.addTasks(projectTask);
			logger.info("addtask added successfully");
		}
		@Test
		public void saveAll() throws Exception{
			Collection<ProjectTasks> taskList = new ArrayList<ProjectTasks>();
			taskList.addAll(taskList);
			projectTasksService.saveAll(taskList);
			logger.info("saveAll added successfully");
			
		}
		
		@Test
		public void updateTasks() throws Exception{
			ProjectTasks projecttask = new ProjectTasks();
			projecttask.setId(1l);
			
			Projects projectId = new Projects();
			projectId.setId(1l);
			projecttask.setProjectId(projectId);
			
			projecttask.setTaskName("Business");
			projecttask.setCreatedBy("yogi");
			projecttask.setDescription("Desc");
			projecttask.setCreatedDate(new Date());
			projecttask.setUpdatedBy("shiv");
			projecttask.setUpdatedDate(new Date());
			projecttask.setStatus("Active");
			projectTasksService.updateTasks(projecttask);
			logger.info("updateservice added successfully");
		}
		@Test
		public void getTasksId() throws Exception{
			ProjectTasks taskid = projectTasksService.getTasksId("1");
			logger.info("getTasksId working successfully"+taskid);
		}
		@Test
		public void deleteTask() throws Exception{
			boolean deletetask = projectTasksService.deleteTask("1");
			logger.info("Deleted task successfully"+ deletetask);
		}
		@Test
		public void getAllTasks() throws Exception{
			List<ProjectTasks> alltask = projectTasksService.getAllTasks();
			logger.info("getAll task successfully working"+alltask.size());

		}
		@Test
		public void getAllTasksByProjectId() throws Exception{
			Collection<ProjectTasks> AllTasksByProjectId = projectTasksService.getAllTasksByProjectId(1l);
			logger.info("getAll task by id successfully working"+AllTasksByProjectId.size());
		}
		@Test
		public void getAllSourceleadTasks() throws Exception{
			Collection<ProjectTasks> AllSourceleadTasks = projectTasksService.getAllSourceleadTasks();
			logger.info("getAll task sourceleadTasks successfully working"+ AllSourceleadTasks.size());
		}
}