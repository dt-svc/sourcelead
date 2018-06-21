package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesCategory;

/**
 * @author BIS_Prasad
 * 
 */
public interface ExpensesCategoryDao {
	
	public List<ExpensesCategory> getAllExpensesCategoryByLocationId(Long orgLocationId);
	
	public List<ExpensesCategory> getAllExpensesCategoryByLocationIdAndStatus(Long orgLocationId, String status,String isGlobalUser);
	
	public ExpensesCategory storeExpenseCategory(ExpensesCategory expensesCategory) throws Exception;
	
	public ExpensesCategory getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long expCategoryId) throws Exception;
	
	public Boolean validateExpensesCategoryType(Long categoryId, String categoryType) throws Exception;

	public String getExpenseAttachmentStatusByCategoryId(Long categoryId);
}
