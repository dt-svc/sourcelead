package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesHistory;

public interface ExpensesHistoryService {

	public ExpensesHistory storeExpensesHistory(ExpensesHistory expensesHistory) throws Exception;
	
	public ExpensesHistory getExpensesHistoryByExpensesHistoryId(Long expensesHistoryId) throws Exception;
	
	public List<ExpensesHistory> getExpensesHistoryListByExpensesHistoryId(Long expensesHistoryId) throws Exception;
	
}
