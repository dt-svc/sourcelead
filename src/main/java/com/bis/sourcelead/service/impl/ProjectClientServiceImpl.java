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
	public List<ProjectClients> getAllProjectClientsByApproverUserAndOrgLocationId(Long userId, Long locationsId)
			throws Exception {
		return projectClientDao.getAllProjectClientsByApproverUserAndOrgLocationId(userId, locationsId);
	}
	
}
