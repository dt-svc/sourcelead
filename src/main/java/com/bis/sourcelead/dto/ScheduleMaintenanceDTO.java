package com.bis.sourcelead.dto;

import java.util.Date;

public class ScheduleMaintenanceDTO {

	private Date scheduleDate;
	private String scheduleMessage;
	public Date getScheduleDate() {
		return scheduleDate;
	}
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	public String getScheduleMessage() {
		return scheduleMessage;
	}
	public void setScheduleMessage(String scheduleMessage) {
		this.scheduleMessage = scheduleMessage;
	}
	
}
