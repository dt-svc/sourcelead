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

}
