package com.bis.sourcelead.payroll.service;


import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.GenerateINDPayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;

public interface GenerateINDPayrollService {
	
	IndPaysheetFrequencyDTO generateINDPayroll(IndPaysheetFrequencyDTO existedData, OrgLocations orgLocation, GenerateINDPayrollDTO generateINDPayrollDTO, User loginUser) throws Exception;

	IndPaysheetFrequencyDTO getPayrollData(GenerateINDPayrollDTO generateINDPayrollDTO,IndPaysheetFrequency paysheetFrequency, OrgLocations orgLocation, User loginUser);
	
	Long getMaxPayRunIdByOrgLocationId(Long orgLocationId);

}
