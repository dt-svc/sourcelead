package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OffBoardingDocuments;

public interface OffBoardingDocumentsDao {

	OffBoardingDocuments saveOffBoardingDocuments(OffBoardingDocuments offBoardingDocuments)throws Exception;
	
	List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
	
	OffBoardingDocuments getOffBoardingDocumentsBydocumentUUID(String documentUUID) throws Exception;
	
	Integer getOffboardingDocumentsByOffboardingId(Long offBoardingId) throws Exception;
	
	List<OffBoardingDocuments> getOffBoardingDocumentsByCandidateOffBoardingId(Long candidateOffBoardingId,String documentType) throws Exception;
	
	List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateIdsList(List<Long> templateIds,String documentType) throws Exception;
	
	OffBoardingDocuments getOffBoardingDocumentsById(Long id) throws Exception;
}
