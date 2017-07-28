package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ExpensesListUtilDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String locationCode;
	
	private List<String> expenseStatus;
	
	private String startDate;
	
	private String endDate;
	

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

	public List<String> getExpenseStatus()
	{
		return expenseStatus;
	}
	public void setExpenseStatus(List<String> expenseStatus)
	{
		this.expenseStatus = expenseStatus;
	}
	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	
	
	
	


	
}
