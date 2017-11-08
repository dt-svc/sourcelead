package com.bis.sourcelead.dto;

public class VendorDocumentsFileInfo extends FileInfo{

	private String docFileName;
	private String checked;
	private String docId;
	
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
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
}
