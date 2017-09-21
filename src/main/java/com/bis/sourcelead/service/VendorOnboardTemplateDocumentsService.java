package com.bis.sourcelead.service;

import java.util.List;

import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;

public interface VendorOnboardTemplateDocumentsService {

	List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateId(Long templateId,String documentType) throws Exception;
}
