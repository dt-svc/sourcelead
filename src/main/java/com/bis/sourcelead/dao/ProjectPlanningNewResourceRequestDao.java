package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;

public interface ProjectPlanningNewResourceRequestDao {

	public List<Long> getProjectPlanningNewResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception;
	
}
