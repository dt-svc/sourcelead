package com.bis.sourcelead.service;

import java.util.List;
import java.util.Map;

import com.bis.sourcelead.dto.AssociateLoanAndAdvancesDTO;
import com.bis.sourcelead.entity.AssociateLoanAndAdvances;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

public interface AssociateLoanAndAdvancesService {

	AssociateLoanAndAdvances saveAssociateLoanAndAdvances(AssociateLoanAndAdvances associateLoanAndAdvances) throws Exception;
	
	AssociateLoanAndAdvances getAssociateLoanAndAdvancesById(Long id) throws Exception ;
	
	AssociateLoanAndAdvances storeAssociateLoanAndAdvances(AssociateLoanAndAdvancesDTO associateLoanAndAdvancesDTO,OrgLocations orgLocations,User user) throws Exception;
	
	AssociateLoanAndAdvances getAssociateLoanAndAdvancesDetailsByCandidateId(Long candidateId,Long orgLocationId) throws Exception;
	
	List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateId(Long candidateId,Long orgLocationId) throws Exception;
	
	AssociateLoanAndAdvancesDTO getAssociateLoanAndAssociateDetails(Long candidateId,OrgLocations orgLocations) throws Exception;
	
	AssociateLoanAndAdvancesDTO getAssociateLoanAndAdvancesDetailsById(Long id,OrgLocations orgLocations) throws Exception ;
	
	List<AssociateLoanAndAdvancesDTO> getAssociateLoanAndAdvancesListByCandidateIdByOrgLocationId(Long candidateId,OrgLocations orgLocation, String type)throws Exception;

	Map<String, Double> getEmpLoansAndAdvancesCountListByOrgLocationId(Long candidateId);
	
	List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByUserIdAndLocationId(User user, OrgLocations orgLocation, String loanStatus) throws Exception;
	
	List<AssociateLoanAndAdvances> getAssociateLoanAndAdvancesListByCandidateIdOrgLocationIdAndLoanStatus(Long candidateId,Long orgLocationId, List<String> loanStatus) throws Exception;
}
