package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Payroll;

public interface PayrollService {
	
	Payroll  getPayrollById(Long id);
	
	List<Payroll> getAllPayroll();
	
}