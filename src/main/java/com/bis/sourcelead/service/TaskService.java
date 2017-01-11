package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.Tasks;

public interface TaskService {
	public Tasks saveTasks(Tasks tasks) throws Exception;
	public Tasks getTasksById(int id);

}
