package com.bis.sourcelead.beans;

public class BudgetReportDTO {
	
	private String  currency;
	
	private Float  budgetTotal;
	
	private Float estimatedHours ;
	
	private Float actualBudget;
	
	private Float actualHours;

	private String  invoiceCurrency;
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Float getActualBudget() {
		return actualBudget;
	}

	public Float getActualHours() {
		return actualHours;
	}

	public void setActualBudget(Float actualBudget) {
		this.actualBudget = actualBudget;
	}

	public void setActualHours(Float actualHours) {
		this.actualHours = actualHours;
	}

	public String getInvoiceCurrency() {
		return invoiceCurrency;
	}

	public void setInvoiceCurrency(String invoiceCurrency) {
		this.invoiceCurrency = invoiceCurrency;
	}

	public Float getBudgetTotal() {
		return budgetTotal;
	}

	public Float getEstimatedHours() {
		return estimatedHours;
	}

	public void setBudgetTotal(Float budgetTotal) {
		this.budgetTotal = budgetTotal;
	}

	public void setEstimatedHours(Float estimatedHours) {
		this.estimatedHours = estimatedHours;
	}
}