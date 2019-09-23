package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.PayrollTax;

public interface PayrollTaxDao {
	public long savePayrollTaxConfiguration(PayrollTax payrollTax);

	public PayrollTax getPayrollTaxById(long id);

	public PayrollTax getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId, String financialYear);

	public PayrollTax getPayrollTaxConfigurationByOrgLocationId(Long orgLocationId);
}
