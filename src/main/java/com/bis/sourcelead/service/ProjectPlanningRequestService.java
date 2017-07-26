package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.User;

public interface ProjectPlanningRequestService {

	public void saveProjectPlanningRequest(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,String projectUUID,String saveType) throws JSONException, Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByProjectId(Long projectId) throws Exception;
	
	Integer getProjectPlanningRequestCountByProjectId(Long projectId) throws Exception;
	
	public void saveProjectNewResourceRequest(JSONObject jsonObj, User loginUser, OrgLocations orgLocations,String projectUUID,String saveType) throws JSONException, Exception;

	ProjectPlanningRequest getProjectPlanningRequestByRequestUUID(String requestUUID) throws Exception;
	
	ProjectPlanningRequest getProjectPlanningRequestById(Long id) throws Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByApproverId(Long approverUserId,Long orgLocationId,String requestStatus) throws Exception;
	
	ProjectPlanningRequest storeProjectPlanningRequest(ProjectPlanningRequest projectPlanningRequest) throws Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByApproverIdAndOrgLocationId(Long approverUserId,Long orgLocationId) throws Exception;

}
