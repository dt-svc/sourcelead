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
	public List<ExpenseProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(
			long projectMemberMasterCandidateId, long projectId) throws Exception {
		// TODO Auto-generated method stub
		return expenseProjectApproverHierarchyDao.getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(projectMemberMasterCandidateId, projectId);
	}

	@Override
	public ExpenseProjectApproverHierarchy saveProjectExpenseProjectApproverHierarchy(
			ExpenseProjectApproverHierarchy expenseProjectApproverHierarchy) throws Exception {
		return expenseProjectApproverHierarchyDao.saveProjectExpenseProjectApproverHierarchy(expenseProjectApproverHierarchy);
	}
	
	
}
