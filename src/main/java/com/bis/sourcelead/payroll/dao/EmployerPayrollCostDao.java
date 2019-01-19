package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.EmployerPayrollCostDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.entity.EmployerPayrollCost;

public interface EmployerPayrollCostDao {

	EmployerPayrollCost saveEmployerPayrollCost(EmployerPayrollCost employerPayrollCost)throws Exception;

	ResponseList<EmployerPayrollCost> loadEmployerPayrollCostByOrgLocationId(OrgLocations orgLocation, EmployerPayrollCostDTO employerPayrollCostDTO) throws Exception;
	
	EmployerPayrollCost getEmployeePayrollCostById(String EmpId);

	int getEmployerPayrollCostVersionByLocationId(Long locationId);

	List<EmployerPayrollCost> getEmployerPayrollCostByLocationId(Long locationId);

}
