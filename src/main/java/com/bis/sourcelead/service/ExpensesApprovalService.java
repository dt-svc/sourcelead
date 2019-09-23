package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.mail.internet.AddressException;

import com.bis.sourcelead.dto.ExpensesApproverDTO;
import com.bis.sourcelead.dto.ExpensesApproverHierarchyDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExpensesApprovalService {

	ExpensesApproverDTO getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser,OrgLocations orgLoc,ExpensesApproverDTO expensesApproverDTO,String isGlobalUser) throws Exception;
	
	ExpensesApproverHierarchy getExpensesApproverHierarchyByApproverUserIdAndExpenseId(Long approverUserId,Long expenseId,Long locationsId,String isGlobalUser) throws Exception;
	
	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyListByExpenseId(Long expenseId) throws Exception;
	
    ExpensesApproverHierarchy updateSubmissionApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
    
    User updateExpenseStatusByExpenseIdAndReOpenReason(Long expenseId,String reason,User loginUser, String type, String isSuperUser);
	  
    //ExpensesApproverHierarchy getExpensesApproverHierarchyByExpenseIdAndApproverUserId(Long expenseId,Long approverUserId);
    
	Integer updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason,OrgLocations location,String isGlobalUser) throws Exception;
	
	ExpensesApproverHierarchy isThereAnyApproverApprovedTheExpense(Long expenseId);
	
	Boolean isTheExpenseTotallyApproved(Long expenseId);
	
	public Boolean isReopenButtonVisibleForLoginOrg(Long expenseId,Long locationsId,String isGlobalUser);
	
	ExpensesApproverHierarchy getexpensesApproverHierarchyForEmployeeByExpenseId(Long expenseId);
	
	public ExpensesApproverHierarchy getApprovedExpenseByExpenseIdAndLocationId(Long expenseId,Long locationsId);
	
	ExpensesApproverHierarchy saveExpensesApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
	
	void updateExpenseStatusByExpenseId(Long expenseId,String status);

	File getExpensesApproverHierarchyListForPdf(List<ExpensesApproverHierarchyDTO> expApperoverHierarchy, OrgLocations orgLoc, User loginUser,String isGlobalUser) throws IOException, Exception;

	File getExpensesApproverHierarchyListForExcel(List<ExpensesApproverHierarchyDTO> expApperoverHierarchy,OrgLocations orgLoc, User loginUser,String isGlobalUser)throws IOException, Exception;

	void sendExpenseReopenAcceptMailToEmployee(Expenses expense, OrgLocations orgLoc, String type, User reopenedUser, User loginUser) throws AddressException, Exception;

	void sendReopenExpenseAcceptOrRejectMail(Expenses expense, String reason, User superUser, OrgLocations orgLoc,String type, User reopenedUser, User loginUser, String projectName) throws AddressException, Exception;

}
