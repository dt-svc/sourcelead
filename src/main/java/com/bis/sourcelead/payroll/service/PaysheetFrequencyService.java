package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface PaysheetFrequencyService {

	Response<PayrollApproverDTO> sendPayrollForApprover(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation);
	
}
