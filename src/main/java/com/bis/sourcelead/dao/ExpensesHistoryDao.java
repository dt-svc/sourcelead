package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesHistory;

public interface ExpensesHistoryDao {

	public ExpensesHistory storeExpensesHistory(ExpensesHistory expensesHistory) throws Exception;
	
	public ExpensesHistory getExpensesHistoryByExpensesHistoryId(Long expensesHistoryId) throws Exception;
	
	public List<ExpensesHistory> getExpensesHistoryListByExpensesHistoryId(Long expensesHistoryId) throws Exception;
	
}
