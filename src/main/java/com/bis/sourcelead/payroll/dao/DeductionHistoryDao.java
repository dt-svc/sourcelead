package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;

public interface DeductionHistoryDao {

	List<DeductionHistoryDTO> getDeductionsByPayrollId(Long payrollId);

	List<ContributionHistoryDTO> getContributionsByPayrollId(Long payrollId);
}
