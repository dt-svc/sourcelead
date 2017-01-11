package com.bis.sourcelead.service.impl;

import com.bis.sourcelead.entity.Tasks;

public interface TasksDao {

	public Tasks saveTasks(Tasks tasks) throws Exception;
	public Tasks getTasksById(int id);
}
