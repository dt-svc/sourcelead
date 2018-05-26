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

	public boolean isModifyButtonVisible() {
		return modifyButtonVisible;
	}

	public void setModifyButtonVisible(boolean modifyButtonVisible) {
		this.modifyButtonVisible = modifyButtonVisible;
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

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
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

	public boolean isMonEditable() {
		return monEditable;
	}

	public void setMonEditable(boolean monEditable) {
		this.monEditable = monEditable;
	}

	public boolean isTueEditable() {
		return tueEditable;
	}

	public void setTueEditable(boolean tueEditable) {
		this.tueEditable = tueEditable;
	}

	public boolean isWedEditable() {
		return wedEditable;
	}

	public void setWedEditable(boolean wedEditable) {
		this.wedEditable = wedEditable;
	}

	public boolean isThuEditable() {
		return thuEditable;
	}

	public void setThuEditable(boolean thuEditable) {
		this.thuEditable = thuEditable;
	}

	public boolean isFriEditable() {
		return friEditable;
	}

	public void setFriEditable(boolean friEditable) {
		this.friEditable = friEditable;
	}

	public boolean isSatEditable() {
		return satEditable;
	}

	public void setSatEditable(boolean satEditable) {
		this.satEditable = satEditable;
	}

	public boolean isSunEditable() {
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
	
}
