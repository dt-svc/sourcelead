package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author Shivayogi
 */

@Entity
@Table(name = "CANDIDATE_CERTIFICATIONS", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "CandidateCertifications")
public class CandidateCertifications {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )        
    private OrgLocations orgLocationId;

	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private Candidate candidateId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PROVIDER")
	private String provider;

	@Column(name = "SCORE")
	private String score;

	@Column(name = "SCORE_TYPE")
	private String scoreType;

	@Column(name = "DESCRIPTION")
	private String description;
	
	
	@Column(name = "VALIDFROM")
	private Date validFrom;

	@Column(name = "VALIDTO")
	private Date validTo;
	
	@Transient
	private String candidatevalidFromDateToString;
    
	@Transient
	private String candidatevalidToDateToString;
	
	@Transient
	private Long candidatecertificateID; 
	
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = " CREATED_BY")
	private String createdBy;

	@Column(name = " UPDATED_DATE")
	private Date updatedDate;

	@Column(name = " UPDATED_BY")
	private String updatedBy;
	
	@Column(name = " STATUS")
	private String status;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
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

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Long getCandidatecertificateID() {
		return candidatecertificateID;
	}

	public void setCandidatecertificateID(Long candidatecertificateID) {
		this.candidatecertificateID = candidatecertificateID;
	}

	
}
