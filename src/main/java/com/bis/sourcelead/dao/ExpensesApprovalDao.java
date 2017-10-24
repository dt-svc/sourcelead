package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;


import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.User;

public interface ExpensesApprovalDao {

	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(Long approverUserId, Long orglocationId, String expenseStatus, Date startDateUTC, Date endDateUTC);
	
   ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId,Long locationsId) throws Exception;
	
	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId) throws Exception;
	
    ExpensesApproverHierarchy updateSubmissionApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
  
    User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId,String reason,User loginUser, String type, String isSuperUser);
    
    Integer updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason) throws Exception;
    
    ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId);
    
    Boolean isTheExpenseTotallyApproved(Long expenseId);
    
    public Boolean isReopenButtonVisibleForLoginOrg(Long expenseId,Long locationsId);
    
    ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId);
    
    void updateExpenseStatusByExpenseId(Long expenseId,String status);

    //ExpensesApproverHierarchy getExpensesApproverHierarchyByExpenseIdAndApproverUserId(Long expenseId,Long approverUserId);
}
