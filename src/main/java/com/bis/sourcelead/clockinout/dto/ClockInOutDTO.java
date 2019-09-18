package com.bis.sourcelead.clockinout.dto;

import java.sql.Time;

public class ClockInOutDTO {
	
	private Long clockInOutId;
	private Long breakId;
	private Long jobs;
	private Long projects;
	private Long projectTasks;
	private Long generalTasks;
	private String comments;
	private String type;
	private String totalTime;
	private String totalBreakTime;
	
	private String previousClockInStr;
	private String previousClockOutStr;
	
	private Time previousClockInTime;
	private Time previousClockOutTime;
	
	private String ipAddress;
	
	public Long getClockInOutId() {
		return clockInOutId;
	}
	public void setClockInOutId(Long clockInOutId) {
		this.clockInOutId = clockInOutId;
	}
	public Long getJobs() {
		return jobs;
	}
	public void setJobs(Long jobs) {
		this.jobs = jobs;
	}
	public Long getProjects() {
		return projects;
	}
	public void setProjects(Long projects) {
		this.projects = projects;
	}
	public Long getProjectTasks() {
		return projectTasks;
	}
	public void setProjectTasks(Long projectTasks) {
		this.projectTasks = projectTasks;
	}
	public Long getGeneralTasks() {
		return generalTasks;
	}
	public void setGeneralTasks(Long generalTasks) {
		this.generalTasks = generalTasks;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getBreakId() {
		return breakId;
	}
	public void setBreakId(Long breakId) {
		this.breakId = breakId;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	public String getTotalBreakTime() {
		return totalBreakTime;
	}
	public void setTotalBreakTime(String totalBreakTime) {
		this.totalBreakTime = totalBreakTime;
	}
	public String getPreviousClockInStr() {
		return previousClockInStr;
	}
	public void setPreviousClockInStr(String previousClockInStr) {
		this.previousClockInStr = previousClockInStr;
	}
	public String getPreviousClockOutStr() {
		return previousClockOutStr;
	}
	public void setPreviousClockOutStr(String previousClockOutStr) {
		this.previousClockOutStr = previousClockOutStr;
	}
	public Time getPreviousClockInTime() {
		return previousClockInTime;
	}
	public void setPreviousClockInTime(Time previousClockInTime) {
		this.previousClockInTime = previousClockInTime;
	}
	public Time getPreviousClockOutTime() {
		return previousClockOutTime;
	}
	public void setPreviousClockOutTime(Time previousClockOutTime) {
		this.previousClockOutTime = previousClockOutTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
}
