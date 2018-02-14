package com.bis.sourcelead.dto;

public class EmailAccountSettingsDTO {
	
	private String id;
	private String orglocationId;
	private String userId;
	private String email;
	private String recieveServerType;
	private String recieveServerName;
	private String recievePort;
	private String recieveEmail;
	private String recieveSSL;
	private String sendServerType;
	private String sendServerName;
	private String sendPort;
	private String sendEmail;
	private String sendSSL;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrglocationId() {
		return orglocationId;
	}
	public void setOrglocationId(String orglocationId) {
		this.orglocationId = orglocationId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRecieveServerType() {
		return recieveServerType;
	}
	public void setRecieveServerType(String recieveServerType) {
		this.recieveServerType = recieveServerType;
	}
	public String getRecieveServerName() {
		return recieveServerName;
	}
	public void setRecieveServerName(String recieveServerName) {
		this.recieveServerName = recieveServerName;
	}
	public String getRecievePort() {
		return recievePort;
	}
	public void setRecievePort(String recievePort) {
		this.recievePort = recievePort;
	}
	public String getRecieveEmail() {
		return recieveEmail;
	}
	public void setRecieveEmail(String recieveEmail) {
		this.recieveEmail = recieveEmail;
	}
	public String getRecieveSSL() {
		return recieveSSL;
	}
	public void setRecieveSSL(String recieveSSL) {
		this.recieveSSL = recieveSSL;
	}
	public String getSendServerType() {
		return sendServerType;
	}
	public void setSendServerType(String sendServerType) {
		this.sendServerType = sendServerType;
	}
	public String getSendServerName() {
		return sendServerName;
	}
	public void setSendServerName(String sendServerName) {
		this.sendServerName = sendServerName;
	}
	public String getSendPort() {
		return sendPort;
	}
	public void setSendPort(String sendPort) {
		this.sendPort = sendPort;
	}
	public String getSendEmail() {
		return sendEmail;
	}
	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}
	public String getSendSSL() {
		return sendSSL;
	}
	public void setSendSSL(String sendSSL) {
		this.sendSSL = sendSSL;
	}
	
	

}
