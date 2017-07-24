package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;

public interface ProjectPlanningResourceRequestDao {

	public List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception;
	
	ProjectPlanningResourceRequest getProjectPlanningResourceRequestById(Long id) throws Exception;
	
	void deleteProjectPlanningResourceRequest(ProjectPlanningResourceRequest projectPlanningResourceRequest) throws Exception;
	
	
	
}
