package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ProjectTaskActivity;
import com.bis.sourcelead.entity.ProjectTaskComments;
import com.bis.sourcelead.entity.ProjectTasks;
import com.bis.sourcelead.entity.User;

public interface ProjectTaskCommentsService {

	ProjectTaskComments saveProjectTaskComments(ProjectTaskComments projectTaskComments) throws Exception;

	List<ProjectTaskComments>  getProjectTaskCommentsByProjectTaskId(Long projectTaskId) throws Exception;

	void storeprojectComments(String comment, ProjectTasks tasks, User user)throws Exception;
	
}
