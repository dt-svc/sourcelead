package com.bis.sourcelead.dao;

import java.util.List;

public interface ProjectPlanningResourceRequestDao {

	public List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception;
}
