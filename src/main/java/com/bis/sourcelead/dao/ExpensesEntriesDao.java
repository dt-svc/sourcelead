package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesEntries;

public interface ExpensesEntriesDao {

	public ExpensesEntries addExpensesEntries(ExpensesEntries entries)throws Exception;

	public List<ExpensesEntries> getExpensesEntriesByExpenseId(long expenseId);

}
