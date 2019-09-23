package com.bis.sourcelead.dto;

import java.math.BigInteger;

public class SourceleadTagsDTO {

	private String tagName;
	
	private BigInteger orgLocationId;
	
	private String entityType;
	
	private BigInteger entityId;
	
	private String createdDate;
	
	private String updatedDate;
	
	private String createdBy;
	
	

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public BigInteger getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(BigInteger orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public BigInteger getEntityId() {
		return entityId;
	}

	public void setEntityId(BigInteger entityId) {
		this.entityId = entityId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
