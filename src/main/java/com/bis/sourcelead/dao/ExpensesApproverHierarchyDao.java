package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

public interface ExpensesApproverHierarchyDao {

	public List<Expenses>  getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String submissionStatus)throws Exception;
	
	public  List<ExpensesApproverHierarchy>  getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String submissionStatus,String isGlobalAccess)throws Exception;
}
