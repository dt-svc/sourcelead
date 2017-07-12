package com.bis.sourcelead.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProjectDocumentBO {
	
	private long id;
	private String fileName;
	private String fileType;
	private byte [] fileContents;
	
	public ProjectDocumentBO(){
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public ProjectDocumentBO(String fileName, String fileType, byte[] fileContents) {
		super();
		
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileContents = fileContents;
	}

	public ProjectDocumentBO(long id, String fileName, String fileType, byte[] fileContents) {
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
	
	
	

}
