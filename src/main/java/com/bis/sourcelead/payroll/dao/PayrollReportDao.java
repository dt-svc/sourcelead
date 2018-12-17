package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.AssociateWithHoldingDTO;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;

public interface PayrollReportDao {

	ResponseList<EmployeePayroll> getPayrollSummaryByPayPeriodAndPayCheckDate(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<CheckRegisterReportDTO> getCheckRegisterReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<DeductionHistory> getContributionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<DeductionHistory> getDeductionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	List<CheckRegisterReportDTO> getexportToPdfCheckRegisterReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	List<DeductionHistory> exportToPdfContributionReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);
	
	List<DeductionHistory> exportToPdfDeductionReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<AssociateWithHoldingDTO> getAssociateWithHoldingReport(OrgLocations orgLocation, AssociateWithHoldingDTO associateWithHoldingDTO);

}
