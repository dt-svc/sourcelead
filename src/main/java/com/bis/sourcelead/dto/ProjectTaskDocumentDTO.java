package com.bis.sourcelead.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class ProjectTaskDocumentDTO{
	
    private String staticPath;
	
    @Required(desc="fileInfo", dependsOn = "tabType", parentValue = "attachment")
	private List<FileInfo> fileInfo;
	
    private String tabType;
    
	private String documentId;
	
	private String loginUserId;
	
	private String documentName;
	
	private String taskDescription;
	
	private String projectTaskId;

	private String validDcoument;
	
	@Required(desc="taskComment", dependsOn = "tabType", parentValue = "comment")
	private String taskComment;
	
	private String mentionedUserIds;
	
	public String getStaticPath() {
		return staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public String getDocumentId() {
		return documentId;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(String projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public String getValidDcoument() {
		return validDcoument;
	}

	public void setValidDcoument(String validDcoument) {
		this.validDcoument = validDcoument;
	}

	public String getTaskComment() {
		return taskComment;
	}

	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}

	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public String getMentionedUserIds() {
		return mentionedUserIds;
	}

	public void setMentionedUserIds(String mentionedUserIds) {
		this.mentionedUserIds = mentionedUserIds;
	}
}
