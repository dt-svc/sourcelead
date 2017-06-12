package com.bis.sourcelead.dao;

public interface ExpensesEntryDocumentsDao {

	public void removeExpensesEntryDocouments(Long id) throws Exception;
	
	public void removeAllExpensesEntryDocoumentsByEntryId(Long entryId) throws Exception;
	
}
