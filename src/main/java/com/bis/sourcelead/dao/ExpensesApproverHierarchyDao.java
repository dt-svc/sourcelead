package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

public interface ExpensesApproverHierarchyDao {

	
	public List<ExpensesApproverHierarchy>  getTotalDraftListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,Long orgLocationId, String submissionStatus)throws Exception;
	
	public  List<ExpensesApproverHierarchy>  getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus)throws Exception;
}
