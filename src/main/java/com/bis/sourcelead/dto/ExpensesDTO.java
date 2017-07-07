package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.enums.Submission_Status;

public class ExpensesDTO implements Serializable {
private static final long serialVersionUID = 1L;

	private Long id;
	
	private String expenseId;
	
	private String purpose;
	
	private float totalAmount;
	
	private String clientName;
	
	private String projectName;
	
	private String noOfExpenses;
	
	private Submission_Status expenseStatus;
	
	private Submission_Status combinedSubmissionStatus;
	
    private List<ExpensesEntriesDTO> expensesEntries;
	
	private Long projectMemberId;
	
	private String startDate;
	
	private String endDate;
	
	private boolean sahApproved;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getNoOfExpenses() {
		return noOfExpenses;
	}

	public void setNoOfExpenses(String noOfExpenses) {
		this.noOfExpenses = noOfExpenses;
	}

	public Submission_Status getExpenseStatus() {
		return expenseStatus;
	}

	public void setExpenseStatus(Submission_Status expenseStatus) {
		this.expenseStatus = expenseStatus;
	}

	public List<ExpensesEntriesDTO> getExpensesEntries() {
		return expensesEntries;
	}

	public void setExpensesEntries(List<ExpensesEntriesDTO> expensesEntries) {
		this.expensesEntries = expensesEntries;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
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

	public Submission_Status getCombinedSubmissionStatus() {
		return combinedSubmissionStatus;
	}

	public void setCombinedSubmissionStatus(Submission_Status combinedSubmissionStatus) {
		this.combinedSubmissionStatus = combinedSubmissionStatus;
	}

	public boolean getSahApproved() {
		return sahApproved;
	}

	public void setSahApproved(boolean sahApproved) {
		this.sahApproved = sahApproved;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
