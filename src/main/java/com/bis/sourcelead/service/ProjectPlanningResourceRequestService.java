package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ProjectPlanningResourceRequestDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.User;

public interface ProjectPlanningResourceRequestService {

	List<Long> getProjectPlanningResourceRequestIdsByRequestId(Long projectPlanningRequestId) throws Exception;
	
	ProjectPlanningResourceRequest getProjectPlanningResourceRequestByRequestUUID(String requestUUID) throws Exception;
	
	ProjectPlanningResourceRequest saveProjectPlanningResourceRequest(ProjectPlanningResourceRequest projectPlanningResourceRequest) throws Exception;
	
	void sendMailToAcceptAndRejectBulkResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest,User user,OrgLocations orgLocations)throws Exception;
	
	void sendMailToOnHoldBulkResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest,User user,OrgLocations orgLocations)throws Exception;
	
	void sendMailToAcceptAndRejectSingleResourceRequestApprover(ProjectPlanningResourceRequest projectPlanningRequest, User user, OrgLocations orgLocations)throws Exception;
	
	ResponseList<ProjectPlanningResourceRequestDTO> getResourceRequestListByApproverIdAndOrgLocationId(ProjectPlanningResourceRequestDTO resourceRequestDTO,Long approverUserId,Long orgLocationId,String userRole) throws Exception;
	
	List<ProjectPlanningResourceRequest> getResourceRequestListByApproverIdAndOrgLocationIdAndStatus(Long approverUserId,Long orgLocationId,String userRole) throws Exception;
}
