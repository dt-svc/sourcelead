package com.bis.sourcelead.dto;

import java.io.Serializable;

public class ScheduleMaintenanceDTO extends SearchDTO implements Serializable{

	private Long id;
	private String startDate;
	private String scheduleMessage;
	private String endDate;
	private String mainMessage;

	public String getScheduleMessage() {
		return scheduleMessage;
	}

	public void setScheduleMessage(String scheduleMessage) {
		this.scheduleMessage = scheduleMessage;
	}

	public String getMainMessage() {
		return mainMessage;
	}

	public void setMainMessage(String mainMessage) {
		this.mainMessage = mainMessage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
