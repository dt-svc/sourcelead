package com.bis.sourcelead.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.EmployeesDao;
import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.entity.Employees;
import com.bis.sourcelead.entity.OrgLocations;
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
	
	/*
	 * (non-Javadoc)
	 * @see com.bis.sourcelead.service.EmployeesService#getEmployeeByUserId(java.lang.String)
	 */
	@Override
	public Employees getEmployeeByUserId(Long userId) throws Exception {
		return employeesDao.getEmployeeByUserId(userId);
	}
	
	@Override
	public Boolean validateEmployeeId(String employeeId, Long orgLocationsId) throws Exception {
		Boolean flag = employeesDao.validateEmployeeId(employeeId,orgLocationsId);
		return flag;
	}
	
	@Override
	public Employees getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId(String employeeId, Long userId, Long orgLocationsId)
			throws Exception {
		return employeesDao.getEmployeeByEmployeeIdAndUserIdAndOrgLocationsId(employeeId,userId,orgLocationsId);
	}

	@Override
	public List<Employees> getAllEmployees() throws Exception {
		logger.debug("In EmployeesServiceImpl class getAllEmployees method...");
		List<Employees> emplist= employeesDao.getAllEmployees();
		return emplist;
	}
	
	@Override
	public List<Employees> getEmployeesExcludeTeamMembers(Long projectId, Long orgLocationsId) throws Exception {
		logger.debug("In EmployeesServiceImpl class getEmployeesExcludeTeamMembers method...");
		List<Employees> emplist= employeesDao.getEmployeesExcludeTeamMembers(projectId,orgLocationsId);
		return emplist;
	}

	@Override
	public Collection<Employees> getAllEmployeesByOrgLocationId(Long orgLocationId,Long level1Manager,Long level2Manager) throws Exception {
		Collection<Employees> employeeList = employeesDao.getAllEmployeesByOrgLocationId(orgLocationId,level1Manager,level2Manager);
		return employeeList;
	}

	@Override
	public Boolean validateEmployeeEmailId(Long userId, String emailId) {
		return employeesDao.validateEmployeeEmailId(userId,emailId);
	}

	@Override
	public Collection<Employees> getAllEmployeesByOrgLocationIdAndSearchByName(Long orgLocationId, Long level1Manager,
			Long level2Manager, String searchByName) throws Exception {
		Collection<Employees> employeeList = employeesDao.getAllEmployeesByOrgLocationIdAndSearchByName(orgLocationId, level1Manager, level2Manager, searchByName);
		return employeeList;
	}

	@Override
	public List<Employees> getEmployeesExcludeTeamMembersBySearchByName(Long projectId, Long orgLocationsId,
			String searchByName) throws Exception {
		List<Employees> emplist= employeesDao.getEmployeesExcludeTeamMembersBySearchByName(projectId, orgLocationsId, searchByName);
		return emplist;
	}

	@Override
	public Employees getEmployeeByUserIdAndOrgLocationsId(Long userId, Long orgLocationsId) {
		// TODO Auto-generated method stub
		return  employeesDao.getEmployeeByUserIdAndOrgLocationsId(userId,orgLocationsId);
	}

	@Override
	public List<Employees> getEmployeesByLocationId(Long orgLocationsId) throws Exception {
		return employeesDao.getEmployeesByLocationId(orgLocationsId);
	}

	@Override
	public Employees getEmployeByUserIdAndOrgRelationId(Long userId, Long orgRelationId) throws Exception {
		return employeesDao.getEmployeByUserIdAndOrgRelationId(userId,orgRelationId);
	}

	@Override
	public Employees getEmployeeByUserAndOrgLocationId(Long userId, Long orgLocationId)
	{
		return employeesDao.getEmployeeByUserAndOrgLocationId(userId,orgLocationId);
	}

	@Override
	public List<UserDTO> getEmployeesBasedOnDepartment(String departmentId, OrgLocations orgLocation) {
		List<Employees> empList =  employeesDao.getEmployeesBasedOnDepartment(departmentId,orgLocation);
		List<UserDTO> userList = new ArrayList<>();
		
		for (Employees employee : empList) {
			UserDTO user = new UserDTO();
			user.setId(employee.getId());
			if(employee.getUser().getLastName() != null) {
				StringBuilder sb = new StringBuilder();
				user.setFullName(sb.append(employee.getUser().getFirstName()).append(" ").append(employee.getUser().getLastName()).toString());
			}else {
				user.setFullName(employee.getUser().getFirstName());
			}
			
			userList.add(user);
		}
		return userList;
	}

	
}
