package com.bis.sourcelead.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.PayrollDao;
import com.bis.sourcelead.dao.UserGroupDao;
import com.bis.sourcelead.entity.AssociateSalaryInd;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.Payroll;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.UserGroup;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.GenerateUSPayrollDTO;
import com.bis.sourcelead.payroll.dto.INDPayrollDTO;
import com.bis.sourcelead.payroll.dto.PayrollConstant;
import com.bis.sourcelead.payroll.dto.PaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.TotalHoursDTO;
import com.bis.sourcelead.payroll.dto.USPayrollDTO;
import com.bis.sourcelead.payroll.entity.EmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.PaysheetFrequency;
import com.bis.sourcelead.payroll.service.USPaysheetService;
import com.bis.sourcelead.payroll.service.impl.GenerateUSPayrollServiceImpl;
import com.bis.sourcelead.service.AssociateSalaryService;
import com.bis.sourcelead.service.EmployeesService;
import com.bis.sourcelead.service.PayrollService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

/**
 * @author Ajith Matta
 *
 */
@Service
public class PayrollServiceImpl implements PayrollService {
	
	private static final Logger logger = LoggerFactory.getLogger(PayrollServiceImpl.class);
	
	@Autowired
	private PayrollDao payrollDao;
	
	@Autowired
	private UserGroupDao userGroupDao;
	
	@Autowired
	private AssociateSalaryService associateSalaryService;
	
	@Autowired
	USPaysheetService uSPaysheetService;
	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	private CommonUtil commonUtil;
	

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
