package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.ProjectBudget;

public interface ProjectBudgetDao {
	
	ProjectBudget saveProjectTaskComments(ProjectBudget projectBudget) throws Exception;
	
	ProjectBudget getProjectBudgetsByProjectId(Long projectId)throws Exception;
	
	ProjectBudget getProjectBudgetsById(Long Id)throws Exception;
	
	List<ProjectBudget>  getProjectBudgetByProjectTaskId(Long projectId,Long loginOrgLocationId) throws Exception;
	
	List<ProjectBudget>  getProjectBudgetListByOrgLocationIdAndBudgetStatus(Long approverUserId,Long orgLocationId,String budgetStatus, String userRoleName) throws Exception;
	
	List<ProjectBudget>  getProjectBudgetListByOrgLocationId(Long approverUserId,Long orgLocationId,String userRoleName) throws Exception;
	
	Long getProjectBudgetReqestedIdByProjectId(Long projectId, Integer generatedIdcount);
	
	List<ProjectBudget>  getProjectBudgetByProjectTaskIdAndApprovedStatus(Long projectId, Long orgLocationID) throws Exception;
}
