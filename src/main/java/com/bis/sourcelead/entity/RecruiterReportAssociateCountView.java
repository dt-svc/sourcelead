package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "RECRUITER_REPORT_ASSOCIATE_COUNT_VIEW")
public class RecruiterReportAssociateCountView {

	@Id
    @Column(name = "REQ_ID", nullable = false)
	private Long id;
	
	@Column(name = "USER_ID", nullable = false)
	private Long userId;
	
	@Column(name = "ORG_LOCATION_ID", nullable = false)
	private Long orgLocationId;
	
	@Column(name = "ORG_ID", nullable = false)
	private Long orgId;
	
	@Column(name = "REQ_COUNT")
	private Long requirementCount;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;
	
	@Transient
	private BigInteger associateUserId;
	
	@Transient
	private BigDecimal jobCount;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getRequirementCount() {
		return requirementCount;
	}

	public void setRequirementCount(Long requirementCount) {
		this.requirementCount = requirementCount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public BigInteger getAssociateUserId() {
		return associateUserId;
	}

	public void setAssociateUserId(BigInteger associateUserId) {
		this.associateUserId = associateUserId;
	}

	public BigDecimal getJobCount() {
		return jobCount;
	}

	public void setJobCount(BigDecimal jobCount) {
		this.jobCount = jobCount;
	}
}
