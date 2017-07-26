package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;

public interface ProjectPlanningNewResourceRequestDao {

	public List<Long> getProjectPlanningNewResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception;
	
	ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestById(Long id) throws Exception;
	
	void deleteProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningnewResourceRequest) throws Exception;
	
	ProjectPlanningNewResourceRequest saveProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) throws Exception;
	
	public ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestByRequestUUID(String requestUUID) throws Exception;
}
