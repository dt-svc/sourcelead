package com.bis.sourcelead.payroll.service;


import java.util.Date;
import java.util.List;
import java.util.Set;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.AssociateSalary;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayAdjustmentDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface GenerateUSPayrollService {

	PaysheetFrequencyDTO generateUSPayroll(PaysheetFrequencyDTO existedData, OrgLocations orgLocation, GenerateUSPayrollDTO generateUSPayrollDTO, User loginUser) throws Exception;

	PaysheetFrequencyDTO getPayrollData(GenerateUSPayrollDTO generateUSPayrollDTO, OrgLocations orgLocation, User loginUser);

	Response<USPayrollDTO> getEmployeePayrollByUUID(String payrollUUID, OrgLocations orgLocation, String approverStatus);

	Response<PayAdjustmentDTO> updateEmployeePayroll(PayAdjustmentDTO payAdjustmentDTO, OrgLocations orgLocation, User loginUser);
	
    Set<EmployeePayroll> calculatePayroll(PaysheetFrequencyDTO existedData, PaysheetFrequency paySheetFreq, User loginUser, OrgLocations orgLocation, List<AssociateSalary> assoSalList, Date freqStartDate, Date freqEndDate, UserGroup userGroup);


}
