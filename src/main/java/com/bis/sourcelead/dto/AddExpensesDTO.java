package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class AddExpensesDTO {
	
	private String expenseId;
	
	@Required(desc = "ExpenseAutoId")
	private String ExpenseAutoId;
	
	private String expensePurpose;
	
	private String projectId;
	@Required(desc = "projectId")
	
	private String clientNameExpense;
	
	@Required(desc = "startDateExpense ")
	private String startDateExpense;
	
	@Required(desc = "endDateExpense ")
	private String endDateExpense;
	
	@Required(desc = "projectMemberId ")
	private String projectMemberId;
	
	private List<CategoryDTO> categoryDTO;
	
	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseAutoId() {
		return ExpenseAutoId;
	}

	public void setExpenseAutoId(String expenseAutoId) {
		ExpenseAutoId = expenseAutoId;
	}

	public String getExpensePurpose() {
		return expensePurpose;
	}

	public void setExpensePurpose(String expensePurpose) {
		this.expensePurpose = expensePurpose;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getClientNameExpense() {
		return clientNameExpense;
	}

	public void setClientNameExpense(String clientNameExpense) {
		this.clientNameExpense = clientNameExpense;
	}

	public String getStartDateExpense() {
		return startDateExpense;
	}

	public void setStartDateExpense(String startDateExpense) {
		this.startDateExpense = startDateExpense;
	}

	public String getEndDateExpense() {
		return endDateExpense;
	}

	public void setEndDateExpense(String endDateExpense) {
		this.endDateExpense = endDateExpense;
	}

	public String getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(String projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public List<CategoryDTO> getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(List<CategoryDTO> categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	
	
}
