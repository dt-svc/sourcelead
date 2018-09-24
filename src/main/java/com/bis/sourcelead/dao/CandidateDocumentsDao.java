package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.CandidateDocuments;

/**
 * 
 * @author Prasad K
 * @date 06th April 2017
 *
 */
public interface CandidateDocumentsDao {
	public CandidateDocuments storeCandidateDocuments(CandidateDocuments candidateDocuments) throws Exception;
	
	public List<CandidateDocuments> getCandidateDocumentsListByCandidateIdAndOrgLocationID(Long candidateId, Long orgLocationId) throws Exception;
	
	public CandidateDocuments updateCandidateDocuments(CandidateDocuments candidateDocuments) throws Exception;
	
	public CandidateDocuments getCandidateDocumentByCandidateId(Long candidateId, String documentUUID) throws Exception;
	
	public CandidateDocuments getCandidateDocumentByDocumentId(Long documentId) throws Exception;
	
	public CandidateDocuments getCandidateDocumentByDocumentUUID(String documentUUID) throws Exception;
	
	public List<CandidateDocuments> getCandidateDocumentsListByRequestedDocumentsId(Long requestedDocumentsId) throws Exception;
	
	public Boolean validateCandidateDocument(Long candidateId, String candidateDocument) throws Exception;
	
	public Integer getCandidateActiveDocumentsByCandidateId(Long candidateId);
}
