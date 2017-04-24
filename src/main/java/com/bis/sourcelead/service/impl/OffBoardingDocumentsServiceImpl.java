package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.OffBoardingDocumentsDao;
import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.service.OffBoardingDocumentsService;

@Service
public class OffBoardingDocumentsServiceImpl implements OffBoardingDocumentsService {
	
	@Autowired
	OffBoardingDocumentsDao offBoardingDocumentsDao;

	@Override
	public OffBoardingDocuments saveOffBoardingDocuments(OffBoardingDocuments offBoardingDocuments)
			throws Exception {
		return offBoardingDocumentsDao.saveOffBoardingDocuments(offBoardingDocuments);
	}

	@Override
	public List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateId(Long templateId, String documentType)
			throws Exception {
		return offBoardingDocumentsDao.getOffBoardingDocumentsByTemplateId(templateId, documentType);
	}

	@Override
	public OffBoardingDocuments getOffBoardingDocumentsBydocumentUUID(String documentUUID) throws Exception {
		return offBoardingDocumentsDao.getOffBoardingDocumentsBydocumentUUID(documentUUID);
	}

	@Override
	public Integer getOffboardingDocumentsByOffboardingId(Long offBoardingId) throws Exception {
		return offBoardingDocumentsDao.getOffboardingDocumentsByOffboardingId(offBoardingId);
	}

	@Override
	public List<OffBoardingDocuments> getOffBoardingDocumentsByCandidateOffBoardingId(Long candidateOffBoardingId,
			String documentType) throws Exception {
		return offBoardingDocumentsDao.getOffBoardingDocumentsByCandidateOffBoardingId(candidateOffBoardingId, documentType);
	}

	@Override
	public List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateIdsList(List<Long> templateIds,
			String documentType) throws Exception {
		return offBoardingDocumentsDao.getOffBoardingDocumentsByTemplateIdsList(templateIds, documentType);
	}

	@Override
	public OffBoardingDocuments getOffBoardingDocumentsById(Long id) throws Exception {
		return offBoardingDocumentsDao.getOffBoardingDocumentsById(id);
	}

}
