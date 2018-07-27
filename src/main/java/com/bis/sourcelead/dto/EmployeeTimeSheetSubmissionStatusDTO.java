package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EmployeeTimeSheetSubmissionStatusDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private Long projectMemberId;
	
	private Long projectId;
	
	private String timesheetStatus;
	
	private boolean reopenButtonVisible;
	
	private boolean reopenRequestSent;
	
	private boolean cancelButtonVisible;
	
	private boolean weekCommentVisible;
	
	private boolean modifyButtonVisible;
	
    private boolean submitButtonVisible;
	
	private boolean saveButtonVisible;
	
	private boolean monEditable;
	
	private boolean tueEditable;
	
	private boolean wedEditable;
	
	private boolean thuEditable;
	
	private boolean friEditable;
	
	private boolean satEditable;
	
	private boolean sunEditable;
	
	private boolean ticketNumberRequired;
	
	private boolean timesheetDocumentRequired;
	
	private boolean taskCommentsRequired;
	
	private String rejectedReason;
	
	private Map<String,Boolean> editableMap;
	
	

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public boolean getModifyButtonVisible() {
		return modifyButtonVisible;
	}

	public void setModifyButtonVisible(boolean modifyButtonVisible) {
		this.modifyButtonVisible = modifyButtonVisible;
	}

	public boolean getSubmitButtonVisible() {
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

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

	public boolean getReopenButtonVisible() {
		return reopenButtonVisible;
	}

	public void setReopenButtonVisible(boolean reopenButtonVisible) {
		this.reopenButtonVisible = reopenButtonVisible;
	}

	public boolean getReopenRequestSent() {
		return reopenRequestSent;
	}

	public void setReopenRequestSent(boolean reopenRequestSent) {
		this.reopenRequestSent = reopenRequestSent;
	}

	public boolean getCancelButtonVisible() {
		return cancelButtonVisible;
	}

	public void setCancelButtonVisible(boolean cancelButtonVisible) {
		this.cancelButtonVisible = cancelButtonVisible;
	}

	public boolean getWeekCommentVisible() {
		return weekCommentVisible;
	}

	public void setWeekCommentVisible(boolean weekCommentVisible) {
		this.weekCommentVisible = weekCommentVisible;
	}

	public boolean getMonEditable() {
		return monEditable;
	}

	public void setMonEditable(boolean monEditable) {
		this.monEditable = monEditable;
	}

	public boolean getTueEditable() {
		return tueEditable;
	}

	public void setTueEditable(boolean tueEditable) {
		this.tueEditable = tueEditable;
	}

	public boolean getWedEditable() {
		return wedEditable;
	}

	public void setWedEditable(boolean wedEditable) {
		this.wedEditable = wedEditable;
	}

	public boolean getThuEditable() {
		return thuEditable;
	}

	public void setThuEditable(boolean thuEditable) {
		this.thuEditable = thuEditable;
	}

	public boolean getFriEditable() {
		return friEditable;
	}

	public void setFriEditable(boolean friEditable) {
		this.friEditable = friEditable;
	}

	public boolean getSatEditable() {
		return satEditable;
	}

	public void setSatEditable(boolean satEditable) {
		this.satEditable = satEditable;
	}

	public boolean getSunEditable() {
		return sunEditable;
	}

	public void setSunEditable(boolean sunEditable) {
		this.sunEditable = sunEditable;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public Map<String, Boolean> getEditableMap() {
		return editableMap;
	}

	public void setEditableMap(Map<String, Boolean> editableMap) {
		this.editableMap = editableMap;
	}

	public boolean isTicketNumberRequired() {
		return ticketNumberRequired;
	}

	public void setTicketNumberRequired(boolean ticketNumberRequired) {
		this.ticketNumberRequired = ticketNumberRequired;
	}

	public boolean isTimesheetDocumentRequired() {
		return timesheetDocumentRequired;
	}

	public void setTimesheetDocumentRequired(boolean timesheetDocumentRequired) {
		this.timesheetDocumentRequired = timesheetDocumentRequired;
	}

	public boolean isTaskCommentsRequired() {
		return taskCommentsRequired;
	}

	public void setTaskCommentsRequired(boolean taskCommentsRequired) {
		this.taskCommentsRequired = taskCommentsRequired;
	}
	
}
