package com.bis.sourcelead.dto;

import java.util.Date;

public class EmailreminderConfigurationDTO {
	private String id;
	private String scenarioName;
	private Long scenarioId;
	private String firstReminder;
	private String secondReminder;
	private String time;
	private Date createdDate;
	private String createdBy;
	private String status;
	private Long updatedby;
	private String updatedDate;
	private Long reminderdays;
	private String remindertime;
	private String orgLocationId;
	
	
	
	public String getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public String getRemindertime() {
		return remindertime;
	}
	public void setRemindertime(String remindertime) {
		this.remindertime = remindertime;
	}
	public Long getReminderdays() {
		return reminderdays;
	}
	public void setReminderdays(Long reminderdays) {
		this.reminderdays = reminderdays;
	}
	public Long getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(Long updatedby) {
		this.updatedby = updatedby;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getScenarioName() {
		return scenarioName;
	}
	public void setScenarioName(String scenarioName) {
		this.scenarioName = scenarioName;
	}
	public Long getScenarioId() {
		return scenarioId;
	}
	public void setScenarioId(Long scenarioId) {
		this.scenarioId = scenarioId;
	}
	public String getFirstReminder() {
		return firstReminder;
	}
	public void setFirstReminder(String firstReminder) {
		this.firstReminder = firstReminder;
	}
	public String getSecondReminder() {
		return secondReminder;
	}
	public void setSecondReminder(String secondReminder) {
		this.secondReminder = secondReminder;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
