package com.bis.sourcelead.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.ExpensesEntriesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.reportsDTO.PendingForExpenseApprovalReportDTO;

public interface ExpensesService {
	
	public ResponseList<Expenses> getAllExpenses(OrgLocations orgLoc, User loginUser, String expenseStatus, String startDate, String endDate, String searchValue, int start, int length) throws Exception;

	public List<Expenses> getIndividualExpense(OrgLocations orgLoc, User loginUser, String expenseId);

	public List<ExpensesEntries> getExpensesEntries(String expenseId);

	public Expenses getExpenseById(long id);

	public Expenses getExpenseByExpenseUniqueId(String expenseId, Long orgLocationId,String isGlobalUser) throws Exception;

	public File exportMyExpensesToPdf(Expenses expenseDetails, OrgLocations orgLoc, User loginUser, String approverType,String isGlobalUser) throws Exception;

	public File exportMyExpensesToExcel(Expenses expenseDetails, OrgLocations orgLoc, User loginUser, String approverType,String isGlobalUser) throws Exception;
	
	public File exportAllMyExpensesToPdf(List<Expenses> expensesList, OrgLocations orgLoc, User loginUser,String isGlobalUser) throws Exception;

	public File exportAllMyExpensesToExcel(List<Expenses> expensesList, OrgLocations orgLoc,String isGlobalUser) throws Exception;
	
	public  List<ExpensesApproverHierarchy>  getEmployeeExpensesApproverHierarchyListByuserIdAndOrgLocationIdForApproverLogin(Long userId,Long orgLocationId, String expenseStatus,String isGlobalAccess)throws Exception;
	
	public List<Expenses>  getTotalMyExpensesListByMasterCandidateIdForEmployeeLogin(Long masterCandidateId, String expenseStatus)throws Exception;

	public List<Expenses> getExpensesByProjectMemberId(Long projectMemberId, String invoiceType,Long expenseId)throws Exception;

	public List<ExpensesEntriesDTO> getApprovedExpenses(Long expenseId,Long locationsId)throws Exception;
	
	public List<Expenses> getExpensesByProjectIdAndMasterCandidateId(Long projectId,Long masterCandidateId) throws Exception;

	public List<Expenses> getAllExpensesByOrgLocationId(Long orgLocationId) throws Exception;
	
	public List<Expenses> getAllExpensesByProjectIdOrgLocationIdStartDateAndEndDate(Long projectId, Long OrglocationId,Date startDate,Date endDate)throws Exception;

	public boolean changeExpenseBillingStatus(long expenseId, String billingStatus);

	public List<Expenses> getAllExpenses();

	public List<PendingForExpenseApprovalReportDTO> getPendingForApprovalExpenseReportByProjectIds(String projectIds, Date frequencyStartDate,Date frequencyEndDate,String dateFormat);
	
	
}
