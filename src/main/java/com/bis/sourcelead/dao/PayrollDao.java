package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Payroll;

public interface PayrollDao {

	Payroll  getPayrollById(Long id);
	
	List<Payroll> getAllPayroll();
	
}