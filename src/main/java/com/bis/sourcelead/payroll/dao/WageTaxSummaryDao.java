package com.bis.sourcelead.payroll.dao;

import java.util.Date;
import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.dto.WagesTaxSummaryDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.PayrollBalance;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;

public interface WageTaxSummaryDao {

	WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary)throws Exception;

	ResponseList<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation, WagesTaxSummaryDTO wagesTaxSummaryDTO) throws Exception;

	List<WageTaxSummary> getWagesAndTaxListByEmployeeId(OrgLocations orgLocations, Long empId)throws Exception;

	int updateWageTaxSummary(Long importedUserId, OrgLocations orgLocation, User slUser)throws Exception;

	List<WageTaxSummary> getAllImportedUsers(OrgLocations orgLocations)throws Exception;

	List<WageTaxSummary> getMappedUsersListByOrgLocationId(Long locationId)throws Exception;

	List<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationId(OrgLocations orgLocation) throws Exception;

	WageTaxSummary getWageTaxSummaryByEmpIdandDuration(String empId,Long locationId, Date startDate, Date endDate);

	int getWageTaxSummaryVersionByLocationId(Long locationId);

	ResponseList<WageTaxSummary> getUniqueWagesAndTaxListByOrgLocationIdAndPagination(OrgLocations orgLocation, PayrollBalance payrollBalance) throws Exception;

	WageTaxSummary getEmployeeIdByUserId(User consultantUser)throws Exception;

	ResponseList<WageTaxSummary> getWagesAndTaxByEmpId(OrgLocations orgLocation, WagesTaxSummaryDTO wagesTaxSummaryDTO);

}
