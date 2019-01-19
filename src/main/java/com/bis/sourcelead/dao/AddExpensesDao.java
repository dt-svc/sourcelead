package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Expenses;

public interface AddExpensesDao {

	public Expenses storeAddExpensesData(Expenses expenses) throws Exception;

	public Long checkExpensIdNumberInDatebase(Long orgLocId, Integer generatedIdcount);

	public void deleteDocumentByDocumentId(Long documentId);

	public long changeStatusToBillable(long expenseEntriesId,String  billableStatus);

}
