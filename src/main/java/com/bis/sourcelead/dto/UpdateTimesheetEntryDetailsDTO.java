package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UpdateTimesheetEntryDetailsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long submissionId;
	
	private List<Long> removedDocumentIds;
	
	private List<FileInfo> fileInfo;
	
	private String billableHours;
	
	private String nonBillableHours;
	
	private String totalHours;
	
	private String weekComment;
	
	private String timesheetStatus;
	
	private String rejectedReason;
	
	private String reopenReason;
	
	private String reopenRejectedReason;
	
	private Long projectMemberId;
	
    private Long userId;
	
	private String locationCode;
	
	String timesheetFor;
	
	private String timeSubFrequency;
	
	private String timeFrequency;
	 
	private String currentTimeFrequency;
	 
	private String currentTimeSubFrequency;

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public List<Long> getRemovedDocumentIds() {
		return removedDocumentIds;
	}

	public void setRemovedDocumentIds(List<Long> removedDocumentIds) {
		this.removedDocumentIds = removedDocumentIds;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public String getBillableHours() {
		return billableHours;
	}

	public void setBillableHours(String billableHours) {
		this.billableHours = billableHours;
	}

	public String getNonBillableHours() {
		return nonBillableHours;
	}

	public void setNonBillableHours(String nonBillableHours) {
		this.nonBillableHours = nonBillableHours;
	}

	public String getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(String totalHours) {
		this.totalHours = totalHours;
	}

	public String getWeekComment() {
		return weekComment;
	}

	public void setWeekComment(String weekComment) {
		this.weekComment = weekComment;
	}

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public String getReopenReason() {
		return reopenReason;
	}

	public void setReopenReason(String reopenReason) {
		this.reopenReason = reopenReason;
	}

	public String getReopenRejectedReason() {
		return reopenRejectedReason;
	}

	public void setReopenRejectedReason(String reopenRejectedReason) {
		this.reopenRejectedReason = reopenRejectedReason;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getTimesheetFor() {
		return timesheetFor;
	}

	public void setTimesheetFor(String timesheetFor) {
		this.timesheetFor = timesheetFor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTimeSubFrequency() {
		return timeSubFrequency;
	}

	public void setTimeSubFrequency(String timeSubFrequency) {
		this.timeSubFrequency = timeSubFrequency;
	}

	public String getTimeFrequency() {
		return timeFrequency;
	}

	public void setTimeFrequency(String timeFrequency) {
		this.timeFrequency = timeFrequency;
	}

	public String getCurrentTimeFrequency() {
		return currentTimeFrequency;
	}

	public void setCurrentTimeFrequency(String currentTimeFrequency) {
		this.currentTimeFrequency = currentTimeFrequency;
	}

	public String getCurrentTimeSubFrequency() {
		return currentTimeSubFrequency;
	}

	public void setCurrentTimeSubFrequency(String currentTimeSubFrequency) {
		this.currentTimeSubFrequency = currentTimeSubFrequency;
	}
	 
	
	
	
}
