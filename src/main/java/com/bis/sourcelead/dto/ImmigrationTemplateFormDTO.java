package com.bis.sourcelead.dto;

import java.util.List;

public class ImmigrationTemplateFormDTO {
	
	private String formDescription;
	
	private String isRequired;
	
	private String formName;
	
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

	
}
