package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;

public interface OnBoardingTemplateDocumentsService {

	OnBoardingTemplateDocuments saveOnBoardingTemplateDocuments(OnBoardingTemplateDocuments onBoardingTemplateDocuments)throws Exception;
	
	List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
	
	OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID) throws Exception;
	
	List<OnBoardingTemplateDocuments> getCandidateOnBoardingTemplateDocumentsByTemplateId(List<Long> templateIds,String documentType) throws Exception;
	
	OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsById(Long Id) throws Exception;
}
