package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.AssociateReportDTO;
import com.bis.sourcelead.payroll.dto.CommonPayrollDataDTO;
import com.bis.sourcelead.payroll.dto.IntiateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.MyPayrollSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.YTDPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface PaysheetFrequencyService {

	Response<PayrollApproverDTO> sendPayrollForApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation, User loginUser);

	MyPayrollSummaryDTO getMyPayrollListByCandidateId(Long candidateId, OrgLocations orgLocation);

	List<PaysheetFrequencyDTO> getPaysheetFrequencyDraftDataByOrgLocationId(OrgLocations orgLocation);

	List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation, String approverStatus, int periodYear);

	ResponseList<PayrollReportDTO> getPayrollRegisterReports(PayrollReportDTO payPeriodDTO, OrgLocations orgLocations);

	PaysheetFrequency getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(String dates,
			OrgLocations orgLocation, Long groupId);

	YTDPayrollDTO getContributionYTD(EmployeePayroll payroll,Long contributionId,OrgLocations ogLocations, String reportType);

	YTDPayrollDTO getDeductionYTD(EmployeePayroll payroll, Long deductionId, OrgLocations orgLocation, String reportType);

	YTDPayrollDTO getPayrollTaxesYTD(EmployeePayroll payroll, OrgLocations orgLocation);

	ResponseList<PayrollReportDTO> getpayrollTaxAndWagesReport(PayrollReportDTO payrollReportDTO, OrgLocations orgLocations);

	ResponseList<AssociateReportDTO> getEmployeePayrollListByPayCheckDate(PayrollReportDTO payrollReportDTO, OrgLocations orgLocation);

	ResponseList<PayrollReportDTO> getContractEmployeeReportList(PayrollReportDTO payrollReportDTO, OrgLocations orgLocation);

	CommonPayrollDataDTO getNextPayrollDate(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId, String orgDateFormat);

	List<PaysheetFrequency> getRecentlyRunPayrollListByFrequency(IntiateUSPayrollDTO intiateUSPayrollDTO, Long orgLocationId, String dateFormat);

	List<PaysheetFrequency> getRecentlyRunPayrollData(Long orgLocationId);

	
}
