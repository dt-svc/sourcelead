package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;

public interface ExpenseProjectApproverHierarchyDao {
	public List<ExpenseProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;
}
