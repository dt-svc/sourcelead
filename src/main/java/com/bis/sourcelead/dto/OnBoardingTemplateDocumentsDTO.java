package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class OnBoardingTemplateDocumentsDTO implements Serializable {
	
	 private String candidateonboardingId;
	 
	 private String savedPoliciesAttcahments;
	 
	 private String policiesAttcahmentMaxRowNumber;
	 
	 private String savedSupportedAttcahments;
	 
	 private String savedContractsAttcahments;
	 
	 private String contractsAttcahmentMaxRowNumber;
	 
	 private String savedFormsAttcahments;
	 
	 private String formsAttcahmentMaxRowNumber;
	 
	 private String staticPath;
		
	private List<FileInfo> contractsFileInfo;
	
	private List<FileInfo> formsFileInfo;
	
	private List<FileInfo> polaciesFileInfo;
	
	private List<FileInfo> supportingFileInfo;
	
	private Long id;
	
	private String fileName;
	
	private String fileURL;
	
	private String documentUUID;
	
	private String onboardDocumentType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public String getOnboardDocumentType() {
		return onboardDocumentType;
	}

	public void setOnboardDocumentType(String onboardDocumentType) {
		this.onboardDocumentType = onboardDocumentType;
	}

	public String getCandidateonboardingId() {
		return candidateonboardingId;
	}

	public String getSavedPoliciesAttcahments() {
		return savedPoliciesAttcahments;
	}

	public String getPoliciesAttcahmentMaxRowNumber() {
		return policiesAttcahmentMaxRowNumber;
	}

	public String getSavedSupportedAttcahments() {
		return savedSupportedAttcahments;
	}

	public String getSavedContractsAttcahments() {
		return savedContractsAttcahments;
	}

	public String getContractsAttcahmentMaxRowNumber() {
		return contractsAttcahmentMaxRowNumber;
	}

	public String getSavedFormsAttcahments() {
		return savedFormsAttcahments;
	}

	public String getFormsAttcahmentMaxRowNumber() {
		return formsAttcahmentMaxRowNumber;
	}

	public String getStaticPath() {
		return staticPath;
	}

 

	public void setCandidateonboardingId(String candidateonboardingId) {
		this.candidateonboardingId = candidateonboardingId;
	}

	public void setSavedPoliciesAttcahments(String savedPoliciesAttcahments) {
		this.savedPoliciesAttcahments = savedPoliciesAttcahments;
	}

	public void setPoliciesAttcahmentMaxRowNumber(String policiesAttcahmentMaxRowNumber) {
		this.policiesAttcahmentMaxRowNumber = policiesAttcahmentMaxRowNumber;
	}

	public void setSavedSupportedAttcahments(String savedSupportedAttcahments) {
		this.savedSupportedAttcahments = savedSupportedAttcahments;
	}

	public void setSavedContractsAttcahments(String savedContractsAttcahments) {
		this.savedContractsAttcahments = savedContractsAttcahments;
	}

	public void setContractsAttcahmentMaxRowNumber(String contractsAttcahmentMaxRowNumber) {
		this.contractsAttcahmentMaxRowNumber = contractsAttcahmentMaxRowNumber;
	}

	public void setSavedFormsAttcahments(String savedFormsAttcahments) {
		this.savedFormsAttcahments = savedFormsAttcahments;
	}

	public void setFormsAttcahmentMaxRowNumber(String formsAttcahmentMaxRowNumber) {
		this.formsAttcahmentMaxRowNumber = formsAttcahmentMaxRowNumber;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<FileInfo> getContractsFileInfo() {
		return contractsFileInfo;
	}

	public List<FileInfo> getFormsFileInfo() {
		return formsFileInfo;
	}

	public List<FileInfo> getPolaciesFileInfo() {
		return polaciesFileInfo;
	}

	public List<FileInfo> getSupportingFileInfo() {
		return supportingFileInfo;
	}

	public void setContractsFileInfo(List<FileInfo> contractsFileInfo) {
		this.contractsFileInfo = contractsFileInfo;
	}

	public void setFormsFileInfo(List<FileInfo> formsFileInfo) {
		this.formsFileInfo = formsFileInfo;
	}

	public void setPolaciesFileInfo(List<FileInfo> polaciesFileInfo) {
		this.polaciesFileInfo = polaciesFileInfo;
	}

	public void setSupportingFileInfo(List<FileInfo> supportingFileInfo) {
		this.supportingFileInfo = supportingFileInfo;
	}
}
