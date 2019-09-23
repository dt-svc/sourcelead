package com.bis.sourcelead.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ProjectMembersHistoryDao;
import com.bis.sourcelead.entity.ProjectMembersHistory;
import com.bis.sourcelead.service.ProjectMembersHistoryService;
import com.bis.sourcelead.util.CommonUtil;

@Service
public class ProjectMembersHistoryServiceImpl implements ProjectMembersHistoryService{

	@Autowired
	private ProjectMembersHistoryDao projectMembersHistoryDao;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Override
	public ProjectMembersHistory getProjectMembersOpenHistoryByProjectMemberId(Long projectMemberId) throws Exception {
		return projectMembersHistoryDao.getProjectMembersOpenHistoryByProjectMemberId(projectMemberId);
	}

	@Override
	public HashSet<Date> getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(Long projectMemberId,
			Date startDate, Date endDate) {
		return projectMembersHistoryDao.getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(projectMemberId, startDate, endDate);
	}

	@Override
	public ProjectMembersHistory updateProjectMembersHistory(ProjectMembersHistory projectMembersHistory)
			throws Exception {
		projectMembersHistory.setUpdatedDate(commonUtil.currentDate());
		return projectMembersHistoryDao.updateProjectMembersHistory(projectMembersHistory);
	}
	
	@Override
	public Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId) {
		return projectMembersHistoryDao.updateEndDateOfProjectMemberId(endDate,projectMemberId);
	}

	@Override
	public ProjectMembersHistory getProjectMembersRecentClosedByProjectMemberId(Long projectMemberId) throws Exception {
		return projectMembersHistoryDao.getProjectMembersRecentClosedByProjectMemberId(projectMemberId);
	}

	@Override
	public HashMap<String, Date> getProjectMembersStartDateAndEndDateByProjectMemberId(Long projectMemberId,String reportFor)
			throws Exception {
		return projectMembersHistoryDao.getProjectMembersStartDateAndEndDateByProjectMemberId(projectMemberId,reportFor);
	}

	@Override
	public ProjectMembersHistory getProjectMembeHistoryForHotlistCandidatesByUsedId(Long projectMemberId) throws Exception {
		return projectMembersHistoryDao.getProjectMembeHistoryForHotlistCandidatesByUsedId(projectMemberId);
	}

	@Override
	public ProjectMembersHistory getProjectMembersAllHistoryByProjectMemberId(Long projectMemberId) throws Exception
	{
		return projectMembersHistoryDao.getProjectMembersAllHistoryByProjectMemberId(projectMemberId);
	}

	@Override
	public void deleteProjectMemberHistory(ProjectMembersHistory projectMembersHistory) throws Exception {
		projectMembersHistoryDao.deleteProjectMemberHistory(projectMembersHistory);
	}

	@Override
	public ProjectMembersHistory getProjectMembersOpenHistoryByProjectClientsId(Long projectClientsId,Long projectMemberId)
			throws Exception {
		return projectMembersHistoryDao.getProjectMembersOpenHistoryByProjectClientsId(projectClientsId,projectMemberId);
	}

	@Override
	public List<ProjectMembersHistory> getProjectMembersHistoryListByProjectMemberId(Long projectMemberId)
			throws Exception {
		return projectMembersHistoryDao.getProjectMembersHistoryListByProjectMemberId(projectMemberId);
	}

	@Override
	public ProjectMembersHistory getProjectMembersHistoryByProjectMemberIdAndOrgLoctionId(Long projectMemberId,
			Long orgLocationId) throws Exception {
		return projectMembersHistoryDao.getProjectMembersHistoryByProjectMemberIdAndOrgLoctionId(projectMemberId, orgLocationId);
	}

	@Override
	public HashMap<String, Date> getAssignDateandEndDateByProjectMemberId(Long projectMemberId) {
		return projectMembersHistoryDao.getAssignDateandEndDateByProjectMemberId(projectMemberId);
	}

	@Override
	public ProjectMembersHistory getProjectMembersHistoryByProjectMemberIdAndOrgId(Long projectMemberId, Long orgId)
			throws Exception {
		return projectMembersHistoryDao.getProjectMembersHistoryByProjectMemberIdAndOrgId(projectMemberId,orgId);
	}

	
}
