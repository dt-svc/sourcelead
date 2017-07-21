package com.bis.sourcelead.service;

import java.util.List;

public interface ProjectPlanningResourceRequestService {

	List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception;
}
