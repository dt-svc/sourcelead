package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectTaskActivityDao;
import com.bis.sourcelead.dao.ProjectTaskCommentsDao;
import com.bis.sourcelead.dto.ProjectTaskDocumentDTO;
import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTaskComments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectTaskActivityService;
import com.bis.sourcelead.service.ProjectTaskCommentsService;
import com.bis.sourcelead.service.ProjectTasksService;
import com.bis.sourcelead.service.UserService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ProjectTaskCommentsServiceImpl implements ProjectTaskCommentsService{

	@Autowired
	ProjectTaskCommentsDao projectTaskCommentsDao;
	
	@Autowired
	 CommonUtil commonUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectTasksService projectTasksService;

	@Override
	public ProjectTaskComments saveProjectTaskComments(ProjectTaskComments projectTaskComments) throws Exception {
		return projectTaskCommentsDao.saveProjectTaskComments(projectTaskComments);
	}

	@Override
	public List<ProjectTaskComments> getProjectTaskCommentsByProjectTaskId(Long projectTaskId) throws Exception {
		return projectTaskCommentsDao.getProjectTaskCommentsByProjectTaskId(projectTaskId);
	}

	@Override
	public void storeprojectComments(ProjectTaskDocumentDTO projectTaskDocumentDTO) throws Exception {
		User user = userService.getUserById(Long.valueOf(projectTaskDocumentDTO.getLoginUserId()));
		ProjectTasks tasks = projectTasksService.getTasksId(projectTaskDocumentDTO.getProjectTaskId());
		ProjectTaskComments projectTaskComments = new ProjectTaskComments();
		projectTaskComments.setProjectTaskId(tasks);
		projectTaskComments.setComment(projectTaskDocumentDTO.getTaskComment());
		projectTaskComments.setCreatedUserName(user.getFullName());
		projectTaskComments.setUserId(user);
		projectTaskComments.setCreatedDate(commonUtil.currentDate());
		projectTaskCommentsDao.saveProjectTaskComments(projectTaskComments);
	}
}
