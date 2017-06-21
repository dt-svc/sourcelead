package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Collection;

public class ExpensesCount implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer expensesDraftCount;
	
	private Integer expensesSubmittedForApprovalCount;
	
	private Integer  expensesApprovedCount;
	
	private Integer  expensesRejectCount;
	
	private Integer empExpensesSubmittedForApprovalCount;
	
	private Integer empExpensesApprovedCount;
	
	private Integer empExpensesRejectedCount;
	
	private Integer empExpensesReopenCount;
	
	Collection<ExpensesApproverHierarchy>  expensesDraftList;
	
	Collection<ExpensesApproverHierarchy>  expensesSubmittedForApprovalList;
	
	Collection<ExpensesApproverHierarchy>  expensesApprovedList;
	
	Collection<ExpensesApproverHierarchy>  expensesRejectList;

	public Integer getExpensesDraftCount() {
		return expensesDraftCount;
	}

	public void setExpensesDraftCount(Integer expensesDraftCount) {
		this.expensesDraftCount = expensesDraftCount;
	}

	public Integer getExpensesSubmittedForApprovalCount() {
		return expensesSubmittedForApprovalCount;
	}

	public void setExpensesSubmittedForApprovalCount(Integer expensesSubmittedForApprovalCount) {
		this.expensesSubmittedForApprovalCount = expensesSubmittedForApprovalCount;
	}

	public Integer getExpensesApprovedCount() {
		return expensesApprovedCount;
	}

	public void setExpensesApprovedCount(Integer expensesApprovedCount) {
		this.expensesApprovedCount = expensesApprovedCount;
	}

	public Integer getExpensesRejectCount() {
		return expensesRejectCount;
	}

	public void setExpensesRejectCount(Integer expensesRejectCount) {
		this.expensesRejectCount = expensesRejectCount;
	}

	public Integer getEmpExpensesSubmittedForApprovalCount() {
		return empExpensesSubmittedForApprovalCount;
	}

	public void setEmpExpensesSubmittedForApprovalCount(Integer empExpensesSubmittedForApprovalCount) {
		this.empExpensesSubmittedForApprovalCount = empExpensesSubmittedForApprovalCount;
	}

	public Integer getEmpExpensesApprovedCount() {
		return empExpensesApprovedCount;
	}

	public void setEmpExpensesApprovedCount(Integer empExpensesApprovedCount) {
		this.empExpensesApprovedCount = empExpensesApprovedCount;
	}

	public Integer getEmpExpensesRejectedCount() {
		return empExpensesRejectedCount;
	}

	public void setEmpExpensesRejectedCount(Integer empExpensesRejectedCount) {
		this.empExpensesRejectedCount = empExpensesRejectedCount;
	}

	public Integer getEmpExpensesReopenCount() {
		return empExpensesReopenCount;
	}

	public void setEmpExpensesReopenCount(Integer empExpensesReopenCount) {
		this.empExpensesReopenCount = empExpensesReopenCount;
	}

	public Collection<ExpensesApproverHierarchy> getExpensesDraftList() {
		return expensesDraftList;
	}

	public void setExpensesDraftList(Collection<ExpensesApproverHierarchy> expensesDraftList) {
		this.expensesDraftList = expensesDraftList;
	}

	public Collection<ExpensesApproverHierarchy> getExpensesSubmittedForApprovalList() {
		return expensesSubmittedForApprovalList;
	}

	public void setExpensesSubmittedForApprovalList(
			Collection<ExpensesApproverHierarchy> expensesSubmittedForApprovalList) {
		this.expensesSubmittedForApprovalList = expensesSubmittedForApprovalList;
	}

	public Collection<ExpensesApproverHierarchy> getExpensesApprovedList() {
		return expensesApprovedList;
	}

	public void setExpensesApprovedList(Collection<ExpensesApproverHierarchy> expensesApprovedList) {
		this.expensesApprovedList = expensesApprovedList;
	}

	public Collection<ExpensesApproverHierarchy> getExpensesRejectList() {
		return expensesRejectList;
	}

	public void setExpensesRejectList(Collection<ExpensesApproverHierarchy> expensesRejectList) {
		this.expensesRejectList = expensesRejectList;
	}
	
}
