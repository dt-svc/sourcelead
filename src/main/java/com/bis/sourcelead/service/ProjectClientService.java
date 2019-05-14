package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ProjectClients;

public interface ProjectClientService {

	ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId,Long orgLocationsId) throws Exception;
	
	List<ProjectClients> getProjectClientsListByProjectId(Long projectId) throws Exception;
	
	List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationId(Long orgLocationsId,Long userId) throws Exception;
	
	List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationIdForTimesheetApproval(Long locationsId, Long userId,String isGlobalUser) throws Exception;
	
	List<ProjectClients> getAllProjectClientsForExpensesByApproverUserAndOrgLocationId(Long userId,Long orgLocationsId,String isGlobalUser,String purpose) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(Long projectId,Long orgLocationsId,Long orgRelationId) throws Exception;
	
	ProjectClients getProjectClientsByUUID(String uuid) throws Exception;
	
	ProjectClients getProjectClientsByProjectID(Long projectId) throws Exception;
	
	public List<ProjectClients> getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted(Long orgLocationsId) throws Exception;
	
	ProjectClients getProjectClientsByProjectIdAndOrgId(Long projectId,Long orgId) throws Exception;
	
	List<Long> getProjectClientsByRelationId(Long orgRelationId) throws Exception;

}
