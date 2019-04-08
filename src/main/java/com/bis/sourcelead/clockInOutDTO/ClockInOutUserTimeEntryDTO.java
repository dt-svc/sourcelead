package com.bis.sourcelead.clockInOutDTO;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class ClockInOutUserTimeEntryDTO {

	private Time clockInTime;
	
	private Time totalTime;
	
	private Time clockOutTime;
	
	private String comments;
	
	private String projectTaskName;
	
	private Date clockInDate;
	
	private Date clockOutDate;
	
	private String clockInDateStr;
	
	private String clockOutDateStr;
	
	private String frequency;
	
	private List<ClockInOutSubDTO> subList;

	public Time getClockInTime() {
		return clockInTime;
	}

	public void setClockInTime(Time clockInTime) {
		this.clockInTime = clockInTime;
	}

	public Time getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Time totalTime) {
		this.totalTime = totalTime;
	}

	public Time getClockOutTime() {
		return clockOutTime;
	}

	public void setClockOutTime(Time clockOutTime) {
		this.clockOutTime = clockOutTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getProjectTaskName() {
		return projectTaskName;
	}

	public void setProjectTaskName(String projectTaskName) {
		this.projectTaskName = projectTaskName;
	}

	public Date getClockInDate() {
		return clockInDate;
	}

	public void setClockInDate(Date clockInDate) {
		this.clockInDate = clockInDate;
	}

	public Date getClockOutDate() {
		return clockOutDate;
	}

	public void setClockOutDate(Date clockOutDate) {
		this.clockOutDate = clockOutDate;
	}

	public String getClockInDateStr() {
		return clockInDateStr;
	}

	public void setClockInDateStr(String clockInDateStr) {
		this.clockInDateStr = clockInDateStr;
	}

	public String getClockOutDateStr() {
		return clockOutDateStr;
	}

	public void setClockOutDateStr(String clockOutDateStr) {
		this.clockOutDateStr = clockOutDateStr;
	}

	public List<ClockInOutSubDTO> getSubList() {
		return subList;
	}

	public void setSubList(List<ClockInOutSubDTO> subList) {
		this.subList = subList;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
}
