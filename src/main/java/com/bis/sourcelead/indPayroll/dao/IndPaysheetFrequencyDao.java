package com.bis.sourcelead.indPayroll.dao;

import java.util.List;

import com.bis.sourcelead.dto.Response;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.indPayroll.dto.IndEmployeePayrollDTO;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;
import com.bis.sourcelead.indPayroll.entity.IndPaysheetFrequency;
import com.bis.sourcelead.payroll.entity.EmployeeSessionIndPayroll;

public interface IndPaysheetFrequencyDao {

	ResponseList<IndPaysheetFrequency> getIndPaysheetFrequencyListByOrgLocationId(OrgLocations orgLocation, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception;

	IndPaysheetFrequency addIndPaysheetFrequency(IndPaysheetFrequency paySheetFreq);
	
	IndPaysheetFrequency getIndPaysheetFrequencyByEmpPayrollUUID(String payrollFrequencyUUID) throws Exception;
	
	ResponseList<IndEmployeePayroll> getIndEmployeePayrollListByPaysheetFrequencyUuid(String paysheetFrequencyUuid) throws Exception;
	
	Response<IndEmployeePayroll> getIndEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception ;
	
	ResponseList<IndEmployeePayroll> getIndPayrollApprovedEmployeeListByPaysheetFrequencyUuidAndEmpApproveStatus(String paysheetFrequencyUuid, String empApproveStatus) throws Exception;
	
	ResponseList<IndEmployeePayroll> getEmployeePaysByOrgLocationIdAndUserId(OrgLocations orgLocation, User user, IndEmployeePayrollDTO indEmployeePayrollDTO) throws Exception;
	
	IndEmployeePayroll  indEmployeePayrollByEmpPayrollUUID(String empPayrollUUID) throws Exception;

	ResponseList<IndEmployeePayroll> getPayrollRegisterReports(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);
	
	ResponseList<IndEmployeePayroll> getPayrollBankReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);

	List<IndEmployeePayroll> getYTDIndEmployeePayrollList(Long candidateId, String startMonth)throws Exception;
	
	ResponseList<IndEmployeePayroll> getPayrollYTDSummaryReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);

	ResponseList<IndEmployeePayroll> getPayrollAnnualTaxReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations) throws Exception;
	
	List<IndEmployeePayrollDTO> getPayrollSalaryStatementReportList(IndEmployeePayrollDTO indEmployeePayrollDTO,OrgLocations orgLocations)throws Exception;

	ResponseList<IndEmployeePayrollDTO> getPayrollESIAndPFReportList(IndEmployeePayrollDTO indEmployeePayrollDTO,OrgLocations orgLocations)throws Exception;

	ResponseList<IndEmployeePayrollDTO> getPayrollSalaryRegisterReportReportList(IndEmployeePayrollDTO indEmployeePayrollDTO, OrgLocations orgLocations);

	public EmployeeSessionIndPayroll saveEmployeeSessionIndPayroll(EmployeeSessionIndPayroll employeeSessionIndPayroll) throws Exception;

	List<EmployeeSessionIndPayroll>  getEmployeeSessionList(String paySheetFrequencyUUID) throws Exception ;
	
	void deleteIndPayrollCaheRecoedByPaySheetFrequencyUUID(List<EmployeeSessionIndPayroll> indPayrollCahe);
}
