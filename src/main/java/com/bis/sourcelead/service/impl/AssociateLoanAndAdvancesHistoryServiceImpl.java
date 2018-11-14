package com.bis.sourcelead.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssociateLoanAndAdvancesHistoryDao;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.AssociateLoanAndAdvancesHistoryService;

@Service
public class AssociateLoanAndAdvancesHistoryServiceImpl implements AssociateLoanAndAdvancesHistoryService{

	@Autowired
	AssociateLoanAndAdvancesHistoryDao associateLoanAndAdvancesHistoryDao;
	
	@Override
	public AssociateLoanAndAdvanceHistory getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(Long loanAndAdvanceId)
	{
		return associateLoanAndAdvancesHistoryDao.getLoanAndAdvanceHistoryByCandidateIdAndLocationIdAndAdvanceId(loanAndAdvanceId);
	}

	@Override
	public AssociateLoanAndAdvanceHistory saveAssociateLoanAndAdvancesHistory(AssociateLoanAndAdvanceHistory assLoanHistory)
	{
		return associateLoanAndAdvancesHistoryDao.saveAssociateLoanAndAdvancesHistory(assLoanHistory);
	}
	
	@Override
	public List<AssociateLoanAndAdvanceHistory> getAssociateLoanAndAdvanceHistoryListByUserIdAndLocationId(User user, OrgLocations orgLocation, String loanStatus) throws Exception {
			return associateLoanAndAdvancesHistoryDao.getAssociateLoanAndAdvanceHistoryListByUserIdAndLocationId(user, orgLocation,loanStatus);
	}

	@Override
	public Double getEmpLoansAndAdvancesPaidAmountByOrgLocationIdCandidateIdLoansAndAdId(Long candidateId, Long orgLocationId, Long assLAId)  throws Exception {
		return associateLoanAndAdvancesHistoryDao.getEmpLoansAndAdvancesPaidAmountByOrgLocationIdCandidateIdLoansAndAdId(candidateId, orgLocationId, assLAId);
	}
}
