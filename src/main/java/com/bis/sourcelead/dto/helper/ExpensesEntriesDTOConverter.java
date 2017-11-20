package com.bis.sourcelead.dto.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;

public class ExpensesEntriesDTOConverter {

	public static ExpensesEntriesDTO  getExpensesEntriesDTOFromExpensesEntries(ExpensesEntries expensesEntries,ExpensesDTO expensesDTO){
		SimpleDateFormat dateFormat=expensesEntries.getExpensesId().getOrgLocationId().getLocFullFormDateFormatter();
		ExpensesEntriesDTO expensesEntriesDTO = new ExpensesEntriesDTO();
		expensesEntriesDTO.setId(expensesEntries.getId());
		expensesEntriesDTO.setDateOccurred(dateFormat.format(expensesEntries.getExpenseDate()));
		expensesEntriesDTO.setPaymentMode(expensesEntries.getPaymentMode().getValue());
		expensesEntriesDTO.setReceiptIssuedBy(expensesEntries.getReceiptIssuedBy());
		expensesEntriesDTO.setCategoryAmountType(expensesEntries.getCurrencyType());
		expensesEntriesDTO.setCategoryAmount(String.valueOf(expensesEntries.getAmount()));
		BigDecimal totalAmo = new BigDecimal(expensesEntries.getAmount());
		BigDecimal amountRoundOff = totalAmo.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		expensesEntriesDTO.setAmountRoundOff(amountRoundOff);	
		expensesEntriesDTO.setEntryDescription(expensesEntries.getDescription());
		expensesEntriesDTO.setPaidBy(expensesEntries.getPaidBy());
		if(expensesEntries.getExpensesCategoryId() != null){
			expensesEntriesDTO.setCategoryTypeId(expensesEntries.getExpensesCategoryId().getId());
			expensesEntriesDTO.setCategoryName(expensesEntries.getExpensesCategoryId().getExpensesName());
			expensesEntriesDTO.setCategoryType(expensesEntries.getExpensesCategoryId().getId()+","+expensesEntries.getExpensesCategoryId().getAllowedAmount());
		}
		List<FileInfo> fileInfoList = new ArrayList<>();
		if(expensesEntries.getExpensesEntryDocuments() != null && expensesEntries.getExpensesEntryDocuments().size() > 0){
			for(ExpensesEntryDocuments eed :expensesEntries.getExpensesEntryDocuments()){
				if(eed.getStatus() == 1){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setFileDbId(eed.getId().toString());
					fileInfo.setFileName(eed.getFileName());
					fileInfo.setFileURL(eed.getFileURL());
					fileInfo.setStatus(eed.getStatus());
					fileInfo.setContentType(eed.getFileContentType());
					fileInfoList.add(fileInfo);
				}
				/*eedDTOList.add(ExpensesEntryDocumentsDTOConverter.getExpensesEntryDocumentsDTOFromExpensesEntryDocuments(eed, expensesEntriesDTO));*/
			}
		}
		
		if(fileInfoList != null && !fileInfoList.isEmpty() && fileInfoList.size() > 0){
			expensesEntriesDTO.setFileInfo(fileInfoList);
		}
		return expensesEntriesDTO;
	}
}
