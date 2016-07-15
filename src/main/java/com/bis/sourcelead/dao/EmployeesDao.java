package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.Employees;

public interface EmployeesDao {

	public Employees addEmployee(Employees employee) throws Exception;

	public Employees updateEmployee(Employees employee) throws Exception;

	public Employees getEmployee(String id) throws Exception;

	public Boolean deleteEmployee(String id) throws Exception;
	
	public Boolean validateEmployeeId(String employeeId, Long orgLocationsId) throws Exception;

	public List<Employees> getAllEmployees() throws Exception;
	
	public List<Employees> getEmployeesExcludeTeamMembers(Long projectId) throws Exception;
}
