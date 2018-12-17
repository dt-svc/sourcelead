package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateLoanAndAdvancesHistoryService {

	AssociateLoanAndAdvanceHistory getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(Long loanAndAdvanceId);

	AssociateLoanAndAdvanceHistory saveAssociateLoanAndAdvancesHistory(AssociateLoanAndAdvanceHistory assLoanHistory);
	
	List<AssociateLoanAndAdvanceHistory> getAssociateLoanAndAdvanceHistoryListByUserIdAndLocationId(User user, OrgLocations orgLocation, String loanStatus) throws Exception;
	
	Double getEmpLoansAndAdvancesPaidAmountByOrgLocationIdCandidateIdLoansAndAdId(Long candidateId, Long orgLocationId, Long assLAId) throws Exception;

}
