package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersHistoryDao;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.service.ProjectMembersHistoryService;

@Service
public class ProjectMembersHistoryServiceImpl implements ProjectMembersHistoryService{

	@Autowired
	private ProjectMembersHistoryDao projectMembersHistoryDao;
	
	@Override
	public ProjectMembersHistory getProjectMembersOpenHistoryByProjectMemberId(Long projectMemberId) throws Exception {
		// TODO Auto-generated method stub
		return projectMembersHistoryDao.getProjectMembersOpenHistoryByProjectMemberId(projectMemberId);
	}

	@Override
	public HashSet<Date> getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(Long projectMemberId,
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return projectMembersHistoryDao.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId, startDate, endDate);
	}

	@Override
	public ProjectMembersHistory updateProjectMembersHistory(ProjectMembersHistory projectMembersHistory)
			throws Exception {
		// TODO Auto-generated method stub
		return projectMembersHistoryDao.updateProjectMembersHistory(projectMembersHistory);
	}
	
	@Override
	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId) {
		return projectMembersHistoryDao.updateEndDateOfProjectMemberId(endDate,projectMemberId);
	}

}
