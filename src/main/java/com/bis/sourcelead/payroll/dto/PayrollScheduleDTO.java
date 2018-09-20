package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.dto.SearchDTO;

public class PayrollScheduleDTO extends SearchDTO {
	
	private String orgLocationId;
	private String payScheduleDate;
	private String payScheduleType;
	private String selectedDate;
	private String dateFrequency;
	private int status;
	
	public String getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public String getPayScheduleDate() {
		return payScheduleDate;
	}
	public void setPayScheduleDate(String payScheduleDate) {
		this.payScheduleDate = payScheduleDate;
	}
	public String getPayScheduleType() {
		return payScheduleType;
	}
	public void setPayScheduleType(String payScheduleType) {
		this.payScheduleType = payScheduleType;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	public String getDateFrequency() {
		return dateFrequency;
	}
	public void setDateFrequency(String dateFrequency) {
		this.dateFrequency = dateFrequency;
	}
	
}
