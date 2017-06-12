package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

public interface ExpensesApprovalDao {

	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(Long approverUserId, Long orglocationId);
	
   ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId) throws Exception;
	
	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId) throws Exception;
	
    ExpensesApproverHierarchy updateSubmissionApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
  
    void updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason) throws Exception;
    
    ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId);
    
    Boolean isTheExpenseTotallyApproved(Long expenseId);
    
    ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId);
    
    void updateExpenseStatusByExpenseId(Long expenseId,String status);

    //ExpensesApproverHierarchy getExpensesApproverHierarchyByExpenseIdAndApproverUserId(Long expenseId,Long approverUserId);
}
