package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExpensesApprovalService {

	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser,OrgLocations orgLoc) throws Exception;
	
	ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId) throws Exception;
	
	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId) throws Exception;
	
    ExpensesApproverHierarchy updateSubmissionApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
	  
    //ExpensesApproverHierarchy getExpensesApproverHierarchyByExpenseIdAndApproverUserId(Long expenseId,Long approverUserId);
    
	void updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason) throws Exception;
	
	ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId);
	
	Boolean isTheExpenseTotallyApproved(Long expenseId);
	
	ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId);
	
	void updateExpenseStatusByExpenseId(Long expenseId,String status);

}
