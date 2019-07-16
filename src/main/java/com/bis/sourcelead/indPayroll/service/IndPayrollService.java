package com.bis.sourcelead.indPayroll.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.indPayroll.dto.IndPaysheetFrequencyDTO;
import com.bis.sourcelead.payroll.entity.EmployeeSessionIndPayroll;

public interface IndPayrollService {

	ResponseList<IndPaysheetFrequencyDTO> getPayPeriodListByOrgLocationId(OrgLocations orgLocation, IndPaysheetFrequencyDTO indPaysheetFrequencyDTO) throws Exception;

	public EmployeeSessionIndPayroll saveEmployeeSessionIndPayroll(EmployeeSessionIndPayroll employeeSessionIndPayroll) throws Exception;
	
	public List<EmployeeSessionIndPayroll> getEmployeeSessionList(String PaySheetFrequencyUUID) throws Exception;

	void deleteIndPayrollCaheRecoedByPaySheetFrequencyUUID(List<EmployeeSessionIndPayroll> indPayrollCahe);
	
	public EmployeeSessionIndPayroll updateThePayHeadHistoryJsonObjectBYExclueUUID(String excludeUUID)throws Exception;
}
