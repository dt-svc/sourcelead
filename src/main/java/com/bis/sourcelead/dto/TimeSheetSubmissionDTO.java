package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TimeSheetSubmissionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String timeSubFrequency;
	
	private List<TimesheetTaskDTO> timesheetTaskList;
	
	private Map<String,Map<String,Boolean>> weekDays;
	
	private List<TimesheetEntriesDTO> timesheetEntries;
	
	private List<FileInfo> fileInfo;
	
	private String billableHours;
	
	private String nonBillableHours;
	
	private String totalHours;
	
	private String weekComment;
	
	private String timesheetStatus;
	
	private boolean isModifyButtonVisible;
	
	private boolean isReopenButtonVisible;
	
	private boolean isReopenAcceptRejectButtonVisible;
	
	private String rejectedReason;
	
	private String reopenReason;
	
	private String reopenRejectedReason;
	
	private boolean isReopenRequestSent;
	
	private boolean isTimesheetDocRequired;
	
	private boolean isSubmitButtonVisible;
	
	private boolean isSaveButtonVisible;
	
	private boolean isCancelButtonVisible;
	
	private boolean isWeekCommentRequired;
	
	private boolean isAttachmentRequired;
	
	private String responseStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTimeSubFrequency() {
		return timeSubFrequency;
	}

	public void setTimeSubFrequency(String timeSubFrequency) {
		this.timeSubFrequency = timeSubFrequency;
	}

	public List<TimesheetEntriesDTO> getTimesheetEntries() {
		return timesheetEntries;
	}

	public void setTimesheetEntries(List<TimesheetEntriesDTO> timesheetEntries) {
		this.timesheetEntries = timesheetEntries;
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

	public boolean isModifyButtonVisible() {
		return isModifyButtonVisible;
	}

	public void setModifyButtonVisible(boolean isModifyButtonVisible) {
		this.isModifyButtonVisible = isModifyButtonVisible;
	}

	public boolean isReopenButtonVisible() {
		return isReopenButtonVisible;
	}

	public void setReopenButtonVisible(boolean isReopenButtonVisible) {
		this.isReopenButtonVisible = isReopenButtonVisible;
	}

	public boolean isReopenAcceptRejectButtonVisible() {
		return isReopenAcceptRejectButtonVisible;
	}

	public void setReopenAcceptRejectButtonVisible(boolean isReopenAcceptRejectButtonVisible) {
		this.isReopenAcceptRejectButtonVisible = isReopenAcceptRejectButtonVisible;
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

	public boolean isReopenRequestSent() {
		return isReopenRequestSent;
	}

	public void setReopenRequestSent(boolean isReopenRequestSent) {
		this.isReopenRequestSent = isReopenRequestSent;
	}

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public boolean isTimesheetDocRequired() {
		return isTimesheetDocRequired;
	}

	public void setTimesheetDocRequired(boolean isTimesheetDocRequired) {
		this.isTimesheetDocRequired = isTimesheetDocRequired;
	}

	public List<TimesheetTaskDTO> getTimesheetTaskList() {
		return timesheetTaskList;
	}

	public void setTimesheetTaskList(List<TimesheetTaskDTO> timesheetTaskList) {
		this.timesheetTaskList = timesheetTaskList;
	}

	public Map<String, Map<String, Boolean>> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(Map<String, Map<String, Boolean>> weekDays) {
		this.weekDays = weekDays;
	}

	public boolean isSubmitButtonVisible() {
		return isSubmitButtonVisible;
	}

	public void setSubmitButtonVisible(boolean isSubmitButtonVisible) {
		this.isSubmitButtonVisible = isSubmitButtonVisible;
	}

	public boolean isSaveButtonVisible() {
		return isSaveButtonVisible;
	}

	public void setSaveButtonVisible(boolean isSaveButtonVisible) {
		this.isSaveButtonVisible = isSaveButtonVisible;
	}

	public boolean isCancelButtonVisible() {
		return isCancelButtonVisible;
	}

	public void setCancelButtonVisible(boolean isCancelButtonVisible) {
		this.isCancelButtonVisible = isCancelButtonVisible;
	}

	public boolean isWeekCommentRequired() {
		return isWeekCommentRequired;
	}

	public void setWeekCommentRequired(boolean isWeekCommentRequired) {
		this.isWeekCommentRequired = isWeekCommentRequired;
	}

	public boolean isAttachmentRequired() {
		return isAttachmentRequired;
	}

	public void setAttachmentRequired(boolean isAttachmentRequired) {
		this.isAttachmentRequired = isAttachmentRequired;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	
	

	
}
