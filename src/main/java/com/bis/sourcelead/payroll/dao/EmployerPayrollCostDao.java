package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;

public interface EmployerPayrollCostDao {

	EmployerPayrollCost saveEmployerPayrollCost(EmployerPayrollCost employerPayrollCost)throws Exception;

	List<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(OrgLocations orgLocation);
	
	EmployerPayrollCost getEmployeePayrollCostById(String EmpId);

	int getEmployerPayrollCostVersionByEmpId(String empId, Long locationId);

}
