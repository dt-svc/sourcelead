package com.bis.sourcelead.dto;

import java.util.List;

public class VendorOnboardResponseList<T> {

	private Long id;
	
	private String isRequired;
	
	private String documentName;
	
	private String documentUUID;
	
	private String documentType;
	
	private List<T> data;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}

	public String getDocumentUUID() {
		return documentUUID;
	}

	public void setDocumentUUID(String documentUUID) {
		this.documentUUID = documentUUID;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
