package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.util.Paginator;

public interface WageTaxSummaryDao {

	WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary)throws Exception;

	List<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation);

	List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

	int updateWageTaxSummary(Long importedUserId, OrgLocations orgLocation, User slUser)throws Exception;

	List<WageTaxSummary> getAllImportedUsers(OrgLocations orgLocations)throws Exception;

	List<WageTaxSummary> getMappedUsersListByOrgLocationId(Long locationId)throws Exception;

	List<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationId(OrgLocations orgLocation)throws Exception;


}
