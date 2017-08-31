package com.bis.sourcelead.payroll.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.SubmissionApproverHierarchy;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;
import com.bis.sourcelead.payroll.entity.UserPayroll;

@Service
public interface EmployerPayrollCostService {

	Integer saveEmployerPayrollCost(MultipartFile multipartFile,OrgLocations orgLocation,User user, JSONObject jsonObj)throws Exception;

	List<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(OrgLocations orgLocation)throws Exception;

	List<UserPayroll> runPayrollCycle(OrgLocations orgLocation, User user, JSONObject jsonObj)throws Exception;
	
	int getEmployerPayrollCostVersionByEmpId(String EmpId,Long locationId);
}
