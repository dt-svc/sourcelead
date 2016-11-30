package com.bis.sourcelead.entity;

import java.io.Serializable;
import java.util.Collection;

public class TimeSheetSubmissionCount implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	private Float savedTimesheetSubmissionCount;
	
	private Float waitingForApprovalTimesheetSubmissionCount;	
	
	private Float approvedTimesheetSubmissionCount;	
	
	private Float rejectedTimesheetSubmissionCount;
	
	Collection<SubmissionApproverHierarchy>  savedTimesheetSubmissionsList;
	
	Collection<SubmissionApproverHierarchy>  waitingForApprovalTimesheetSubmissionList;
	
	Collection<SubmissionApproverHierarchy>  approvedTimesheetSubmissionList;
	
	Collection<SubmissionApproverHierarchy>  rejectedTimesheetSubmissionList;
	
	private  String draftTimesheetsSubmissionsHoursCount ;
	
	private  String  submittedTimesheetsSubmissionsHoursCount;
	
	private  String  approvalTimesheetsSubmissionsHoursCount;
	
	private  String  rejectedTimesheetsSubmissionsHoursCount;
	
	private Integer newRequirementCount;
	
	private Integer	draftRequirementCount;
	
	private Integer submittedRequirementCount;
	
	private Integer openRequirementCount;
	
	private Integer onHoldRequirementCount;
	
	private Integer cancelRequirementCount;
	
	
	
	
	
	
	 

	public Float getSavedTimesheetSubmissionCount() {
		return savedTimesheetSubmissionCount;
	}

	public void setSavedTimesheetSubmissionCount(Float savedTimesheetSubmissionCount) {
		this.savedTimesheetSubmissionCount = savedTimesheetSubmissionCount;
	}

	public Float getWaitingForApprovalTimesheetSubmissionCount() {
		return waitingForApprovalTimesheetSubmissionCount;
	}

	public void setWaitingForApprovalTimesheetSubmissionCount(Float waitingForApprovalTimesheetSubmissionCount) {
		this.waitingForApprovalTimesheetSubmissionCount = waitingForApprovalTimesheetSubmissionCount;
	}

	public Float getApprovedTimesheetSubmissionCount() {
		return approvedTimesheetSubmissionCount;
	}

	public void setApprovedTimesheetSubmissionCount(Float approvedTimesheetSubmissionCount) {
		this.approvedTimesheetSubmissionCount = approvedTimesheetSubmissionCount;
	}

	public Float getRejectedTimesheetSubmissionCount() {
		return rejectedTimesheetSubmissionCount;
	}

	public void setRejectedTimesheetSubmissionCount(Float rejectedTimesheetSubmissionCount) {
		this.rejectedTimesheetSubmissionCount = rejectedTimesheetSubmissionCount;
	}

	public Collection<SubmissionApproverHierarchy> getSavedTimesheetSubmissionsList() {
		return savedTimesheetSubmissionsList;
	}

	public void setSavedTimesheetSubmissionsList(Collection<SubmissionApproverHierarchy> savedTimesheetSubmissionsList) {
		this.savedTimesheetSubmissionsList = savedTimesheetSubmissionsList;
	}

	public Collection<SubmissionApproverHierarchy> getWaitingForApprovalTimesheetSubmissionList() {
		return waitingForApprovalTimesheetSubmissionList;
	}

	public void setWaitingForApprovalTimesheetSubmissionList(
			Collection<SubmissionApproverHierarchy> waitingForApprovalTimesheetSubmissionList) {
		this.waitingForApprovalTimesheetSubmissionList = waitingForApprovalTimesheetSubmissionList;
	}

	public Collection<SubmissionApproverHierarchy> getApprovedTimesheetSubmissionList() {
		return approvedTimesheetSubmissionList;
	}

	public void setApprovedTimesheetSubmissionList(
			Collection<SubmissionApproverHierarchy> approvedTimesheetSubmissionList) {
		this.approvedTimesheetSubmissionList = approvedTimesheetSubmissionList;
	}

	public Collection<SubmissionApproverHierarchy> getRejectedTimesheetSubmissionList() {
		return rejectedTimesheetSubmissionList;
	}

	public void setRejectedTimesheetSubmissionList(
			Collection<SubmissionApproverHierarchy> rejectedTimesheetSubmissionList) {
		this.rejectedTimesheetSubmissionList = rejectedTimesheetSubmissionList;
	}

	public String getDraftTimesheetsSubmissionsHoursCount() {
		return draftTimesheetsSubmissionsHoursCount;
	}

	public void setDraftTimesheetsSubmissionsHoursCount(String draftTimesheetsSubmissionsHoursCount) {
		this.draftTimesheetsSubmissionsHoursCount = draftTimesheetsSubmissionsHoursCount;
	}

	public String getSubmittedTimesheetsSubmissionsHoursCount() {
		return submittedTimesheetsSubmissionsHoursCount;
	}

	public void setSubmittedTimesheetsSubmissionsHoursCount(String submittedTimesheetsSubmissionsHoursCount) {
		this.submittedTimesheetsSubmissionsHoursCount = submittedTimesheetsSubmissionsHoursCount;
	}

	public String getApprovalTimesheetsSubmissionsHoursCount() {
		return approvalTimesheetsSubmissionsHoursCount;
	}

	public void setApprovalTimesheetsSubmissionsHoursCount(String approvalTimesheetsSubmissionsHoursCount) {
		this.approvalTimesheetsSubmissionsHoursCount = approvalTimesheetsSubmissionsHoursCount;
	}

	public String getRejectedTimesheetsSubmissionsHoursCount() {
		return rejectedTimesheetsSubmissionsHoursCount;
	}

	public void setRejectedTimesheetsSubmissionsHoursCount(String rejectedTimesheetsSubmissionsHoursCount) {
		this.rejectedTimesheetsSubmissionsHoursCount = rejectedTimesheetsSubmissionsHoursCount;
	}

	public Integer getNewRequirementCount() {
		return newRequirementCount;
	}

	public void setNewRequirementCount(Integer newRequirementCount) {
		this.newRequirementCount = newRequirementCount;
	}

	public Integer getDraftRequirementCount() {
		return draftRequirementCount;
	}

	public void setDraftRequirementCount(Integer draftRequirementCount) {
		this.draftRequirementCount = draftRequirementCount;
	}

	public Integer getSubmittedRequirementCount() {
		return submittedRequirementCount;
	}

	public void setSubmittedRequirementCount(Integer submittedRequirementCount) {
		this.submittedRequirementCount = submittedRequirementCount;
	}

	public Integer getOpenRequirementCount() {
		return openRequirementCount;
	}

	public void setOpenRequirementCount(Integer openRequirementCount) {
		this.openRequirementCount = openRequirementCount;
	}

	public Integer getOnHoldRequirementCount() {
		return onHoldRequirementCount;
	}

	public void setOnHoldRequirementCount(Integer onHoldRequirementCount) {
		this.onHoldRequirementCount = onHoldRequirementCount;
	}

	public Integer getCancelRequirementCount() {
		return cancelRequirementCount;
	}

	public void setCancelRequirementCount(Integer cancelRequirementCount) {
		this.cancelRequirementCount = cancelRequirementCount;
	}
	
	
}
