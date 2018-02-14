package com.bis.sourcelead.dto;

import java.util.Date;

public class ScheduleMaintenanceDTO {

	private String scheduleDate;
	private String scheduleMessage;
	private String startTime;
	private String endTime;
	private String mainMessage;
	public String getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getScheduleMessage() {
		return scheduleMessage;
	}
	public void setScheduleMessage(String scheduleMessage) {
		this.scheduleMessage = scheduleMessage;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getMainMessage() {
		return mainMessage;
	}
	public void setMainMessage(String mainMessage) {
		this.mainMessage = mainMessage;
	}
}
