package com.bis.sourcelead.dao;

import java.util.List;

import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;

public interface VendorOnboardTemplateDocumentsDao {

	List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
	
	VendorOnboardingTemplateDocuments saveVendorOnBoardingTemplateDocuments(VendorOnboardingTemplateDocuments vendorOnboardingTemplateDocuments)throws Exception;
	
	VendorOnboardingTemplateDocuments getVendorOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID) throws Exception;
	
	List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateList(List<Long> templateIdList,String documentType) throws Exception;
}
