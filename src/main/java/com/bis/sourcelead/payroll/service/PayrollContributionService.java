package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.ContributionDTO;
import com.bis.sourcelead.payroll.dto.PayrollContributionDTO;
import com.bis.sourcelead.payroll.entity.PayrollContribution;

public interface PayrollContributionService {
	
	ResponseList<PayrollContributionDTO> savePayrollContribution(PayrollContributionDTO payrollContributionDTO, OrgLocations orgLocation, User user);
	
	ResponseList<PayrollContributionDTO> findAllPayrollContribution(PayrollContributionDTO payrollContributionDTO, OrgLocations orgLocation);
	
	PayrollContribution findPayrollContributionByUUID(String contributionUUID);
	
	boolean deleteContributionById(long contributionId, String changeStatus);

	boolean isValidContributionType(long deductionTypeId, OrgLocations orgLocation);

	List<PayrollContributionDTO> getAllContributionTypeByLocationId(OrgLocations orgLocation);

	PayrollContributionDTO getContributionAmount(long contributionId);


}
