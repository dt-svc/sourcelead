package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;

public interface USPayrollService {

	ResponseList<PayrollDeductionDTO> findAllDeduction();
	
}
