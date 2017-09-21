package com.bis.sourcelead.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.dao.VendorOnboardTemplateDocumentsDao;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.service.VendorOnboardTemplateDocumentsService;

@Service
public class VendorOnboardTemplateDocumentsServiceImpl implements VendorOnboardTemplateDocumentsService{
	
	@Autowired
	VendorOnboardTemplateDocumentsDao vendorOnboardTemplateDocumentsDao;

	@Override
	public List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateId(Long templateId,
			String documentType) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsByTemplateId(templateId, documentType);
	}

}
