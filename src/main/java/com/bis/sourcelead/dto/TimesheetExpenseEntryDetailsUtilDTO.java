package com.bis.sourcelead.dto;

import java.io.Serializable;

public class TimesheetExpenseEntryDetailsUtilDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	
	private String locationCode;
	
	private String expenseFrom;
	
	private String expenseId;
	
	private String timeSheetsFor;
	
	private String expensesFor;
	
	private Long projectMemberId;
	
	private Long projectId;
	
	private Long masterCandidateId;
	
	private String currentTimeFrequency;
	
	private String currentTimeSubFrequency;
	
	private String timesheetStatus;

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

	public String getExpenseFrom()
	{
		return expenseFrom;
	}

	public void setExpenseFrom(String expenseFrom)
	{
		this.expenseFrom = expenseFrom;
	}

	public String getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(String expenseId) {
		this.expenseId = expenseId;
	}

	public String getTimeSheetsFor() {
		return timeSheetsFor;
	}

	public void setTimeSheetsFor(String timeSheetsFor) {
		this.timeSheetsFor = timeSheetsFor;
	}

	public String getExpensesFor() {
		return expensesFor;
	}

	public void setExpensesFor(String expensesFor) {
		this.expensesFor = expensesFor;
	}

	public Long getProjectMemberId() {
		return projectMemberId;
	}

	public void setProjectMemberId(Long projectMemberId) {
		this.projectMemberId = projectMemberId;
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

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getMasterCandidateId() {
		return masterCandidateId;
	}

	public void setMasterCandidateId(Long masterCandidateId) {
		this.masterCandidateId = masterCandidateId;
	}

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

 

}
