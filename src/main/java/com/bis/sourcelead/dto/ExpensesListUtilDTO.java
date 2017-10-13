package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ExpensesListUtilDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String locationCode;
	
	private String expenseLocationCode;
	
	private List<String> expenseStatus;
	
	private String startDate;
	
	private String endDate;
	
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

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseLocationCode() {
		return expenseLocationCode;
	}

	public void setExpenseLocationCode(String expenseLocationCode) {
		this.expenseLocationCode = expenseLocationCode;
	}
	
}
