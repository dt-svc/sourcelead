package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExpensesService {
	
	public List<Expenses> getAllExpenses(OrgLocations orgLoc, User loginUser);

	public Expenses getExpenseByExpenseId(long expenseId);

}
