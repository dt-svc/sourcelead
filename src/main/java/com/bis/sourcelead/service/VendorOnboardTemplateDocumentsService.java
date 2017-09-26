package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;

public interface VendorOnboardTemplateDocumentsService {

	List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
	
	void storeVendorOnBoardTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, User loginUser) throws Exception;
	
	VendorOnboardingTemplateDocuments saveVendorOnBoardingTemplateDocuments(VendorOnboardingTemplateDocuments vendorOnboardingTemplateDocuments)throws Exception;
	
	VendorOnboardingTemplateDocuments getVendorOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID) throws Exception;
}
