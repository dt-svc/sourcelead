package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectTaskComments;

public interface ProjectTaskCommentsDao {
	
	ProjectTaskComments saveProjectTaskComments(ProjectTaskComments projectTaskComments) throws Exception;

	List<ProjectTaskComments>  getProjectTaskCommentsByProjectTaskId(Long projectTaskId) throws Exception;
	
	
}
