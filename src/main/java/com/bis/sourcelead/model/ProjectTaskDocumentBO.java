package com.bis.sourcelead.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProjectTaskDocumentBO {
	
	private long id;
	private String fileName;
	private String fileType;
	private byte [] fileContents;
	private Integer status;
	


	public ProjectTaskDocumentBO(){
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public ProjectTaskDocumentBO(String fileName, String fileType, byte[] fileContents) {
		super();
		
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileContents = fileContents;
	}

	public ProjectTaskDocumentBO(long id, String fileName, String fileType, byte[] fileContents) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileContents = fileContents;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	@JsonIgnore
	public byte[] getFileContents() {
		return fileContents;
	}
	public void setFileContents(byte[] fileContents) {
		this.fileContents = fileContents;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
