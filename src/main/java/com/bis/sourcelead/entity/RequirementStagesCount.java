package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REQUIREMENT_STAGES_COUNT", schema = "SOURCELEAD")
public class RequirementStagesCount implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;

	@Column(name = "NEW_REQUIREMENTS_COUNT ")
	private BigDecimal newRequirementsCount;
	
	@Column(name = "DRAFT_REQUIREMENTS_COUNT ")
	private BigDecimal draftRequirementsCount;
	
	@Column(name = "SUBMITTED_REQUIREMENTS_COUNT ")
	private BigDecimal submittedRequirementsCount;
	
	@Column(name = "OPEN_REQUIREMENTS_COUNT ")
	private BigDecimal openRequirementsCount;
	
	@Column(name = "CANCEL_REQUIREMENTS_COUNT ")
	private BigDecimal cancelRequirementsCount;
	
	@Column(name = "ONHOLD_REQUIREMENTS_COUNT ")
	private BigDecimal onholdRequirementsCount;
	
	@Column(name = "FILLEDCLOSED_REQUIREMENTS_COUNT ")
	private BigDecimal filledclosedRequirementsCount;
	
	@Column(name = "UNFILLEDCLOSED_REQUIREMENTS_COUNT ")
	private BigDecimal unfilledclosedRequirementsCount;
	
	@Column(name = "APPROVED_REQUIREMENTS_COUNT ")
	private BigDecimal approvedRequirementsCount;
	
	@Column(name = "REJECTED_REQUIREMENTS_COUNT ")
	private BigDecimal rejectedRequirementsCount;
	
	@Column(name = "CLOSED_REQUIREMENTS_COUNT ")
	private BigDecimal closedRequirementsCount;

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public BigDecimal getNewRequirementsCount() {
		return newRequirementsCount;
	}

	public void setNewRequirementsCount(BigDecimal newRequirementsCount) {
		this.newRequirementsCount = newRequirementsCount;
	}

	public BigDecimal getDraftRequirementsCount() {
		return draftRequirementsCount;
	}

	public void setDraftRequirementsCount(BigDecimal draftRequirementsCount) {
		this.draftRequirementsCount = draftRequirementsCount;
	}

	public BigDecimal getSubmittedRequirementsCount() {
		return submittedRequirementsCount;
	}

	public void setSubmittedRequirementsCount(BigDecimal submittedRequirementsCount) {
		this.submittedRequirementsCount = submittedRequirementsCount;
	}

	public BigDecimal getOpenRequirementsCount() {
		return openRequirementsCount;
	}

	public void setOpenRequirementsCount(BigDecimal openRequirementsCount) {
		this.openRequirementsCount = openRequirementsCount;
	}

	public BigDecimal getCancelRequirementsCount() {
		return cancelRequirementsCount;
	}

	public void setCancelRequirementsCount(BigDecimal cancelRequirementsCount) {
		this.cancelRequirementsCount = cancelRequirementsCount;
	}

	public BigDecimal getOnholdRequirementsCount() {
		return onholdRequirementsCount;
	}

	public void setOnholdRequirementsCount(BigDecimal onholdRequirementsCount) {
		this.onholdRequirementsCount = onholdRequirementsCount;
	}

	public BigDecimal getFilledclosedRequirementsCount() {
		return filledclosedRequirementsCount;
	}

	public void setFilledclosedRequirementsCount(BigDecimal filledclosedRequirementsCount) {
		this.filledclosedRequirementsCount = filledclosedRequirementsCount;
	}

	public BigDecimal getUnfilledclosedRequirementsCount() {
		return unfilledclosedRequirementsCount;
	}

	public void setUnfilledclosedRequirementsCount(BigDecimal unfilledclosedRequirementsCount) {
		this.unfilledclosedRequirementsCount = unfilledclosedRequirementsCount;
	}

	public BigDecimal getApprovedRequirementsCount() {
		return approvedRequirementsCount;
	}

	public void setApprovedRequirementsCount(BigDecimal approvedRequirementsCount) {
		this.approvedRequirementsCount = approvedRequirementsCount;
	}

	public BigDecimal getRejectedRequirementsCount() {
		return rejectedRequirementsCount;
	}

	public void setRejectedRequirementsCount(BigDecimal rejectedRequirementsCount) {
		this.rejectedRequirementsCount = rejectedRequirementsCount;
	}

	public BigDecimal getClosedRequirementsCount() {
		return closedRequirementsCount;
	}

	public void setClosedRequirementsCount(BigDecimal closedRequirementsCount) {
		this.closedRequirementsCount = closedRequirementsCount;
	}
	

}