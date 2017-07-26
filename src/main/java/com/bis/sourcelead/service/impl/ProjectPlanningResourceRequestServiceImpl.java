package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectPlanningResourceRequestDao;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.service.ProjectPlanningResourceRequestService;

@Service
public class ProjectPlanningResourceRequestServiceImpl implements ProjectPlanningResourceRequestService{

	@Autowired
	ProjectPlanningResourceRequestDao projectPlanningResourceRequestDao;

	@Override
	public List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception {
		return projectPlanningResourceRequestDao.getProjectPlanningResourceRequestIdsByRequestId(projectPlanningRequestId);
	}

	@Override
	public ProjectPlanningResourceRequest getProjectPlanningResourceRequestByRequestUUID(String requestUUID)
			throws Exception {
		return projectPlanningResourceRequestDao.getProjectPlanningResourceRequestByRequestUUID(requestUUID);
	}

	@Override
	public ProjectPlanningResourceRequest saveProjectPlanningResourceRequest(
			ProjectPlanningResourceRequest projectPlanningResourceRequest) throws Exception {
		return projectPlanningResourceRequestDao.saveProjectPlanningResourceRequest(projectPlanningResourceRequest);
	}
}
