package com.bis.sourcelead.payroll.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.payroll.dao.PayrollAdjustmentDao;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.service.PayrollAdjustmentService;

@Service
public class PayrollAdjustmentServiceImpl implements PayrollAdjustmentService{

	@Autowired
	PayrollAdjustmentDao  payrollAdjustmentDao;
	
	
	@Override
	public EmployeePayroll getEmployeePayrollByUUID(String payrollUUID)
	{
		return payrollAdjustmentDao.getEmployeePayrollByUUID(payrollUUID);
	}

	@Override
	public EmployeePayroll updateEmployeePayroll(EmployeePayroll payroll)
	{
		return  payrollAdjustmentDao.updateEmployeePayroll(payroll);
	}


}
