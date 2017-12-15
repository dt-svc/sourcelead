package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.dto.ProjectsDocumentsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.model.ProjectDocumentBO;

public interface ProjectDocumentsService {
	
	public List<ProjectDocumentBO> findDocumentsByProjectId(long projectId);
	
	public ProjectDocumentBO findDocumentById(long porjectDocumentId);
	
	public ProjectDocuments updateProjectDocuments(ProjectDocuments projectDocuments) throws Exception;
	
	public List<ProjectDocuments> getProjectDocumentsListByProjectIdId(Long projectId) throws Exception;

	ProjectDocuments getDocumentById(Long porjectDocumentId)throws Exception;
	
	ProjectDocuments storeProjectDocuments(JSONObject jsonObj, Long projectId, MultipartFile multiFile,Long documentId) throws JSONException, Exception;
	
	ProjectDocuments storeProjectDocuments(ProjectsDocumentsDTO projectsDocumentsDTO) throws Exception;
	
    List<ProjectDocuments> getEachProjectDocumentsListByProjectIdIdAndFolderId(Long projectId,Long userRoleId,String userRoleType,Long folderId,Long candidateId) throws Exception;
     
    List<ProjectDocuments> getDeleteProjectDocumentsListByDocumentId(String documentid) throws Exception;

	Boolean validateProjectDocumentFolderNameByProjectIdIdAndFolderName(Long projectId,String folderName) throws Exception;
	
	Boolean validateProjectDocumentNameByProjectIdIdAndDocumentName(Long projectId,String documentName) throws Exception;
	
	ProjectDocuments sendProjectDocumentEmail(ProjectsDocumentsDTO projectsDocumentsDTO, User user, OrgLocations loginOrgLocation)throws Exception;

}
