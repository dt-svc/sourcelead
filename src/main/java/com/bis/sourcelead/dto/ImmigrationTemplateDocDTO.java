package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class ImmigrationTemplateDocDTO {
	
	private Long id;
	
    @Required(desc="documentType")
	private Long documentType;
	
	private String description;
	
	private String descriptionOther;
	
	private String isRequired;
	
	private String multiple;
	
	private String immigrationId;
	
	private String immigrationcandidateId;
	
	private String immigrationTempplateId;
	
	private String otherType;
	
	// this variables added for immigration candidate docs
	private List<FileInfo> fileInfo;
	
	private String immigrationCandidateFileUrl;
	
	private String staticPath;
	
	private String immigrationCandidateFileName;
	
	private String immigrationCandidateFileContentType;
	
	private Long immigrationCandidateDocId;
	
	private List<Long> immigrationCandidateDocHistoryId;
	
	private String documentCategory;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDocumentType() {
		return documentType;
	}
	public void setDocumentType(Long documentType) {
		this.documentType = documentType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getImmigrationCandidateFileUrl() {
		return immigrationCandidateFileUrl;
	}
	public void setImmigrationCandidateFileUrl(String immigrationCandidateFileUrl) {
		this.immigrationCandidateFileUrl = immigrationCandidateFileUrl;
	}
	public String getImmigrationCandidateFileName() {
		return immigrationCandidateFileName;
	}
	public void setImmigrationCandidateFileName(String immigrationCandidateFileName) {
		this.immigrationCandidateFileName = immigrationCandidateFileName;
	}
	public String getImmigrationCandidateFileContentType() {
		return immigrationCandidateFileContentType;
	}
	public void setImmigrationCandidateFileContentType(String immigrationCandidateFileContentType) {
		this.immigrationCandidateFileContentType = immigrationCandidateFileContentType;
	}
	public String getImmigrationId() {
		return immigrationId;
	}
	public void setImmigrationId(String immigrationId) {
		this.immigrationId = immigrationId;
	}
	
	public String getImmigrationcandidateId() {
		return immigrationcandidateId;
	}
	public void setImmigrationcandidateId(String immigrationcandidateId) {
		this.immigrationcandidateId = immigrationcandidateId;
	}
	public String getImmigrationTempplateId() {
		return immigrationTempplateId;
	}
	public void setImmigrationTempplateId(String immigrationTempplateId) {
		this.immigrationTempplateId = immigrationTempplateId;
	}
	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}
	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}
	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}
	public Long getImmigrationCandidateDocId() {
		return immigrationCandidateDocId;
	}
	public void setImmigrationCandidateDocId(Long immigrationCandidateDocId) {
		this.immigrationCandidateDocId = immigrationCandidateDocId;
	}
	public String getOtherType() {
		return otherType;
	}
	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}
	public String getDescriptionOther() {
		return descriptionOther;
	}
	public void setDescriptionOther(String descriptionOther) {
		this.descriptionOther = descriptionOther;
	}
	public List<Long> getImmigrationCandidateDocHistoryId() {
		return immigrationCandidateDocHistoryId;
	}
	public void setImmigrationCandidateDocHistoryId(List<Long> immigrationCandidateDocHistoryId) {
		this.immigrationCandidateDocHistoryId = immigrationCandidateDocHistoryId;
	}
	public String getDocumentCategory() {
		return documentCategory;
	}
	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}
	
	
	
}
