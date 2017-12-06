package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.ProjectsDocumentFolderDTO;
import com.bis.sourcelead.entity.ProjectDocumentFolder;

public interface ProjectDocumentFolderService {

	ProjectDocumentFolder saveProjectDocumentFolder(ProjectDocumentFolder projectDocumentFolder) throws Exception;

	List<ProjectDocumentFolder>  getProjectDocumentFolderByProjectId(Long peojectId) throws Exception;
	
	String  saveProjectDocumentFolder(ProjectsDocumentFolderDTO projectsDocumentFolderDTO) throws Exception;
	
	ProjectDocumentFolder getProjectDocumentFolderByDocumentId(Long folderId)throws Exception;

}
