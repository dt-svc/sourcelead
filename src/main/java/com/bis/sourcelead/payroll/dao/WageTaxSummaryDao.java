package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.WageTaxSummary;
import com.bis.sourcelead.payroll.entity.WageTaxSummaryBulk;

public interface WageTaxSummaryDao {

	WageTaxSummary saveWageTaxSummary(WageTaxSummary wageTaxSummary)throws Exception;

	List<WageTaxSummary> getWagesAndTaxListByOrgLocationId(OrgLocations orgLocation);

	WageTaxSummaryBulk saveWageTaxSummaryBulk(WageTaxSummaryBulk wageTaxSummaryBulk)throws Exception;

}
