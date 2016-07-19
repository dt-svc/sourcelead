package com.bis.sourcelead.service;

import java.util.Collection;

import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.Projects;

public interface ProjectsService {

    public Projects saveProject(Projects projects) throws Exception;
	
	public Projects updateProject(Projects projects) throws Exception;
	
	public Projects getProjectById(Long id) throws Exception;
	
	Collection<Projects> getProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
    Projects getPrimaryProjectByOrgLocationsId(Long orgLocationsId) throws Exception;
	
	public Boolean deleteProjectById(Long id) throws Exception;
	
	public Boolean validateProjectSequeneNumber(String projectSequeneNumber, Long orgLocationsId) throws Exception;
	
	public Integer projectIdValidation(Long id) throws Exception;
	
	Collection<Projects> getAllProjects() throws Exception;
	
	Collection<Projects> getAllProjectsAndManagersByLoginUserId(Long userId) throws Exception;
	
	Collection<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId,Long locationId) throws Exception;

}
