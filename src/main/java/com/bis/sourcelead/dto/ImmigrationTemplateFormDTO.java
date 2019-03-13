package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class ImmigrationTemplateFormDTO {
	
	private Long id;
	
	private String formDescription;
	
	private String isRequired;
	
    @Required(desc="formName")
	private String formName;
	
	private String fileName;
	
	private String fileUrl;
	
	private List<FileInfo> immigrationFormFileInfo;
	
	// this variables added for immigration candidate forms
	private String immigrationId;
	
	private String immigrationcandidateId;
	
	private String immigrationTempplateId;
	
	private String staticPath;
	
	private Long immigrationCandidateFormId;
	
	private String formCategory;
	
	public String getFormDescription() {
		return formDescription;
	}

	public void setFormDescription(String formDescription) {
		this.formDescription = formDescription;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}


	public List<FileInfo> getImmigrationFormFileInfo() {
		return immigrationFormFileInfo;
	}

	public void setImmigrationFormFileInfo(List<FileInfo> immigrationFormFileInfo) {
		this.immigrationFormFileInfo = immigrationFormFileInfo;
	}

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

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
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

	public Long getImmigrationCandidateFormId() {
		return immigrationCandidateFormId;
	}

	public void setImmigrationCandidateFormId(Long immigrationCandidateFormId) {
		this.immigrationCandidateFormId = immigrationCandidateFormId;
	}

	public String getFormCategory() {
		return formCategory;
	}

	public void setFormCategory(String formCategory) {
		this.formCategory = formCategory;
	}

}
