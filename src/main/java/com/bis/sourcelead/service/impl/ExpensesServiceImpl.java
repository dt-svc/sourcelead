package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.ExpensesDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.ExpensesService;

/**
 * 
 * @author : Pradeep Kumar Reddy
 *
 */

@Service
public class ExpensesServiceImpl implements ExpensesService{
	
	@Autowired
	ExpensesDao<Expenses> expensesDao;
	
	@Value("${sourceleadUrl}")
	private String sourceleadUrl;	
	
	@Override
	public List<Expenses> getAllExpenses(OrgLocations orgLoc,User loginUser) {
		List<Expenses> expensesList =  expensesDao.getAllExpenses(orgLoc.getId(),loginUser.getId());

		return expensesList;

	}
	
	@Override
	public List<Expenses> getIndividualExpense(OrgLocations orgLoc,User loginUser,String expenseId) {
		List<Expenses> expensesList =  expensesDao.getIndividualExpense(orgLoc.getId(),loginUser.getId(),expenseId);

		return expensesList;

	}
	
	@Override
	public List<ExpensesEntries> getExpensesEntries(String expenseId) {
		List<ExpensesEntries> expensesEntriesList =  expensesDao.getExpensesEntries(expenseId);

		return expensesEntriesList;

	}
	
		
}
