package com.bis.sourcelead.indPayroll.dao;

import java.util.List;

import com.bis.sourcelead.indPayroll.entity.AssociatePayrollPayheadHistory;
import com.bis.sourcelead.indPayroll.entity.IndEmployeePayroll;

public interface AssociatePayrollPayheadHistoryDao {

	List<AssociatePayrollPayheadHistory> getUpdatedAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
	
	List<AssociatePayrollPayheadHistory>  getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(String empPayrollUUID) throws Exception;
	
    List<AssociatePayrollPayheadHistory> getLatestAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
	
	List<AssociatePayrollPayheadHistory> getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(String paySheetFrequencyUUID)throws Exception;
	
	AssociatePayrollPayheadHistory saveAssociatePayheadAdjustInd(AssociatePayrollPayheadHistory associatePayrollPayheadHistory) throws Exception;
	
	IndEmployeePayroll saveIndEmployeePayroll(IndEmployeePayroll indEmployeePayroll) throws Exception;
}
