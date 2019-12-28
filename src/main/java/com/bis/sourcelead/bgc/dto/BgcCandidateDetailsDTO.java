package com.bis.sourcelead.bgc.dto;

import java.io.Serializable;

import com.bis.sourcelead.dto.SearchDTO;

/**
 * @author Srinivas.Sabbu
 *
 */
public class BgcCandidateDetailsDTO extends SearchDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String uuid;
	
	private String generatedId;
	
	private Long candidateId;
	
	private Long sourceOrglocationId;
	
	private Long orgLocationId;
	
	private String fullName;
	
	private String currentBgcStatus;
	
	private String personalInfoStatus;
	
	private String educationCheckStaus;
	
	private String priorEmployersCheckStatus;
	
	private String healthCheckStatus;
	
	private String crimeCheckStatus;
	
	private String othersInfoStatus;
	
	private String updatedPercentage;
	
	private String comments;
	
	private Integer status;
	
	private String createdDate;
	
	private Long createdBy;

	private String updatedDate;
	
	private Long updatedBy;
	
	private String clinetOrVendorName;

	//dto fields for candidate
	
	private String candidateUUID;
	
	private String aadharNumber;
		
	private String panNumber;
		
	private String address;
		
	private String email;
	
	private Long bgcVendorContact;
	
	private String OrgType;
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public Long getSourceOrglocationId() {
		return sourceOrglocationId;
	}

	public void setSourceOrglocationId(Long sourceOrglocationId) {
		this.sourceOrglocationId = sourceOrglocationId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getCurrentBgcStatus() {
		return currentBgcStatus;
	}

	public void setCurrentBgcStatus(String currentBgcStatus) {
		this.currentBgcStatus = currentBgcStatus;
	}

	public String getPersonalInfoStatus() {
		return personalInfoStatus;
	}

	public void setPersonalInfoStatus(String personalInfoStatus) {
		this.personalInfoStatus = personalInfoStatus;
	}

	public String getEducationCheckStaus() {
		return educationCheckStaus;
	}

	public void setEducationCheckStaus(String educationCheckStaus) {
		this.educationCheckStaus = educationCheckStaus;
	}

	public String getPriorEmployersCheckStatus() {
		return priorEmployersCheckStatus;
	}

	public void setPriorEmployersCheckStatus(String priorEmployersCheckStatus) {
		this.priorEmployersCheckStatus = priorEmployersCheckStatus;
	}

	public String getHealthCheckStatus() {
		return healthCheckStatus;
	}

	public void setHealthCheckStatus(String healthCheckStatus) {
		this.healthCheckStatus = healthCheckStatus;
	}

	public String getCrimeCheckStatus() {
		return crimeCheckStatus;
	}

	public void setCrimeCheckStatus(String crimeCheckStatus) {
		this.crimeCheckStatus = crimeCheckStatus;
	}

	public String getOthersInfoStatus() {
		return othersInfoStatus;
	}

	public void setOthersInfoStatus(String othersInfoStatus) {
		this.othersInfoStatus = othersInfoStatus;
	}

	public String getUpdatedPercentage() {
		return updatedPercentage;
	}

	public void setUpdatedPercentage(String updatedPercetage) {
		this.updatedPercentage = updatedPercentage;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getCandidateUUID() {
		return candidateUUID;
	}

	public void setCandidateUUID(String candidateUUID) {
		this.candidateUUID = candidateUUID;
	}

	public String getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getBgcVendorContact() {
		return bgcVendorContact;
	}

	public void setBgcVendorContact(Long bgcVendorContact) {
		this.bgcVendorContact = bgcVendorContact;
	}

	public String getGeneratedId() {
		return generatedId;
	}

	public void setGeneratedId(String generatedId) {
		this.generatedId = generatedId;
	}

	public String getClinetOrVendorName() {
		return clinetOrVendorName;
	}

	public void setClinetOrVendorName(String clinetOrVendorName) {
		this.clinetOrVendorName = clinetOrVendorName;
	}

	public String getOrgType() {
		return OrgType;
	}

	public void setOrgType(String orgType) {
		OrgType = orgType;
	}
	
	
}
