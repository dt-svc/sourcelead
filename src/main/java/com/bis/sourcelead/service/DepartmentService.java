package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;

import com.bis.sourcelead.entity.Department;

public interface DepartmentService {

	Department saveDepartment(Department department) throws Exception;

	Department getDepartmentById(Long departmentId) throws Exception;
	
	List<Department>  getDepartmentByOrgLocationId(Long orgLocationId) throws Exception;
	
	String  saveDepartment(JSONObject jsonObj) throws Exception;
	
	Department validateDepartmentnameByDepartmentIdAndDepartmentName(Long departmentId,String departmentName) throws Exception;
	
	Boolean validateDepartmentnameByOrgLocationIdAndDepartmentName(Long orgLocationId,String departmentName) throws Exception;
}
