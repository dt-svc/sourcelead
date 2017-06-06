package com.bis.sourcelead.service;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Expenses;
import com.bis.sourcelead.entity.ExpensesEntries;

public interface ExpensesEntriesService {

	public Set<ExpensesEntries> prepareExpensesEntriesListByJson(JSONObject jsonObj, HttpServletRequest request,Expenses expenses) throws Exception;

}
