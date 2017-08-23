package com.bis.sourcelead.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;

/**
 * @author Pradeep Kumar Reddy
 * @param <Expenses>
 * 
 */
public interface ExpensesDao<Expenses> {
	
	public List<Expenses> getAllExpenses(Long orgLocId, Long loginUserId, String expenseStatus, Date startDateUTC, Date endDateUTC);
	public List<Expenses> getIndividualExpense(Long orgLocId,Long loginUserId,String expenseId);
	public List<ExpensesEntries> getExpensesEntries(String expenseId);
	public Expenses getExpenseById(long id);
    public Expenses getExpenseByExpenseUniqueId(String expenseId, Long orgLocationID)throws Exception;
	public List<Expenses> getExpensesByProjectMemberId(Long projectMemberId, String invoiceType);
	}
	


