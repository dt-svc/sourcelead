package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ExpensesListUtilDTO  extends SearchDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String locationCode;
	
	private String expenseLocationCode;
	
	private List<String> expenseStatus;
	
	private String startDate;
	
	private String endDate;
	
	private String expenseId;
	
	private int pageNo;
	
	private int pageSize;
	
	private String expenseDashboardStatus;
	
	private String isGlobalAccess;

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

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getExpenseDashboardStatus() {
		return expenseDashboardStatus;
	}

	public void setExpenseDashboardStatus(String expenseDashboardStatus) {
		this.expenseDashboardStatus = expenseDashboardStatus;
	}

	public String getIsGlobalAccess() {
		return isGlobalAccess;
	}

	public void setIsGlobalAccess(String isGlobalAccess) {
		this.isGlobalAccess = isGlobalAccess;
	}
	
}
