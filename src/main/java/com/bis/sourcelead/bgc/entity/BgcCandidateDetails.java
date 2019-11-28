package com.bis.sourcelead.bgc.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.bis.sourcelead.entity.Candidate;
import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.User;

/**
 * @author Srinivas.Sabbu
 *
 */
@Entity
@Table(name = "BGC_CANDIDATE_DETAILS")
public class BgcCandidateDetails {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "BGC_UUID", nullable = false) // auto generated uuid;
	private String uuid;
	
	@ManyToOne
	@JoinColumn(name = "CANDIDATE_ID")
	private Candidate candidate;
	
	@ManyToOne
	@JoinColumn(name = "SOURCE_ORG_LOCATION_ID")
	private OrgLocations sourceOrglocationId;
	
	@ManyToOne
	@JoinColumn(name = "ORG_LOCATION_ID")
	private OrgLocations orgLocationId;
	
	
	@Column(name = "CURRENT_BGC_STATUS ")
	private String currentBgcStatus;
	
	@Column(name = "PERSONAL_INFO_STATUS ")
	private String personalInfoStatus;
	
	@Column(name = "EDUCATION_CHECK_STATUS ")
	private String educationCheckStaus;
	
	@Column(name = "PRIOR_EMPLOYERS_CHECK_STATUS ")
	private String priorEmployersCheckStatus;
	
	@Column(name = "HEALTH_CHECK_STATUS ")
	private String healthCheckStatus;
	
	@Column(name = "CRIME_RECORD_STATUS ")
	private String crimeCheckStatus;
	
	@Column(name = "OTHERS_INFO_STATUS ")
	private String OthersInfoStatus;
	
	@Column(name = "UPDATED_PERCENTAGE ")
	private String updatedPercentage;
	
	@Column(name = "COMMENTS ", columnDefinition = "TEXT default NULL")
	private String comments;
	
	@Column(name = "STATUS")
	private Integer status;
	
	
	@Column(name = "CREATED_DATE ")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "CREATED_BY")
	private User createdBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY")
	private User updatedBy;

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

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public OrgLocations getSourceOrglocationId() {
		return sourceOrglocationId;
	}

	public void setSourceOrglocationId(OrgLocations sourceOrglocationId) {
		this.sourceOrglocationId = sourceOrglocationId;
	}


	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
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
		return OthersInfoStatus;
	}

	public void setOthersInfoStatus(String othersInfoStatus) {
		OthersInfoStatus = othersInfoStatus;
	}

	public String getupdatedPercentage() {
		return updatedPercentage;
	}

	public void setUpdatedPercentage(String updatedPercentage) {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}


}
