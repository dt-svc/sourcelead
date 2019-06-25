package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.util.List;

import com.bis.sourcelead.dto.FileInfo;

public class PerformanceFeedbackCategoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<FileInfo> uploadedFileInfo;
	
	private String assignedUUID;
	
	private String staticPath;
	
	private String jsonData;
	
	private String con;
	

	public List<FileInfo> getUploadedFileInfo() {
		return uploadedFileInfo;
	}

	public void setUploadedFileInfo(List<FileInfo> uploadedFileInfo) {
		this.uploadedFileInfo = uploadedFileInfo;
	}

	public String getAssignedUUID() {
		return assignedUUID;
	}

	public void setAssignedUUID(String assignedUUID) {
		this.assignedUUID = assignedUUID;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}
	
	
}
