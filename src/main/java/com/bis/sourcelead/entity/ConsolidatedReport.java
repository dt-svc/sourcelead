package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Transient;

public class ConsolidatedReport implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
    private Integer submissionCount;
	
	private Integer applicantsCount;	
	
	private Integer interviewsCount;	
	
	private Integer offersCount;
	
	private String requirementReleasedOrNot;
	
	private Integer rejectedCount;
	
	private Integer shortlistedCount;
	
	private Integer submitToClientCount;
	
	private Integer numberOfActiveJobsCount;
	
	private Integer createdCandidatesCount;
	
	private Integer numberOfTasksCompletedCount;

	private String userName;
	
	private String requirementName;
	
	private String numberOfCandidatesCreated;
	
	private Integer activeRequirementsCount;
	
	private String tasksCompletedCount;
	
	private Integer releasedRequirementCount;
	
	private Integer requestSentCount;
	
	private Integer recievedSubmussionsCount;
	
	private String teamMemberName;
	
	@Transient
	private ArrayList<String> approverNames;
	
	
	public Integer getSubmissionCount() {
		return submissionCount;
	}

	public void setSubmissionCount(Integer submissionCount) {
		this.submissionCount = submissionCount;
	}

	public Integer getApplicantsCount() {
		return applicantsCount;
	}

	public void setApplicantsCount(Integer applicantsCount) {
		this.applicantsCount = applicantsCount;
	}

	public Integer getInterviewsCount() {
		return interviewsCount;
	}

	public void setInterviewsCount(Integer interviewsCount) {
		this.interviewsCount = interviewsCount;
	}

	public Integer getOffersCount() {
		return offersCount;
	}

	public void setOffersCount(Integer offersCount) {
		this.offersCount = offersCount;
	}

	public String getRequirementReleasedOrNot() {
		return requirementReleasedOrNot;
	}

	public void setRequirementReleasedOrNot(String requirementReleasedOrNot) {
		this.requirementReleasedOrNot = requirementReleasedOrNot;
	}

	public Integer getRejectedCount() {
		return rejectedCount;
	}

	public void setRejectedCount(Integer rejectedCount) {
		this.rejectedCount = rejectedCount;
	}

	public Integer getShortlistedCount() {
		return shortlistedCount;
	}

	public void setShortlistedCount(Integer shortlistedCount) {
		this.shortlistedCount = shortlistedCount;
	}

	public Integer getSubmitToClientCount() {
		return submitToClientCount;
	}

	public void setSubmitToClientCount(Integer submitToClientCount) {
		this.submitToClientCount = submitToClientCount;
	}

	public Integer getNumberOfActiveJobsCount() {
		return numberOfActiveJobsCount;
	}

	public void setNumberOfActiveJobsCount(Integer numberOfActiveJobsCount) {
		this.numberOfActiveJobsCount = numberOfActiveJobsCount;
	}

	public Integer getCreatedCandidatesCount() {
		return createdCandidatesCount;
	}

	public void setCreatedCandidatesCount(Integer createdCandidatesCount) {
		this.createdCandidatesCount = createdCandidatesCount;
	}

	public Integer getNumberOfTasksCompletedCount() {
		return numberOfTasksCompletedCount;
	}

	public void setNumberOfTasksCompletedCount(Integer numberOfTasksCompletedCount) {
		this.numberOfTasksCompletedCount = numberOfTasksCompletedCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getNumberOfCandidatesCreated() {
		return numberOfCandidatesCreated;
	}

	public void setNumberOfCandidatesCreated(String numberOfCandidatesCreated) {
		this.numberOfCandidatesCreated = numberOfCandidatesCreated;
	}

	
	public Integer getActiveRequirementsCount() {
		return activeRequirementsCount;
	}

	public void setActiveRequirementsCount(Integer activeRequirementsCount) {
		this.activeRequirementsCount = activeRequirementsCount;
	}

	public String getTasksCompletedCount() {
		return tasksCompletedCount;
	}

	public void setTasksCompletedCount(String tasksCompletedCount) {
		this.tasksCompletedCount = tasksCompletedCount;
	}

	public Integer getReleasedRequirementCount() {
		return releasedRequirementCount;
	}

	public void setReleasedRequirementCount(Integer releasedRequirementCount) {
		this.releasedRequirementCount = releasedRequirementCount;
	}

	public ArrayList<String> getApproverNames() {
		return approverNames;
	}

	public void setApproverNames(ArrayList<String> approverNames) {
		this.approverNames = approverNames;
	}

	public Integer getRequestSentCount() {
		return requestSentCount;
	}

	public void setRequestSentCount(Integer requestSentCount) {
		this.requestSentCount = requestSentCount;
	}

	public String getTeamMemberName() {
		return teamMemberName;
	}

	public void setTeamMemberName(String teamMemberName) {
		this.teamMemberName = teamMemberName;
	}

	public Integer getRecievedSubmussionsCount() {
		return recievedSubmussionsCount;
	}

	public void setRecievedSubmussionsCount(Integer recievedSubmussionsCount) {
		this.recievedSubmussionsCount = recievedSubmussionsCount;
	}
   	
 }
