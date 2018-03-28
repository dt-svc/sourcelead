package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.LookUpData;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;

public interface USPayrollDao {

	List<LookUpData> findAllDeduction();

	PayrollDeductionDTO savePayrollDeduction(PayrollDeduction payrollDeduction, PayrollDeductionDTO payrollDeductionDTO);

	ResponseList<PayrollDeduction> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation);
}
