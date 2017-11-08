package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExpensesApprovalService {

	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser,OrgLocations orgLoc, String expenseStatus, String startDate, String endDate) throws Exception;
	
	ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId,Long locationsId) throws Exception;
	
	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId) throws Exception;
	
    ExpensesApproverHierarchy updateSubmissionApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
    
    User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId,String reason,User loginUser, String type, String isSuperUser);
	  
    //ExpensesApproverHierarchy getExpensesApproverHierarchyByExpenseIdAndApproverUserId(Long expenseId,Long approverUserId);
    
	Integer updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason) throws Exception;
	
	ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId);
	
	Boolean isTheExpenseTotallyApproved(Long expenseId);
	
	public Boolean isReopenButtonVisibleForLoginOrg(Long expenseId,Long locationsId);
	
	ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId);
	
	void updateExpenseStatusByExpenseId(Long expenseId,String status);

	File getExpensesApproverHierarchyListForPdf(List<ExpensesApproverHierarchy> expApperoverHierarchy, OrgLocations orgLoc, User loginUser) throws IOException, Exception;

	File getExpensesApproverHierarchyListForExcel(List<ExpensesApproverHierarchy> expApperoverHierarchy,OrgLocations orgLoc, User loginUser)throws IOException, Exception;
	
	public void sendReopenExpenseAcceptOrRejectMail(Expenses expense,String reason, User loginUser, OrgLocations orgLoc, String type, User reopenedUser, String projectName) throws Exception;

}
