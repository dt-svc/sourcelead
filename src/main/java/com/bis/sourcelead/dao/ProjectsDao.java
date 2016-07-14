package com.bis.sourcelead.dao;

import java.util.Collection;

import com.bis.sourcelead.entity.Projects;

public interface ProjectsDao {

	public Projects saveProject(Projects projects) throws Exception;
	
	public Projects updateProject(Projects projects) throws Exception;
	
	public Projects getProjectById(Long id) throws Exception;
	
	Collection<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	public Boolean deleteProjectById(Long id) throws Exception;
	
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception;
	
	public Integer projectIdValidation(Long projectId) throws Exception;
	
	Collection<Projects> getAllProjects() throws Exception;
	
	Collection<Projects> getAllProjectsAndManagers() throws Exception;
	
	
}
