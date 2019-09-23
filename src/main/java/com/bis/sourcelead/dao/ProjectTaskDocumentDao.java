package com.bis.sourcelead.dao;
import java.util.List;

import com.bis.sourcelead.entity.ProjectTaskDocuments;

public interface ProjectTaskDocumentDao {
	     ProjectTaskDocuments updateProjectTaskDocuments(ProjectTaskDocuments projectDocuments) throws Exception;

	     List<ProjectTaskDocuments> getProjectTaskDocumentsListByProjectIdId(Long projectId) throws Exception;
	     
	     ProjectTaskDocuments getProjectTaskDocumentsById(Long projectTaskDocumentId)throws Exception;
	     
	     ProjectTaskDocuments deleteProjectTaskDocuments(ProjectTaskDocuments projectDocuments) throws Exception;
}
