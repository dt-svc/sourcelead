package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.dto.ProjectsDocumentFolderDTO;
import com.bis.sourcelead.entity.Department;
import com.bis.sourcelead.entity.ProjectDocumentFolder;

public interface ProjectDocumentFolderService {

	ProjectDocumentFolder saveProjectDocumentFolder(ProjectDocumentFolder projectDocumentFolder) throws Exception;

	List<ProjectDocumentFolder>  getProjectDocumentFolderByProjectId(Long peojectId,Long roleId,String UserRoleType,Long candidateId) throws Exception;
	
	String  saveProjectDocumentFolder(ProjectsDocumentFolderDTO projectsDocumentFolderDTO) throws Exception;
	
	ProjectDocumentFolder getProjectDocumentFolderByDocumentId(Long folderId)throws Exception;
	
	ProjectDocumentFolder getProjectDocumentFolderByProjectId(Long projectId)throws Exception;
	
	ProjectDocumentFolder validateProjectDocumentFolderByFolderIdAndFolderName(Long folder,String folderName) throws Exception;

	void deleteProjectFolderByFolderId(Long folderId)throws Exception;
	
}