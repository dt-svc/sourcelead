package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;

public interface ConfigurePayrollApproverDao {

	ConfigurePayrollApprover savePayrollApprover(ConfigurePayrollApprover confPayApprover);

	ConfigurePayrollApprover getConfigurePayrollApproverByLoc(OrgLocations orgLocation);

	int deletePayrollApprover(String confAprroverId);
}
