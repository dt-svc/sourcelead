package com.bis.sourcelead.entity;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;
/**
 * Upload File Entity
 * @author Rajashekar
 *
 */
public class UploadedFile implements Serializable{

	private static final long serialVersionUID = 1L;

	private MultipartFile file;
	
	private String email;
	
	private String inviteEmail;

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

	public String getInviteEmail() {
		return inviteEmail;
	}

	public void setInviteEmail(String inviteEmail) {
		this.inviteEmail = inviteEmail;
	}
}
