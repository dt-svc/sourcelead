package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectClients;

public interface ProjectClientDao {

    ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception;
	
    List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationId(Long locationsId,Long userId) throws Exception;
    
	ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId,Long orgLocationsId) throws Exception;
	
	List<ProjectClients> getProjectClientsListByProjectId(Long projectId) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(Long projectId,Long orgLocationsId,Long orgRelationId) throws Exception;
	
	ProjectClients getProjectClientsByUUID(String uuid) throws Exception;
}
