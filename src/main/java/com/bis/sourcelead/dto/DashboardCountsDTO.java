package com.bis.sourcelead.dto;

import java.io.Serializable;

public class DashboardCountsDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long interviewCount;

	private Long taskCount;

	private float savedTimesheetCount;

	private float waitingForApprovalTimesheetCount;

	private float approvedTimesheetCount;

	private float rejectedTimesheetCount;

	private float expensesDraftCount;

	private float expensesSubmittedForApprovalCount;

	private float expensesApprovedCount;

	private float expensesRejectCount;

	private float empExpensesReopenCount;

	private String leavesApprovedCount;

	private String leavesRejectedCount;

	private String leavesPendingCount;

	private long leavesBalanceCount;

	public String getLeavesApprovedCount() {
		return leavesApprovedCount;
	}

	public void setLeavesApprovedCount(String leavesApprovedCount) {
		this.leavesApprovedCount = leavesApprovedCount;
	}

	public String getLeavesRejectedCount() {
		return leavesRejectedCount;
	}

	public void setLeavesRejectedCount(String leavesRejectedCount) {
		this.leavesRejectedCount = leavesRejectedCount;
	}

	public String getLeavesPendingCount() {
		return leavesPendingCount;
	}

	public void setLeavesPendingCount(String leavesPendingCount) {
		this.leavesPendingCount = leavesPendingCount;
	}

	public long getLeavesBalanceCount() {
		return leavesBalanceCount;
	}

	public void setLeavesBalanceCount(long leavesBalanceCount) {
		this.leavesBalanceCount = leavesBalanceCount;
	}

	public float getSavedTimesheetCount() {
		return savedTimesheetCount;
	}

	public void setSavedTimesheetCount(float savedTimesheetCount) {
		this.savedTimesheetCount = savedTimesheetCount;
	}

	public float getWaitingForApprovalTimesheetCount() {
		return waitingForApprovalTimesheetCount;
	}

	public void setWaitingForApprovalTimesheetCount(float waitingForApprovalTimesheetCount) {
		this.waitingForApprovalTimesheetCount = waitingForApprovalTimesheetCount;
	}

	public float getApprovedTimesheetCount() {
		return approvedTimesheetCount;
	}

	public void setApprovedTimesheetCount(float approvedTimesheetCount) {
		this.approvedTimesheetCount = approvedTimesheetCount;
	}

	public float getRejectedTimesheetCount() {
		return rejectedTimesheetCount;
	}

	public void setRejectedTimesheetCount(float rejectedTimesheetCount) {
		this.rejectedTimesheetCount = rejectedTimesheetCount;
	}

	public float getExpensesDraftCount() {
		return expensesDraftCount;
	}

	public void setExpensesDraftCount(float expensesDraftCount) {
		this.expensesDraftCount = expensesDraftCount;
	}

	public float getExpensesSubmittedForApprovalCount() {
		return expensesSubmittedForApprovalCount;
	}

	public void setExpensesSubmittedForApprovalCount(float expensesSubmittedForApprovalCount) {
		this.expensesSubmittedForApprovalCount = expensesSubmittedForApprovalCount;
	}

	public float getExpensesApprovedCount() {
		return expensesApprovedCount;
	}

	public void setExpensesApprovedCount(float expensesApprovedCount) {
		this.expensesApprovedCount = expensesApprovedCount;
	}

	public float getExpensesRejectCount() {
		return expensesRejectCount;
	}

	public void setExpensesRejectCount(float expensesRejectCount) {
		this.expensesRejectCount = expensesRejectCount;
	}

	public float getEmpExpensesReopenCount() {
		return empExpensesReopenCount;
	}

	public void setEmpExpensesReopenCount(float empExpensesReopenCount) {
		this.empExpensesReopenCount = empExpensesReopenCount;
	}

	public Long getInterviewCount() {
		return interviewCount;
	}

	public void setInterviewCount(Long interviewCount) {
		this.interviewCount = interviewCount;
	}

	public Long getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(Long taskCount) {
		this.taskCount = taskCount;
	}

}
