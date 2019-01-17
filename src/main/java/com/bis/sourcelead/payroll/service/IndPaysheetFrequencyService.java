package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.payroll.dto.IndApprovePayrollDTO;
import com.bis.sourcelead.payroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.payroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.payroll.entity.IndPaysheetFrequency;

public interface IndPaysheetFrequencyService {

	ResponseList<IndPaysheetFrequency> getIndPaysheetFrequencyListByOrgLocationId(OrgLocations orgLocation, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception;

	IndPaysheetFrequency addIndPaysheetFrequency(IndPaysheetFrequency paySheetFreq);
	
	Response<IndPaysheetFrequencyDTO> getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID, OrgLocations orgLocation) throws Exception;
	
	ResponseList<IndEmployeePayroll> getIndEmployeePayrollListByPaysheetFrequencyUuid(String paysheetFrequencyUuid) throws Exception;
	
	Response<IndEmployeePayroll> getIndEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception;
	
	Response<IndApprovePayrollDTO> sentForApproveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO,IndPaysheetFrequencyDTO indPaysheetFrequencyDTO, OrgLocations orgLocation, User loginUser) throws Exception;;
	
	Response<IndApprovePayrollDTO> getApproveInfoIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, OrgLocations orgLocation, User loginUser) throws Exception;;
	
	IndPaysheetFrequency getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID) throws Exception;
	
	Response<IndApprovePayrollDTO> approveIndPayroll(IndApprovePayrollDTO indApprovePayrollDTO, IndPaysheetFrequency indPaysheetFrequency,OrgLocations orgLocation, User loginUser) throws Exception;
	
	ResponseList<IndEmployeePayrollDTO> getEmployeePaysByOrgLocationIdAndUserId(OrgLocations orgLocation, User user, IndEmployeePayrollDTO indEmployeePayrollDTO) throws Exception;
	
	IndEmployeePayroll  indEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception;
	
	ResponseList<IndEmployeePayroll> getPayrollRegisterReports(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);
	
	List<IndEmployeePayroll> getYTDIndEmployeePayrollList(String employeePayRollUUID)throws Exception;

}
