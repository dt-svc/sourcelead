package com.bis.sourcelead.clockInOutDTO;

import java.sql.Time;

public class ClockInOutSubDTO {
	
	private Time clockInTime;
	private Time clockOutTime;
	private Time individualNoOfHours;
	
	private String taskName;
	private String location;
	private String comments;
	private String clockOutDate;
	
	
	public Time getClockInTime() {
		return clockInTime;
	}
	public void setClockInTime(Time clockInTime) {
		this.clockInTime = clockInTime;
	}
	public Time getClockOutTime() {
		return clockOutTime;
	}
	public void setClockOutTime(Time clockOutTime) {
		this.clockOutTime = clockOutTime;
	}
	public Time getIndividualNoOfHours() {
		return individualNoOfHours;
	}
	public void setIndividualNoOfHours(Time individualNoOfHours) {
		this.individualNoOfHours = individualNoOfHours;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getClockOutDate() {
		return clockOutDate;
	}
	public void setClockOutDate(String clockOutDate) {
		this.clockOutDate = clockOutDate;
	}
	
	

}
