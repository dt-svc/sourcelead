package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ExpensesEntriesStoringDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String expenseId;

	private String purpose;

	private Long projectMemberId;

	private String clientName;

	private String startDate;

	private String endDate;

    private List<ExpensesCategoryDTO> categoryList;
    
    private String locationsCode;
    
    private Long userId; 
    
    private String status;
    
    private List<Long> removedCategoryIds;
    
    
	
	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public List<ExpensesCategoryDTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<ExpensesCategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}

	public String getLocationsCode()
	{
		return locationsCode;
	}

	public void setLocationsCode(String locationsCode)
	{
		this.locationsCode = locationsCode;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public List<Long> getRemovedCategoryIds() {
		return removedCategoryIds;
	}

	public void setRemovedCategoryIds(List<Long> removedCategoryIds) {
		this.removedCategoryIds = removedCategoryIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}
