package com.bis.sourcelead.dto;

import java.io.Serializable;

public class CandiateBulkUploadCommonDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	 
	private String filePath;
	private String uploadType;
	private String headerPosition;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getHeaderPosition() {
		return headerPosition;
	}
	public void setHeaderPosition(String headerPosition) {
		this.headerPosition = headerPosition;
	}
	
}
