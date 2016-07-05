package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.service.EmployeesService;

@Service
public class EmployeesServiceImpl implements EmployeesService {

	@Autowired
	EmployeesDao employeeDao;
	
	
	@Override
	public Employees addEmployee(Employees employee) throws Exception {
		return employeeDao.addEmployee(employee);
	}

	@Override
	public Employees updateEmployee(Employees employee) throws Exception {
		// TODO Auto-generated method stub
		return employeeDao.updateEmployee(employee);
	}

	@Override
	public Employees getEmployee(String id) throws Exception {
		// TODO Auto-generated method stub
		return employeeDao.getEmployee(id);
	}

	@Override
	public Boolean deleteEmployee(String id) throws Exception {
		Boolean flag = employeeDao.deleteEmployee(id);
		return flag;
		// TODO Auto-generated method stub
	}

	@Override
	public List<Employees> getAllEmployees() throws Exception {
		// TODO Auto-generated method stub
		List<Employees> emplist= employeeDao.getAllEmployees();
		return emplist;
	}


	
}
