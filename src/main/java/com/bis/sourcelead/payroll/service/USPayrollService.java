package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;

public interface USPayrollService {

	ResponseList<PayrollDeductionDTO> findAllDeduction();

	PayrollDeductionDTO savePayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation, User user);

	ResponseList<PayrollDeductionDTO> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation);
}
