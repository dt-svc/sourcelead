package com.bis.sourcelead.dto;

public class CandidateVerificationConfigDTO {

	private Long id;
	private boolean emailRequired;
	private String email;
	private boolean phoneRequired;
	private String phoneNumber;
	private boolean panRequired;
	private String panNumber;
	private boolean aadharRequired;
	private String aadharNumber;
	private boolean ssnRequired;
	private String ssnNumber;
	private long userId;
	private String uuid;
	private String status;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private long orgLocationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEmailRequired() {
		return emailRequired;
	}

	public void setEmailRequired(boolean emailRequired) {
		this.emailRequired = emailRequired;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isPhoneRequired() {
		return phoneRequired;
	}

	public void setPhoneRequired(boolean phoneRequired) {
		this.phoneRequired = phoneRequired;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isPanRequired() {
		return panRequired;
	}

	public void setPanRequired(boolean panRequired) {
		this.panRequired = panRequired;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public boolean isAadharRequired() {
		return aadharRequired;
	}

	public void setAadharRequired(boolean aadharRequired) {
		this.aadharRequired = aadharRequired;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public boolean isSsnRequired() {
		return ssnRequired;
	}

	public void setSsnRequired(boolean ssnRequired) {
		this.ssnRequired = ssnRequired;
	}

	public String getSsnNumber() {
		return ssnNumber;
	}

	public void setSsnNumber(String ssnNumber) {
		this.ssnNumber = ssnNumber;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

}
