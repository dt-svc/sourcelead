package com.bis.sourcelead.dto;

import java.io.Serializable;

public class VendorsDocumentsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String docFileName;
	
	private String checked;
	
	private String docChecked;
	
	private String docId;
	
	private String documentName;
	
	private String documentType;
	

	public String getDocFileName() {
		return docFileName;
	}

	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getDocChecked() {
		return docChecked;
	}

	public void setDocChecked(String docChecked) {
		this.docChecked = docChecked;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
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

}
