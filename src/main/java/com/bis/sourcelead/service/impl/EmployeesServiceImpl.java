package com.bis.sourcelead.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.service.EmployeesService;

@Service
public class EmployeesServiceImpl implements EmployeesService {
    
	private static final Logger logger = LoggerFactory.getLogger(EmployeesServiceImpl.class);
	
	@Autowired
	private EmployeesDao employeesDao;
	
	
	@Override
	public Employees addEmployee(Employees employee) throws Exception {
		logger.debug("In EmployeesServiceImpl class addEmployee method...");
		return employeesDao.addEmployee(employee);
	}

	@Override
	public Employees updateEmployee(Employees employee) throws Exception {
		logger.debug("In EmployeesServiceImpl class updateEmployee method...");
		return employeesDao.updateEmployee(employee);
	}

	@Override
	public Employees getEmployee(String id) throws Exception {
		logger.debug("In EmployeesServiceImpl class getEmployee method...");
		return employeesDao.getEmployee(id);
	}

	@Override
	public Boolean deleteEmployee(String id) throws Exception {
		logger.debug("In EmployeesServiceImpl class deleteEmployee method...");
		Boolean flag = employeesDao.deleteEmployee(id);
		return flag;
	}
	
	@Override
	public Boolean validateEmployeeId(String employeeId, Long orgLocationsId) throws Exception {
		Boolean flag = employeesDao.validateEmployeeId(employeeId,orgLocationsId);
		return flag;
	}

	@Override
	public List<Employees> getAllEmployees() throws Exception {
		logger.debug("In EmployeesServiceImpl class getAllEmployees method...");
		List<Employees> emplist= employeesDao.getAllEmployees();
		return emplist;
	}


	
}
