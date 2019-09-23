package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;

public interface PayrollDao {

	Payroll  getPayrollById(Long id);
	
	List<Payroll> getAllPayroll();

	PaysheetFrequency getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation,
			User loginUser);
	
}