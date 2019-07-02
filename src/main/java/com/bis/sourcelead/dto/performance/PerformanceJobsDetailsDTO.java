package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class PerformanceJobsDetailsDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer assignedJobsCount;
	
	private Integer submittedCandidatesCount;
	
	private Integer offerMadeCount;
	
	private Integer offerAcceptedCount;
	
	private Integer clientOfferMadeCount;
	
	private Integer clientOfferAcceptedCount;
	
	private Integer joinedCount;
	
	private BigInteger requirementId;
	
	private String submissionStage;
	
	private String fromSubmissionStage;
	
	private String requirementName;
	
	private Map<String,String> assignjobDetailsMap;
	
	private BigInteger submissionId;
	
	List<AppraisalJobsSubmissionDetailsDTO> appraisalJobDetailsList;

	public Integer getAssignedJobsCount() {
		return assignedJobsCount;
	}

	public void setAssignedJobsCount(Integer assignedJobsCount) {
		this.assignedJobsCount = assignedJobsCount;
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

	public BigInteger getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(BigInteger requirementId) {
		this.requirementId = requirementId;
	}

	public String getSubmissionStage() {
		return submissionStage;
	}

	public void setSubmissionStage(String submissionStage) {
		this.submissionStage = submissionStage;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public Map<String, String> getAssignjobDetailsMap() {
		return assignjobDetailsMap;
	}

	public void setAssignjobDetailsMap(Map<String, String> assignjobDetailsMap) {
		this.assignjobDetailsMap = assignjobDetailsMap;
	}

	public BigInteger getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(BigInteger submissionId) {
		this.submissionId = submissionId;
	}

	public List<AppraisalJobsSubmissionDetailsDTO> getAppraisalJobDetailsList() {
		return appraisalJobDetailsList;
	}

	public void setAppraisalJobDetailsList(List<AppraisalJobsSubmissionDetailsDTO> appraisalJobDetailsList) {
		this.appraisalJobDetailsList = appraisalJobDetailsList;
	}

	public String getFromSubmissionStage() {
		return fromSubmissionStage;
	}

	public void setFromSubmissionStage(String fromSubmissionStage) {
		this.fromSubmissionStage = fromSubmissionStage;
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

	
	
}
