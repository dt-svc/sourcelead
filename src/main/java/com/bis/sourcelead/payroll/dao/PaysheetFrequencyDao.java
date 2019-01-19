package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.InvoicePayment;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.CommonPayrollDataDTO;
import com.bis.sourcelead.payroll.dto.IntiateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface PaysheetFrequencyDao {

	List<EmployeePayroll> getMyPayrollListByCandidateId(Long candidateId);

	List<PaysheetFrequency> getPaysheetFrequencyDraftDataByOrgLocationId(Long orgLocationId);

	List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation, String approverStatus,int periodYear);

	ResponseList<EmployeePayroll> getPayrollRegisterReports(PayrollReportDTO payPeriodDTO);

	PaysheetFrequency getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(String date,
			OrgLocations orgLocation, Long groupId);

	YTDPayrollDTO getContributionYTD(EmployeePayroll payroll,Long contributionId,OrgLocations orgLocations, String reportType);

	YTDPayrollDTO getDeductionYTD(EmployeePayroll payroll, Long deductionId, OrgLocations orgLocation, String reportType);

	List<EmployeePayroll> getPayrollTaxesYTD(EmployeePayroll payroll, OrgLocations orgLocation);

	List<EmployeePayroll> getEmployeePayrollListByPayCheckDate(PayrollReportDTO payrollReportDTO, OrgLocations orgLocation);

	List<InvoicePayment> getContractEmployeeReportList(PayrollReportDTO payrollReportDTO);

	CommonPayrollDataDTO getNextPayrollDate(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId, String orgDateFormat);

	List<PaysheetFrequency> getRecentlyRunPayrollListByFrequency(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId, String dateFormat);

	List<PaysheetFrequency> getRecentlyRunPayrollData(Long orgLocationId);

			
}
