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
	
	private Long projectMembersId;
	
	private Long projectsId;
	
	private Long masterCandidatesId;
	
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

	

	public Long getProjectMembersId() {
		return projectMembersId;
	}

	public void setProjectMembersId(Long projectMembersId) {
		this.projectMembersId = projectMembersId;
	}

	public Long getProjectsId() {
		return projectsId;
	}

	public void setProjectsId(Long projectsId) {
		this.projectsId = projectsId;
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

	public Long getMasterCandidatesId() {
		return masterCandidatesId;
	}

	public void setMasterCandidatesId(Long masterCandidatesId) {
		this.masterCandidatesId = masterCandidatesId;
	}

	public String getTimesheetStatus() {
		return timesheetStatus;
	}

	public void setTimesheetStatus(String timesheetStatus) {
		this.timesheetStatus = timesheetStatus;
	}

 

}
