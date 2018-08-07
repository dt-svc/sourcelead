package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.bis.sourcelead.entity.ProjectMembersHistory;

public interface ProjectMembersHistoryDao {
	
	ProjectMembersHistory getProjectMembersOpenHistoryByProjectMemberId(Long projectMemberId) throws Exception;
	
	ProjectMembersHistory getProjectMembersRecentClosedByProjectMemberId(Long projectMemberId) throws Exception;
	
	HashMap<String,Date> getProjectMembersStartDateAndEndDateByProjectMemberId(Long projectMemberId,String reportFor) throws Exception;
	
	HashSet<Date> getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(Long projectMemberId,Date startDate,Date endDate);
	
    ProjectMembersHistory updateProjectMembersHistory(ProjectMembersHistory projectMembersHistory) throws Exception ;
    
    Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId);
    
	ProjectMembersHistory getProjectMembeHistoryForHotlistCandidatesByUsedId(Long userId) throws Exception;

	ProjectMembersHistory getProjectMembersAllHistoryByProjectMemberId(Long projectMemberId) throws Exception;

	void deleteProjectMemberHistory(ProjectMembersHistory projectMembersHistory) throws Exception;
	
	ProjectMembersHistory getProjectMembersOpenHistoryByProjectClientsId(Long projectClientsId,Long projectMemberId) throws Exception;
	
	List<ProjectMembersHistory> getProjectMembersHistoryListByProjectMemberId(Long projectMemberId) throws Exception;
	
	public HashMap<String,Date> getAssignDateandEndDateByProjectMemberId(Long projectMemberId);
	
	ProjectMembersHistory getProjectMembersHistoryByProjectMemberIdAndOrgLoctionId(Long projectMemberId,Long orgLocationId) throws Exception;
	
	public ProjectMembersHistory getProjectMembersHistoryByProjectMemberIdAndOrgId(Long projectMemberId,Long orgId) throws Exception;
	
}
