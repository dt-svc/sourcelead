package com.bis.sourcelead.couchbase;

import java.io.Serializable;
import java.util.List;

public class BulkImportCacheDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String documentType;
	
	List<String> queueDocumentsList;
	
	String orgLocationId;
	
	private String userId;
	
	private String email;

	private String createdDate; 
	
	private String userName;
	
	private String orgName;
	
	public List<String> getQueueDocumentsList() {
		return queueDocumentsList;
	}

	public void setQueueDocumentsList(List<String> queueDocumentsList) {
		this.queueDocumentsList = queueDocumentsList;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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

	public String getUserName() {
		return userName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	
}
