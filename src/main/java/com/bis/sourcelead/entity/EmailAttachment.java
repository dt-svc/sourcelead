package com.bis.sourcelead.entity;

import java.io.Serializable;

public class EmailAttachment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String type;
	
	private String content;
	
	private Boolean binary;
	
	private byte[] docFile;
	
	private String UUID;

	private String fileUrl;
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public Boolean getBinary() {
		return binary;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setBinary(Boolean binary) {
		this.binary = binary;
	}

	public byte[] getDocFile() {
		return docFile;
	}

	public void setDocFile(byte[] docFile) {
		this.docFile = docFile;
	}

	public String getUUID() {
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
