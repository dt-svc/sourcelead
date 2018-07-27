package com.bis.sourcelead.dao;

import com.bis.sourcelead.dto.PayrollLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.PayrollLoanAndAdvances;

public interface PayrollLoanAndAdvancesDao {

	PayrollLoanAndAdvances savePayrollLoanAndAdvances(PayrollLoanAndAdvances payrollLoanAndAdvances) throws Exception;
	
	ResponseList<PayrollLoanAndAdvancesDTO> getPayrollLoanAndAdvancesList(PayrollLoanAndAdvancesDTO payrollLoanAndAdvancesDTO,OrgLocations orgLocations) throws Exception;
	
	PayrollLoanAndAdvances getPayrollLoanAndAdvancesByUUID(String uuid) throws Exception;
}
