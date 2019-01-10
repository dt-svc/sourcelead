package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.DepartmentDTO;
import com.bis.sourcelead.entity.Department;

public interface DepartmentDao {
	
	/**
	 * @author Laxman
	 * @param department
	 * @return
	 * @throws Exception
	 */
	 Department saveDepartment(Department department) throws Exception;
	
	 Department getDepartmentById(Long departmentId) throws Exception;
	
	 List<Department>  getDepartmentByOrgLocationId(Long orgLocationId) throws Exception;
	 
	 List<Department>  getDepartmentByOrgLocationIdAndStatus(Long orgLocationId,String status) throws Exception;
	 
	 Department validateDepartmentnameByDepartmentIdAndDepartmentName(Long departmentId,String departmentName) throws Exception;
		
	 Boolean validateDepartmentnameByOrgLocationIdAndDepartmentName(Long orgLocationId,String departmentName) throws Exception;
	 
	 List<String> getDepartmentList(Long orgLocationId) throws Exception;
	 
	 Department getDepartmentByOrgLocationIdAndDepartmentName(Long orgLocationId,String departmentName) throws Exception;
	 
	 List<DepartmentDTO> getDepartmentsListByIds(List<Long> id) throws Exception;
	 
	 List<DepartmentDTO> getDepartmentsListByOrgLocationId(Long orgLocationId) throws Exception;
	
}
