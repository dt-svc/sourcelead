package com.bis.sourcelead.beans;

public class BudgetReportDTO {
	
	private String  currency;
	
	private String  budgetTotal;
	
	private String estimatedHours ;

	public String getCurrency() {
		return currency;
	}

	public String getBudgetTotal() {
		return budgetTotal;
	}

	public String getEstimatedHours() {
		return estimatedHours;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setBudgetTotal(String budgetTotal) {
		this.budgetTotal = budgetTotal;
	}

	public void setEstimatedHours(String estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
}