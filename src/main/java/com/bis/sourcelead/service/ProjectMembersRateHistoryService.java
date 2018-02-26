package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.ProjectMembersRateHistory;

public interface ProjectMembersRateHistoryService {

	ProjectMembersRateHistory saveProjectMembersRateHistory(ProjectMembersRateHistory projectMembersRateHistory) throws Exception;
	
	ProjectMembersRateHistory getProjectMembersRateHistoryByIdAndStatus(Long Id) throws Exception;
	
	
}
