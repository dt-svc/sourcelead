package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExpensesApprovalService {

	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(User loginUser,OrgLocations orgLoc) throws Exception;
	
    ExpensesApproverHierarchy updateSubmissionApproverHierarchy(ExpensesApproverHierarchy expensesApproverHierarchy) throws Exception;
	  
	void updateApprovalStatusByApproverForExpense(Long expenseId,Long approverUserId,String status,String rejectedReason) throws Exception;

	File getExpensesApproverHierarchyListForPdf(List<ExpensesApproverHierarchy> expApperoverHierarchy, OrgLocations orgLoc) throws IOException;

	File getExpensesApproverHierarchyListForExcel(List<ExpensesApproverHierarchy> expApperoverHierarchy,OrgLocations orgLoc)throws IOException;

}
