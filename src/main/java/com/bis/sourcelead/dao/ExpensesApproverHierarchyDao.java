package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

public interface ExpensesApproverHierarchyDao {

	
	public List<ExpensesApproverHierarchy>  getTotalDraftListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,Long orgLocationId, String submissionStatus)throws Exception;
	public List<ExpensesApproverHierarchy>  getTotalSubmittedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,String status,Long orgLocationId) throws Exception;
	public List<ExpensesApproverHierarchy>  getTotalApprovedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,Long orgLocationId, String submissionStatus)throws Exception;
	public List<ExpensesApproverHierarchy> getTotalRejectedListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,Long orgLocationId, String submissionStatus)throws Exception;
	
	public  List<ExpensesApproverHierarchy>  getSubmissionApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus)throws Exception;
}
