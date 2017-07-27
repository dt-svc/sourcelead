package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectPlanningNewResourceRequest;
import com.bis.sourcelead.entity.ProjectPlanningRequest;
import com.bis.sourcelead.entity.User;

public interface ProjectPlanningNewResourceRequestService {

	ProjectPlanningNewResourceRequest saveProjectPlanningNewResourceRequest(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest) throws Exception;
	
	ProjectPlanningNewResourceRequest getProjectPlanningNewResourceRequestByRequestUUID(String requestUUID) throws Exception;
	
	void sendMailToAcceptAndRejectSingleNewResourceRequestApprover(ProjectPlanningNewResourceRequest projectPlanningNewResourceRequest, User user,OrgLocations  orgLocations)throws Exception;
	
	void sendMailToAcceptAndRejectBulkNewResourceRequestApprover(ProjectPlanningRequest projectPlanningRequest, User user,OrgLocations  orgLocations)throws Exception;
}
