package com.bis.sourcelead.couchbase;

import java.io.Serializable;
import java.util.List;

public class BulkImportFailureDocumentsCacheDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String documentType;
	
	List<BulkImportFailureCacheDTO> failureList;
	
	String orgLocationId;
	
	private String userId;
	
	private String email;

	private String createdDate;


	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<BulkImportFailureCacheDTO> getFailureList() {
		return failureList;
	}

	public void setFailureList(List<BulkImportFailureCacheDTO> failureList) {
		this.failureList = failureList;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	} 
	
	
	
}
