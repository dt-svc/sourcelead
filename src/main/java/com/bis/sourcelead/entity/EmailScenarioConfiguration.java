package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="EMAIL_SCENARIO_CONFIGURATION")
public class EmailScenarioConfiguration implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="EMAIL_UUID")
	private String email_UUID;
	
	
	@Column(name="EMAIL_PASSWORD")
	private String emailPassword;
	
	@Column(name="ENCRYPTION_TYPE")
	private String encryptionType;
	
	@Column(name="SERVER_NAME")
	private String serverName;
	
	@Column(name="PORT_NUMBER")
	private Long portNumber;
	
	@Column(name="USAGE_TYPE")
	private String usageType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_lOCATION_ID")
    private OrgLocations orgLocationId;
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@Column(name="VERIFIED_STATUS")
	private String verifiedStatus;
	

	@Column(name="IS_ACTIVE")
	private String isActive;
	

	public String getEmail_UUID() {
		return email_UUID;
	}

	public void setEmail_UUID(String email_UUID) {
		this.email_UUID = email_UUID;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	

	public Long getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(Long portNumber) {
		this.portNumber = portNumber;
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

	public String getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(String encryptionType) {
		this.encryptionType = encryptionType;
	}


	public String getVerifiedStatus() {
		return verifiedStatus;
	}

	public void setVerifiedStatus(String verifiedStatus) {
		this.verifiedStatus = verifiedStatus;
	}

	public String getUsageType() {
		return usageType;
	}

	public void setUsageType(String usageType) {
		this.usageType = usageType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
}
