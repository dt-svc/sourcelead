package com.bis.sourcelead.service;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ProjectBudgetRequestDTO;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.User;

public interface ProjectBudgetService {

	ProjectBudget saveProjectBudget(ProjectBudget projectBudget) throws Exception;

	ProjectBudget getProjectBudgetsByProjectId(Long projectId)throws Exception;
	
	ProjectBudget getProjectBudgetsById(Long Id)throws Exception;
	
	List<ProjectBudget>  getProjectBudgetByProjectTaskId(Long projectId, Long loginOrgLocationId) throws Exception;
	
	List<ProjectBudget>  getProjectBudgetListByOrgLocationIdAndBudgetStatus(Long orgLocationId,String budgetStatus) throws Exception;
	
	List<ProjectBudget>  getProjectBudgetListByOrgLocationId(Long orgLocationId) throws Exception;
	
	Long getProjectBudgetReqestedIdByProjectId(Long projectId, Integer generatedIdcount);
	
	void saveProjectBudgetRequset(ProjectBudgetRequestDTO projectBudgetRequestDTO,User user)throws Exception;
	
	void sendEmailForBugetRequestApproversByAcceptAndRejectAndOnHold(User user, ProjectBudget projectBudget)throws Exception;
	
	List<ProjectBudget>  getProjectBudgetByProjectTaskIdAndApprovedStatus(Long projectId,String filterDate, Date startDateUTC, Date endDateUTC) throws Exception;
}
