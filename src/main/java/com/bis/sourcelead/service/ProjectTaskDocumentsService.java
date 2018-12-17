package com.bis.sourcelead.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.ProjectTaskDocumentDTO;
import com.bis.sourcelead.dto.ProjectTasksDTO;
import com.bis.sourcelead.entity.ProjectTaskDocuments;

public interface ProjectTaskDocumentsService {
	
    ProjectTaskDocuments updateProjectTaskDocuments(ProjectTaskDocuments projectDocuments) throws Exception;
	
    ProjectTaskDocuments getProjectTaskDocumentsById(Long projectTaskDocumentId)throws Exception;
    
    List<ProjectTaskDocuments> getProjectTaskDocumentsListByProjectIdId(Long projectId) throws Exception;

    ProjectTaskDocuments deleteProjectTaskDocuments(ProjectTaskDocuments projectDocuments) throws Exception;
    
	void viewProjectTaskDocument(Long documentId, HttpServletResponse response) throws Exception;

	void deleteProjectTaskDocumentByPorjectTaskDocumentId(Long porjectTaskDocumentId) throws Exception;

	void storeProjectTaskDocuments(ProjectTaskDocumentDTO projectTaskDocumentDTO)throws Exception;
}
