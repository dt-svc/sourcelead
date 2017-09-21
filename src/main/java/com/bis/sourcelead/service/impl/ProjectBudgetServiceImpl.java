package com.bis.sourcelead.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.ProjectBudgetDao;
import com.bis.sourcelead.dto.ProjectBudgetRequestDTO;
import com.bis.sourcelead.dto.ProjectsDTO;
import com.bis.sourcelead.entity.ProjectBudget;
import com.bis.sourcelead.entity.Projects;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ProjectBudgetService;
import com.bis.sourcelead.service.ProjectsService;
import com.bis.sourcelead.service.UserService;

@Service
public class ProjectBudgetServiceImpl implements ProjectBudgetService{

	@Autowired
	ProjectBudgetDao projectBudgetDao;
 
	@Autowired
	UserService userService;
	
	@Autowired
	ProjectsService  projectsService;
	
	@Override
	public ProjectBudget saveProjectBudget(ProjectBudget projectBudget) throws Exception {
		return projectBudgetDao.saveProjectTaskComments(projectBudget);
	}

	@Override
	public ProjectBudget getProjectBudgetsById(Long projectId) throws Exception {
		return projectBudgetDao.getProjectBudgetsById(projectId);
	}

	@Override
	public List<ProjectBudget> getProjectBudgetByProjectTaskId(Long Id) throws Exception {
		return projectBudgetDao.getProjectBudgetByProjectTaskId(Id);
	}

	@Override
	public Long getProjectBudgetReqestedIdByProjectId(Long projectId, Integer generatedIdcount) {
		return projectBudgetDao.getProjectBudgetReqestedIdByProjectId(projectId, generatedIdcount);
	}

	@Override
	public ProjectBudget getProjectBudgetsByProjectId(Long projectId) throws Exception {
		return projectBudgetDao.getProjectBudgetsByProjectId(projectId);
	}
	
	@Override
	public List<ProjectBudget> getProjectBudgetListByOrgLocationIdAndBudgetStatus(Long orgLocationId, String budgetStatus) throws Exception {
		return projectBudgetDao.getProjectBudgetListByOrgLocationIdAndBudgetStatus(orgLocationId, budgetStatus);
	}
	
	@Override
	public List<ProjectBudget> getProjectBudgetListByOrgLocationId(Long orgLocationId) throws Exception {
		return projectBudgetDao.getProjectBudgetListByOrgLocationId(orgLocationId);
	}
	
	@Override
	public void saveProjectBudgetRequset(ProjectBudgetRequestDTO projectBudgetRequestDTO, User user) throws Exception {
		ProjectBudget projectBudget = null;
	      Projects projects = projectsService.getProjectByProjectUUID(projectBudgetRequestDTO.getProjectUUID());
		   if(StringUtils.isNotBlank(projectBudgetRequestDTO.getBudgetRequestedId())) {
			   projectBudget = projectBudgetDao.getProjectBudgetsById(Long.valueOf(projectBudgetRequestDTO.getBudgetRequestedId()));
			   projectBudget.setUpdatedDate(new Date());
			   projectBudget.setUpdateUserId(user);
		   }else {
			   projectBudget = new ProjectBudget();
			   projectBudget.setCreateUserId(user);
				projectBudget.setCreatedDate(new Date());
				projectBudget.setBudgetRequestedId(generatebudgetRequestId(projects));
		   }
	    	projectBudget.setProjectId(projects);
	    	projectBudget.setEstimatedBudgetCurrency(projectBudgetRequestDTO.getEstimatedBudgetCurrency());
			projectBudget.setBudgetStatus(Constants.BUDGET_REQUEST_WAITING_FOR_APPROVAL);
			projectBudget.setEstimatedHours(projectBudgetRequestDTO.getEstimatedHours());
			projectBudget.setEstimatedBudgetValue(projectBudgetRequestDTO.getEstimatedBudget());
			projectBudget.setBudgetDescription(projectBudgetRequestDTO.getBaseLine());
			if(StringUtils.isNotBlank(projectBudgetRequestDTO.getBudgetApprover())) {
		     User use = userService.getUserById(Long.valueOf(projectBudgetRequestDTO.getBudgetApprover()));
				projectBudget.setApproverUserId(use);
			}else {
				projectBudget.setApproverUserId(null);
			}
			projectBudgetDao.saveProjectTaskComments(projectBudget);
	}
	 
	private String generatebudgetRequestId(Projects projects) {
		String companyShortCode = null;
       	Long number = 1L;
   		String candidateId = null;
   		Integer generatedIdcount = 0;
   		StringBuilder sbuilder = new StringBuilder();
   		sbuilder.append(projects.getProjectShortCode()).append("BR");
   		String generatedUsreId = sbuilder.toString();
   		for(int i=0;i< generatedUsreId.length();i++){
   			char ch = generatedUsreId.charAt(i);
   			if(Character.isAlphabetic(ch)){
   				generatedIdcount++;
   			}
   		}
   		// in dao to count from next character
   		generatedIdcount = generatedIdcount+1;
   		Long checkNumber = null;
   		if(projects.getId() != null) {
   			 checkNumber = projectBudgetDao.getProjectBudgetReqestedIdByProjectId(projects.getId(),generatedIdcount);
   		}
   		 
   		if(checkNumber == null){
   			candidateId = number.toString();
   		}else{
   		Long canId = checkNumber+1;
   		candidateId = canId.toString();
   		}
   		final DecimalFormat decimalFormat = new DecimalFormat("0");                
           String convetredCandidateId = decimalFormat.format(Long.parseLong(candidateId));
           StringBuffer sb = new StringBuffer();
           if(projects.getProjectShortCode() != null && !"".equalsIgnoreCase(projects.getProjectShortCode())){
           	 companyShortCode = projects.getProjectShortCode();
           }
   		return sb.append(companyShortCode).append("BR").append(convetredCandidateId).toString();
	}
}
