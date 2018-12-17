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

@Entity
@Table(name = "CANDIDATE_LCA_JOB_DETAILS")
public class CandidateLcaJobDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
    @ManyToOne
	@JoinColumn(name = "IMMIGRATION_ID", nullable = false)
	private Immigration immigration;
	
	@ManyToOne
	@JoinColumn(name="CANDIDATE_ID",nullable=false)        
	private Candidate candidateId;
	
	@ManyToOne
	@JoinColumn(name="JOB_ID",nullable=false)        
	private Requirement requirementId;
	
	/*@Column(name = "ID_NUMBER")
	private String idNumber;
	
	@Column(name = "ID_TYPE")
	private String idType;*/
	
	@Column(name = "EMPLOYMENT_TYPE")
	private String employmentType;
	
	@Column(name = "EMPLOYMENT_LEVEL")
	private String employmentLevel;
	
	@Column(name = "EMPLOYER_NAME")
	private String employerName;
	
	@Column(name = "EMPLOYER_LOCATION", columnDefinition = "LONGTEXT default NULL")
	private String employerLocation;
	
	@Column(name = "CURR_STATUS")
	private String currentStatus;
	
	@Column(name = "START_DATE ")
	private Date startDate;
	
	@Column(name = "END_DATE ")
	private Date endDate;
	
	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE ")
	private Date updatedDate;
	
	@Column(name = "IS_CURR")
	private String isCurrent;
	
	@Column(name = "JOB_START_DATE ")
	private Date jobStartDate;
	
	@Column(name = "JOB_END_DATE ")
	private Date jobEndDate;
	
	@Column(name = "RATE_CURRENCY")
	private String rateCurrency;
	
	@Column(name = "RATE")
	private String rate;
	
	@Column(name = "RATE_TYPE")
	private String rateType;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Candidate getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Candidate candidateId) {
		this.candidateId = candidateId;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	
	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getEmploymentLevel() {
		return employmentLevel;
	}

	public void setEmploymentLevel(String employmentLevel) {
		this.employmentLevel = employmentLevel;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	public String getRateCurrency() {
		return rateCurrency;
	}

	public void setRateCurrency(String rateCurrency) {
		this.rateCurrency = rateCurrency;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRateType() {
		return rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}

	public String getEmployerLocation() {
		return employerLocation;
	}

	public void setEmployerLocation(String employerLocation) {
		this.employerLocation = employerLocation;
	}

	public Date getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(Date jobStartDate) {
		this.jobStartDate = jobStartDate;
	}

	public Date getJobEndDate() {
		return jobEndDate;
	}

	public void setJobEndDate(Date jobEndDate) {
		this.jobEndDate = jobEndDate;
	}

	public Immigration getImmigration() {
		return immigration;
	}

	public void setImmigration(Immigration immigration) {
		this.immigration = immigration;
	}
	
	
	
}
