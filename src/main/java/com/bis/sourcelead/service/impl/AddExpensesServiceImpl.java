package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.ProjectMembers;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AddExpensesService;
import com.bis.sourcelead.service.ProjectMembersService;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class AddExpensesServiceImpl implements AddExpensesService{

	@Autowired
	ProjectMembersService projectMembersService;
	
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
}
