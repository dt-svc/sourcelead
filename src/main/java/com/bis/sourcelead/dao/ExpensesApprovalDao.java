package com.bis.sourcelead.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExpensesApprovalDao {

	ResponseList<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(Long approverUserId, Long orglocationId, String expenseStatus, Date startDateUTC, Date endDateUTC, String searchValue,int start,int length,String isGlobalUser) throws ParseException, Exception;
	
   ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId,Long locationsId,String isGlobalUesr) throws Exception;
	
	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId) throws Exception;
	
    ExpensesApproverHierarchy updateSubmissionApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
  
    User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId,String reason,User loginUser, String type, String isSuperUser);
    
    Integer updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason,OrgLocations location,String isGlobalUser) throws Exception;
    
    ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId);
    
    ExpensesApproverHierarchy saveExpensesApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
    
    Boolean isTheExpenseTotallyApproved(Long expenseId);
    
    public Boolean isReopenButtonVisibleForLoginOrg(Long expenseId,Long locationsId,String isGlobalUser);
    
    ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId);
    
    void updateExpenseStatusByExpenseId(Long expenseId,String status);
    
    public ExpensesApproverHierarchy getApprovedExpenseByExpenseIdAndLocationId(Long expenseId,Long locationsId);


	 public List<ExpensesApproverHierarchy> makeReopenReasonStatusNull(Long id);

    //ExpensesApproverHierarchy getExpensesApproverHierarchyByExpenseIdAndApproverUserId(Long expenseId,Long approverUserId);
}
