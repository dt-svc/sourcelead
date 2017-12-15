package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.ProjectPlanningNewResourceRequestDTO;
import com.bis.sourcelead.dto.ProjectPlanningResourceRequestDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.ProjectPlanningResourceRequest;
import com.bis.sourcelead.entity.User;

public interface ProjectPlanningNewResourceRequestService {

	ProjectPlanningNewResourceRequest saveProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) throws Exception;
	
	ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestByRequestUUID(String requestUUID) throws Exception;
	
	void sendMailToAcceptAndRejectSingleNewResourceRequestApprover(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest, User user,OrgLocations  orgLocations)throws Exception;
	
	void sendMailToAcceptAndRejectBulkNewResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest, User user,OrgLocations  orgLocations)throws Exception;
	
	void sendMailToOnHoldBulkNewResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest, User user,OrgLocations  orgLocations)throws Exception;
	
	ResponseList<ProjectPlanningNewResourceRequestDTO> getNewResourceRequestListByApproverIdAndOrgLocationId(ProjectPlanningNewResourceRequestDTO resourceRequestDTO,Long approverUserId,Long orgLocationId,String userRole) throws Exception;
	
	List<ProjectPlanningNewResourceRequest> getNewResourceRequestListByApproverIdAndOrgLocationIdAndStatus(Long approverUserId,Long orgLocationId,String userRole) throws Exception;
}
