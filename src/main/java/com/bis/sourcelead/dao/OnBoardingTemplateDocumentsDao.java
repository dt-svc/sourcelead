package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;

public interface OnBoardingTemplateDocumentsDao {

	OnBoardingTemplateDocuments saveOnBoardingTemplateDocuments(OnBoardingTemplateDocuments onBoardingTemplateDocuments)throws Exception;
	
	List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
	
	OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID) throws Exception;
	
	List<OnBoardingTemplateDocuments> getCandidateOnBoardingTemplateDocumentsByTemplateId(List<Long> templateIds,String documentType) throws Exception;
	
	OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsById(Long Id) throws Exception;
	
	Integer getCandidateOnboardingDocumentsByCandidateOnboardingId(Long candidateOnBoardingId) throws Exception;
	
	List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByCandidateOnBoardingId(Long candidateOnBoardingId,String documentType) throws Exception;
	
	List<Long> getOnBoardingTemplateDocumentIdsByCandidateOnBoardingId(Long candidateOnBoardingId,String documentType) throws Exception;
	
	Integer getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(Long candidateOnBoardingId) throws Exception;
	
	Integer getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(Long candidateOnBoardingId) throws Exception;
}
