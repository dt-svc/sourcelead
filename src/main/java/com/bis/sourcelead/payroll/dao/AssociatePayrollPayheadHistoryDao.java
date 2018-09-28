package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociatePayrollPayheadHistory;

public interface AssociatePayrollPayheadHistoryDao {

	List<AssociatePayrollPayheadHistory> getUpdatedAssociatePayrollPayheadByEmpPayrollId(Long empPayrollId) throws Exception;
}
