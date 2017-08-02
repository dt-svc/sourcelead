package com.bis.sourcelead.service;

import java.io.File;
import java.util.List;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface ExpensesService {
	
	public List<Expenses> getAllExpenses(OrgLocations orgLoc, User loginUser, String expenseStatus, String startDate, String endDate) throws Exception;

	public List<Expenses> getIndividualExpense(OrgLocations orgLoc, User loginUser, String expenseId);

	public List<ExpensesEntries> getExpensesEntries(String expenseId);

	public Expenses getExpenseByExpenseId(long expenseId);

	public Expenses getExpenseByExpenseUniqueId(String expenseId, Long orgLocationId) throws Exception;

	public File exportMyExpensesToPdf(Expenses expenseDetails, OrgLocations orgLoc, User loginUser, String approverType) throws Exception;

	public File exportMyExpensesToExcel(Expenses expenseDetails, OrgLocations orgLoc, User loginUser, String approverType) throws Exception;
	
	public File exportAllMyExpensesToPdf(List<Expenses> expensesList, OrgLocations orgLoc, User loginUser) throws Exception;

	public File exportAllMyExpensesToExcel(List<Expenses> expensesList, OrgLocations orgLoc) throws Exception;
	
	public  List<ExpensesApproverHierarchy>  getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String expenseStatus)throws Exception;
	
	public List<Expenses>  getTotalMyExpensesListByuserIdAndOrgLocationIdForEmployeeLogin(Long userId,Long orgLocationId, String expenseStatus)throws Exception;

	public List<Expenses> getExpensesByProjectMemberId(Long projectMemberId)throws Exception;

	
}
