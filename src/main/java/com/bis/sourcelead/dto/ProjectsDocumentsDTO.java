package com.bis.sourcelead.dto;

import java.util.List;

public class ProjectsDocumentsDTO {

	private String staticPath;
	
	private List<FileInfo> fileInfo;
	
	private String description ;
	
	private String projectId ;
	
	private String documentId ;
	
	private String projectFolderId;
	
	private String sendTo;
	
	private String subject;

	private String message;

	public String getStaticPath() {
		return staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public String getDescription() {
		return description;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getProjectFolderId() {
		return projectFolderId;
	}

	public void setProjectFolderId(String projectFolderId) {
		this.projectFolderId = projectFolderId;
	}

	public String getSendTo() {
		return sendTo;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
