package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;

public interface AddExpensesService {

	public String getExpenseId();

	public List<ProjectMembers> getAllProjectsForExpenses( OrgLocations orgLoc, User loginUser);

}
