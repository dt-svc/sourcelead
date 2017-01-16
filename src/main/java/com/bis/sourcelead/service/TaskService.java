package com.bis.sourcelead.service;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Tasks;

public interface TaskService {

	Tasks saveTasks(Tasks tasks) throws Exception;
	Tasks getTasksById(Long id);
	
	String  saveTasks(JSONObject jsonObj) throws Exception;
}
