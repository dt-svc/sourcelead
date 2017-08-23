package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.ExpensesEntryDocuments;


public interface ExpensesEntryDocumentsDao {

	public void removeExpensesEntryDocouments(Long id) throws Exception;
	
	public void removeExpensesEntryDocoumentList(List<Long> id) throws Exception;
	
	public void removeAllExpensesEntryDocoumentsByEntryId(Long entryId) throws Exception;
	
	public ExpensesEntryDocuments getExpensesEntryDocumentById(Long id)throws Exception;
	
}
