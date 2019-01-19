package com.bis.sourcelead.entity;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "RECRUITER_REPORT_CANDIDATE_COUNT_VIEW")
public class RecruiterReportCandidateCountView {

	@Id
    @Column(name = "CAND_ID", nullable = false)
	private Long id;
	
	@Column(name = "USER_CODE", nullable = false)
	private String userCode;
	
	@Column(name = "ORG_LOCATION_ID", nullable = false)
	private Long orgLocationId;
	
	@Column(name = "ORG_ID", nullable = false)
	private Long orgId;
	
	@Column(name = "CAND_COUNT")
	private Long candidateCount;
	
	@Column(name = "CREATED_DATE")
	private String createdDate;
	
	@Transient
	private String associateUserCode;
	
	@Transient
	private BigDecimal candCount;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

	public Long getCandidateCount() {
		return candidateCount;
	}

	public void setCandidateCount(Long candidateCount) {
		this.candidateCount = candidateCount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getAssociateUserCode() {
		return associateUserCode;
	}

	public void setAssociateUserCode(String associateUserCode) {
		this.associateUserCode = associateUserCode;
	}

	public BigDecimal getCandCount() {
		return candCount;
	}

	public void setCandCount(BigDecimal candCount) {
		this.candCount = candCount;
	}
}
