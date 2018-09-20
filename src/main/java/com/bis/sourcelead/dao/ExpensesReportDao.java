package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;

public interface ExpensesReportDao {

	List<ExpensesApproverHierarchy> getAllExpensesReportListByLoginUserIdAndOrgLocationId(Long loginUserId, Long loginOrgLocationId) throws Exception;

	List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long proMembersId,Date convertedStartDate, Date convertedEndDate);

	List<ExpensesApproverHierarchy> getExpensesTotalMontWiseCountBasedOnprojectMemberIdStartDateAndEndDate(Date startDate, Date endDate,Long projectMemberId);

	List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(Long projectMembersId,Date startDate, Date endDate);

}
