package com.bis.sourcelead.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dto.AddExpensesDTO;
import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;

public interface ExpensesEntriesService {

	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(AddExpensesDTO addExpensesDTO, HttpServletRequest request,Expenses expenses) throws Exception;

	public List<ExpensesEntries> getExpensesEntriesByExpenseId(long parseLong);

}