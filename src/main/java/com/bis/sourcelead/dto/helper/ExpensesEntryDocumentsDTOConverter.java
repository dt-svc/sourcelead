package com.bis.sourcelead.dto.helper;

import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.ExpensesEntryDocumentsDTO;
import com.bis.sourcelead.entity.ExpensesEntryDocuments;

public class ExpensesEntryDocumentsDTOConverter {

	public static ExpensesEntryDocumentsDTO  getExpensesEntryDocumentsDTOFromExpensesEntryDocuments(ExpensesEntryDocuments eed,ExpensesEntriesDTO eeDTO){
		ExpensesEntryDocumentsDTO expensesEntryDocumentsDTO = new ExpensesEntryDocumentsDTO();
		expensesEntryDocumentsDTO.setId(eed.getId());
		expensesEntryDocumentsDTO.setExpensesEntriesId(eeDTO);
		expensesEntryDocumentsDTO.setDocFile(eed.getDocFile());
		expensesEntryDocumentsDTO.setFileContentType(eed.getFileContentType());
		expensesEntryDocumentsDTO.setFileName(eed.getFileName());
		return expensesEntryDocumentsDTO;
	}
}
