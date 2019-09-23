package com.bis.sourcelead.dto;

import java.util.Date;

public class EmailAccountSettingsDTO {

	private long id;
	private long orglocationId;
	private long userId;
	private String email;
	private String password;
	private String incomingServer;
	private String incomingPort;
	private boolean incomingSSL;
	private String outgoingServer;
	private String outgoingPort;
	private boolean outgoingSSL;
	private String emailType;
	private Date createdDate;
	private String createdBy;
	private Date updatedDate;
	private String updatedBy;
	
	/*
	 * private String recieveServerType;
	 * 
	 * private String sendServerType;
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getOrglocationId() {
		return orglocationId;
	}

	public void setOrglocationId(long orglocationId) {
		this.orglocationId = orglocationId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIncomingServer() {
		return incomingServer;
	}

	public void setIncomingServer(String incomingServer) {
		this.incomingServer = incomingServer;
	}

	public String getIncomingPort() {
		return incomingPort;
	}

	public void setIncomingPort(String incomingPort) {
		this.incomingPort = incomingPort;
	}

	public boolean isIncomingSSL() {
		return incomingSSL;
	}

	public void setIncomingSSL(boolean incomingSSL) {
		this.incomingSSL = incomingSSL;
	}

	public String getOutgoingServer() {
		return outgoingServer;
	}

	public void setOutgoingServer(String outgoingServer) {
		this.outgoingServer = outgoingServer;
	}

	public String getOutgoingPort() {
		return outgoingPort;
	}

	public void setOutgoingPort(String outgoingPort) {
		this.outgoingPort = outgoingPort;
	}

	public boolean isOutgoingSSL() {
		return outgoingSSL;
	}

	public void setOutgoingSSL(boolean outgoingSSL) {
		this.outgoingSSL = outgoingSSL;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
