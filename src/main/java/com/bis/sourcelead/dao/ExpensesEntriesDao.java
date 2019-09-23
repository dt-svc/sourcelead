package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Set;

import com.bis.sourcelead.entity.ExpensesEntries;

public interface ExpensesEntriesDao {

	public ExpensesEntries getExpensesEntriesById(Long id)throws Exception;
	
	public ExpensesEntries addExpensesEntries(ExpensesEntries entries)throws Exception;

	public List<ExpensesEntries> getExpensesEntriesByExpenseId(long expenseId);
	
	public List<Long> getExpensesEntryIdsByExpenseId(long expenseId);
	
	public void removeAllExpenseEntries(List<Long> entryIds) throws Exception;

	public int dumpExpenceCategoryAmountToOldRecords();

	public int changeDocumentNameWithWhiteSpacesToUnderScore(String staticPath);

}
