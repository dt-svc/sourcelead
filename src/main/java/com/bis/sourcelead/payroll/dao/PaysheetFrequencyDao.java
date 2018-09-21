package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PayrollComparisonDTO;
import com.bis.sourcelead.payroll.dto.PayPeriodDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface PaysheetFrequencyDao {

	List<EmployeePayroll> getMyPayrollListByCandidateId(Long candidateId);

	List<PaysheetFrequency> getPaysheetFrequencyDraftDataByOrgLocationId(Long orgLocationId);

	List<EmployeePayroll> employeePayrollYTD(Candidate candidate, OrgLocations orgLocation, String approverStatus);

	PaysheetFrequency getPaySheetFrequencyByGroupIdAndFrequencyStartEnddate(String date,
			OrgLocations orgLocation, Long groupId);
			
	List<PaysheetFrequency> getPayrollRegisterReports(PayrollReportDTO payPeriodDTO);
}
