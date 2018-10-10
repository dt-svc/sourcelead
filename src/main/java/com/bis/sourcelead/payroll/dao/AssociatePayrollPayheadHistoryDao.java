package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;

public interface AssociatePayrollPayheadHistoryDao {

	List<AssociatePayrollPayheadHistory> getUpdatedAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
	
	List<AssociatePayrollPayheadHistory>  getUpdatedAssociatePayrollPayheadByEmpPayrollUUID(String empPayrollUUID) throws Exception;
	
	List<AssociatePayrollPayheadHistory> getAllUpdatedAssociatePayrollPayheadByPaySheetFrequencyUUID(String paySheetFrequencyUUID)throws Exception;
}
