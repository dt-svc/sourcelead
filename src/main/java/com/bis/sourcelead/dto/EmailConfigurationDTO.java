package com.bis.sourcelead.dto;

import java.io.Serializable;
 
public class EmailConfigurationDTO implements Serializable{
	
	private String id;
	
	private String emailId;
	
	private String EmailPassword;
	
	private String serverName;
	
	private String portNumber;
	
	private String usageType;
	
	private String encryptType;
	
	private String emailUUID;
	

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailPassword() {
		return EmailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		EmailPassword = emailPassword;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public String getEmailUUID() {
		return emailUUID;
	}

	public void setEmailUUID(String emailUUID) {
		this.emailUUID = emailUUID;
	}
	
}
