package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectTaskActivity;

public interface ProjectTaskActivityDao {
	
	ProjectTaskActivity saveProjectTaskActivity(ProjectTaskActivity projectTaskActivity) throws Exception;

	List<ProjectTaskActivity>  getProjectTaskActivityByProjectTaskId(Long projectTaskId) throws Exception;
	
	
}
