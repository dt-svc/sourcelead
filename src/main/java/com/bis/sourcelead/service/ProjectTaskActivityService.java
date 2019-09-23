package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;

public interface ProjectTaskActivityService {

	ProjectTaskActivity saveProjectTaskActivity(ProjectTaskActivity projectTaskActivity) throws Exception;

	List<ProjectTaskActivity>  getProjectTaskActivityByProjectTaskId(Long projectTaskId) throws Exception;

	void saveProjectTaskActivities(String activity, String activityDescription, ProjectTasks projectTasks, User user)throws Exception;
	
	void saveProjectTaskActivitiesByLookupData(String activity, String activityDescription, ProjectTasks projectTasks, User user,LookUpData  data)throws Exception;
	
	void savePriorityProjectTaskActivitiesByLookupData(String activity, String activityDescription, ProjectTasks projectTasks, User user,LookUpData  data)throws Exception;
	
    List<ProjectTaskActivity> getTasksActitivtyListByLookupIdAndCategoryType(Long lookupId, String taskType) throws Exception;

}
