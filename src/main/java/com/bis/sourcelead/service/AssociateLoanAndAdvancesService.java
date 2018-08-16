package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateLoanAndAdvancesService {

	AssociateLoanAndAdvances saveAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances) throws Exception;
	
	AssociateLoanAndAdvances getAssociateLoanAndAdvancesById(Long id) throws Exception ;
	
	AssociateLoanAndAdvances storeAssociateLoanAndAdvances(AssociateLoanAndAdvancesDTO associateLoanAndAdvancesDTO,OrgLocations orgLocations,User user) throws Exception;
	
	AssociateLoanAndAdvances getAssociateLoanAndAdvancesDetailsByCandidateId(Long candidateId,Long orgLocationId) throws Exception;
	
	List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateId(Long candidateId,Long orgLocationId) throws Exception;
	
	AssociateLoanAndAdvancesDTO getAssociateLoanAndAssociateDetails(Long candidateId,OrgLocations orgLocations) throws Exception;
}
