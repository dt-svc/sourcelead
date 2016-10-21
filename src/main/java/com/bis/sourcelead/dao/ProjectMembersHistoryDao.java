package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.HashSet;

import com.bis.sourcelead.entity.ProjectMembersHistory;

public interface ProjectMembersHistoryDao {
	
	ProjectMembersHistory getProjectMembersOpenHistoryByProjectMemberId(Long projectMemberId) throws Exception;
	
	ProjectMembersHistory getProjectMembersRecentClosedByProjectMemberId(Long projectMemberId) throws Exception;
	
	HashSet<Date> getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(Long projectMemberId,Date startDate,Date endDate);
	
    ProjectMembersHistory updateProjectMembersHistory(ProjectMembersHistory projectMembersHistory) throws Exception ;
    
    Integer updateEndDateOfProjectMemberId(Date endDate, Long projectMemberId);

}
