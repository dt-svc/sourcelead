package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;

public interface PayrollDeductionDao {
	
	PayrollDeduction findPayrollDeductionByUUID(String deductionUUID);
	
	PayrollDeductionDTO savePayrollDeduction(PayrollDeduction payrollDeduction, PayrollDeductionDTO payrollDeductionDTO);

	ResponseList<PayrollDeduction> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation);
	
	List<PayrollDeduction> getAllDeductionTypeByLocationId(OrgLocations orgLocation);
	
	PayrollDeduction findPayrollDeductionByID(Long deductionId);

	PayrollDeduction isValidDeductionType(String payrollDeductionType, OrgLocations orgLocation);

	PayrollDeduction getPayrollDeductionTypeById(long deductionId);

	PayrollDeduction getDeductionByDeductionTypeAndLocationId(String deductionType, Long orgLocationId);

}
