package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.Employees;

public interface EmployeesService {

	public Employees addEmployee(Employees employee)throws Exception;
    public Employees updateEmployee(Employees employee)throws Exception;
    public Employees getEmployee(String id)throws Exception;
    public Boolean deleteEmployee(String id)throws Exception;
    public List<Employees> getAllEmployees()throws Exception; 
}
