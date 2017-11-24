package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


public class TimesheetApprovalDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String frequencyMonth;
	private String timesheetFrequency;
	private String weekFrequency;
	private String client;
	private String stage;
	
	private Long userId;
	private String locationCode;
	
	private String frequencyStartDate;
	private String frequencyEndDate;
	
	private LinkedHashSet<String> frequencyMonthList;
	private LinkedHashSet<String> timesheetFrequencyList;
	private Map<String,String> weekFrequencyList;
	private Map<Long,String> clientList;
	private LinkedHashSet<String> stagesList;
	private List<String> dataTableWeekDateList;
	
	private LinkedHashMap<Date,Date> displayWeekList;
	
	private List<TimesheetApprovalSubmissionDetailsDTO> timesheetSubmission;
	
	public String getWeekFrequency() {
		return weekFrequency;
	}
	public void setWeekFrequency(String weekFrequency) {
		this.weekFrequency = weekFrequency;
	}
	public String getFrequencyMonth() {
		return frequencyMonth;
	}
	public void setFrequencyMonth(String frequencyMonth) {
		this.frequencyMonth = frequencyMonth;
	}
	public String getTimesheetFrequency() {
		return timesheetFrequency;
	}
	public void setTimesheetFrequency(String timesheetFrequency) {
		this.timesheetFrequency = timesheetFrequency;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public LinkedHashSet<String> getFrequencyMonthList() {
		return frequencyMonthList;
	}
	public void setFrequencyMonthList(LinkedHashSet<String> frequencyMonthList) {
		this.frequencyMonthList = frequencyMonthList;
	}
	public LinkedHashSet<String> getTimesheetFrequencyList() {
		return timesheetFrequencyList;
	}
	public void setTimesheetFrequencyList(LinkedHashSet<String> timesheetFrequencyList) {
		this.timesheetFrequencyList = timesheetFrequencyList;
	}
	public Map<String, String> getWeekFrequencyList() {
		return weekFrequencyList;
	}
	public void setWeekFrequencyList(Map<String, String> weekFrequencyList) {
		this.weekFrequencyList = weekFrequencyList;
	}
	public Map<Long, String> getClientList() {
		return clientList;
	}
	public void setClientList(Map<Long, String> clientList) {
		this.clientList = clientList;
	}
	public LinkedHashSet<String> getStagesList() {
		return stagesList;
	}
	public void setStagesList(LinkedHashSet<String> stagesList) {
		this.stagesList = stagesList;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFrequencyStartDate() {
		return frequencyStartDate;
	}
	public void setFrequencyStartDate(String frequencyStartDate) {
		this.frequencyStartDate = frequencyStartDate;
	}
	public String getFrequencyEndDate() {
		return frequencyEndDate;
	}
	public void setFrequencyEndDate(String frequencyEndDate) {
		this.frequencyEndDate = frequencyEndDate;
	}
	public List<String> getDataTableWeekDateList() {
		return dataTableWeekDateList;
	}
	public void setDataTableWeekDateList(List<String> dataTableWeekDateList) {
		this.dataTableWeekDateList = dataTableWeekDateList;
	}
	public List<TimesheetApprovalSubmissionDetailsDTO> getTimesheetSubmission() {
		return timesheetSubmission;
	}
	public void setTimesheetSubmission(List<TimesheetApprovalSubmissionDetailsDTO> timesheetSubmission) {
		this.timesheetSubmission = timesheetSubmission;
	}
	public LinkedHashMap<Date, Date> getDisplayWeekList() {
		return displayWeekList;
	}
	public void setDisplayWeekList(LinkedHashMap<Date, Date> displayWeekList) {
		this.displayWeekList = displayWeekList;
	}
	
	
}
