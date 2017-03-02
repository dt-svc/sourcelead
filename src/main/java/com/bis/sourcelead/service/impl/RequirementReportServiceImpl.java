package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.RequirementReportDao;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Requirement;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.RequirementReportService;

@Service
public class RequirementReportServiceImpl implements RequirementReportService{
     
	@Autowired
	RequirementReportDao requirementReportDao;
	@Override
	public List<Requirement> getRequirementReports(String userRoleName, OrgLocations orgLocation, User user,String filterDate) {
		return  requirementReportDao.getRequirementReports(userRoleName,orgLocation,user,filterDate);
	}

}
