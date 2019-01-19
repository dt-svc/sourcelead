package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.payroll.dto.AssociatePayrollPayheadHistoryDTO;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;

public interface AssociatePayrollPayheadHistoryService {

	ResponseList<AssociatePayrollPayheadHistoryDTO> getUpdatedAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
	
	ResponseList<AssociatePayrollPayheadHistoryDTO> getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(String empPayrollUUID) throws Exception;
	
	List<AssociatePayrollPayheadHistory> getLatestAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
	
	List<AssociatePayrollPayheadHistory> getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(String paySheetFrequencyUUID)throws Exception;
	
	IndEmployeePayroll saveIndEmployeePayroll(IndEmployeePayroll indEmployeePayroll) throws Exception;
	
}
