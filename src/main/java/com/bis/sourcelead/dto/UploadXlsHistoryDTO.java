package com.bis.sourcelead.dto;

import javax.ws.rs.DefaultValue;

public class UploadXlsHistoryDTO extends SearchDTO{

	private int totalCount;
	
	private int successCount;
	
	private int failureCount;
	
	private String uploadProcessingStatus;

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

	
	
}
