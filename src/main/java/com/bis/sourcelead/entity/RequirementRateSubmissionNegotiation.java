package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bis.sourcelead.enums.SubmissionRateStatus;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name = "REQUIREMENT_SUBMISSION_RATE_NEGOTIATION")
public class RequirementRateSubmissionNegotiation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="REQUIREMENT_SUBMISSION_ID", nullable=false )        
    private RequirementSubmission requirementsubmissionId;
	
	@Column(name = "RATE_INITIATOR_TYPE")
	@Enumerated(EnumType.STRING)
	private SubmissionRateStatus rateInitiatorType;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="INITIATOR_ORG_LOCATION_ID", nullable=false )        
    private OrgLocations initiatorOrgLocationId;
	
	@Column(name = "CURRENTRATE")
	private String currentRate;
	
	@Column(name = "CURRENTRATETYPE")
	private String currentRateType;
	
	@Column(name = "EXPECTEDRATE")
	private String expectedRate;
	
	@Column(name = "EXPECTEDRATETYPE")
	private String expectedRateType;
	
	@Column(name = "RATE_STAGE")
	@Enumerated(EnumType.STRING)
	private SubmissionRateStatus rateStage;
	
	@Column(name = "RATE_REASON", columnDefinition = "TEXT default NULL")
	private String rateReason;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	
	@Transient
	@JsonProperty
	private String createdDateStr;
	
	@Transient
	@JsonProperty
	private String rateAndType;
	
	@Transient
	@JsonProperty
	private String proposeByName;
	
	@Transient
	@JsonProperty
	private String initiatorOrgLocationIdStr;
	
	@Transient
	@JsonProperty
	private String previouslyProposedBy;
	
	@Transient
	@JsonProperty
	private String requirementName;
	
	@Transient
	@JsonProperty
	private String requirementRate;
	
	@Transient
	@JsonProperty
	private String submissionRateStatus;
	
	@Transient
	@JsonProperty
	private String isNeedApplicantPermit;
	
	@Transient
	@JsonProperty
	private String submissionStatus;
	
	@Transient
	@JsonProperty
	private String expectedRatePer;
	
	/*expectedRateRN, expectedRateTypeRN, expectedRatePerRN are temp variables to keep expected rate for rate negotiation*/
	@Transient
	@JsonProperty
	private String expectedRateRN;
	
	@Transient
	@JsonProperty
	private String expectedRateTypeRN;
	
	@Transient
	@JsonProperty
	private String expectedRatePerRN;
	
	@Transient
	@JsonProperty
	private String isLastProposedUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequirementSubmission getRequirementsubmissionId() {
		return requirementsubmissionId;
	}

	public void setRequirementsubmissionId(RequirementSubmission requirementsubmissionId) {
		this.requirementsubmissionId = requirementsubmissionId;
	}

	public SubmissionRateStatus getRateInitiatorType() {
		return rateInitiatorType;
	}

	public void setRateInitiatorType(SubmissionRateStatus rateInitiatorType) {
		this.rateInitiatorType = rateInitiatorType;
	}

	public String getIsNeedApplicantPermit() {
		return isNeedApplicantPermit;
	}

	public void setIsNeedApplicantPermit(String isNeedApplicantPermit) {
		this.isNeedApplicantPermit = isNeedApplicantPermit;
	}

	public String getSubmissionStatus() {
		return submissionStatus;
	}

	public void setSubmissionStatus(String submissionStatus) {
		this.submissionStatus = submissionStatus;
	}


	public String getExpectedRatePer() {
		return expectedRatePer;
	}

	public void setExpectedRatePer(String expectedRatePer) {
		this.expectedRatePer = expectedRatePer;
	}

	public OrgLocations getInitiatorOrgLocationId() {
		return initiatorOrgLocationId;
	}

	public void setInitiatorOrgLocationId(OrgLocations initiatorOrgLocationId) {
		this.initiatorOrgLocationId = initiatorOrgLocationId;
	}

	public String getCurrentRate() {
		return currentRate;
	}

	public void setCurrentRate(String currentRate) {
		this.currentRate = currentRate;
	}

	public String getCurrentRateType() {
		return currentRateType;
	}

	public void setCurrentRateType(String currentRateType) {
		this.currentRateType = currentRateType;
	}

	public String getExpectedRate() {
		return expectedRate;
	}

	public void setExpectedRate(String expectedRate) {
		this.expectedRate = expectedRate;
	}

	public String getExpectedRateType() {
		return expectedRateType;
	}

	public void setExpectedRateType(String expectedRateType) {
		this.expectedRateType = expectedRateType;
	}

	public SubmissionRateStatus getRateStage() {
		return rateStage;
	}

	public void setRateStage(SubmissionRateStatus rateStage) {
		this.rateStage = rateStage;
	}

	public String getRateReason() {
		return rateReason;
	}

	public void setRateReason(String rateReason) {
		this.rateReason = rateReason;
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

	public String getCreatedDateStr() {
		return createdDateStr;
	}

	public void setCreatedDateStr(String createdDateStr) {
		this.createdDateStr = createdDateStr;
	}

	public String getRateAndType() {
		return rateAndType;
	}

	public void setRateAndType(String rateAndType) {
		this.rateAndType = rateAndType;
	}

	public String getProposeByName() {
		return proposeByName;
	}

	public void setProposeByName(String proposeByName) {
		this.proposeByName = proposeByName;
	}

	public String getInitiatorOrgLocationIdStr() {
		return initiatorOrgLocationIdStr;
	}

	public void setInitiatorOrgLocationIdStr(String initiatorOrgLocationIdStr) {
		this.initiatorOrgLocationIdStr = initiatorOrgLocationIdStr;
	}

	public String getPreviouslyProposedBy() {
		return previouslyProposedBy;
	}

	public void setPreviouslyProposedBy(String previouslyProposedBy) {
		this.previouslyProposedBy = previouslyProposedBy;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getRequirementRate() {
		return requirementRate;
	}

	public void setRequirementRate(String requirementRate) {
		this.requirementRate = requirementRate;
	}

	public String getSubmissionRateStatus() {
		return submissionRateStatus;
	}

	public void setSubmissionRateStatus(String submissionRateStatus) {
		this.submissionRateStatus = submissionRateStatus;
	}

	public String getExpectedRateRN() {
		return expectedRateRN;
	}

	public void setExpectedRateRN(String expectedRateRN) {
		this.expectedRateRN = expectedRateRN;
	}

	public String getExpectedRateTypeRN() {
		return expectedRateTypeRN;
	}

	public void setExpectedRateTypeRN(String expectedRateTypeRN) {
		this.expectedRateTypeRN = expectedRateTypeRN;
	}

	public String getExpectedRatePerRN() {
		return expectedRatePerRN;
	}

	public void setExpectedRatePerRN(String expectedRatePerRN) {
		this.expectedRatePerRN = expectedRatePerRN;
	}

	public String getIsLastProposedUser()
	{
		return isLastProposedUser;
	}

	public void setIsLastProposedUser(String isLastProposedUser)
	{
		this.isLastProposedUser = isLastProposedUser;
	}
	
}
