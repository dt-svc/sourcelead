package com.bis.sourcelead.dto;

import java.math.BigInteger;

public class UploadXlsHistoryDTO extends SearchDTO{

	private int totalCount;
	
	private int successCount;
	
	private int failureCount;
	
	private String uploadProcessingStatus;
	
	private String fileUrl;
	
	private String uuid;
	
	private BigInteger uploadHistoryId;
	
	private String fileName;

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}

	public String getUploadProcessingStatus() {
		return uploadProcessingStatus;
	}

	public void setUploadProcessingStatus(String uploadProcessingStatus) {
		this.uploadProcessingStatus = uploadProcessingStatus;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigInteger getUploadHistoryId() {
		return uploadHistoryId;
	}

	public void setUploadHistoryId(BigInteger uploadHistoryId) {
		this.uploadHistoryId = uploadHistoryId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	
}
