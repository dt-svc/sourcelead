package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ProjectsDocumentFolderDTO;
import com.bis.sourcelead.entity.ProjectDocumentPermission;

public interface ProjectDocumentPermissionService {

	ProjectDocumentPermission saveProjectDocumentPermission(ProjectDocumentPermission projectDocumentPermission) throws Exception;

	String  saveProjectDocumentPermissionWithDTO(ProjectsDocumentFolderDTO projectsDocumentFolderDTO) throws Exception;
	
	List<ProjectDocumentPermission> projectDocumentPermissionListByFolderIdAndAccessStatus(Long folderId,Long documentId, String accessStatus);
	
    List<Long> projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(Long folderId, Long documentId, String accessStatus) throws Exception;
	
	List<Long> projectDocumentPermissionMemberIdListByFolderIdAndAccessStatus(Long folderId,Long documentId, String accessStatus) throws Exception;
}
