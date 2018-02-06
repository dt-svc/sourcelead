package com.bis.sourcelead.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface ExpensesReportService {

	public List<ExpensesApproverHierarchy> getAllExpensesReportListByLoginUserIdAndOrgLocationId(Long loginUserId, Long loginOrgLocationId)
			throws Exception;

	public Map<String, Map<String,String>> getYearsAndQuartersComboList(String dateFormat) throws JSONException, ParseException;

	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long proMembersId,Date convertedStartDate, Date convertedEndDate) throws ParseException;

	public Map<String, String> getmonthStringNamefromStartAndEndDate(Date convertedStartDate, Date convertedEndDate, List<ProjectMembers> projectMembers);

	public File expensesReportExportListPdf(String frequencyMonth, OrgLocations orgLoc, User loginUser) throws ParseException, IOException;

	public File expensesReportExportListExcel(String frequencyMonth, OrgLocations orgLoc, User loginUser)throws ParseException, IOException;

	List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDateForClaimedExpenses(Long proMembersId,Date startDate, Date endtDate) throws ParseException;

}
