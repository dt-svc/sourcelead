package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ProjectMembersRateHistory;

public interface ProjectMembersRateHistoryDao {

	ProjectMembersRateHistory saveProjectMembersRateHistory(ProjectMembersRateHistory projectMembersRateHistory) throws Exception;
	
	ProjectMembersRateHistory getProjectMembersRateHistoryByMemberIdAndStatus(Long Id) throws Exception;
	
	List<ProjectMembersRateHistory> getProjectMembersRateHistoryListByProjectMemberId(Long projectMemberId) throws Exception;
	
	ProjectMembersRateHistory getProjectMembersRateHistoryById(Long Id) throws Exception;
}
