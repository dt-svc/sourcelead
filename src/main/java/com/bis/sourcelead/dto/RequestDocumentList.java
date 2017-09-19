package com.bis.sourcelead.dto;

public class RequestDocumentList {

	private String docType;
	
	private String description;
	
	private String checked;

	private String otherDocType;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getOtherDocType() {
		return otherDocType;
	}

	public void setOtherDocType(String otherDocType) {
		this.otherDocType = otherDocType;
	}	
}
