package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ProjectClients;

public interface ProjectClientService {

	ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId,Long orgLocationsId) throws Exception;
	
	List<ProjectClients> getProjectClientsListByProjectId(Long projectId) throws Exception;
}
