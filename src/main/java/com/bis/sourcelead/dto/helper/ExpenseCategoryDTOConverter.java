package com.bis.sourcelead.dto.helper;

import com.bis.sourcelead.dto.ExpensesCategoryListDTO;
import com.bis.sourcelead.entity.ExpensesCategory;

public class ExpenseCategoryDTOConverter {

	public static ExpensesCategoryListDTO  getExpensesCategoryDTOFromExpensesCategory(ExpensesCategory expensesCategory){
		ExpensesCategoryListDTO ecDTO = new ExpensesCategoryListDTO();
		ecDTO.setId(expensesCategory.getId());
		ecDTO.setCategoryName(expensesCategory.getExpensesName());
		ecDTO.setAllowedAmount(expensesCategory.getAllowedAmount());
		ecDTO.setAmountType(expensesCategory.getAmountType());
		ecDTO.setDescription(expensesCategory.getDescription());
		ecDTO.setIsAttachemtRequired(expensesCategory.getIsAttachemtRequired());
		return ecDTO;
	}
}
