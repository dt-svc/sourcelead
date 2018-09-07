package com.bis.sourcelead.dao;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.AssociateLoanAndAdvances;

public interface AssociateLoanAndAdvancesDao {
	
	AssociateLoanAndAdvances saveAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances) throws Exception;
	
	AssociateLoanAndAdvances getAssociateLoanAndAdvancesById(Long id) throws Exception ;

	AssociateLoanAndAdvances getAssociateLoanAndAdvancesDetailsByCandidateId(Long candidateId,Long orgLocationId) throws Exception;
	
	List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateId(Long candidateId,Long orgLocationId, String type) throws Exception;
	
	List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateIdByOrgLocationId(Long candidateId,Long orgLocation);

	Map<String, Double> getEmpLoansAndAdvancesCountListByOrgLocationId(Long candidateId);
}
