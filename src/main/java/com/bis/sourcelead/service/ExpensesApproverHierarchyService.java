package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesCount;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;

public interface ExpensesApproverHierarchyService {
	
	public ExpensesCount getExpensesCountByUserIdAndOrgLocationIdForApprover(Long userId,Long orgLocationId) throws Exception;
	
	public ExpensesCount getExpensesCountByUserIdAndOrgLocationId(Long userId,Long orgLocationId) throws Exception;
	

}
