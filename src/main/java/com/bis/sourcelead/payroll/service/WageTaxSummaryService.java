package com.bis.sourcelead.payroll.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

public interface WageTaxSummaryService {
	
	WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary)throws Exception;

	Integer saveWagesTaxSummary(MultipartFile multipartFile, OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;

	List<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation);

	Integer saveWagesTaxSummaryBulk(MultipartFile multipartFile, OrgLocations orgLocation, User user) throws Exception;

	List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

	int mergeUser(JSONObject jsonObj, OrgLocations orgLocation) throws Exception;

	List<WageTaxSummary> getAllImportedUsers(OrgLocations orgLocations)throws Exception;

	List<WageTaxSummary> getMappedUsersListByOrgLocationId(Long locationId)throws Exception;

	List<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationId(OrgLocations orgLocation)throws Exception;
	
	/*
	 *this service is used to get wagetaxsummary details of particular employee on particular date  
	 */
	WageTaxSummary getWageTaxSummaryByEmpIdandDuration(String EmpId,Long locationId,Date startDate, Date endDate);
	
	int getWageTaxSummaryVersionByLocationId(Long locationId);
	
	

}
