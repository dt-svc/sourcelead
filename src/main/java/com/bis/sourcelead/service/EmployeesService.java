package com.bis.sourcelead.service;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgRelations;

@Service
public interface EmployeesService {

	public Employees addEmployee(Employees employee) throws Exception;

	public Employees updateEmployee(Employees employee) throws Exception;

	public Employees getEmployee(String id) throws Exception;

	public Boolean deleteEmployee(String id) throws Exception;
	
	public Employees getEmployeeByUserId(Long userId) throws Exception;
	
	public Boolean validateEmployeeId(String employeeId, Long orgLocationsId) throws Exception;

	public List<Employees> getAllEmployees() throws Exception;
	
	public List<Employees> getEmployeesExcludeTeamMembers(Long projectId) throws Exception;
	
	public Collection<Employees> getAllEmployeesByOrgLocationId(Long orgLocationId)throws Exception;
}
