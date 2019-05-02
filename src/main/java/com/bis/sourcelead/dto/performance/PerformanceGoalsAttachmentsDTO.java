package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class PerformanceGoalsAttachmentsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String fileName;
	
	private String fileUrl;
	
	private String uuid;
	
	private Long attachmentId;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	
}
