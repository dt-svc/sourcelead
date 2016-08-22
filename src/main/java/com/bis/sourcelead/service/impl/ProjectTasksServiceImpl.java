package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ProjectTasksServiceImpl implements ProjectTasksService {

	@Autowired
	private ProjectTasksDao tasksDao;

	@Override
	public ProjectTasks addTasks(ProjectTasks tasks) throws Exception {
		return tasksDao.addTasks(tasks);
	}

	@Override
	public ProjectTasks updateTasks(ProjectTasks tasks) throws Exception {
		return tasksDao.updateTasks(tasks);
	}

	@Override
	public ProjectTasks getTasksId(String id) throws Exception {
		return tasksDao.getTasksId(id);
	}

	@Override
	public Boolean deleteTask(String id) throws Exception {
		Boolean status = tasksDao.deleteTask(id);
		return status;
	}

	@Override
	public List<ProjectTasks> getAllTasks() throws Exception {
		List<ProjectTasks> list = tasksDao.getAllTasks();
		return list;
	}

	@Override
	public List<ProjectTasks> getAllTasksByProjectId(Long projectId) throws Exception {
		List<ProjectTasks> list = tasksDao.getAllTasksByProjectId(projectId);
		return list;
	}

	@Override
	public Collection<ProjectTasks> getAllSourceleadTasks() throws Exception {
		
		Collection<ProjectTasks> list = tasksDao.getAllSourceleadTasks();
		return list;
		
	}

	@Override
	public void saveAll(Collection<ProjectTasks> taskList) throws Exception {
		tasksDao.saveAll(taskList);
	}
	
	@Autowired
	 private CommonUtil commonUtil;
	
	@Autowired
	 private ProjectsService projectsService;
	

	@Override
	public String saveProjectTasksByProjectId(Long projectId, JSONObject jsonObj) throws Exception {
		String resultJson = null;
	 	String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
		int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);
		
		for (int c = 1; c < maxCount; c++) {
			ProjectTasks  tasks  = new ProjectTasks();
			if(jsonObj.has(c+"taskName")){
				tasks.setTaskName(jsonObj.getString(c+"taskName"));
				if(jsonObj.has(c+"taskDescription")){
				tasks.setDescription(jsonObj.getString(c+"taskDescription"));
				}
			tasks.setCreatedDate(new Date());
			tasks.setUpdatedDate(new Date());
			Projects projects = projectsService.getProjectById(projectId);
			tasks.setProjectId(projects);	
			
			ProjectTasks savedtasks = addTasks(tasks);
			}
		}
		Collection<ProjectTasks> taskList=getAllTasksByProjectId(projectId);
	 	
		 resultJson = commonUtil.toJSON(taskList);
		 return resultJson;
	}

	@Override
	public Set<ProjectTasks> prepareProjectTasksListByJson(JSONObject jsonObj, HttpServletRequest request,
			Projects projects) throws Exception {
		 
		    HashSet<ProjectTasks> projectTasksList = new HashSet<ProjectTasks>(); 
		    if(jsonObj.has("maxTsRowNum")){
			String maxAdminTimeTypeRowNum = jsonObj.getString("maxTsRowNum");
			int maxCount = Integer.parseInt(maxAdminTimeTypeRowNum);

			for (int c = 1; c < maxCount; c++) {
				ProjectTasks  tasks  = new ProjectTasks();
				if(jsonObj.has(c+"taskName")){
					if(jsonObj.has(c+"taskDescription")){
						tasks.setDescription(jsonObj.getString(c+"taskDescription"));
					}
						tasks.setTaskName(jsonObj.getString(c+"taskName"));
						tasks.setCreatedDate(new Date());
						tasks.setUpdatedDate(new Date());
						tasks.setProjectId(projects);
						projectTasksList.add(tasks);
			  }
			}
		    }
		   return projectTasksList;
		    		  
		
	}
	
	
	//by sarath 
	
	
	

}
