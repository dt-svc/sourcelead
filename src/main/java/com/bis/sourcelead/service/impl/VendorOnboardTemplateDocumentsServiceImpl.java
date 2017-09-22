package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.VendorOnboardTemplateDao;
import com.bis.sourcelead.dao.VendorOnboardTemplateDocumentsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.entity.OnBoardingTemplate;
import com.bis.sourcelead.entity.OnBoardingTemplateDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.service.VendorOnboardTemplateDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class VendorOnboardTemplateDocumentsServiceImpl implements VendorOnboardTemplateDocumentsService{
	
	protected static boolean isNullOrEmpty(String str) {
		return (str == null || "".equals(str.trim()));
	}
	@Autowired
	VendorOnboardTemplateDocumentsDao vendorOnboardTemplateDocumentsDao;

	@Autowired
	VendorOnboardTemplateDao vendorOnboardTemplateDao;

	@Override
	public List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateId(Long templateId,
			String documentType) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsByTemplateId(templateId, documentType);
	}

	@Override
	public void storeVendorOnBoardTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, User loginUser)
			throws Exception {
		
		VendorOnboardingTemplate template = vendorOnboardTemplateDao.getVendorOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
		if(onBoardingTemplateDTO.getVendorDocumentsFileInfo() != null &&  onBoardingTemplateDTO.getVendorDocumentsFileInfo().size() > 0) {
			for(FileInfo fileInfo : onBoardingTemplateDTO.getVendorDocumentsFileInfo()) {
				VendorOnboardingTemplateDocuments documents = new VendorOnboardingTemplateDocuments();
				if(fileInfo != null && fileInfo.getFileURL() != null) {
					   documents.setFileName(fileInfo.getFileName());
					   documents.setFileContentType(fileInfo.getContentType());
					   documents.setFileUrl(fileInfo.getFileURL());
				   }
				    documents.setVendorOnboardingTemplateId(template);
				    documents.setDocumentType("Documents");
				    documents.setDocumentName(fileInfo.getDocFileName());
				    if(!isNullOrEmpty(fileInfo.getChecked()) && fileInfo.getChecked().equalsIgnoreCase(Constants.YES)){
				    	documents.setIsRequired(Constants.YES);
				    }else{
				    	documents.setIsRequired(Constants.NO);
				    }
                    documents.setStatus(Constants.CANDIDATE_ACTIVE);
                    documents.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
                    documents.setCreatedDate(CommonUtil.currentDate());
                    documents.setCreatedBy(loginUser);
                    documents.setUpdatedDate(CommonUtil.currentDate());
                    documents.setUpdatedBy(loginUser);
                    saveVendorOnBoardingTemplateDocuments(documents);
                    FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Vendor-Onboard-Template-Documents");
			}
		}
		
	}

	@Override
	public VendorOnboardingTemplateDocuments saveVendorOnBoardingTemplateDocuments(
			VendorOnboardingTemplateDocuments vendorOnboardingTemplateDocuments) throws Exception {
		return vendorOnboardTemplateDocumentsDao.saveVendorOnBoardingTemplateDocuments(vendorOnboardingTemplateDocuments);
	}

	@Override
	public VendorOnboardingTemplateDocuments getVendorOnBoardingTemplateDocumentsBydocumentUUID(String documentUUID)
			throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsBydocumentUUID(documentUUID);
	}

}
