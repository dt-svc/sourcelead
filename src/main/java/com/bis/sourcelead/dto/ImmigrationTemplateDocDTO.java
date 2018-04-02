package com.bis.sourcelead.dto;

public class ImmigrationTemplateDocDTO {
	
	private Long id;
	
	private Long documentType;
	
	private String description;
	
	private String isRequired;
	
	private String multiple;
	
	private String immigrationId;
	
	private String immigrationcandidateId;
	
	private String immigrationTempplateId;
	// this variables added for immigration candidate docs
	private String immigrationCandidateFileUrl;
	
	private String immigrationCandidateFileName;
	
	private String immigrationCandidateFileContentType;
	
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
	
	
}
