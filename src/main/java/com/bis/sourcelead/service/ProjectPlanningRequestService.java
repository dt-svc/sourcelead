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
	
	ProjectPlanningRequest getProjectPlanningRequestByRequestUUID(String requestUUID) throws Exception;
}
