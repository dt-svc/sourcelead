package com.bis.sourcelead.entity;

import java.util.Collection;

public class TimeSheetSubmissionCount {
	
	private Integer savedTimesheetSubmissionCount;
	
	private Integer waitingForApprovalTimesheetSubmissionCount;	
	
	private Integer approvedTimesheetSubmissionCount;	
	
	private Integer rejectedTimesheetSubmissionCount;
	
	Collection<SubmissionApproverHierarchy>  savedTimesheetSubmissionsList;
	
	Collection<SubmissionApproverHierarchy>  waitingForApprovalTimesheetSubmissionList;
	
	Collection<SubmissionApproverHierarchy>  approvedTimesheetSubmissionList;
	
	Collection<SubmissionApproverHierarchy>  rejectedTimesheetSubmissionList;
	
	private  String draftTimesheetsSubmissionsHoursCount ;
	
	private  String  submittedTimesheetsSubmissionsHoursCount;
	
	private  String  approvalTimesheetsSubmissionsHoursCount;
	
	private  String  rejectedTimesheetsSubmissionsHoursCount;
	

	public Integer getSavedTimesheetSubmissionCount() {
		return savedTimesheetSubmissionCount;
	}

	public void setSavedTimesheetSubmissionCount(Integer savedTimesheetSubmissionCount) {
		this.savedTimesheetSubmissionCount = savedTimesheetSubmissionCount;
	}

	public Integer getWaitingForApprovalTimesheetSubmissionCount() {
		return waitingForApprovalTimesheetSubmissionCount;
	}

	public void setWaitingForApprovalTimesheetSubmissionCount(Integer waitingForApprovalTimesheetSubmissionCount) {
		this.waitingForApprovalTimesheetSubmissionCount = waitingForApprovalTimesheetSubmissionCount;
	}

	public Integer getApprovedTimesheetSubmissionCount() {
		return approvedTimesheetSubmissionCount;
	}

	public void setApprovedTimesheetSubmissionCount(Integer approvedTimesheetSubmissionCount) {
		this.approvedTimesheetSubmissionCount = approvedTimesheetSubmissionCount;
	}

	public Integer getRejectedTimesheetSubmissionCount() {
		return rejectedTimesheetSubmissionCount;
	}

	public void setRejectedTimesheetSubmissionCount(Integer rejectedTimesheetSubmissionCount) {
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

	
	
}
