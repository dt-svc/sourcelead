package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AddExpensesServiceImpl implements AddExpensesService{

	@Autowired
	ProjectMembersService projectMembersService;
	
	@Autowired
	ExpensesEntriesService expensesEntriesService;
	
	@Autowired
	private AddExpensesDao addExpensesDao;
	
	@Autowired
	HttpServletRequest request;
	
	@Override
	public String getExpenseId() {
		String expensesId = SourceLeadRandomCodeHelper.generateRandomExpensesIdCode();
		return expensesId;
		
	}
	
@Override
public List<ProjectMembers> getAllProjectsForExpenses(OrgLocations orgLoc,User loginUser) {
	List<ProjectMembers> projectMembersList =  projectMembersService.getProjectMembersListByOrgLocationsIdAndEmpUserId(orgLoc.getId(),loginUser.getId());
	
	return projectMembersList;
	
	}

@Override
public void storeAddExpensesData(JSONObject jsonObj, User loginUser, OrgLocations orgLoc) throws NumberFormatException, Exception {
	Expenses expenses = new Expenses();
	if (jsonObj.has("ExpenseAutoId") && !(jsonObj.getString("ExpenseAutoId").isEmpty())){
		expenses.setExpenseId(jsonObj.getString("ExpenseAutoId"));	
	 }
	if (jsonObj.has("expensePurpose") && !(jsonObj.getString("expensePurpose").isEmpty())){
		expenses.setPurpose(jsonObj.getString("expensePurpose"));
	 }
	if (jsonObj.has("expenseProject") && !(jsonObj.getString("expenseProject").isEmpty())){
		ProjectMembers projMembers = projectMembersService.getProjectMemberByUserIdAndProjectId(loginUser.getId(), Long.parseLong(jsonObj.getString("expenseProject")));
		expenses.setProjectMembers(projMembers);
	}
	if (jsonObj.has("clientNameExpense") && !(jsonObj.getString("clientNameExpense").isEmpty())){
		expenses.setClientName(jsonObj.getString("clientNameExpense")); 
	}
	if (jsonObj.has("startDateExpense") && !(jsonObj.getString("startDateExpense").isEmpty())){
		expenses.setStartDate(Constants.DMY.parse(jsonObj.getString("startDateExpense")));
	}
	if (jsonObj.has("endDateExpense") && !(jsonObj.getString("endDateExpense").isEmpty())){
		expenses.setEndDate(Constants.DMY.parse(jsonObj.getString("endDateExpense")));
	 }
	if(null != orgLoc){
		expenses.setOrgLocationId(orgLoc);
	}
	expenses.setCreatedBy(loginUser.getUserCode());
	expenses.setUpdatedBy(loginUser.getUserCode());
	expenses.setCreatedDate(CommonUtil.currentDate());
	expenses.setUpdatedDate(CommonUtil.currentDate());
	expenses.setStatus(Constants.EXPENSES_STATUS);
	
	Set<ExpensesEntries>  expensesEntriesList = expensesEntriesService.prepareExpensesEntriesListByJson(jsonObj, request, expenses);
	expenses.setExpensesEntries(expensesEntriesList);
	
	addExpensesDao.storeAddExpensesData(expenses);
}


}
