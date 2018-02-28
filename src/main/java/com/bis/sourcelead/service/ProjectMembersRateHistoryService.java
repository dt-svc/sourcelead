package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ProjectMembersRateHistory;

public interface ProjectMembersRateHistoryService {

	ProjectMembersRateHistory saveProjectMembersRateHistory(ProjectMembersRateHistory projectMembersRateHistory) throws Exception;
	
	ProjectMembersRateHistory getProjectMembersRateHistoryByMemberIdAndStatus(Long Id) throws Exception;
	
	List<ProjectMembersRateHistory> getProjectMembersRateHistoryListByProjectMemberId(Long projectMemberId) throws Exception;
	
	ProjectMembersRateHistory getProjectMembersRateHistoryById(Long Id) throws Exception;
	
	
}
