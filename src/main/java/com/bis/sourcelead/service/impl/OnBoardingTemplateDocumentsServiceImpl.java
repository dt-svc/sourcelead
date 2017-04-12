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

}
