package com.bis.sourcelead.payroll.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

public interface WageTaxSummaryDao {

	WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary)throws Exception;

	List<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation);

	List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

	int updateWageTaxSummary(Long importedUserId, OrgLocations orgLocation, User slUser)throws Exception;

	List<WageTaxSummary> getAllImportedUsers(OrgLocations orgLocations)throws Exception;

	List<WageTaxSummary> getMappedUsersListByOrgLocationId(Long locationId)throws Exception;

	List<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationId(OrgLocations orgLocation)throws Exception;

	WageTaxSummary getWageTaxSummaryByEmpIdandDuration(String empId,Long locationId, Date startDate, Date endDate);

	int getWageTaxSummaryVersionByEmpId(String empId, Long locationId);

}
