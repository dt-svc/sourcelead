package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ProjectTaskDocumentDTO;
import com.bis.sourcelead.dto.TaskDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectTaskComments;

public interface ProjectTaskCommentsService {

	ProjectTaskComments saveProjectTaskComments(ProjectTaskComments projectTaskComments) throws Exception;

	List<ProjectTaskComments>  getProjectTaskCommentsByProjectTaskId(Long projectTaskId) throws Exception;

	void storeprojectComments(ProjectTaskDocumentDTO projectTaskDocumentDTO, TaskDTO taskDTO, OrgLocations loginOrgLocation)throws Exception;
	
}
