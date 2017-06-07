package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.AddExpensesDao;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ExpensesEntries;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.TimesheetSubmissions;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.enums.Submission_ApprovalStatus;
import com.bis.sourcelead.enums.Submission_Status;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.ExpensesEntriesService;
import com.bis.sourcelead.service.ProjectApproverHierarchyService;
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
	ProjectApproverHierarchyService projectApproverHierarchyService;
	
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
public void storeAddExpensesData(JSONObject jsonObj, User loginUser, OrgLocations orgLoc,MultipartHttpServletRequest request,String status) throws NumberFormatException, Exception {
	Expenses expenses = new Expenses();
	ProjectMembers projMembers = null;
	if (jsonObj.has("ExpenseAutoId") && !(jsonObj.getString("ExpenseAutoId").isEmpty())){
		expenses.setExpenseId(jsonObj.getString("ExpenseAutoId"));	
	 }
	if (jsonObj.has("expensePurpose") && !(jsonObj.getString("expensePurpose").isEmpty())){
		expenses.setPurpose(jsonObj.getString("expensePurpose"));
	 }
	if (jsonObj.has("projectMemberId") && !(jsonObj.getString("projectMemberId").isEmpty())){
	    projMembers = projectMembersService.getProjectMemberById(Long.parseLong(jsonObj.getString("projectMemberId")));
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
	if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue())){
		expenses.setExpenseStatus(Submission_Status.SUBMITTED);
	}
	else{
		expenses.setExpenseStatus(Submission_Status.DRAFT);
	}
	expenses.setStatus(Constants.EXPENSES_STATUS);
	Set<ExpensesEntries>  expensesEntriesList = expensesEntriesService.prepareExpensesEntriesListByJson(jsonObj, request, expenses);
	expenses.setExpensesEntries(expensesEntriesList);
	if(status.equalsIgnoreCase(Submission_Status.SUBMITTED.getValue()) && projMembers != null){
    	List<ProjectApproverHierarchy> hierarchyList = projectApproverHierarchyService.getApproverUsersByProjectId(projMembers.getProjects().getId());
    	HashSet<ExpensesApproverHierarchy> saHierarchyList = new HashSet<ExpensesApproverHierarchy>();
        if(hierarchyList != null && !hierarchyList.isEmpty()){
				for(ProjectApproverHierarchy hierarchy : hierarchyList){
					ExpensesApproverHierarchy saHierarchy = new ExpensesApproverHierarchy();
					saHierarchy.setHierarchyId(hierarchy);
					saHierarchy.setExpenseId(expenses);
					saHierarchy.setExpenseStatus(Submission_Status.SUBMITTED);
					saHierarchy.setExpenseApprovalStatus(Submission_ApprovalStatus.SUBMITTEDFORAPPROVAL);
					saHierarchy.setCreatedDate(expenses.getCreatedDate());
					saHierarchy.setCreatedBy(expenses.getCreatedBy());
					saHierarchy.setUpdatedDate(expenses.getUpdatedDate());
					saHierarchy.setUpdatedBy(expenses.getUpdatedBy());
					saHierarchy.setStatus(Constants.RECORD_ACTIVE+"");
					saHierarchyList.add(saHierarchy);
				}
				expenses.setApproverHierarchyList(saHierarchyList);
		}
	}
	 expenses = addExpensesDao.storeAddExpensesData(expenses);
  }
}
