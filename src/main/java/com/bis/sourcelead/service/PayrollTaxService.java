package com.bis.sourcelead.service;

import com.bis.sourcelead.dto.PayrollTaxDTO;
import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface PayrollTaxService {

	Response<Long> savePayrollTaxConfiguration(PayrollTaxDTO payrollTaxDTO, User user, OrgLocations orgLocations);

	PayrollTaxDTO getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId, String financialYear);

	PayrollTaxDTO getPayrollTaxConfigurationForOrgLocation(String stateName, String financialYear, String taxType);
	
	PayrollTaxDTO getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId);
}
