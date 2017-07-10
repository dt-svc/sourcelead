package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.model.ProjectDocumentBO;

public interface ProjectDocumentsService {
	
	public List<ProjectDocumentBO> findDocumentsByProjectId(long projectId);
	
	public ProjectDocumentBO findDocumentById(long porjectDocumentId);
	
	

}
