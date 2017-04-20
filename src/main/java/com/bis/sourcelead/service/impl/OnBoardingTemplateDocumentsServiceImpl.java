package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OnBoardingTemplateDocumentsDao;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.service.OnBoardingTemplateDocumentsService;

@Service
public class OnBoardingTemplateDocumentsServiceImpl implements OnBoardingTemplateDocumentsService {

	@Autowired
	OnBoardingTemplateDocumentsDao onBoardingTemplateDocumentsDao;
	
	@Override
	public OnBoardingTemplateDocuments saveOnBoardingTemplateDocuments(
			OnBoardingTemplateDocuments onBoardingTemplateDocuments) throws Exception {
		return onBoardingTemplateDocumentsDao.saveOnBoardingTemplateDocuments(onBoardingTemplateDocuments);
	}

	@Override
	public List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType)
			throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsByTemplateId(templateId,documentType);
	}

	@Override
	public OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID)
			throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
	}

	@Override
	public List<OnBoardingTemplateDocuments> getCandidateOnBoardingTemplateDocumentsByTemplateId(List<Long> templateIds,
			String documentType) throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateOnBoardingTemplateDocumentsByTemplateId(templateIds, documentType);
	}

	@Override
	public OnBoardingTemplateDocuments getOnBoardingTemplateDocumentsById(Long Id) throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsById(Id);
	}

	@Override
	public Integer getCandidateOnboardingDocumentsByCandidateOnboardingId(Long candidateOnBoardingId) throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateOnboardingDocumentsByCandidateOnboardingId(candidateOnBoardingId);
	}

	@Override
	public List<OnBoardingTemplateDocuments> getOnBoardingTemplateDocumentsByCandidateOnBoardingId(
			Long candidateOnBoardingId, String documentType) throws Exception {
		return onBoardingTemplateDocumentsDao.getOnBoardingTemplateDocumentsByCandidateOnBoardingId(candidateOnBoardingId, documentType);
	}

	@Override
	public Integer getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(
			Long candidateOnBoardingId) throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateOnboardingDocumentsCountByCandidateOnboardingIdExpectSupportDocs(candidateOnBoardingId);
	}

	@Override
	public Integer getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(Long candidateOnBoardingId)
			throws Exception {
		return onBoardingTemplateDocumentsDao.getCandidateAcceptedOnboardingDocumentsCountByCandidateOnboardingId(candidateOnBoardingId);
	}

}
