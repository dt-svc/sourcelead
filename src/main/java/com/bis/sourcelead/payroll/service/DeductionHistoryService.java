package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;

public interface DeductionHistoryService {

	List<DeductionHistoryDTO> getDeductionsByPayrollId(Long payrollId);

	List<ContributionHistoryDTO> getContributionsByPayrollId(Long id);
}
