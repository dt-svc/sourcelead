package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ExpensesSearchDTO extends SearchDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 861376442253861654L;
	
	String expenseStatus;
	String startDate;
	String endDate;
	public String getExpenseStatus() {
		return expenseStatus;
	}
	public void setExpenseStatus(String expenseStatus) {
		this.expenseStatus = expenseStatus;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
