package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;

public interface PayrollService {
	
	Payroll  getPayrollById(Long id);
	
	List<Payroll> getAllPayroll();

	
}