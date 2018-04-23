package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;

public interface ProjectDocumentFloderDao {
	
	ProjectDocumentFolder saveProjectDocumentFolder(ProjectDocumentFolder projectDocumentFolder) throws Exception;

	List<ProjectDocumentFolder>  getProjectDocumentFolderByProjectId(Long peojectId,Long roleId,String userRoleType,Long candidateId) throws Exception;
	
	ProjectDocumentFolder getProjectDocumentFolderByDocumentId(Long folderId)throws Exception;
	
	ProjectDocumentFolder getProjectDocumentFolderByProjectId(Long projectId)throws Exception;
	
	ProjectDocumentFolder validateProjectDocumentFolderByFolderIdAndFolderName(Long folder,String folderName) throws Exception;

	void deleteProjectFolderByFolderId(Long folderId)throws Exception;
}
