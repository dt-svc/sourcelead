package com.bis.sourcelead.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;

import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface AddExpensesService {

	public String getExpenseId(OrgLocations orgLoc);

	public List<ProjectMembers> getAllProjectsForExpenses( OrgLocations orgLoc, User loginUser);

	public void storeAddExpensesData(ExpensesDTO addExpensesDTO, User loginUser, OrgLocations orgLoc,HttpServletRequest request, String currencyCode,String isGlobalUser,String loginUserType) throws JSONException, NumberFormatException, Exception;

	//public void sendExpensesEmailToManager(User loginUser, Expenses expenses, OrgLocations orgLoc,String currencyCode) throws Exception;

	public Expenses storeAddExpensesData(Expenses expenses) throws Exception;

	public void deleteDocumentByDocumentId(String documentId);

	public Response<String> changeStatusToBillable(long expenseEntriesId,String  billableStatus);

	
}