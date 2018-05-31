package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.dto.ProjectResourceRequestDTO;
import com.bis.sourcelead.dto.ResourceRequestDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.User;

public interface ProjectPlanningRequestService {

	public void saveProjectPlanningRequest(ProjectResourceRequestDTO  resourceRequestDTO, User loginUser, OrgLocations orgLocations) throws JSONException, Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByProjectId(Long projectId) throws Exception;
	
	Integer getProjectPlanningRequestCountByProjectId(Long projectId) throws Exception;
	
	public void saveProjectNewResourceRequest(ResourceRequestDTO  resourceRequestDTO, User loginUser, OrgLocations orgLocations) throws JSONException, Exception;

	ProjectPlanningRequest getProjectPlanningRequestByRequestUUID(String requestUUID) throws Exception;
	
	ProjectPlanningRequest getProjectPlanningRequestById(Long id) throws Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByApproverId(Long approverUserId,Long orgLocationId,String requestStatus,String userRole) throws Exception;
	
	ProjectPlanningRequest storeProjectPlanningRequest(ProjectPlanningRequest projectPlanningRequest) throws Exception;
	
	List<ProjectPlanningRequest> getProjectPlanningRequestByApproverIdAndOrgLocationId(Long approverUserId,Long orgLocationId,String userRole) throws Exception;

}
