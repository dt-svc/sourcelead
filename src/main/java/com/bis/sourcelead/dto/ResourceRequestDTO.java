package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class ResourceRequestDTO {

	private String savedPlanningRequestUUID;
	
	private String saveType;
	
	@Required(desc = "requestId")
	private String requestId;
	
	private String projectUUID;
	
	@Required(desc = "description")
	private String description;
	
	@Required(desc = "approver")
	private String approver;
	
	private int totalResourcesValue;

	private String totalEstHours;
	
	private String totalEstBudget;
	
	private String estimatedHrs;
	
	private String estimatedBudget;
	
	private String estimatedBudgetCurrencyType;
	
	private List<ResourcesDTO> planningResources;
	
	private List<AssociateRequestDTO> associateResources;
	
	private String loginuserId;
	
	private String loginOrglocationId;
	
	public String getSavedPlanningRequestUUID() {
		return savedPlanningRequestUUID;
	}

	public String getSaveType() {
		return saveType;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public String getDescription() {
		return description;
	}

	public String getApprover() {
		return approver;
	}

	public int getTotalResourcesValue() {
		return totalResourcesValue;
	}

	public String getTotalEstHours() {
		return totalEstHours;
	}

	public String getTotalEstBudget() {
		return totalEstBudget;
	}

	public String getEstimatedBudgetCurrencyType() {
		return estimatedBudgetCurrencyType;
	}

	public void setSavedPlanningRequestUUID(String savedPlanningRequestUUID) {
		this.savedPlanningRequestUUID = savedPlanningRequestUUID;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public void setTotalResourcesValue(int totalResourcesValue) {
		this.totalResourcesValue = totalResourcesValue;
	}

	public void setTotalEstHours(String totalEstHours) {
		this.totalEstHours = totalEstHours;
	}

	public void setTotalEstBudget(String totalEstBudget) {
		this.totalEstBudget = totalEstBudget;
	}

	public void setEstimatedBudgetCurrencyType(String estimatedBudgetCurrencyType) {
		this.estimatedBudgetCurrencyType = estimatedBudgetCurrencyType;
	}

	public List<ResourcesDTO> getPlanningResources() {
		return planningResources;
	}

	public void setPlanningResources(List<ResourcesDTO> planningResources) {
		this.planningResources = planningResources;
	}

	public String getEstimatedHrs() {
		return estimatedHrs;
	}

	public String getEstimatedBudget() {
		return estimatedBudget;
	}

	public List<AssociateRequestDTO> getAssociateResources() {
		return associateResources;
	}

	public void setEstimatedHrs(String estimatedHrs) {
		this.estimatedHrs = estimatedHrs;
	}

	public void setEstimatedBudget(String estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public void setAssociateResources(List<AssociateRequestDTO> associateResources) {
		this.associateResources = associateResources;
	}

	public String getLoginuserId() {
		return loginuserId;
	}

	public String getLoginOrglocationId() {
		return loginOrglocationId;
	}

	public void setLoginuserId(String loginuserId) {
		this.loginuserId = loginuserId;
	}

	public void setLoginOrglocationId(String loginOrglocationId) {
		this.loginOrglocationId = loginOrglocationId;
	}
}
