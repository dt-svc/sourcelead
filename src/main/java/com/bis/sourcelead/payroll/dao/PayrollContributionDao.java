package com.bis.sourcelead.payroll.dao;

import java.util.List;

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

	PayrollContribution findPayrollContributionByDeductionTypeId(long deductionTypeId, OrgLocations orgLocation);

	List<PayrollContribution> getAllContributionTypeByLocationId(OrgLocations orgLocation);

	PayrollContribution getContributionByDeductionTypeAndLocationId(String contributionTypeName, Long id);


}
