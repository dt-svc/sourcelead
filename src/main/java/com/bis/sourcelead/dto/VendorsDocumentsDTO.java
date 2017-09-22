package com.bis.sourcelead.dto;

import java.io.Serializable;

public class VendorsDocumentsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String docFileName;
	
	private String checked;

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

}
