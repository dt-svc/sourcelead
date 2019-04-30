package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;

public interface PayrollLoanAndAdvancesDao {

	PayrollLoanAndAdvances savePayrollLoanAndAdvances(PayrollLoanAndAdvances payrollLoanAndAdvances) throws Exception;
	
	ResponseList<PayrollLoanAndAdvancesDTO> getPayrollLoanAndAdvancesList(PayrollLoanAndAdvancesDTO payrollLoanAndAdvancesDTO,OrgLocations orgLocations) throws Exception;
	
	PayrollLoanAndAdvances getPayrollLoanAndAdvancesByUUID(String uuid) throws Exception;
	
	List<PayrollLoanAndAdvances> getPayrollLoanAndAdvancesByTypeAndOrgLocationId(String type,Long orgLocationId) throws Exception;
	
	PayrollLoanAndAdvances getPayrollLoanAndAdvancesById(Long id) throws Exception;

	List<PayrollLoanAndAdvances> getPayrollLoanAndAdvancesByOrgLocationId(Long orgLocationId);
}
