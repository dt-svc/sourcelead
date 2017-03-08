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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "REQUIREMENT_REFER", catalog = "sourcelead")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "requirementRefer")
public class RequirementRefer implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_FROM_USER_ID")        
    private User referFromUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_TO_USER_ID")        
    private User referToUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_ID")        
    private Requirement requirementId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REFER_ID")        
    private RequirementRefer referId;
	
	
	@ManyToOne
    @JoinColumn(name="ORGLOCATION_ID")        
    private OrgLocations orgLocationId;
	
	
	@Column(name = "REFER_STATUS")
	private String referStatus;
	
	@Column(name = "REFER_FROM_EMAIL")
	private String referFromEmail;
	
	@Column(name = "REFER_TO_EMAIL")
	private String referToEmail;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private Integer appliedJobs;
	
	@Transient
	private Integer referToMeJobs;
	
	@Transient
	private Integer referByMeJobs;
	
	@Transient
	private Integer shortlistJobs;
	
	@Transient
	private Integer offeredJobs;
	
	@Transient
	private Integer hiredJobs;
	
	@Transient
	private Integer rejectedJobs;
	
	@Transient
	private Integer interviews;
	
	@Transient
	private String dateString;
	
	
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getReferFromUser() {
		return referFromUser;
	}

	public void setReferFromUser(User referFromUser) {
		this.referFromUser = referFromUser;
	}

	public User getReferToUser() {
		return referToUser;
	}

	public void setReferToUser(User referToUser) {
		this.referToUser = referToUser;
	}

	public Requirement getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Requirement requirementId) {
		this.requirementId = requirementId;
	}

	public RequirementRefer getReferId() {
		return referId;
	}

	public void setReferId(RequirementRefer referId) {
		this.referId = referId;
	}

	public String getReferFromEmail() {
		return referFromEmail;
	}

	public void setReferFromEmail(String referFromEmail) {
		this.referFromEmail = referFromEmail;
	}

	public String getReferToEmail() {
		return referToEmail;
	}

	public void setReferToEmail(String referToEmail) {
		this.referToEmail = referToEmail;
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

	public String getReferStatus() {
		return referStatus;
	}

	public void setReferStatus(String referStatus) {
		this.referStatus = referStatus;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Integer getAppliedJobs() {
		return appliedJobs;
	}

	public void setAppliedJobs(Integer appliedJobs) {
		this.appliedJobs = appliedJobs;
	}

	public Integer getReferToMeJobs() {
		return referToMeJobs;
	}

	public void setReferToMeJobs(Integer referToMeJobs) {
		this.referToMeJobs = referToMeJobs;
	}

	public Integer getReferByMeJobs() {
		return referByMeJobs;
	}

	public void setReferByMeJobs(Integer referByMeJobs) {
		this.referByMeJobs = referByMeJobs;
	}

	public Integer getShortlistJobs() {
		return shortlistJobs;
	}

	public void setShortlistJobs(Integer shortlistJobs) {
		this.shortlistJobs = shortlistJobs;
	}

	public Integer getOfferedJobs() {
		return offeredJobs;
	}

	public void setOfferedJobs(Integer offeredJobs) {
		this.offeredJobs = offeredJobs;
	}


	public Integer getHiredJobs() {
		return hiredJobs;
	}

	public void setHiredJobs(Integer hiredJobs) {
		this.hiredJobs = hiredJobs;
	}

	public Integer getRejectedJobs() {
		return rejectedJobs;
	}

	public void setRejectedJobs(Integer rejectedJobs) {
		this.rejectedJobs = rejectedJobs;
	}

	public Integer getInterviews() {
		return interviews;
	}

	public void setInterviews(Integer interviews) {
		this.interviews = interviews;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}
	
	
	
}
