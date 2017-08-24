package com.bis.sourcelead.dto.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.ExpensesEntryDocumentsDTO;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;

public class ExpensesEntriesDTOConverter {

	public static ExpensesEntriesDTO  getExpensesEntriesDTOFromExpensesEntries(ExpensesEntries expensesEntries,ExpensesDTO expensesDTO){
		SimpleDateFormat dateFormat=expensesEntries.getExpensesId().getOrgLocationId().getLocFullFormDateFormatter();
		ExpensesEntriesDTO expensesEntriesDTO = new ExpensesEntriesDTO();
		expensesEntriesDTO.setId(expensesEntries.getId());
		expensesEntriesDTO.setExpensesId(expensesDTO);
		expensesEntriesDTO.setExpenseDate(dateFormat.format(expensesEntries.getExpenseDate()));
		expensesEntriesDTO.setPaymentMode(expensesEntries.getPaymentMode());
		expensesEntriesDTO.setReceiptIssuedBy(expensesEntries.getReceiptIssuedBy());
		expensesEntriesDTO.setCurrencyType(expensesEntries.getCurrencyType());
		expensesEntriesDTO.setAmount(expensesEntries.getAmount());
		BigDecimal totalAmo = new BigDecimal(expensesEntries.getAmount());
		BigDecimal amountRoundOff = totalAmo.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		expensesEntriesDTO.setAmountRoundOff(amountRoundOff);	
		expensesEntriesDTO.setDescription(expensesEntries.getDescription());
		if(expensesEntries.getExpensesCategoryId() != null){
			expensesEntriesDTO.setExpensesCategoryId(ExpenseCategoryDTOConverter.getExpensesCategoryDTOFromExpensesCategory(expensesEntries.getExpensesCategoryId()));
		}
		List<ExpensesEntryDocumentsDTO> eedDTOList = new ArrayList<>();
		if(expensesEntries.getExpensesEntryDocuments() != null && expensesEntries.getExpensesEntryDocuments().size() > 0){
			for(ExpensesEntryDocuments eed :expensesEntries.getExpensesEntryDocuments()){
				eedDTOList.add(ExpensesEntryDocumentsDTOConverter.getExpensesEntryDocumentsDTOFromExpensesEntryDocuments(eed, expensesEntriesDTO));
			}
		}
		
		if(eedDTOList != null && !eedDTOList.isEmpty() && eedDTOList.size() > 0){
			expensesEntriesDTO.setExpensesEntryDocuments(eedDTOList);
		}
		return expensesEntriesDTO;
	}
}
