package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TimeSheetSubmissionDayEntryDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String timeSubFrequency;
	
	private List<TimesheetEntriesDTO> timesheetEntries;
	
	//Single Day Saving Purpose
    private String timeFrequency;
	
	private Long projectMemberId;
	
    private Long userId;
	
	private String locationCode;
	
	private String timesheetFor;

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
	
	
	
	}
