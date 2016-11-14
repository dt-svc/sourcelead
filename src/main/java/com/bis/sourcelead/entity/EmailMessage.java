package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;

public class EmailMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -519129838464344312L;
	
	private String firstName;
	
	private String lastName;
	
	private String toEmail;
	
	private String fromEmail;
	
	private String fromName;
	
	private String subject;
	
	private String messageBody;
	
	private String signature;
	
    private String userName;
	
	private String password;
	
	private Long orgLocationId;
	
	private String createdBy;
	
	private Map<String, Object> model;
	
	private String[] attachmentFiles;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

	public String[] getAttachmentFiles() {
		return attachmentFiles;
	}

	public void setAttachmentFiles(String[] attachmentFiles) {
		this.attachmentFiles = attachmentFiles;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
