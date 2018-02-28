package com.bis.sourcelead.service.impl;

import java.util.List;

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

	@Override
	public ProjectMembersRateHistory getProjectMembersRateHistoryByMemberIdAndStatus(Long Id) throws Exception {
		return projectMembersRateHistoryDao.getProjectMembersRateHistoryByMemberIdAndStatus(Id);
	}

	@Override
	public List<ProjectMembersRateHistory> getProjectMembersRateHistoryListByProjectMemberId(Long projectMemberId)
			throws Exception {
		return projectMembersRateHistoryDao.getProjectMembersRateHistoryListByProjectMemberId(projectMemberId);
	}

	@Override
	public ProjectMembersRateHistory getProjectMembersRateHistoryById(Long Id) throws Exception {
		return projectMembersRateHistoryDao.getProjectMembersRateHistoryById(Id);
	}

}
