package com.bis.sourcelead.dto;

import java.io.Serializable;

public class VendorOnboardingTemplateDocumentDTO implements Serializable{

 private static final long serialVersionUID = 1L;
	 
     private Long id;
     
 	 private Long vendorOnboardingTemplateId;
     
 	 private Long vendorOnboardingId;
     
 	 private String documentName;
     
 	 private String documentType;
     
 	 private String fileContentType;
     
 	 private String fileName;
     
 	 private String fileUrl;
     
 	 private String uploadedFileContentType;
     
 	 private String uploadedFileName;
     
 	 private String uploadedFileUrl;
     
 	 private String documentUUID;
     
 	 private String documentStatus;
     
 	 private String isRequired;
 	 
 	 
 	 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVendorOnboardingTemplateId() {
		return vendorOnboardingTemplateId;
	}

	public void setVendorOnboardingTemplateId(Long vendorOnboardingTemplateId) {
		this.vendorOnboardingTemplateId = vendorOnboardingTemplateId;
	}

	public Long getVendorOnboardingId() {
		return vendorOnboardingId;
	}

	public void setVendorOnboardingId(Long vendorOnboardingId) {
		this.vendorOnboardingId = vendorOnboardingId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUploadedFileContentType() {
		return uploadedFileContentType;
	}

	public void setUploadedFileContentType(String uploadedFileContentType) {
		this.uploadedFileContentType = uploadedFileContentType;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public String getUploadedFileUrl() {
		return uploadedFileUrl;
	}

	public void setUploadedFileUrl(String uploadedFileUrl) {
		this.uploadedFileUrl = uploadedFileUrl;
	}

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
}
