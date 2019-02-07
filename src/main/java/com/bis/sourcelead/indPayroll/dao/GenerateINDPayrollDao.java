package com.bis.sourcelead.indPayroll.dao;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;

public interface GenerateINDPayrollDao {

	IndPaysheetFrequency getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO, OrgLocations orgLocation, User loginUser);
	
	Long getMaxPayRunIdByOrgLocationId(Long orgLocationId);


}
