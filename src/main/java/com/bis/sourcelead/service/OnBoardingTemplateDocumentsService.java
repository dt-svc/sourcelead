package com.bis.sourcelead.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.User;

public interface OnBoardingTemplateDocumentsService {

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
	
	void storeOnBoardTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, User loginUser) throws Exception;
	
	
}
