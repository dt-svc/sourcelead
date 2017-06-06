package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.ExpensesCategory;
import com.bis.sourcelead.entity.User;

public interface ExpensesCategoryService {

	public List<ExpensesCategory> getAllExpensesCategoryByLocationIdAndCategoryId(Long orgLocationId);
	
	/**
	 * @param jsonObject
	 * @param orgLocationId
	 * @return ExpensesCategory
	 * @throws Exception
	 */
	public ExpensesCategory storeExpenseCategory(JSONObject jsonObject,Long orgLocationId,User user) throws Exception;
	
	
	public ExpensesCategory getExpensesCategoryByExpensesCategoryIdAndOrgLocationId(Long expCategoryId,Long orgLocationId) throws Exception;
}
