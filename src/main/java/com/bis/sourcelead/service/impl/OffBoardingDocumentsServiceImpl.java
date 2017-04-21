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

}
