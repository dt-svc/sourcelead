package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.OffBoardingDocuments;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;

public interface OffBoardingDocumentsService {

	OffBoardingDocuments saveOffBoardingDocuments(OffBoardingDocuments offBoardingDocuments)throws Exception;
	
	List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
	
	OffBoardingDocuments getOffBoardingDocumentsBydocumentUUID(String documentUUID) throws Exception;
}
