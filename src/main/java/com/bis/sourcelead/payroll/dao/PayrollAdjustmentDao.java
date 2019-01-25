package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.payroll.entity.EmployeePayroll;

public interface PayrollAdjustmentDao {

	EmployeePayroll getEmployeePayrollByUUID(String payrollUUID);

	EmployeePayroll updateEmployeePayroll(EmployeePayroll payroll);
}
