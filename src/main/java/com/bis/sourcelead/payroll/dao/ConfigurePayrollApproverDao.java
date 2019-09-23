package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;
import com.bis.sourcelead.payroll.entity.PayrollApprover;

public interface ConfigurePayrollApproverDao {

	ConfigurePayrollApprover savePayrollApprover(ConfigurePayrollApprover confPayApprover);

	ConfigurePayrollApprover getConfigurePayrollApproverByLoc(OrgLocations orgLocation);

	int deletePayrollApprover(String confAprroverId);

	List<PayrollApprover> getPayrollApproversByCPAID(Long configurePayrollApproverId);
	
	boolean isLoginUserIsPayRollApprover(Long userId,Long orgLocationsId);

	ConfigurePayrollApprover getConfigurePayrollApproverByLocAndStatus(OrgLocations orgLocation);
}
