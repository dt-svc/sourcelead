package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;

public interface ProjectPlanningResourceRequestService {

	List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception;
	
	ProjectPlanningResourceRequest getProjectPlanningResourceRequestByRequestUUID(String requestUUID) throws Exception;
	
	ProjectPlanningResourceRequest saveProjectPlanningResourceRequest(ProjectPlanningResourceRequest projectPlanningResourceRequest) throws Exception;
}
