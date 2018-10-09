package com.bis.sourcelead.payroll.dao;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;

public interface IndPaysheetFrequencyDao {

	ResponseList<IndPaysheetFrequency> getIndPaysheetFrequencyListByOrgLocationId(OrgLocations orgLocation, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception;

	IndPaysheetFrequency addIndPaysheetFrequency(IndPaysheetFrequency paySheetFreq);
	
	IndPaysheetFrequency getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID) throws Exception;
	
	ResponseList<IndEmployeePayroll> getIndEmployeePayrollListByPaysheetFrequencyUuid(String paysheetFrequencyUuid) throws Exception;
	
	Response<IndEmployeePayroll> getIndEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception ;
	
	ResponseList<IndEmployeePayroll> getIndPayrollApprovedEmployeeListByPaysheetFrequencyUuidAndEmpApproveStatus(String paysheetFrequencyUuid, String empApproveStatus) throws Exception;
	
	ResponseList<IndEmployeePayroll> getEmployeePaysByOrgLocationIdAndUserId(OrgLocations orgLocation, User user, IndEmployeePayrollDTO indEmployeePayrollDTO) throws Exception;
}
