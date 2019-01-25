package com.bis.sourcelead.payroll.service;

import java.util.List;

import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.payroll.dto.ContributionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionHistoryDTO;
import com.bis.sourcelead.payroll.dto.DeductionSummaryDTO;
import com.bis.sourcelead.payroll.dto.PayrollReportDTO;

public interface DeductionHistoryService {

	List<DeductionHistoryDTO> getDeductionsByPayrollId(Long payrollId);

	List<ContributionHistoryDTO> getContributionsByPayrollId(Long id);

	ResponseList<DeductionSummaryDTO> getDedudctionDataBasedOnPayrollCheckDate(OrgLocations orgLocation,PayrollReportDTO payrollReportDTO);

	ResponseList<DeductionSummaryDTO> getContributionDataBasedOnPayrollCheckDate(OrgLocations orgLocation,
			PayrollReportDTO payrollReportDTO);
}
