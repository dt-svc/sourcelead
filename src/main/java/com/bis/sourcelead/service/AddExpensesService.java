package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface AddExpensesService {

	public String getExpenseId(OrgLocations orgLoc);

	public List<ProjectMembers> getAllProjectsForExpenses( OrgLocations orgLoc, User loginUser);
	
	public Expenses storeAddExpensesData(Expenses expenses) throws Exception;

	public void storeAddExpensesData(JSONObject jsonObj, User loginUser, OrgLocations orgLoc,MultipartHttpServletRequest request,String status, String currencyCode) throws JSONException, NumberFormatException, Exception;

	public void sendExpensesEmailToManager(User loginUser, Expenses expenses, OrgLocations orgLoc,String currencyCode) throws Exception;
}
