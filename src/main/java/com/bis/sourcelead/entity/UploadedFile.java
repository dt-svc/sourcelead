package com.bis.sourcelead.entity;

import org.springframework.web.multipart.MultipartFile;

public class UploadedFile {

	private MultipartFile file;
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
