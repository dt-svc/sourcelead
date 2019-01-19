package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.User;

public interface ExpensesCategoryService {

	public List<ExpensesCategory> getAllExpensesCategoryByLocationIdAndCategoryId(Long orgLocationId);
	
	public List<ExpensesCategory> getAllExpensesCategoryByLocationIdAndStatus(Long orgLocationId, String status,String isGlobalUser);
	
	/**
	 * @param jsonObject
	 * @param orgLocationId
	 * @return ExpensesCategory
	 * @throws Exception
	 */
	public ExpensesCategory storeExpenseCategory(JSONObject jsonObject,Long orgLocationId,User user) throws Exception;
	
	
	public ExpensesCategory getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long expCategoryId) throws Exception;
	
	public ExpensesCategory removeExpensesCategoryByCategoryId(Long expCategoryId) throws Exception;
	
	public Boolean validateExpensesCategoryType(Long orgLocationId, String categoryName) throws Exception;

	public String getExpenseAttachmentStatusByCategoryId(Long categoryId);
}
