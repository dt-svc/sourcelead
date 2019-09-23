package com.bis.sourcelead.indPayroll.service;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.dto.IndApprovePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;

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
	
	ResponseList<IndEmployeePayroll> getPayrollBankReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);

	ResponseList<IndEmployeePayroll> getPayrollYTDSummaryReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);

	ResponseList<IndEmployeePayroll> getPayrollAnnualTaxReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) throws Exception;
	
	List<IndEmployeePayroll> getYTDIndEmployeePayrollList(Long candidateId, String startMonth)throws Exception;

	List<IndEmployeePayrollDTO> getPayrollSalaryStatementReportList(IndEmployeePayrollDTO indEmployeePayrollDTO,OrgLocations orgLocations)throws Exception;

	ResponseList<IndEmployeePayrollDTO> getPayrollESIAndPFReportList(IndEmployeePayrollDTO indEmployeePayrollDTO,OrgLocations orgLocations)throws Exception;

	ResponseList<IndEmployeePayrollDTO> getPayrollSalaryRegisterReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);

}
