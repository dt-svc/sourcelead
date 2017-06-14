package com.bis.sourcelead.service;

import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;

public interface ExpensesEntryDocumentsService {
	
	public ExpensesEntryDocuments getExpensesEntryDocumentById(Long id)throws Exception;
	
}
