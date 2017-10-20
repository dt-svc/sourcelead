package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.bis.sourcelead.dto.CandidateReferencesDTO;
import com.bis.sourcelead.dto.ResponseList;
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
	
    public ResponseList<CandidateReferencesDTO>  getCandidateReferencesListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateReferences getCandidateReferencesByCandidateIdAndReferenceId(Long candidateId,Long refId) throws Exception;
	
    public CandidateReferences updateCandidateReferences(CandidateReferences candidateReferences) throws Exception;
	
	public CandidateReferences getCandidateReferenceByReferenceId(Long refId) throws Exception;
	
}
