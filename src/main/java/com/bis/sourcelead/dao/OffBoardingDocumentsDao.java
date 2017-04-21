package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.OffBoardingDocuments;

public interface OffBoardingDocumentsDao {

	OffBoardingDocuments saveOffBoardingDocuments(OffBoardingDocuments offBoardingDocuments)throws Exception;
	
	List<OffBoardingDocuments> getOffBoardingDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
	
	OffBoardingDocuments getOffBoardingDocumentsBydocumentUUID(String documentUUID) throws Exception;
}
