package com.bis.sourcelead.service;

import org.json.JSONException;

import com.bis.sourcelead.dto.CandidateReferencesDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.CandidateReferences;
import com.bis.sourcelead.entity.User;

public interface CandidateReferencesService {

	
	/**
	 * @param jsonObj
	 * @param candidate
	 * @param orgLocation
	 * @param multiFile
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public CandidateReferences storeCandidateReferences(CandidateReferencesDTO candidateReferencesDTO,Long candidateId,Long orgLocationId,User user) throws JSONException,Exception;
	
    public ResponseList<CandidateReferencesDTO>  getCandidateReferencesListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId, String loginUserCode) throws Exception;
	
	public CandidateReferences getCandidateReferencesByCandidateIdAndReferenceId(Long candidateId,Long refId) throws Exception;
	
    public CandidateReferences updateCandidateReferences(CandidateReferences candidateReferences) throws Exception;
	
	public CandidateReferences getCandidateReferenceByReferenceId(Long refId) throws Exception;

	void saveOrUpdateCandidateElkDocumentWithReferenceDetails(Candidate candidate, Long locationId) throws Exception;
	
	Boolean validateReferenceEmail(String referenceEmail, Long candidateId,Long candidateReferenceId) throws Exception;
	
    public CandidateReferences validateReferenceEmailByReferEmailAndReferId(String referEmail,Long referId) throws Exception;

	Boolean validateReferenceEmailByReferEmailAndCandidateId(String referenceEmail, Long candidateId) throws Exception;

	
	
}
