package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.TimesheetActivities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TimeSheetSubmissionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String timeSubFrequency;
	
	private List<TimesheetTaskDTO> timesheetTaskList;
	
	private List<WeekDaysDTO> weekDays;
	
	@Required(desc="timesheetEntries",timesheetListValidate = true)
	private List<TimesheetEntriesDTO> timesheetEntries;
	
	private Map<String,List<TimesheetEntriesDTO>> entriesMap;
	
	private Map<String,List<EmployeeLeaveDTO>> empLeaveMap;
	
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
	
	private boolean weekCommentVisible;
	
	private boolean attachmentRequired;
	
	private String responseStatus;
	
	private String reopenUserId;
	
	private float previousMonthTotalHours;
	
	private float previousMonthBillableHours;
	
	private float previousMonthNonBillableHours;
	
	private float projectToDateTotalHours;
	
	private float projectToDateBillableHours;
	
	private float projectToDateNonBillableHours;
	
	private float currentMonthTotalHours;
	
	private float currentMonthBillableHours;
	
	private float currentMonthNonBillableHours;
	
	private String currentMonthName;
	
	private int incrStartsFrom;

	//Single Day Saving Purpose
    private String timeFrequency;
	
	private Long projectMemberId;
	
    private Long userId;
	
	private String locationCode;
	
	String timesheetFor;
	
	private List<TimesheetActivities> activityList;
	
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

	public List<TimesheetEntriesDTO> getTimesheetEntries() {
		return timesheetEntries;
	}

	public void setTimesheetEntries(List<TimesheetEntriesDTO> timesheetEntries) {
		this.timesheetEntries = timesheetEntries;
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

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public boolean isReopenAcceptRejectButtonVisible() {
		return reopenAcceptRejectButtonVisible;
	}

	public void setReopenAcceptRejectButtonVisible(boolean reopenAcceptRejectButtonVisible) {
		this.reopenAcceptRejectButtonVisible = reopenAcceptRejectButtonVisible;
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

	public boolean isWeekCommentVisible() {
		return weekCommentVisible;
	}

	public void setWeekCommentVisible(boolean weekCommentVisible) {
		this.weekCommentVisible = weekCommentVisible;
	}

	public boolean isAttachmentRequired() {
		return attachmentRequired;
	}

	public void setAttachmentRequired(boolean attachmentRequired) {
		this.attachmentRequired = attachmentRequired;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getReopenUserId() {
		return reopenUserId;
	}

	public void setReopenUserId(String reopenUserId) {
		this.reopenUserId = reopenUserId;
	}

	public Map<String, List<TimesheetEntriesDTO>> getEntriesMap() {
		return entriesMap;
	}

	public void setEntriesMap(Map<String, List<TimesheetEntriesDTO>> entriesMap) {
		this.entriesMap = entriesMap;
	}

	public float getPreviousMonthTotalHours() {
		return previousMonthTotalHours;
	}

	public void setPreviousMonthTotalHours(float previousMonthTotalHours) {
		this.previousMonthTotalHours = previousMonthTotalHours;
	}

	public float getPreviousMonthBillableHours() {
		return previousMonthBillableHours;
	}

	public void setPreviousMonthBillableHours(float previousMonthBillableHours) {
		this.previousMonthBillableHours = previousMonthBillableHours;
	}

	public float getPreviousMonthNonBillableHours() {
		return previousMonthNonBillableHours;
	}

	public void setPreviousMonthNonBillableHours(float previousMonthNonBillableHours) {
		this.previousMonthNonBillableHours = previousMonthNonBillableHours;
	}

	public float getProjectToDateTotalHours() {
		return projectToDateTotalHours;
	}

	public void setProjectToDateTotalHours(float projectToDateTotalHours) {
		this.projectToDateTotalHours = projectToDateTotalHours;
	}

	public float getProjectToDateBillableHours() {
		return projectToDateBillableHours;
	}

	public void setProjectToDateBillableHours(float projectToDateBillableHours) {
		this.projectToDateBillableHours = projectToDateBillableHours;
	}

	public float getProjectToDateNonBillableHours() {
		return projectToDateNonBillableHours;
	}

	public void setProjectToDateNonBillableHours(float projectToDateNonBillableHours) {
		this.projectToDateNonBillableHours = projectToDateNonBillableHours;
	}

	public float getCurrentMonthTotalHours() {
		return currentMonthTotalHours;
	}

	public void setCurrentMonthTotalHours(float currentMonthTotalHours) {
		this.currentMonthTotalHours = currentMonthTotalHours;
	}

	public float getCurrentMonthBillableHours() {
		return currentMonthBillableHours;
	}

	public void setCurrentMonthBillableHours(float currentMonthBillableHours) {
		this.currentMonthBillableHours = currentMonthBillableHours;
	}

	public float getCurrentMonthNonBillableHours() {
		return currentMonthNonBillableHours;
	}

	public void setCurrentMonthNonBillableHours(float currentMonthNonBillableHours) {
		this.currentMonthNonBillableHours = currentMonthNonBillableHours;
	}

	public String getCurrentMonthName() {
		return currentMonthName;
	}

	public void setCurrentMonthName(String currentMonthName) {
		this.currentMonthName = currentMonthName;
	}

	public int getIncrStartsFrom() {
		return incrStartsFrom;
	}

	public void setIncrStartsFrom(int incrStartsFrom) {
		this.incrStartsFrom = incrStartsFrom;
	}

	public String getTimeFrequency() {
		return timeFrequency;
	}

	public void setTimeFrequency(String timeFrequency) {
		this.timeFrequency = timeFrequency;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public String getTimesheetFor() {
		return timesheetFor;
	}

	public void setTimesheetFor(String timesheetFor) {
		this.timesheetFor = timesheetFor;
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

	public List<TimesheetActivities> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<TimesheetActivities> activityList) {
		this.activityList = activityList;
	}

	public Map<String, List<EmployeeLeaveDTO>> getEmpLeaveMap() {
		return empLeaveMap;
	}

	public void setEmpLeaveMap(Map<String, List<EmployeeLeaveDTO>> empLeaveMap) {
		this.empLeaveMap = empLeaveMap;
	}

}
