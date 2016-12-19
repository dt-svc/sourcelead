package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollDao;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.service.PayrollService;
import com.bis.sourcelead.util.CommonUtil;

/**
 * @author Ajith Matta
 *
 */
@Service
public class PayrollServiceImpl implements PayrollService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollServiceImpl.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
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
