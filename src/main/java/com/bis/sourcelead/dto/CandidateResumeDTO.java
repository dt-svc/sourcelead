package com.bis.sourcelead.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.entity.Candidate;

public class CandidateResumeDTO {

	private String candidateId;
	
	private String staticPath;
	
	private List<FileInfo> fileInfo;
	
	//private List<FileInfo> uploadedFileInfo;
	
	private String title ;
	
	private String description ;
	
	private String fileContentType;
	
	private String fileName;
	
	private String resumeStatus;
	
	private String resumeUUID;
	
	private String createdBy;
	
	private String updatedBy;

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getResumeStatus() {
		return resumeStatus;
	}

	public void setResumeStatus(String resumeStatus) {
		this.resumeStatus = resumeStatus;
	}

	public String getResumeUUID() {
		return resumeUUID;
	}

	public void setResumeUUID(String resumeUUID) {
		this.resumeUUID = resumeUUID;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
	
}
