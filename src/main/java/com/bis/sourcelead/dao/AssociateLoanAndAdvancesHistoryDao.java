package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;

public interface AssociateLoanAndAdvancesHistoryDao {

	AssociateLoanAndAdvanceHistory getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(Long loanAndAdvanceId);

	AssociateLoanAndAdvanceHistory saveAssociateLoanAndAdvancesHistory(AssociateLoanAndAdvanceHistory assLoanHistory);
	
}
