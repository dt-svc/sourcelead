package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersRateHistoryDao;
import com.bis.sourcelead.entity.ProjectMembersRateHistory;
import com.bis.sourcelead.service.ProjectMembersRateHistoryService;

@Service
public class ProjectMembersRateHistoryServiceImpl implements ProjectMembersRateHistoryService{
	
	@Autowired
	ProjectMembersRateHistoryDao projectMembersRateHistoryDao;

	@Override
	public ProjectMembersRateHistory saveProjectMembersRateHistory(ProjectMembersRateHistory projectMembersRateHistory)
			throws Exception {
		return projectMembersRateHistoryDao.saveProjectMembersRateHistory(projectMembersRateHistory);
	}

}
