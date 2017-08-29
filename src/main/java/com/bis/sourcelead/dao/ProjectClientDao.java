package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.ProjectClients;

public interface ProjectClientDao {

    ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId,Long orgLocationsId) throws Exception;
}
