package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectDocumentFolder;
import com.bis.sourcelead.entity.ProjectDocuments;

public interface ProjectDocumentFloderDao {
	
	ProjectDocumentFolder saveProjectDocumentFolder(ProjectDocumentFolder projectDocumentFolder) throws Exception;

	List<ProjectDocumentFolder>  getProjectDocumentFolderByProjectId(Long peojectId) throws Exception;
	
	ProjectDocumentFolder getProjectDocumentFolderByDocumentId(Long folderId)throws Exception;
}
