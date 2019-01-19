package com.bis.sourcelead.payroll.service;

import com.bis.sourcelead.payroll.entity.EmployeePayroll;

public interface PayrollAdjustmentService {
	

	EmployeePayroll getEmployeePayrollByUUID(String payrollUUID);

	EmployeePayroll updateEmployeePayroll(EmployeePayroll payroll);

}
