package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.AssociateWithHoldingDTO;
import com.bis.sourcelead.payroll.dto.CheckRegisterReportDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;

public interface PayrollReportService {

	ResponseList<PayrollReportDTO> getPayrollSummaryByPayPeriodAndPayCheckDate(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<CheckRegisterReportDTO> getCheckRegisterReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	PayrollComparisonDTO payrollComparisonData(PayrollComparisonDTO payrollComparisonDTO, OrgLocations orgLocation);

	ResponseList<PayrollReportDTO> getContributionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<PayrollReportDTO> getDeductionReports(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	List<CheckRegisterReportDTO> getexportToPdfCheckRegisterReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	List<PayrollReportDTO>  exportToPdfContributionReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);
	
	List<PayrollReportDTO> exportToPdfDeductionReport(OrgLocations orgLocation, PayrollReportDTO payrollReportDTO);

	ResponseList<AssociateWithHoldingDTO> getAssociateWithHoldingReports(OrgLocations orgLocation, AssociateWithHoldingDTO associateWithHoldingDTO);
}
