package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.ProjectClients;

public interface ProjectClientService {

	ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId,Long orgLocationsId) throws Exception;
}
