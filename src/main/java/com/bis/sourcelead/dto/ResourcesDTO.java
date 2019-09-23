package com.bis.sourcelead.dto;

import java.math.BigDecimal;

import com.bis.sourcelead.annotations.Required;

public class ResourcesDTO {

	private String roleName;
	
	private String otherRole;
	
	@Required(desc = "roleId")
	private String roleId;
	
	@Required(desc = "noOfResources")
	private String noOfResources;
	
	private String resourceRequestId;
	
	private String customRole;
	
	private String min;

	private String max;
	
	@Required(desc = "primarySkill")
	private String primarySkill;
	
	private String currencyCode;

	private String billRate;
	
	private String payRate;
	
	private String rateType;
	
	private String startDate;
	
	private String endDate;
	
	private String estimatedHours;
	
	private String estimatedBudget;

	private String budgetCurrency;
	
	@Required(desc = "consulting")
	private String consulting;

	
	private String validateRow;
	
	public String getRoleName() {
		return roleName;
	}

	public String getOtherRole() {
		return otherRole;
	}

	public String getRoleId() {
		return roleId;
	}

	public String getNoOfResources() {
		return noOfResources;
	}

	public String getResourceRequestId() {
		return resourceRequestId;
	}

	public String getCustomRole() {
		return customRole;
	}

	public String getMax() {
		return max;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getBillRate() {
		return billRate;
	}

	public String getPayRate() {
		return payRate;
	}

	public String getRateType() {
		return rateType;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getEstimatedHours() {
		return estimatedHours;
	}

	public String getEstimatedBudget() {
		return estimatedBudget;
	}

	public String getBudgetCurrency() {
		return budgetCurrency;
	}

	public String getConsulting() {
		return consulting;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setOtherRole(String otherRole) {
		this.otherRole = otherRole;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setNoOfResources(String noOfResources) {
		this.noOfResources = noOfResources;
	}

	public void setResourceRequestId(String resourceRequestId) {
		this.resourceRequestId = resourceRequestId;
	}

	public void setCustomRole(String customRole) {
		this.customRole = customRole;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setBillRate(String billRate) {
		this.billRate = billRate;
	}

	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}

	public void setEstimatedBudget(String estimatedBudget) {
		this.estimatedBudget = estimatedBudget;
	}

	public void setBudgetCurrency(String budgetCurrency) {
		this.budgetCurrency = budgetCurrency;
	}

	public void setConsulting(String consulting) {
		this.consulting = consulting;
	}

	public String getValidateRow() {
		return validateRow;
	}

	public void setValidateRow(String validateRow) {
		this.validateRow = validateRow;
	}

	
}
