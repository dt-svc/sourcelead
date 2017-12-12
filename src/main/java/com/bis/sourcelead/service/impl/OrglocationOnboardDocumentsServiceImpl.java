package com.bis.sourcelead.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bis.sourcelead.constants.Constants;
import com.bis.sourcelead.dao.OrglocationOnboardDocumentsDao;
import com.bis.sourcelead.dto.FileInfo;
import com.bis.sourcelead.dto.OrglocationOnboardDocumentsDTO;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrglocationOnboardDocuments;
import com.bis.sourcelead.entity.User;
import com.bis.sourcelead.service.OrglocationOnboardDocumentsService;
import com.bis.sourcelead.util.CommonUtil;
import com.bis.sourcelead.util.FileUploadUtil;
import com.bis.sourcelead.util.SourceLeadRandomCodeHelper;

@Service
public class OrglocationOnboardDocumentsServiceImpl implements OrglocationOnboardDocumentsService{
	
	@Autowired
	OrglocationOnboardDocumentsDao orglocationOnboardDocumentsDao;
	
	@Autowired
	CommonUtil commonUtil;
	
	

	@Override
	public OrglocationOnboardDocuments saveOrglocationOnboardDocuments(
			OrglocationOnboardDocuments orglocationOnboardDocuments) throws Exception {
		return orglocationOnboardDocumentsDao.saveOrglocationOnboardDocuments(orglocationOnboardDocuments);
	}

	@Override
	public String addOrglocationOnboardDocuments(OrglocationOnboardDocumentsDTO documentsDTO, OrgLocations orgLocations,User loginUser)
			throws Exception {

		if(documentsDTO.getBusinessLicense() != null && documentsDTO.getBusinessLicense().size() > 0) {
			OrglocationOnboardDocuments onboardDocuments = null;
			for(FileInfo fileInfo : documentsDTO.getBusinessLicense()) {
				 onboardDocuments = orglocationOnboardDocumentsDao.getOrglocationOnboardDocumentByDocumentName(Constants.ONBOARD_DOCUMENT_BUSNIESS_LICENSE, orgLocations.getId());
				if(onboardDocuments == null) {
					onboardDocuments = new OrglocationOnboardDocuments();
					onboardDocuments.setDocumentName(Constants.ONBOARD_DOCUMENT_BUSNIESS_LICENSE);
					onboardDocuments.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					onboardDocuments.setOrgLocationId(orgLocations);
					onboardDocuments.setStatus(Constants.RECORD_ACTIVE);
				}
				onboardDocuments.setFileName(fileInfo.getFileName());
				onboardDocuments.setFileContentType(fileInfo.getContentType());
				onboardDocuments.setFileUrl(fileInfo.getFileURL());
				onboardDocuments.setCreatedBy(loginUser);
				onboardDocuments.setUpdatedBy(loginUser);
				onboardDocuments.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
				onboardDocuments.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
				orglocationOnboardDocumentsDao.saveOrglocationOnboardDocuments(onboardDocuments);
				FileUploadUtil.moveFile(documentsDTO.getStaticPath(), fileInfo, "CompanyOnboardDocs");
			}
			
		}
			
			if(documentsDTO.getBankCheque() != null && documentsDTO.getBankCheque().size() > 0) {
				OrglocationOnboardDocuments onboardDocuments = null;
				for(FileInfo fileInfo : documentsDTO.getBankCheque()) {
					onboardDocuments = orglocationOnboardDocumentsDao.getOrglocationOnboardDocumentByDocumentName(Constants.BANK_CHEQUE, orgLocations.getId());
					if(onboardDocuments == null) {
						onboardDocuments = new OrglocationOnboardDocuments();
						onboardDocuments.setDocumentName(Constants.BANK_CHEQUE);
						onboardDocuments.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						onboardDocuments.setOrgLocationId(orgLocations);
						onboardDocuments.setStatus(Constants.RECORD_ACTIVE);
					}
					onboardDocuments.setFileName(fileInfo.getFileName());
					onboardDocuments.setFileContentType(fileInfo.getContentType());
					onboardDocuments.setFileUrl(fileInfo.getFileURL());
					onboardDocuments.setCreatedBy(loginUser);
					onboardDocuments.setUpdatedBy(loginUser);
					onboardDocuments.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
					onboardDocuments.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
					orglocationOnboardDocumentsDao.saveOrglocationOnboardDocuments(onboardDocuments);
					FileUploadUtil.moveFile(documentsDTO.getStaticPath(), fileInfo, "CompanyOnboardDocs");
				}
			
		}
			
			
		if(documentsDTO.getRegistrationDocument() != null && documentsDTO.getRegistrationDocument().size() > 0) {
			OrglocationOnboardDocuments onboardDocuments = null;
				for(FileInfo fileInfo : documentsDTO.getRegistrationDocument()) {
					onboardDocuments = orglocationOnboardDocumentsDao.getOrglocationOnboardDocumentByDocumentName(Constants.REGISTRATION_OR_INCORPORATION_OF_RECORDING, orgLocations.getId());
					if(onboardDocuments == null) {
						onboardDocuments = new OrglocationOnboardDocuments();
						onboardDocuments.setDocumentName(Constants.REGISTRATION_OR_INCORPORATION_OF_RECORDING);
						onboardDocuments.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
						onboardDocuments.setOrgLocationId(orgLocations);
						onboardDocuments.setStatus(Constants.RECORD_ACTIVE);
					}
					onboardDocuments.setFileName(fileInfo.getFileName());
					onboardDocuments.setFileContentType(fileInfo.getContentType());
					onboardDocuments.setFileUrl(fileInfo.getFileURL());
					onboardDocuments.setCreatedBy(loginUser);
					onboardDocuments.setUpdatedBy(loginUser);
					onboardDocuments.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
					onboardDocuments.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
					orglocationOnboardDocumentsDao.saveOrglocationOnboardDocuments(onboardDocuments);
					FileUploadUtil.moveFile(documentsDTO.getStaticPath(), fileInfo, "CompanyOnboardDocs");
				}
			
		}
		
		
		if(documentsDTO.getAnnualBusinessLicense() != null && documentsDTO.getAnnualBusinessLicense().size() > 0) {
			OrglocationOnboardDocuments onboardDocuments = null;
			for(FileInfo fileInfo : documentsDTO.getAnnualBusinessLicense()) {
				onboardDocuments = orglocationOnboardDocumentsDao.getOrglocationOnboardDocumentByDocumentName(Constants.ANNUAL_REPORT_BUSINESS_LICENSE, orgLocations.getId());
				if(onboardDocuments == null) {
					onboardDocuments = new OrglocationOnboardDocuments();
					onboardDocuments.setDocumentName(Constants.ANNUAL_REPORT_BUSINESS_LICENSE);
					onboardDocuments.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					onboardDocuments.setOrgLocationId(orgLocations);
					onboardDocuments.setStatus(Constants.RECORD_ACTIVE);
				}
				onboardDocuments.setFileName(fileInfo.getFileName());
				onboardDocuments.setFileContentType(fileInfo.getContentType());
				onboardDocuments.setFileUrl(fileInfo.getFileURL());
				onboardDocuments.setCreatedBy(loginUser);
				onboardDocuments.setUpdatedBy(loginUser);
				onboardDocuments.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
				onboardDocuments.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
				orglocationOnboardDocumentsDao.saveOrglocationOnboardDocuments(onboardDocuments);
				FileUploadUtil.moveFile(documentsDTO.getStaticPath(), fileInfo, "CompanyOnboardDocs");
			}
		
	}
		
	if(documentsDTO.getW9Document() != null && documentsDTO.getW9Document().size() > 0) {
		OrglocationOnboardDocuments onboardDocuments = null;
			for(FileInfo fileInfo : documentsDTO.getW9Document()) {
				onboardDocuments = orglocationOnboardDocumentsDao.getOrglocationOnboardDocumentByDocumentName(Constants.W9_W8_BEN_E, orgLocations.getId());
				if(onboardDocuments == null) {
					onboardDocuments = new OrglocationOnboardDocuments();
					onboardDocuments.setDocumentName(Constants.W9_W8_BEN_E);
					onboardDocuments.setUuid(SourceLeadRandomCodeHelper.generateRandomUUID());
					onboardDocuments.setOrgLocationId(orgLocations);
					onboardDocuments.setStatus(Constants.RECORD_ACTIVE);
				}
				onboardDocuments.setFileName(fileInfo.getFileName());
				onboardDocuments.setFileContentType(fileInfo.getContentType());
				onboardDocuments.setFileUrl(fileInfo.getFileURL());
				onboardDocuments.setCreatedBy(loginUser);
				onboardDocuments.setUpdatedBy(loginUser);
				onboardDocuments.setCreatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
				onboardDocuments.setUpdatedDate(commonUtil.getOrgLocationCurrentDate(orgLocations.getDateFormat(), new Date()));
				orglocationOnboardDocumentsDao.saveOrglocationOnboardDocuments(onboardDocuments);
				FileUploadUtil.moveFile(documentsDTO.getStaticPath(), fileInfo, "CompanyOnboardDocs");
			}
		
	}
			
		return "success";
	}

	@Override
	public OrglocationOnboardDocuments getOrglocationOnboardDocumentByDocumentName(String documentName,
			Long orgLocationId) throws Exception {
		return orglocationOnboardDocumentsDao.getOrglocationOnboardDocumentByDocumentName(documentName, orgLocationId);
	}

	@Override
	public OrglocationOnboardDocuments getOrglocationOnboardDocumentsByUUId(String uuid) throws Exception {
		return orglocationOnboardDocumentsDao.getOrglocationOnboardDocumentsByUUId(uuid);
	}

	@Override
	public void deleteOrglocationOnboardDocuments(OrglocationOnboardDocuments orglocationOnboardDocuments)
			throws Exception {
		orglocationOnboardDocumentsDao.deleteOrglocationOnboardDocuments(orglocationOnboardDocuments);
		
	}

}
