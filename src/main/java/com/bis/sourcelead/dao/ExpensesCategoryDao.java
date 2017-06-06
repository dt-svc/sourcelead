package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesCategory;

/**
 * @author BIS_Prasad
 * 
 */
public interface ExpensesCategoryDao {
	
	public List<ExpensesCategory> getAllExpensesCategoryByLocationId(Long orgLocationId);
	public ExpensesCategory storeExpenseCategory(ExpensesCategory expensesCategory) throws Exception;
	
	public ExpensesCategory getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long expCategoryId,Long orgLocationId) throws Exception;
}
