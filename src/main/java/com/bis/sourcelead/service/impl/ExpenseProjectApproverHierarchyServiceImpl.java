package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ExpenseProjectApproverHierarchyDao;
import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;
import com.bis.sourcelead.service.ExpenseProjectApproverHierarchyService;

@Service
public class ExpenseProjectApproverHierarchyServiceImpl implements ExpenseProjectApproverHierarchyService {

	@Autowired
	private  ExpenseProjectApproverHierarchyDao  expenseProjectApproverHierarchyDao;

	@Override
	public List<ExpenseProjectApproverHierarchy> getProjectsHierarchyListByProjectIdAndOrgLocationId(Long projectId,
			Long orgLocationId) throws Exception {
		return expenseProjectApproverHierarchyDao.getProjectsHierarchyListByProjectIdAndOrgLocationId(projectId, orgLocationId);
	}
	
	@Override
	public List<ExpenseProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
			long projectMemberMasterCandidateId, long projectId) throws Exception {
		return expenseProjectApproverHierarchyDao.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMemberMasterCandidateId, projectId);
	}

	@Override
	public ExpenseProjectApproverHierarchy saveProjectExpenseProjectApproverHierarchy(
			ExpenseProjectApproverHierarchy expenseProjectApproverHierarchy) throws Exception {
		return expenseProjectApproverHierarchyDao.saveProjectExpenseProjectApproverHierarchy(expenseProjectApproverHierarchy);
	}

	@Override
	public ExpenseProjectApproverHierarchy getExpenseApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(
			Long approverUserId, Long projectClientId) throws Exception {
		return expenseProjectApproverHierarchyDao.getExpenseApproverUsersByProjectIdAndProjectClientIdAndApproverUserId(approverUserId, projectClientId);
	}
	
	
	@Override
	public ExpenseProjectApproverHierarchy getApproverUsersByProjectIdAndProjectClientId(Long projectId, Long projectClientId)
			throws Exception{
		return expenseProjectApproverHierarchyDao.getApproverUsersByProjectIdAndProjectClientId(projectId, projectClientId);
	}

	@Override
	public String getExpenseApprovalMechanismTypeByProjectClientId(Long projectClientId) throws Exception {
		return expenseProjectApproverHierarchyDao.getExpenseApprovalMechanismTypeByProjectClientId(projectClientId);
	}
	
	
}
