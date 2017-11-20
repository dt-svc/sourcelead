package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTaskActivityDao;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ProjectTaskActivityServiceImpl implements ProjectTaskActivityService{

	@Autowired
	ProjectTaskActivityDao projectTaskActivityDao;
	
	@Autowired
	 CommonUtil commonUtil;
	
	@Override
	public ProjectTaskActivity saveProjectTaskActivity(ProjectTaskActivity projectTaskActivity) throws Exception {
		return projectTaskActivityDao.saveProjectTaskActivity(projectTaskActivity);
	}

	@Override
	public List<ProjectTaskActivity> getProjectTaskActivityByProjectTaskId(Long projectTaskId) throws Exception {
		return projectTaskActivityDao.getProjectTaskActivityByProjectTaskId(projectTaskId);
	}

	
	
	@Override
	public void saveProjectTaskActivities(String activity, String activityDescription, ProjectTasks projectTasks, User user) throws Exception {
		ProjectTaskActivity projectTaskActivity = new ProjectTaskActivity();
		 projectTaskActivity.setProjecId(projectTasks.getProjectId());
		 projectTaskActivity.setProjectTaskId(projectTasks);
		 projectTaskActivity.setActivity(activity);
		 projectTaskActivity.setUserId(user);
		 projectTaskActivity.setCreatedUserName(user.getFullName());
		 projectTaskActivity.setActivityDescription(activityDescription);
		 projectTaskActivity.setCreatedDate(commonUtil.currentDate());
		 projectTaskActivityDao.saveProjectTaskActivity(projectTaskActivity);
	}

	@Override
	public void saveProjectTaskActivitiesByLookupData(String activity, String activityDescription, ProjectTasks projectTasks, User user, LookUpData data) throws Exception {
		ProjectTaskActivity projectTaskActivity = new ProjectTaskActivity();
		 projectTaskActivity.setProjecId(projectTasks.getProjectId());
		 projectTaskActivity.setProjectTaskId(projectTasks);
		 projectTaskActivity.setActivity(activity);
		 projectTaskActivity.setUserId(user);
		 projectTaskActivity.setTaskStatus(data);
		 projectTaskActivity.setCreatedUserName(user.getFullName());
		 projectTaskActivity.setActivityDescription(activityDescription);
		 projectTaskActivity.setCreatedDate(commonUtil.currentDate());
		 projectTaskActivityDao.saveProjectTaskActivity(projectTaskActivity);
		
	}
	
	@Override
	public void savePriorityProjectTaskActivitiesByLookupData(String activity, String activityDescription, ProjectTasks projectTasks, User user, LookUpData data) throws Exception {
		ProjectTaskActivity projectTaskActivity = new ProjectTaskActivity();
		 projectTaskActivity.setProjecId(projectTasks.getProjectId());
		 projectTaskActivity.setProjectTaskId(projectTasks);
		 projectTaskActivity.setActivity(activity);
		 projectTaskActivity.setUserId(user);
		 projectTaskActivity.setPriority(data);
		 projectTaskActivity.setCreatedUserName(user.getFullName());
		 projectTaskActivity.setActivityDescription(activityDescription);
		 projectTaskActivity.setCreatedDate(commonUtil.currentDate());
		 projectTaskActivityDao.saveProjectTaskActivity(projectTaskActivity);
		
	}
	
	
}
