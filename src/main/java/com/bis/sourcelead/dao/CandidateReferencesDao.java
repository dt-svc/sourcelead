package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateReferences;

/**
 * @author BIS_Prasad
 */
public interface CandidateReferencesDao {
	
	public CandidateReferences storeCandidateReferences(CandidateReferences candidateReferences) throws Exception; 
	
	public List<CandidateReferences>  getCandidateReferencesListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateReferences getCandidateReferencesByCandidateIdAndReferenceId(Long candidateId,Long refId) throws Exception;
	
	public CandidateReferences updateCandidateReferences(CandidateReferences candidateReferences) throws Exception;
	
	public CandidateReferences getCandidateReferenceByReferenceId(Long refId) throws Exception;
	
	Boolean validateReferenceEmail(String referenceEmail, Long candidateId,Long candidateReferenceId) throws Exception;

	public CandidateReferences validateReferenceEmailByReferEmailAndReferId(String referEmail,Long referId) throws Exception;

	Boolean validateReferenceEmailByReferEmailAndCandidateId(String referenceEmail, Long candidateId) throws Exception;
	
}
