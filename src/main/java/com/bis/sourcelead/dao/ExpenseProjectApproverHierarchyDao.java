package com.bis.sourcelead.dao;
import java.util.List;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;



public interface ExpenseProjectApproverHierarchyDao {
	
	public ExpenseProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId, Long projectClientId) throws Exception;
	
	public List<ExpenseProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,Long orgLocationId) throws Exception;
	
	public void deleteProjectExpenseProjectApproverHierarchy(ExpenseProjectApproverHierarchy expenseProjectApproverHierarchy) throws Exception;
	
	public List<ExpenseProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;

	public ExpenseProjectApproverHierarchy saveProjectExpenseProjectApproverHierarchy(ExpenseProjectApproverHierarchy expenseProjectApproverHierarchy) throws Exception;
	
	public ExpenseProjectApproverHierarchy getExpenseApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(Long approverUserId, Long projectClientId) throws Exception;
	
	String getExpenseApprovalMechanismTypeByProjectClientId(Long projectClientId) throws Exception;
}
