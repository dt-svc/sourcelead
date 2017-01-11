package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Tasks;
import com.bis.sourcelead.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	TasksDao tasksDao;

	@Override
	public Tasks saveTasks(Tasks tasks) throws Exception {
		return tasksDao.saveTasks(tasks);
	}

	@Override
	public Tasks getTasksById(int id) {
		return tasksDao.getTasksById(id);
	}

}
