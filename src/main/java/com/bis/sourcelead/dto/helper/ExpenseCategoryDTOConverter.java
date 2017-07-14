package com.bis.sourcelead.dto.helper;

import com.bis.sourcelead.dto.ExpensesCategoryDTO;
import com.bis.sourcelead.entity.ExpensesCategory;

public class ExpenseCategoryDTOConverter {

	public static ExpensesCategoryDTO  getExpensesCategoryDTOFromExpensesCategory(ExpensesCategory expensesCategory){
		ExpensesCategoryDTO ecDTO = new ExpensesCategoryDTO();
		ecDTO.setId(expensesCategory.getId());
		ecDTO.setExpensesName(expensesCategory.getExpensesName());
		ecDTO.setAllowedAmount(expensesCategory.getAllowedAmount());
		ecDTO.setAmountType(expensesCategory.getAmountType());
		ecDTO.setDescription(expensesCategory.getDescription());
		return ecDTO;
	}
}
