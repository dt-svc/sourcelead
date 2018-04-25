package com.bis.sourcelead.dto;

import java.util.Date;

import com.bis.sourcelead.annotations.CleanHtml;
import com.bis.sourcelead.annotations.Required;

public class CandidateCertificationDTO extends CommonDTO {

	private Long id;

	private String candidateId;

	@Required(desc = "Certificate Name")
	private String name;

	@Required(desc = "Provider")
	private String provider;

	private String score;

	private String scoreType;

	@CleanHtml(escape=false)
	private String description;

	private String validFrom;

	private String validTo;

	private Date createdDate;

	private String createdBy;

	private String updatedDate;

	private String updatedBy;

	private String candidatecertificateID;

	private String candidatevalidFromDateToString;

	private String candidatevalidToDateToString;

	private long loginUserId;

	private long orgLocationId;

	private long candidateCertificationId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
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

	public String getCandidatecertificateID() {
		return candidatecertificateID;
	}

	public void setCandidatecertificateID(String candidatecertificateID) {
		this.candidatecertificateID = candidatecertificateID;
	}

	public String getCandidatevalidFromDateToString() {
		return candidatevalidFromDateToString;
	}

	public void setCandidatevalidFromDateToString(String candidatevalidFromDateToString) {
		this.candidatevalidFromDateToString = candidatevalidFromDateToString;
	}

	public String getCandidatevalidToDateToString() {
		return candidatevalidToDateToString;
	}

	public void setCandidatevalidToDateToString(String candidatevalidToDateToString) {
		this.candidatevalidToDateToString = candidatevalidToDateToString;
	}

	public long getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(long loginUserId) {
		this.loginUserId = loginUserId;
	}

	public long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public long getCandidateCertificationId() {
		return candidateCertificationId;
	}

	public void setCandidateCertificationId(long candidateCertificationId) {
		this.candidateCertificationId = candidateCertificationId;
	}

}
