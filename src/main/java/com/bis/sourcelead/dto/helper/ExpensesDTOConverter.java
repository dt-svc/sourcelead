package com.bis.sourcelead.dto.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;

public class ExpensesDTOConverter {

	public static ExpensesDTO  getExpensesDTOFromExpenses(Expenses expenses,ExpensesDTO expensesDTO){
		expensesDTO = new ExpensesDTO();
		expensesDTO.setId(expenses.getId());
		expensesDTO.setExpenseAutoId(expenses.getExpenseId());
		expensesDTO.setExpensePurpose(expenses.getPurpose());
		expensesDTO.setTotalAmount(expenses.getTotalAmount());
		BigDecimal totalAmo = new BigDecimal(expenses.getTotalAmount());
		BigDecimal totalAmountRoundOff = totalAmo.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		expensesDTO.setExpenseAmountDivId(totalAmountRoundOff);
		expensesDTO.setClientName(expenses.getClientName());
		if(expenses.getProjectMembers() != null && expenses.getProjectMembers().getProjects() != null){
			expensesDTO.setProjectMemberId(expenses.getProjectMembers().getId());
			expensesDTO.setMasterCandidateId(expenses.getProjectMembers().getMasterCandidateId().getId());
			expensesDTO.setProjectId(expenses.getProjectMembers().getProjects().getId());
			expensesDTO.setProjectName(expenses.getProjectMembers().getProjects().getProjectName());
		}
		SimpleDateFormat dateFormat=expenses.getOrgLocationId().getLocFullFormDateFormatter();
		if(expenses.getStartDate() != null){
			expensesDTO.setStartDate(dateFormat.format(expenses.getStartDate()));	
		}
		if(expenses.getEndDate() != null){
			expensesDTO.setEndDate(dateFormat.format(expenses.getEndDate()));	
		}
		expensesDTO.setExpenseLocationCode(expenses.getOrgLocationId().getLocationCode());
		expensesDTO.setNoOfExpenses(expenses.getNoOfExpenses());
		List<ExpensesEntriesDTO> eeDTOList = new ArrayList<>();
		if(expenses.getExpensesEntries() != null && !expenses.getExpensesEntries().isEmpty() && expenses.getExpensesEntries().size() > 0){
			for(ExpensesEntries ee : expenses.getExpensesEntries()){
				eeDTOList.add(ExpensesEntriesDTOConverter.getExpensesEntriesDTOFromExpensesEntries(ee, expensesDTO));
			}
		}
		if(expenses.getProjectMembers() != null && expenses.getProjectMembers().getProjectMemberCandidteId() != null){
			expensesDTO.setProjectMemberName(expenses.getProjectMembers().getEmpName());
		}
		expensesDTO.setExpenseEntry(eeDTOList);
		return expensesDTO;
	}
}
