package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ProjectTasks;

public interface ProjectTasksService {
	
public ProjectTasks addTasks(ProjectTasks tasks)throws Exception;
	
    public ProjectTasks updateTasks(ProjectTasks tasks)throws Exception;
    
    public ProjectTasks getTasksId(String id)throws Exception;
    
    public Boolean deleteTask(String id)throws Exception;
    
    public List<ProjectTasks> getAllTasks()throws Exception;
    
}