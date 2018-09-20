package com.bis.sourcelead.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrgRelationsDao;
import com.bis.sourcelead.dao.VendorOnboardDao;
import com.bis.sourcelead.dao.VendorOnboardTemplateDao;
import com.bis.sourcelead.dao.VendorOnboardTemplateDocumentsDao;
import com.bis.sourcelead.dto.OnBoardingTemplateDTO;
import com.bis.sourcelead.dto.VendorDocumentsFileInfo;
import com.bis.sourcelead.dto.VendorsDocumentsDTO;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.entity.VendorOnboarding;
import com.bis.sourcelead.entity.VendorOnboardingTemplate;
import com.bis.sourcelead.entity.VendorOnboardingTemplateDocuments;
import com.bis.sourcelead.service.VendorOnboardTemplateDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
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

	@Autowired
	VendorOnboardDao vendorOnboardDao;
	
	@Autowired
	OrgRelationsDao orgRelationsDao;
	
	@Override
	public List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateId(Long templateId,
			String documentType) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsByTemplateId(templateId, documentType);
	}

	@Override
	public VendorOnboardingTemplateDocuments storeVendorOnBoardTemplateDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, User loginUser)
			throws Exception {
		
		VendorOnboardingTemplate template = vendorOnboardTemplateDao.getVendorOnBoardingTemplateById(Long.valueOf(onBoardingTemplateDTO.getTemplateId()));
		 VendorOnboardingTemplateDocuments vendorOnboardingTemplateDocuments =null;
		if(onBoardingTemplateDTO.getVendorDocumentsFileInfo() != null &&  onBoardingTemplateDTO.getVendorDocumentsFileInfo().size() > 0) {
			for(VendorDocumentsFileInfo fileInfo : onBoardingTemplateDTO.getVendorDocumentsFileInfo()) {
				
				if(StringUtils.isNotBlank(fileInfo.getDocFileName())) {
					
					 VendorOnboardingTemplateDocuments documents = new VendorOnboardingTemplateDocuments();
					/*	if(fileInfo != null && fileInfo.getFileURL() != null) {
							   documents.setFileName(fileInfo.getFileName());
							   documents.setFileContentType(fileInfo.getContentType());
							   documents.setFileUrl(fileInfo.getFileURL());
						   }*/
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
		                    vendorOnboardingTemplateDocuments =  saveVendorOnBoardingTemplateDocuments(documents);
		                  //  FileUploadUtil.moveFile(onBoardingTemplateDTO .getStaticPath(), fileInfo, "Vendor-Onboard-Template-Documents");
		                   
				}
				
			}
		}
		 return vendorOnboardingTemplateDocuments;
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

	@Override
	public List<VendorOnboardingTemplateDocuments> getVendorOnBoardingTemplateDocumentsByTemplateList(
			List<Long> templateIdList, String documentType) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsByTemplateList(templateIdList, documentType);
	}

	@Override
	public List<VendorOnboardingTemplateDocuments> getVendorOnboaringTemplateDocumentsByVendorOnboardId(
			Long vendorOnboardingId) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnboaringTemplateDocumentsByVendorOnboardId(vendorOnboardingId);
	}

	@Override
	public void storeVendorOnboardDocuments(OnBoardingTemplateDTO onBoardingTemplateDTO, User loginUser)
			throws Exception {
		
		VendorOnboarding vendorOnboarding = vendorOnboardDao.getVendorOnboardingById(onBoardingTemplateDTO.getVendorOnboardId());
		if(onBoardingTemplateDTO.getVendorTemplateDocumentsFileInfo() != null && onBoardingTemplateDTO.getVendorTemplateDocumentsFileInfo().size() > 0){
			for(VendorsDocumentsDTO documentsDTO : onBoardingTemplateDTO.getVendorTemplateDocumentsFileInfo()) {
				if(StringUtils.isNotBlank(documentsDTO.getDocId()) && StringUtils.isNotBlank(documentsDTO.getDocumentName())){
					VendorOnboardingTemplateDocuments templateDocuments = null;
					if(StringUtils.isNotBlank(documentsDTO.getDocumentType()) && documentsDTO.getDocumentType().equalsIgnoreCase("Documents")){
						templateDocuments = new VendorOnboardingTemplateDocuments();
						templateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					}else{
						templateDocuments = getVendorOnBoardingTemplateDocumentsById(Long.parseLong(documentsDTO.getDocId()));
						if(templateDocuments == null){
							templateDocuments = new VendorOnboardingTemplateDocuments();
							templateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
						}
					}
					
					templateDocuments.setVendorOnboardingId(vendorOnboarding);
					templateDocuments.setDocumentName(documentsDTO.getDocumentName());
					if(StringUtils.isNotBlank(documentsDTO.getDocChecked())){
						templateDocuments.setIsRequired(Constants.YES);
					}else{
						templateDocuments.setIsRequired(Constants.NO);
					}
					templateDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
					templateDocuments.setDocumentType("Vendor Documents");
					templateDocuments.setCreatedBy(loginUser);
					templateDocuments.setUpdatedBy(loginUser);
					templateDocuments.setCreatedDate(CommonUtil.currentDate());
					templateDocuments.setUpdatedDate(CommonUtil.currentDate());
					saveVendorOnBoardingTemplateDocuments(templateDocuments);
				}
			}
		}
		
		if(onBoardingTemplateDTO.getVendorOnboardDocumentsFileInfo() != null && onBoardingTemplateDTO.getVendorOnboardDocumentsFileInfo().size() > 0){
			for(VendorsDocumentsDTO documentsDTO : onBoardingTemplateDTO.getVendorOnboardDocumentsFileInfo()) {
				
				if(StringUtils.isNotBlank(documentsDTO.getDocFileName())) {
					VendorOnboardingTemplateDocuments templateDocuments = new VendorOnboardingTemplateDocuments();
					templateDocuments.setDocumentName(documentsDTO.getDocFileName());
					templateDocuments.setVendorOnboardingId(vendorOnboarding);
					if(StringUtils.isNotBlank(documentsDTO.getChecked())){
						templateDocuments.setIsRequired(Constants.YES);
					}else{
						templateDocuments.setIsRequired(Constants.NO);
					}
					templateDocuments.setDocumentUUID(SourceLeadRandomCodeHelper.generateRandomUUID());
					templateDocuments.setDocumentType("Vendor Documents");
					templateDocuments.setStatus(Constants.CANDIDATE_ACTIVE);
					templateDocuments.setCreatedBy(loginUser);
					templateDocuments.setUpdatedBy(loginUser);
					templateDocuments.setCreatedDate(CommonUtil.currentDate());
					templateDocuments.setUpdatedDate(CommonUtil.currentDate());
					saveVendorOnBoardingTemplateDocuments(templateDocuments);
				}
			}
		}
		
		OrgRelations orgRelations = vendorOnboarding.getOrgRelationId();
		orgRelations.setIsVendorDocumentsFilled(Constants.YES);
		orgRelationsDao.addOrgRelations(orgRelations);
		
	}

	@Override
	public VendorOnboardingTemplateDocuments getVendorOnBoardingTemplateDocumentsById(Long id) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnBoardingTemplateDocumentsById(id);
	}

	@Override
	public List<VendorOnboardingTemplateDocuments> getVendorOnBoardTemplateDocumentsByTemplateId(Long templateId)
			throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnBoardTemplateDocumentsByTemplateId(templateId);
	}

	@Override
	public Integer getTemplateDocumentsCountByTemplateId(Long templateId) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getTemplateDocumentsCountByTemplateId(templateId);
	}

	@Override
	public List<VendorOnboardingTemplateDocuments> getVendorOnboaringRequiredDocumentsByVendorOnboardId(
			Long vendorOnboardingId) throws Exception {
		return vendorOnboardTemplateDocumentsDao.getVendorOnboaringRequiredDocumentsByVendorOnboardId(vendorOnboardingId);
	}

}
