package com.bis.sourcelead.dto;

import java.util.List;

public class ImmigrationTemplateFormDTO {
	
	private Long id;
	
	private String formDescription;
	
	private String isRequired;
	
	private String formName;
	
	private String fileName;
	
	private String fileUrl;
	
	private List<FileInfo> immigrationFormFileInfo;
	
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

	
}
