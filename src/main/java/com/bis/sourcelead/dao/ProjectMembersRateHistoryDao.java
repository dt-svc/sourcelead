package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.ProjectMembersRateHistory;

public interface ProjectMembersRateHistoryDao {

	ProjectMembersRateHistory saveProjectMembersRateHistory(ProjectMembersRateHistory projectMembersRateHistory) throws Exception;
	
	ProjectMembersRateHistory getProjectMembersRateHistoryByMemberIdAndStatus(Long Id) throws Exception;
}
