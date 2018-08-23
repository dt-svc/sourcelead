package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.payroll.entity.EmployeePayroll;

public interface PaysheetFrequencyDao {

	List<EmployeePayroll> getMyPayrollListByCandidateId(Long candidateId);
}
