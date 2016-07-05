package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Tasks;

public interface TasksService {
	
public Tasks addTasks(Tasks tasks)throws Exception;
	
    public Tasks updateTasks(Tasks tasks)throws Exception;
    
    public Tasks getTasksId(String id)throws Exception;
    
    public Boolean deleteTask(String id)throws Exception;
    
    public List<Tasks> getAllTasks()throws Exception;
    
}