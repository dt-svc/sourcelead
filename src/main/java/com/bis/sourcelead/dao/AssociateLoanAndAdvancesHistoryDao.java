package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateLoanAndAdvancesHistoryDao {

	AssociateLoanAndAdvanceHistory getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(Long loanAndAdvanceId);

	AssociateLoanAndAdvanceHistory saveAssociateLoanAndAdvancesHistory(AssociateLoanAndAdvanceHistory assLoanHistory);

	List<AssociateLoanAndAdvanceHistory> getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceLoanId(Long loanAndAdvanceId);

	List<AssociateLoanAndAdvanceHistory> getAssociateLoanAndAdvanceHistoryListByUserIdAndLocationId(User user, OrgLocations orgLocation, String loanStatus);
	
}
