package com.bis.sourcelead.dto;

import java.io.Serializable;

public class TimesheetExpenseEntryDetailsUtilDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String locationCode;
	
	private String expenseFrom;
	
	private String expenseId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getExpenseFrom()
	{
		return expenseFrom;
	}

	public void setExpenseFrom(String expenseFrom)
	{
		this.expenseFrom = expenseFrom;
	}

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}
	
	                     
}
