package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface AddExpensesService {

	public String getExpenseId();

	public List<ProjectMembers> getAllProjectsForExpenses( OrgLocations orgLoc, User loginUser);

	public void storeAddExpensesData(JSONObject jsonObj, User loginUser, OrgLocations orgLoc,MultipartHttpServletRequest request,String status) throws JSONException, NumberFormatException, Exception;

}
