package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONException;

import com.bis.sourcelead.dto.CandidateDocumentsDTO;
import com.bis.sourcelead.dto.ResponseList;
import com.bis.sourcelead.entity.CandidateDocuments;
import com.bis.sourcelead.entity.User;

public interface CandidateDocumentsService {
	/**
	 * @param jsonObj
	 * @param candidate
	 * @param orgLocation
	 * @param multiFile
	 * @return
	 * @throws JSONException
	 * @throws Exception
	 */
	public CandidateDocuments storeCandidateDocuments(CandidateDocumentsDTO candidateDocumentsDTO, Long candidateId, String documentUUID, Long orgLocationId, User user) throws JSONException, Exception;
	
	public List<CandidateDocuments> getCandidateDocumentsListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateDocuments getCandidateDocumentByDocumentId(Long documentId) throws Exception;
	
	public CandidateDocuments updateCandidateDocuments(CandidateDocuments candidateDocuments) throws Exception;
	
	public CandidateDocuments getCandidateDocumentByDocumentUUID(String documentUUID) throws Exception;
	
	public List<CandidateDocuments> getCandidateDocumentsListByRequestedDocumentsId(Long requestedDocumentsId) throws Exception;
	
	public Boolean validateCandidateDocument(Long candidateId, String candidateDocument) throws Exception;
	
	public List<CandidateDocumentsDTO> getCandidateDocumentsDTOListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;

	void saveOrUpdateCandidateElkDocumentWithDocumentDetails(Long candidateId,Long orgLocationId) throws Exception;
	
	public Integer getCandidateActiveDocumentsByCandidateId(Long candidateId);
}
