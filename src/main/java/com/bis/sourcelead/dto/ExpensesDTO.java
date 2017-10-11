package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.enums.Submission_Status;

public class ExpensesDTO implements Serializable {
private static final long serialVersionUID = 1L;

	private Long id;
	
	private String expenseAutoId;
	
	private String expensePurpose;
	
	private long projectId;
	
	private String projectMemberName;
	
	private String projectName;
	
	private Long projectMemberId;
	
	private String clientName;
	
	private float totalAmount;
	
	private String noOfExpenses;
	
	private Submission_Status expenseStatus;
	
	private Submission_Status combinedSubmissionStatus;
	
    private List<ExpensesEntriesDTO> expenseEntry;
	
	private String startDate;
	
	private String endDate;
	
	private String createdBy;
	
	private boolean sahApproved;
	
	private BigDecimal expenseAmountDivId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExpenseAutoId() {
		return expenseAutoId;
	}

	public void setExpenseAutoId(String expenseAutoId) {
		this.expenseAutoId = expenseAutoId;
	}

	public String getExpensePurpose() {
		return expensePurpose;
	}

	public void setExpensePurpose(String expensePurpose) {
		this.expensePurpose = expensePurpose;
	}

	
	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
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

	public Submission_Status getCombinedSubmissionStatus() {
		return combinedSubmissionStatus;
	}

	public void setCombinedSubmissionStatus(Submission_Status combinedSubmissionStatus) {
		this.combinedSubmissionStatus = combinedSubmissionStatus;
	}

	public List<ExpensesEntriesDTO> getExpenseEntry() {
		return expenseEntry;
	}

	public void setExpenseEntry(List<ExpensesEntriesDTO> expenseEntry) {
		this.expenseEntry = expenseEntry;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public boolean isSahApproved() {
		return sahApproved;
	}

	public void setSahApproved(boolean sahApproved) {
		this.sahApproved = sahApproved;
	}

	public BigDecimal getExpenseAmountDivId() {
		return expenseAmountDivId;
	}

	public void setExpenseAmountDivId(BigDecimal expenseAmountDivId) {
		this.expenseAmountDivId = expenseAmountDivId;
	}

	public String getProjectMemberName() {
		return projectMemberName;
	}

	public void setProjectMemberName(String projectMemberName) {
		this.projectMemberName = projectMemberName;
	}

	
}
