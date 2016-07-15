package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.ProjectTasks;

public interface ProjectTasksDao {

	
    public ProjectTasks addTasks(ProjectTasks tasks)throws Exception;
	
    public ProjectTasks updateTasks(ProjectTasks tasks)throws Exception;
    
    public ProjectTasks getTasksId(String id)throws Exception;
    
    public Boolean deleteTask(String id)throws Exception;
    
    public List<ProjectTasks> getAllTasks()throws Exception;
    
    public Collection<ProjectTasks> getAllTasksByProjectId(Long projectId)throws Exception;
     
    public Collection	<ProjectTasks> getAllSourceleadTasks()throws Exception;
}