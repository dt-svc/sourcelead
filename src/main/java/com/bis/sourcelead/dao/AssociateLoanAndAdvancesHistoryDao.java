package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;

public interface AssociateLoanAndAdvancesHistoryDao {

	AssociateLoanAndAdvanceHistory getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(Long loanAndAdvanceId);

	AssociateLoanAndAdvanceHistory saveAssociateLoanAndAdvancesHistory(AssociateLoanAndAdvanceHistory assLoanHistory);

	List<AssociateLoanAndAdvanceHistory> getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceLoanId(Long loanAndAdvanceId);
	
}
