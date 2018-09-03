package com.bis.sourcelead.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.AssociateLoanAndAdvancesHistoryDao;
import com.bis.sourcelead.entity.AssociateLoanAndAdvanceHistory;
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
	
	
}
