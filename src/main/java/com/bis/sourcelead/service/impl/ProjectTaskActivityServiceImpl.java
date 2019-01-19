package com.bis.sourcelead.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectTaskActivityDao;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.Timezone;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.TimeZoneService;
import com.bis.sourcelead.util.CalendarUtil;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ProjectTaskActivityServiceImpl implements ProjectTaskActivityService{

	@Autowired
	ProjectTaskActivityDao projectTaskActivityDao;
	
	@Autowired
	 CommonUtil commonUtil;
	
	@Autowired
	TimeZoneService timeZoneService;
	
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
		 projectTaskActivity.setCreatedDate(CalendarUtil.getUTCDateTimeForGivenDate(new Date(), projectTasks.getProjectId().getOrgLocationsId().getDateFormat()));
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
		 projectTaskActivity.setCreatedDate(CalendarUtil.getUTCDateTimeForGivenDate(new Date(), projectTasks.getProjectId().getOrgLocationsId().getDateFormat()));
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
		 projectTaskActivity.setCreatedDate(CalendarUtil.getUTCDateTimeForGivenDate(new Date(), projectTasks.getProjectId().getOrgLocationsId().getDateFormat()));
		 projectTaskActivityDao.saveProjectTaskActivity(projectTaskActivity);
		
	}

	@Override
	public List<ProjectTaskActivity> getTasksActitivtyListByLookupIdAndCategoryType(Long lookupId, String taskType)throws Exception {
		return projectTaskActivityDao.getTasksActitivtyListByLookupIdAndCategoryType(lookupId, taskType);
	}
	
	
}
