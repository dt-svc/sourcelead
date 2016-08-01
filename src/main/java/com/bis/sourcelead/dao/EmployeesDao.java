package com.bis.sourcelead.dao;

import java.util.Collection;
import java.util.List;

import com.bis.sourcelead.entity.Employees;

public interface EmployeesDao {

	public Employees addEmployee(Employees employee) throws Exception;

	public Employees updateEmployee(Employees employee) throws Exception;

	public Employees getEmployee(String id) throws Exception;

	public Boolean deleteEmployee(String id) throws Exception;
	
	public Employees getEmployeeByUserId(Long userId) throws Exception;
	
	public Boolean validateEmployeeId(String employeeId, Long orgLocationsId) throws Exception;
	
	public Employees getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId(String employeeId,Long userId, Long orgLocationsId) throws Exception;

	public List<Employees> getAllEmployees() throws Exception;
	
	public List<Employees> getEmployeesExcludeTeamMembers(Long projectId, Long orgLocationsId) throws Exception;
	
	public Collection<Employees> getAllEmployeesByOrgLocationId(Long orgLocationId)throws Exception;

	public Boolean validateEmployeeEmailId(Long userId, String emailId);
	
}
