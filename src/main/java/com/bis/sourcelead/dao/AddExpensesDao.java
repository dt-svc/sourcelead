package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.Expenses;

public interface AddExpensesDao {

	public Expenses storeAddExpensesData(Expenses expenses) throws Exception;

	public Expenses checkExpensIdNumberInDatebase(Long orgLocId);

	public void deleteDocumentByDocumentId(Long parseLong);

}
