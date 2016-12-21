package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollDao;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.service.PayrollService;

/**
 * @author Ajith Matta
 *
 */
@Service
public class PayrollServiceImpl implements PayrollService {
	

	
	@Autowired
	private PayrollDao payrollDao;
	

	public static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}


	@Override
	public Payroll getPayrollById(Long id) {
		
		return payrollDao.getPayrollById(id);
		
	}


	@Override
	public List<Payroll> getAllPayroll() {

		return payrollDao.getAllPayroll();
		
	}
	
}
