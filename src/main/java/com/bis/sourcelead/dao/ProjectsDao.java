package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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
	
	Collection<Projects> getAllProjectsAndManagersByLoginUserId(Long userId) throws Exception;
	
	Collection<Projects> getAllClientProjectsByOrgRelationId(Long orgRelationId, Long locationId) throws Exception;

	public Integer updateStartDateOfProjectId(Date startDate, Long projectId);
	
	public Integer updateEndDateOfProjectId(Date endDate,Long projectId);

	List<Long> getApprovarAssignedProjects(Long userId,Long orgLocationId) throws Exception;
	
	public Projects getProjectByProjectSequenceNumber(String projectSequeneNumber) throws Exception;

	Collection<Projects> getProjectsBasedOnClientsAndStatusSelectionInFilters(Long userId,String clients,String status) throws Exception;

	public Collection<Projects> getProjectByOrgLocationsIdAndUserId(Long id, Long userId);

}
