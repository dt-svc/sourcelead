package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ExpenseProjectApproverHierarchy;

public interface ExpenseProjectApproverHierarchyService {
	
	public List<ExpenseProjectApproverHierarchy> getActiveOrgApproverUsersByProjectMemberMasterCandidateIdAndProjectId(long projectMemberMasterCandidateId,long projectId) throws Exception;

}
