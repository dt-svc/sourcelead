package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectPlanningRequest;

public interface ProjectPlanningRequestDao {

	ProjectPlanningRequest saveProjectPlanningRequest(ProjectPlanningRequest projectPlanningRequest) throws Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByProjectId(Long projectId) throws Exception;
	
	Integer getProjectPlanningRequestCountByProjectId(Long projectId) throws Exception;
	
	ProjectPlanningRequest getProjectPlanningRequestByRequestUUID(String requestUUID) throws Exception;
	
	ProjectPlanningRequest getProjectPlanningRequestById(Long id) throws Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByApproverId(Long approverUserId,Long orgLocationId,String requestStatus,String userRole) throws Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByApproverIdAndOrgLocationId(Long approverUserId,Long orgLocationId,String userRole) throws Exception;
}
