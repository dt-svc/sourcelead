package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.ConfirmPayTotalSum;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;

public interface USPayrollService {

	ResponseList<PayrollDeductionDTO> findAllDeduction();

	ConfirmPayTotalSum calculateTotalPaySum(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation);
	
}
