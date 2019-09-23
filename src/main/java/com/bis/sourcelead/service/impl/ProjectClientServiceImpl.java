package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectClientDao;
import com.bis.sourcelead.entity.ProjectClients;
import com.bis.sourcelead.service.ProjectClientService;

@Service
public class ProjectClientServiceImpl implements ProjectClientService {
	
	@Autowired
	ProjectClientDao projectClientDao;
	

	@Override
	public ProjectClients storeProjectClients(ProjectClients projectClients) throws Exception {
		return projectClientDao.storeProjectClients(projectClients);
	}

	@Override
	public ProjectClients getProjectClientsByProjectIdAndOrgLocationId(Long projectId, Long orgLocationsId)
			throws Exception {
		return projectClientDao.getProjectClientsByProjectIdAndOrgLocationId(projectId, orgLocationsId);
	}

	@Override
	public List<ProjectClients> getProjectClientsListByProjectId(Long projectId) throws Exception {
		return projectClientDao.getProjectClientsListByProjectId(projectId);
	}

	@Override
	public ProjectClients getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(Long projectId,
			Long orgLocationsId, Long orgRelationId) throws Exception {
		return projectClientDao.getProjectClientsByProjectIdAndOrgLocationIdAndOrgRelationId(projectId, orgLocationsId, orgRelationId);
	}

	@Override
	public ProjectClients getProjectClientsByUUID(String uuid) throws Exception {
		return projectClientDao.getProjectClientsByUUID(uuid);
	}

	@Override
	public List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationId(Long locationsId, Long userId)
			throws Exception {
		return projectClientDao.getProjectClientsListByLoginUserAndOrgLocationId(locationsId, userId);
	}

	@Override
	public List<ProjectClients> getAllProjectClientsForExpensesByApproverUserAndOrgLocationId(Long userId, Long locationsId,String isGlobalUser,String purpose)
			throws Exception {
		return projectClientDao.getAllProjectClientsForExpensesByApproverUserAndOrgLocationId(userId, locationsId,isGlobalUser,purpose);
	}

	@Override
	public ProjectClients getProjectClientsByProjectID(Long projectId) throws Exception
	{
		return projectClientDao.getProjectClientsByProjectID(projectId);
	}

	@Override
	public List<ProjectClients> getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted(Long orgLocationsId) throws Exception
	{
		return projectClientDao.getAllProjectsByOrgLocationIdAndStatusInProgressAndNotStarted((orgLocationsId));
	}

	@Override
	public ProjectClients getProjectClientsByProjectIdAndOrgId(Long projectId, Long orgId) throws Exception {
		return projectClientDao.getProjectClientsByProjectIdAndOrgId(projectId, orgId);
	}

	@Override
	public List<Long> getProjectClientsByRelationId(Long orgRelationId) throws Exception {
		return projectClientDao.getProjectClientsByRelationId(orgRelationId);
	}

	@Override
	public List<ProjectClients> getProjectClientsListByLoginUserAndOrgLocationIdForTimesheetApproval(Long locationsId,
			Long userId, String isGlobalUser) throws Exception {
		return projectClientDao.getProjectClientsListByLoginUserAndOrgLocationIdForTimesheetApproval(locationsId, userId, isGlobalUser);
	}
	
}
