package com.bis.sourcelead.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.bis.sourcelead.entity.ProjectMembersHistory;

public interface ProjectMembersHistoryService {
	
	ProjectMembersHistory getProjectMembersOpenHistoryByProjectMemberId(Long projectMemberId) throws Exception;
	
	HashSet<Date> getAllProjectMemberHistoryDatesByProjectMemberIdAndWeekStartDateEndDate(Long projectMemberId,Date startDate,Date endDate);
	
	 ProjectMembersHistory updateProjectMembersHistory(ProjectMembersHistory projectMembersHistory) throws Exception ;
	 
	 Integer updateEndDateOfProjectMemberId(Date endDate1, Long projectMemberId);

}
