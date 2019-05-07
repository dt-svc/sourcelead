package com.bis.sourcelead.reports.service;

import com.bis.sourcelead.dto.LoanAndAdvanceReportDTO;
import com.bis.sourcelead.dto.ResponseList;

public interface LoanAndAdvancesReportService {
	
	ResponseList<LoanAndAdvanceReportDTO> getLoansAndAdvancesByLoanNameAndOrglocationId(Long orgLocations,LoanAndAdvanceReportDTO loanAndAdvanceReportDTO)throws Exception;

}
