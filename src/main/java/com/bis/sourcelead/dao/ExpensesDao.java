package com.bis.sourcelead.dao;

import java.util.List;

/**
 * @author Pradeep Kumar Reddy
 * @param <Expenses>
 * 
 */
public interface ExpensesDao<Expenses> {
	
	public List<Expenses> getAllExpenses(Long orgLocId,Long loginUserId);
}
