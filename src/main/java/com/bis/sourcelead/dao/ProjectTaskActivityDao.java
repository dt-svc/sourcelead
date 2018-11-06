package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTasks;

public interface ProjectTaskActivityDao {
	
	ProjectTaskActivity saveProjectTaskActivity(ProjectTaskActivity projectTaskActivity) throws Exception;

	List<ProjectTaskActivity>  getProjectTaskActivityByProjectTaskId(Long projectTaskId) throws Exception;
	
    List<ProjectTaskActivity> getTasksActitivtyListByLookupIdAndCategoryType(Long lookupId, String taskType) throws Exception;
}
