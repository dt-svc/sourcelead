package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.ConfirmPayTotalSumDTO;
import com.bis.sourcelead.payroll.dto.PayrollApproverDTO;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;

public interface USPayrollService {

	ResponseList<PayrollDeductionDTO> findAllDeduction();

	ConfirmPayTotalSumDTO calculateTotalPaySum(PayrollApproverDTO payrollApproverDTO, OrgLocations orgLocation);

	Response<ConfirmPayTotalSumDTO> approvePayroll(ConfirmPayTotalSumDTO confirmPayTotalSumDto, OrgLocations orgLocation, User loginUser);
	
	
}
