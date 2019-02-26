package com.bis.sourcelead.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RECRUITER_JOBS_REPORT_VIEW")
public class RecruiterJobsReportView {

	@Id
	@Column(name = "RSH_ID", nullable = false)
	private Long id;

	@Column(name = "APPLICANT_SUBMISSION_COUNT")
	private BigDecimal applicantSubmissionCount;

	@Column(name = "APPLICANT_SHORTLISTED_COUNT")
	private BigDecimal applicantShortlistedCount;

	@Column(name = "APPLICANT_SUBMIT_TO_CLIENT_COUNT")
	private BigDecimal applicantSubmittedClientCount;

	@Column(name = "APPLICANT_INTERVIEW_COUNT")
	private BigDecimal applicantInterviewCount;

	@Column(name = "APPLICANT_OFFERMADE_COUNT")
	private BigDecimal applicantOfferMadeCount;

	@Column(name = "APPLICANT_REJECTED_COUNT")
	private BigDecimal applicantRejectedCount;

	@Column(name = "APPLICANT_BACKOUT_COUNT")
	private BigDecimal applicantBackOutCount;

	@Column(name = "APPLICANT_JOINED_COUNT")
	private BigDecimal applicantJoinedCount;

	@Column(name = "APPLICANT_ONHOLD_COUNT")
	private BigDecimal userOnHoldApplicantsCount;

	@Column(name = "APPLICANT_NORESPONSECOUNT_COUNT")
	private BigDecimal userNoResponseApplicantsCount;

	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;

	@Column(name = "REQUIREMENT_ID")
	private Long requirementId;

	@Column(name = "CREATED_DATE")
	private String createdDate;

	@Column(name = "USER_CODE")
	private String userCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getApplicantSubmissionCount() {
		return applicantSubmissionCount;
	}

	public void setApplicantSubmissionCount(BigDecimal applicantSubmissionCount) {
		this.applicantSubmissionCount = applicantSubmissionCount;
	}

	public BigDecimal getApplicantShortlistedCount() {
		return applicantShortlistedCount;
	}

	public void setApplicantShortlistedCount(BigDecimal applicantShortlistedCount) {
		this.applicantShortlistedCount = applicantShortlistedCount;
	}

	public BigDecimal getApplicantSubmittedClientCount() {
		return applicantSubmittedClientCount;
	}

	public void setApplicantSubmittedClientCount(BigDecimal applicantSubmittedClientCount) {
		this.applicantSubmittedClientCount = applicantSubmittedClientCount;
	}

	public BigDecimal getApplicantInterviewCount() {
		return applicantInterviewCount;
	}

	public void setApplicantInterviewCount(BigDecimal applicantInterviewCount) {
		this.applicantInterviewCount = applicantInterviewCount;
	}

	public BigDecimal getApplicantOfferMadeCount() {
		return applicantOfferMadeCount;
	}

	public void setApplicantOfferMadeCount(BigDecimal applicantOfferMadeCount) {
		this.applicantOfferMadeCount = applicantOfferMadeCount;
	}

	public BigDecimal getApplicantBackOutCount() {
		return applicantBackOutCount;
	}

	public void setApplicantBackOutCount(BigDecimal applicantBackOutCount) {
		this.applicantBackOutCount = applicantBackOutCount;
	}

	public BigDecimal getApplicantRejectedCount() {
		return applicantRejectedCount;
	}

	public void setApplicantRejectedCount(BigDecimal applicantRejectedCount) {
		this.applicantRejectedCount = applicantRejectedCount;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public BigDecimal getApplicantJoinedCount() {
		return applicantJoinedCount;
	}

	public void setApplicantJoinedCount(BigDecimal applicantJoinedCount) {
		this.applicantJoinedCount = applicantJoinedCount;
	}

	public BigDecimal getUserOnHoldApplicantsCount() {
		return userOnHoldApplicantsCount;
	}

	public void setUserOnHoldApplicantsCount(BigDecimal userOnHoldApplicantsCount) {
		this.userOnHoldApplicantsCount = userOnHoldApplicantsCount;
	}

	public BigDecimal getUserNoResponseApplicantsCount() {
		return userNoResponseApplicantsCount;
	}

	public void setUserNoResponseApplicantsCount(BigDecimal userNoResponseApplicantsCount) {
		this.userNoResponseApplicantsCount = userNoResponseApplicantsCount;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}
	
	

}
