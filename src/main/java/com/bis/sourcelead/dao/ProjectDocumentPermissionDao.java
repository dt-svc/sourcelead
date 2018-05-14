package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectDocumentPermission;

public interface ProjectDocumentPermissionDao {
	
	/**
	 * @author Laxman
	 * @param department
	 * @return
	 * @throws Exception
	 */
	ProjectDocumentPermission saveProjectDocumentPermission(ProjectDocumentPermission projectDocumentPermission) throws Exception;
	
	List<ProjectDocumentPermission> projectDocumentPermissionListByFolderIdAndAccessStatus(Long folderId,Long documentId,String accessStatus);
	
	List<Long> projectDocumentPermissionRoleIdListByFolderIdAndAccessStatus(Long folderId, Long documentid,String accessStatus) throws Exception;
	
	List<Long> projectDocumentPermissionMemberIdListByFolderIdAndAccessStatus(Long folderId, Long documentid,String accessStatus) throws Exception;
	
	void deleteAllProjectDocumentPermissionByFoderIdAndRoleIdAndAccess(Long foderId,List<Long> roleIds,String access) throws Exception;
	
	void deleteAllProjectDocumentPermissionByFoderIdAndMemberAndAccess(Long foderId,List<Long> memberId,String access) throws Exception;
	
	void deleteAllProjectDocumentByFoderId(Long foderId) throws Exception;
	
	void deleteAllProjectDocumentByDocumentId(Long documentId) throws Exception;

	ProjectDocumentPermission getDocumentPermissionByDocumentId(Long documentId,Long projectMemberId)throws Exception;
}
