package com.bis.sourcelead.dto;

public class XlsBulkUploadHistroyDTO {

	private int failureCount;
	
	private String fileUrl;
	
	private String failureFileUrl;
	
	private String uuid;
	
	private String documentKey;
	
	private String date;
	
	private String time;
	
	private String uploadEntityType;
	
	private String orgName;
	
	private String uploadStatus;

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDocumentKey() {
		return documentKey;
	}

	public void setDocumentKey(String documentKey) {
		this.documentKey = documentKey;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUploadEntityType() {
		return uploadEntityType;
	}

	public void setUploadEntityType(String uploadEntityType) {
		this.uploadEntityType = uploadEntityType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFailureFileUrl() {
		return failureFileUrl;
	}

	public void setFailureFileUrl(String failureFileUrl) {
		this.failureFileUrl = failureFileUrl;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
	
}
