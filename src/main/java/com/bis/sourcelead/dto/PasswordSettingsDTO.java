package com.bis.sourcelead.dto;

import java.io.Serializable;

public class PasswordSettingsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private String password;
	
	private String currentPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
}
