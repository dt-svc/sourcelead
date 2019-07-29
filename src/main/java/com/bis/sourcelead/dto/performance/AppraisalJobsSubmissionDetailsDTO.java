package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class AppraisalJobsSubmissionDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String requirementName;
	
	private String clientName;
	
	private Integer submittedCandidatesCount;
	
	private Integer offerMadeCount;
	
	private Integer offerAcceptedCount;
	
	private Integer joinedCount;
	
	private Integer clientOfferMadeCount;
	
	private Integer clientOfferAcceptedCount;

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public Integer getSubmittedCandidatesCount() {
		return submittedCandidatesCount;
	}

	public void setSubmittedCandidatesCount(Integer submittedCandidatesCount) {
		this.submittedCandidatesCount = submittedCandidatesCount;
	}

	public Integer getOfferMadeCount() {
		return offerMadeCount;
	}

	public void setOfferMadeCount(Integer offerMadeCount) {
		this.offerMadeCount = offerMadeCount;
	}

	public Integer getOfferAcceptedCount() {
		return offerAcceptedCount;
	}

	public void setOfferAcceptedCount(Integer offerAcceptedCount) {
		this.offerAcceptedCount = offerAcceptedCount;
	}

	public Integer getJoinedCount() {
		return joinedCount;
	}

	public void setJoinedCount(Integer joinedCount) {
		this.joinedCount = joinedCount;
	}

	public Integer getClientOfferMadeCount() {
		return clientOfferMadeCount;
	}

	public void setClientOfferMadeCount(Integer clientOfferMadeCount) {
		this.clientOfferMadeCount = clientOfferMadeCount;
	}

	public Integer getClientOfferAcceptedCount() {
		return clientOfferAcceptedCount;
	}

	public void setClientOfferAcceptedCount(Integer clientOfferAcceptedCount) {
		this.clientOfferAcceptedCount = clientOfferAcceptedCount;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
}
