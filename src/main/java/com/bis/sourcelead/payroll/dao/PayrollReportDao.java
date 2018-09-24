package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;

public interface PayrollReportDao {

	ResponseList<EmployeePayroll> getPayrollSummaryByPayPeriodAndPayCheckDate(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<CheckRegisterReportDTO> getCheckRegisterReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<EmployeePayroll> getDeductionAndContributionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

}
