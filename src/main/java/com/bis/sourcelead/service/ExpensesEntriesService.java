package com.bis.sourcelead.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.bis.sourcelead.dto.ExpensesDTO;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;

public interface ExpensesEntriesService {

	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(ExpensesDTO addExpensesDTO, HttpServletRequest request,Expenses expenses) throws Exception;

	public List<ExpensesEntries> getExpensesEntriesByExpenseId(long parseLong);

	public int dumpExpenceCategoryAmountToOldRecords();

	public int changeDocumentNameWithWhiteSpacesToUnderScore(String staticPath);

}