package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.entity.PayrollContribution;
import com.bis.sourcelead.payroll.entity.PayrollDeduction;

public interface PayrollContributionDao {
	
	PayrollContribution findPayrollContributionByUUID(String contributionUUID);
	
	PayrollDeduction findById(long deductionTypeId);
	
	PayrollContribution savePayrollContribution(PayrollContribution payrollContribution);
	
	ResponseList<PayrollContribution> findAllPayrollContribution(PayrollContributionDTO payrollContributionDTO, OrgLocations orgLocation);
	
	PayrollContribution findPayrollContributionByID(long contributionID);

}
