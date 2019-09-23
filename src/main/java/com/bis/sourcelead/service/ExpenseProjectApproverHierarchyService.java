package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;

public interface ExpenseProjectApproverHierarchyService {
	
	public List<ExpenseProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;
	
	public ExpenseProjectApproverHierarchy saveProjectExpenseProjectApproverHierarchy(ExpenseProjectApproverHierarchy expenseProjectApproverHierarchy) throws Exception;
	
	public List<ExpenseProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,Long orgLocationId) throws Exception;

	public ExpenseProjectApproverHierarchy getExpenseApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(Long approverUserId, Long projectClientId) throws Exception;
	
	public ExpenseProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId, Long projectClientId)throws Exception;
	
	String getExpenseApprovalMechanismTypeByProjectClientId(Long projectClientId) throws Exception;
			
}
