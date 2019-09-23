package com.bis.sourcelead.payroll.dao;

import java.util.List;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;
import com.bis.sourcelead.payroll.entity.DeductionHistory;

public interface DeductionHistoryDao {

	List<DeductionHistoryDTO> getDeductionsByPayrollId(Long payrollId);

	List<ContributionHistoryDTO> getContributionsByPayrollId(Long payrollId);

	List<DeductionHistory> getDedudctionDataBasedOnPayrollCheckDate(OrgLocations orgLocation,PayrollReportDTO payrollReportDTO);
}
