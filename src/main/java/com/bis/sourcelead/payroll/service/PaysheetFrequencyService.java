package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.MyPayrollSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface PaysheetFrequencyService {

	Response<PayrollApproverDTO> sendPayrollForApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation, User loginUser);

	MyPayrollSummaryDTO getMyPayrollListByCandidateId(Long candidateId, OrgLocations orgLocation);

	List<PaysheetFrequencyDTO> getPaysheetFrequencyDraftDataByOrgLocationId(OrgLocations orgLocation);

	List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation, String approverStatus);

	ResponseList<PayrollReportDTO> getPayrollRegisterReports(PayrollReportDTO payPeriodDTO, OrgLocations orgLocations);

	PaysheetFrequency getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(String dates,
			OrgLocations orgLocation, Long groupId);
	
}
