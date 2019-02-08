package com.bis.sourcelead.indPayroll.service;


import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;

public interface GenerateINDPayrollService {
	
	IndPaysheetFrequencyDTO generateINDPayroll(OrgLocations orgLocation, GenerateINDPayrollDTO generateINDPayrollDTO, User loginUser) throws Exception;

	IndPaysheetFrequencyDTO getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO,IndPaysheetFrequency paysheetFrequency, OrgLocations orgLocation, User loginUser);
	
	Long getMaxPayRunIdByOrgLocationId(Long orgLocationId);

}
