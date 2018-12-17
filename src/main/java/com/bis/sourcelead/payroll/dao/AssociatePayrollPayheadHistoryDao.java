package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.payroll.entity.IndEmployeePayroll;

public interface AssociatePayrollPayheadHistoryDao {

	List<AssociatePayrollPayheadHistory> getUpdatedAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
	
	List<AssociatePayrollPayheadHistory>  getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(String empPayrollUUID) throws Exception;
	
    List<AssociatePayrollPayheadHistory> getLatestAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
	
	List<AssociatePayrollPayheadHistory> getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(String paySheetFrequencyUUID)throws Exception;
	
	AssociatePayrollPayheadHistory saveAssociatePayheadAdjustInd(AssociatePayrollPayheadHistory associatePayrollPayheadHistory) throws Exception;
	
	IndEmployeePayroll saveIndEmployeePayroll(IndEmployeePayroll indEmployeePayroll) throws Exception;
}
