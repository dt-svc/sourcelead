package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("serial")
public class ProjectBudgetRequestDTO implements Serializable {
	 private String baseLine;
	 
	 private String budgetApprover;
	 
	 private String estimatedHours;
	 
	 private String estimatedBudget;
	 
	 private String projectUUID;

	 private String loginUserid;
	 
	 private String reasonForProjectBudget;
	 
	 private String budgetRequestedId;
	 
	 private String estimatedBudgetCurrency;
	 
	public String getBaseLine() {
		return baseLine;
	}

	public String getBudgetApprover() {
		return budgetApprover;
	}

	public String getEstimatedHours() {
		return estimatedHours;
	}

	public String getEstimatedBudget() {
		return estimatedBudget;
	}

	public void setBaseLine(String baseLine) {
		this.baseLine = baseLine;
	}

	public void setBudgetApprover(String budgetApprover) {
		this.budgetApprover = budgetApprover;
	}

	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public void setEstimatedBudget(String estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public String getProjectUUID() {
		return projectUUID;
	}

	public void setProjectUUID(String projectUUID) {
		this.projectUUID = projectUUID;
	}

	public String getLoginUserid() {
		return loginUserid;
	}

	public void setLoginUserid(String loginUserid) {
		this.loginUserid = loginUserid;
	}

	public String getReasonForProjectBudget() {
		return reasonForProjectBudget;
	}

	public void setReasonForProjectBudget(String reasonForProjectBudget) {
		this.reasonForProjectBudget = reasonForProjectBudget;
	}

	public String getBudgetRequestedId() {
		return budgetRequestedId;
	}

	public void setBudgetRequestedId(String budgetRequestedId) {
		this.budgetRequestedId = budgetRequestedId;
	}

	public String getEstimatedBudgetCurrency() {
		return estimatedBudgetCurrency;
	}

	public void setEstimatedBudgetCurrency(String estimatedBudgetCurrency) {
		this.estimatedBudgetCurrency = estimatedBudgetCurrency;
	}
}
