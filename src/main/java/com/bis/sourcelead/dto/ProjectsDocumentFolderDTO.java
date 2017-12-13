package com.bis.sourcelead.dto;

public class ProjectsDocumentFolderDTO {

	private String folderName;
	
	private String projectId;
	
	private String folderId;
	
	private String[] readRoleIds;
	
	private String[] writeRoleIds;
	
	private String[] readOtherMemberIds;
	
	private String[] writeOtherMemberIds;
	
	private String documentId;

	public String getFolderName() {
		return folderName;
	}

	public String getProjectId() {
		return projectId;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public String[] getReadRoleIds() {
		return readRoleIds;
	}

	public String[] getWriteRoleIds() {
		return writeRoleIds;
	}

	public String[] getReadOtherMemberIds() {
		return readOtherMemberIds;
	}

	public String[] getWriteOtherMemberIds() {
		return writeOtherMemberIds;
	}

	public void setReadRoleIds(String[] readRoleIds) {
		this.readRoleIds = readRoleIds;
	}

	public void setWriteRoleIds(String[] writeRoleIds) {
		this.writeRoleIds = writeRoleIds;
	}

	public void setReadOtherMemberIds(String[] readOtherMemberIds) {
		this.readOtherMemberIds = readOtherMemberIds;
	}

	public void setWriteOtherMemberIds(String[] writeOtherMemberIds) {
		this.writeOtherMemberIds = writeOtherMemberIds;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
}
