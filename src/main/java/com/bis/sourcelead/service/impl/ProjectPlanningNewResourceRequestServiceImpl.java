package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectPlanningNewResourceRequestDao;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.service.ProjectPlanningNewResourceRequestService;

@Service
public class ProjectPlanningNewResourceRequestServiceImpl implements ProjectPlanningNewResourceRequestService{

	@Autowired
	ProjectPlanningNewResourceRequestDao projectPlanningNewResourceRequestDao;

	@Override
	public ProjectPlanningNewResourceRequest saveProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) throws Exception {
		return projectPlanningNewResourceRequestDao.saveProjectPlanningNewResourceRequest(projectPlanningNewResourceRequest);
	}

	@Override
	public ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestByRequestUUID(String requestUUID) throws Exception {
		return projectPlanningNewResourceRequestDao.getProjectPlanningNewResourceRequestByRequestUUID(requestUUID);
	}
}
