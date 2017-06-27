package com.bis.sourcelead.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesApproverHierarchy;
import com.bis.sourcelead.entity.ProjectMembers;

public interface ExpensesReportService {

	public List<ExpensesApproverHierarchy> getAllExpensesReportListByLoginUserIdAndOrgLocationId(Long loginUserId, Long loginOrgLocationId)
			throws Exception;

	public Map<String, Map<String,String>> getYearsAndQuartersComboList() throws JSONException, ParseException;

	public List<Expenses> getExpensesByProjectMemberIdAndExpenseStartAndEndDate(Long proMembersId,Date convertedStartDate, Date convertedEndDate) throws ParseException;

	public Map<String, String> getmonthStringNamefromStartAndEndDate(Date convertedStartDate, Date convertedEndDate, List<ProjectMembers> projectMembers);

	public void expensesReportExportListPdf();

	public void expensesReportExportListExcel();

}
