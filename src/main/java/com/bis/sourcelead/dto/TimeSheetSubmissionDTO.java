package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TimeSheetSubmissionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String timeSubFrequency;
	
	private List<TimesheetTaskDTO> timesheetTaskList;
	
	private List<WeekDaysDTO> weekDays;
	
	private List<TimesheetEntriesDTO> timesheetEntries;
	
	private List<Long> removedDocumentIds;
	
	private List<FileInfo> fileInfo;
	
	private String billableHours;
	
	private String nonBillableHours;
	
	private String totalHours;
	
	private String weekComment;
	
	private String timesheetStatus;
	
	private int maxRowCount;
	
	private boolean reopenAcceptRejectButtonVisible;
	
	private String rejectedReason;
	
	private String reopenReason;
	
	private String reopenRejectedReason;
	
	//Button Visibility
	private boolean modifyButtonVisible;
	
	private boolean reopenButtonVisible;
	
	private boolean reopenRequestSent;
	
	private boolean submitButtonVisible;
	
	private boolean saveButtonVisible;
	
	private boolean cancelButtonVisible;
	
	private boolean weekCommentRequired;
	
	private boolean attachmentRequired;
	
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

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public boolean isModifyButtonVisible() {
		return modifyButtonVisible;
	}

	public void setModifyButtonVisible(boolean modifyButtonVisible) {
		this.modifyButtonVisible = modifyButtonVisible;
	}

	public boolean isReopenButtonVisible() {
		return reopenButtonVisible;
	}

	public void setReopenButtonVisible(boolean reopenButtonVisible) {
		this.reopenButtonVisible = reopenButtonVisible;
	}

	public boolean isReopenAcceptRejectButtonVisible() {
		return reopenAcceptRejectButtonVisible;
	}

	public void setReopenAcceptRejectButtonVisible(boolean reopenAcceptRejectButtonVisible) {
		this.reopenAcceptRejectButtonVisible = reopenAcceptRejectButtonVisible;
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

	public List<FileInfo> getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(List<FileInfo> fileInfo) {
		this.fileInfo = fileInfo;
	}

	public List<TimesheetTaskDTO> getTimesheetTaskList() {
		return timesheetTaskList;
	}

	public void setTimesheetTaskList(List<TimesheetTaskDTO> timesheetTaskList) {
		this.timesheetTaskList = timesheetTaskList;
	}
	public List<WeekDaysDTO> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(List<WeekDaysDTO> weekDays) {
		this.weekDays = weekDays;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<Long> getRemovedDocumentIds() {
		return removedDocumentIds;
	}

	public void setRemovedDocumentIds(List<Long> removedDocumentIds) {
		this.removedDocumentIds = removedDocumentIds;
	}

	public boolean isReopenRequestSent() {
		return reopenRequestSent;
	}

	public void setReopenRequestSent(boolean reopenRequestSent) {
		this.reopenRequestSent = reopenRequestSent;
	}

	public boolean isSubmitButtonVisible() {
		return submitButtonVisible;
	}

	public void setSubmitButtonVisible(boolean submitButtonVisible) {
		this.submitButtonVisible = submitButtonVisible;
	}

	public boolean isSaveButtonVisible() {
		return saveButtonVisible;
	}

	public void setSaveButtonVisible(boolean saveButtonVisible) {
		this.saveButtonVisible = saveButtonVisible;
	}

	public boolean isCancelButtonVisible() {
		return cancelButtonVisible;
	}

	public void setCancelButtonVisible(boolean cancelButtonVisible) {
		this.cancelButtonVisible = cancelButtonVisible;
	}

	public boolean isWeekCommentRequired() {
		return weekCommentRequired;
	}

	public void setWeekCommentRequired(boolean weekCommentRequired) {
		this.weekCommentRequired = weekCommentRequired;
	}

	public boolean isAttachmentRequired() {
		return attachmentRequired;
	}

	public void setAttachmentRequired(boolean attachmentRequired) {
		this.attachmentRequired = attachmentRequired;
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	
}
