package com.bis.sourcelead.reports.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.dto.LoanAndAdvanceReportDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.reports.dao.LoanAndAdvancesReportDao;
import com.bis.sourcelead.reports.service.LoanAndAdvancesReportService;

@Service
public class LoanAndAdvancesReportServiceImpl implements LoanAndAdvancesReportService {
	
	@Autowired
	LoanAndAdvancesReportDao loanAndAdvancesReportDao;

	@Override
	public ResponseList<LoanAndAdvanceReportDTO> getLoansAndAdvancesByLoanNameAndOrglocationId(Long orgLocations,
			LoanAndAdvanceReportDTO loanAndAdvanceReportDTO) throws Exception {
		
		return loanAndAdvancesReportDao.getLoansAndAdvancesByLoanNameAndOrglocationId(orgLocations, loanAndAdvanceReportDTO);
	}

}
