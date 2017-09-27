package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ProjectClients;

public interface ProjectClientService {

	ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId,Long orgLocationsId) throws Exception;
	
	List<ProjectClients> getProjectClientsListByProjectId(Long projectId) throws Exception;
	
	List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationId(Long orgLocationsId,Long userId) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(Long projectId,Long orgLocationsId,Long orgRelationId) throws Exception;
	
	ProjectClients getProjectClientsByUUID(String uuid) throws Exception;
}
