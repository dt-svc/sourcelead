package com.bis.sourcelead.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectTasksDao;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.service.ProjectTasksService;

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
	public Collection<ProjectTasks> getAllTasksByProjectId(Long projectId) throws Exception {
		// TODO Auto-generated method stub
		Collection<ProjectTasks> list = tasksDao.getAllTasksByProjectId(projectId);
		return list;
	}
	
	
	

}
