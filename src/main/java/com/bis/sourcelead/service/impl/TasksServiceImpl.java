package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.TasksDao;
import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.service.TasksService;

@Service
public class TasksServiceImpl implements TasksService {

	@Autowired
	private TasksDao tasksDao;

	@Override
	public Tasks addTasks(Tasks tasks) throws Exception {
		return tasksDao.addTasks(tasks);
	}

	@Override
	public Tasks updateTasks(Tasks tasks) throws Exception {
		return tasksDao.updateTasks(tasks);
	}

	@Override
	public Tasks getTasksId(String id) throws Exception {
		return tasksDao.getTasksId(id);
	}

	@Override
	public Boolean deleteTask(String id) throws Exception {
		Boolean status = tasksDao.deleteTask(id);
		return status;
	}

	@Override
	public List<Tasks> getAllTasks() throws Exception {
		List<Tasks> list = tasksDao.getAllTasks();
		return list;
	}
	

}
