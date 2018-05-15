package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.annotations.Required;

@SuppressWarnings("serial")
public class ProjectBudgetRequestDTO implements Serializable {
	 @Required(desc="baseLine")
	 private String baseLine;
	 
	 @Required(desc="budgetApprover")
	 private String budgetApprover;
	 
	 @Required(desc="estimatedHours")
	 private double estimatedHours;
	 
	 @Required(desc="estimatedBudget")
	 private double estimatedBudget;
	 
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

	public void setBaseLine(String baseLine) {
		this.baseLine = baseLine;
	}

	public void setBudgetApprover(String budgetApprover) {
		this.budgetApprover = budgetApprover;
	}

	public double getEstimatedHours() {
		return estimatedHours;
	}

	public double getEstimatedBudget() {
		return estimatedBudget;
	}

	public void setEstimatedHours(double estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public void setEstimatedBudget(double estimatedBudget) {
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
