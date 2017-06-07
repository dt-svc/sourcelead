package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

public interface ExpensesApprovalDao {

	List<ExpensesApproverHierarchy> getExpensesApproverHierarchyByApproverUserIdAndOrgLocationId(Long approverUserId, Long orglocationId);

}
