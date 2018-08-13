package com.bis.sourcelead.dao;

import com.bis.sourcelead.entity.AssociateLoanAndAdvances;

public interface AssociateLoanAndAdvancesDao {
	
	AssociateLoanAndAdvances saveAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances) throws Exception;
	
	AssociateLoanAndAdvances getAssociateLoanAndAdvancesById(Long id) throws Exception ;

}
