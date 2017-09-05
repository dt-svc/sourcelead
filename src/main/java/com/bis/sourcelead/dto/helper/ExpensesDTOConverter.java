package com.bis.sourcelead.dto.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.ibm.icu.math.BigDecimal;

public class ExpensesDTOConverter {

	public static ExpensesDTO  getExpensesDTOFromExpenses(Expenses expenses,ExpensesDTO expensesDTO){
		expensesDTO = new ExpensesDTO();
		expensesDTO.setId(expenses.getId());
		expensesDTO.setExpenseId(expenses.getExpenseId());
		expensesDTO.setExpensePurpose(expenses.getPurpose());
		expensesDTO.setTotalAmount(expenses.getTotalAmount());
		BigDecimal totalAmo = new BigDecimal(expenses.getTotalAmount());
		BigDecimal totalAmountRoundOff = totalAmo.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		expensesDTO.setExpenseAmountDivId(totalAmountRoundOff);
		expensesDTO.setClientNameExpense(expenses.getClientName());
		expensesDTO.setProjectName(expenses.getProjectMembers().getProjects().getProjectName());
		expensesDTO.setProjectMemberId(expenses.getProjectMembers().getId());
		SimpleDateFormat dateFormat=expenses.getOrgLocationId().getLocFullFormDateFormatter();
		if(expenses.getStartDate() != null){
			expensesDTO.setStartDateExpense(dateFormat.format(expenses.getStartDate()));	
		}
		if(expenses.getEndDate() != null){
			expensesDTO.setEndDateExpense(dateFormat.format(expenses.getEndDate()));	
		}
		expensesDTO.setNoOfExpenses(expenses.getNoOfExpenses());
		List<ExpensesEntriesDTO> eeDTOList = new ArrayList<>();
		if(expenses.getExpensesEntries() != null && !expenses.getExpensesEntries().isEmpty() && expenses.getExpensesEntries().size() > 0){
			for(ExpensesEntries ee : expenses.getExpensesEntries()){
				eeDTOList.add(ExpensesEntriesDTOConverter.getExpensesEntriesDTOFromExpensesEntries(ee, expensesDTO));
			}
		}
		expensesDTO.setExpensesEntries(eeDTOList);
		return expensesDTO;
	}
}
