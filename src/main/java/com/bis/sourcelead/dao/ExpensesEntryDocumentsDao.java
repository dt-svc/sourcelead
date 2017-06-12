package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.ExpensesEntryDocuments;
import com.bis.sourcelead.entity.TimesheetSubmissionsDocuments;

public interface ExpensesEntryDocumentsDao {

	public void removeExpensesEntryDocouments(Long id) throws Exception;
	
	public void removeAllExpensesEntryDocoumentsByEntryId(Long entryId) throws Exception;
	
	public ExpensesEntryDocuments getExpensesEntryDocumentById(Long id)throws Exception;
	
}
