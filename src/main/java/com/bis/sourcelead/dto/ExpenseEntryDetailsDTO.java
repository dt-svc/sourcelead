package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

public class ExpenseEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String expenseId;
	
	private String currencyCode;
	
	List<ProjectMembersDTO> projectMembersList;
	
	List<ExpensesCategoryDTO> expenseCategoryList;


	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public List<ProjectMembersDTO> getProjectMembersList() {
		return projectMembersList;
	}

	public void setProjectMembersList(List<ProjectMembersDTO> projectMembersList) {
		this.projectMembersList = projectMembersList;
	}

	

	public List<ExpensesCategoryDTO> getExpenseCategoryList() {
		return expenseCategoryList;
	}
	
	public void setExpenseCategoryList(List<ExpensesCategoryDTO> expenseCategoryList) {
		this.expenseCategoryList = expenseCategoryList;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	
}
