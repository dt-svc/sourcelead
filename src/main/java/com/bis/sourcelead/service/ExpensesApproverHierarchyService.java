package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.ExpensesCount;

public interface ExpensesApproverHierarchyService {
	
	public ExpensesCount getExpensesCountByUserIdAndOrgLocationIdForApprover(Long userId,Long orgLocationId,String isGlobalAccess) throws Exception;
	
	public ExpensesCount getExpensesCountByMasterCandidateId(Long masterCandidateId) throws Exception;
	

}
