package com.bis.sourcelead.dao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.ExpensePendingApproveReportView;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;

/**
 * @author Pradeep Kumar Reddy
 * @param <Expenses>
 * 
 */
public interface ExpensesDao {
	
	public ResponseList<Expenses> getAllExpenses(Long masterCandidateId,String expenseStatus, Date startDateUTC, Date endDateUTC, String searchValue, int start, int length, OrgLocations orgLoc) throws ParseException;
	public List<Expenses> getIndividualExpense(Long orgLocId,Long loginUserId,String expenseId);
	public List<ExpensesEntries> getExpensesEntries(String expenseId);
	public Expenses getExpenseById(long id);
    public Expenses getExpenseByExpenseUniqueId(String expenseId, Long loginUserId,String isGlobalUser)throws Exception;
	public List<Expenses> getExpensesByProjectMemberId(Long projectMemberId, String invoiceType,Long expenseId);
	public List<Expenses> getExpensesByProjectIdAndMasterCandidateId(Long projectId,Long masterCandidateId) throws Exception;
	public List<Expenses> getAllExpensesByOrgLocationId(Long orgLocationId);
	public List<Expenses> getAllExpensesByProjectIdOrgLocationIdStartDateAndEndDate(Long projectId, Long orglocationId,Date startDate, Date endDate);
	public BigDecimal getExpenseAmountByCandidateIdAndExpenseStartAndEndDateForClaimedExpenses(Long candidateId,Date startDate, Date endDate);
	public boolean changeExpenseBillingStatus(long expenseId, String billingStatus);
	List<Expenses> getAllExpenses();
	public List<ExpensePendingApproveReportView> getPendingForApprovalExpenseReportByProjectIds(String projectIds,Date frequencyStartDate, Date frequencyEndDate);
}
	


