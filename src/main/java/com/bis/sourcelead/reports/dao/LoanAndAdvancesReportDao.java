package com.bis.sourcelead.reports.dao;

import com.bis.sourcelead.dto.LoanAndAdvanceReportDTO;
import com.bis.sourcelead.dto.ResponseList;

public interface LoanAndAdvancesReportDao {
	
	ResponseList<LoanAndAdvanceReportDTO> getLoansAndAdvancesByLoanNameAndOrglocationId(Long orgLocations,LoanAndAdvanceReportDTO loanAndAdvanceReportDTO)throws Exception;
	
}
