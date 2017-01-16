package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Tasks;

public interface TaskDao {

	Tasks saveTasks(Tasks tasks) throws Exception;
	
	Tasks getTasksById(Long id);

}
