package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.PayrollDeductionDTO;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;

public interface PayrollDeductionService {
	
	PayrollDeductionDTO savePayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation, User user);
	
	ResponseList<PayrollDeductionDTO> findAllPayrollDeduction(PayrollDeductionDTO payrollDeductionDTO, OrgLocations orgLocation);
	
	ResponseList<PayrollDeductionDTO> getAllDeductionTypeByLocationId(OrgLocations orgLocation);
	
	PayrollDeduction findPayrollDeductionByUUID(String deductionUUID);
	
	boolean deleteDeductionById(Long deductionId, String changeStatus);

	boolean isValidDeductionType(String payrollDeductionType, OrgLocations orgLocation);

	PayrollDeduction getPayrollDeductionTypeById(long parseLong);

	PayrollDeduction getDeductionByDeductionTypeAndLocationId(String cellValueAsString, Long id);

}
