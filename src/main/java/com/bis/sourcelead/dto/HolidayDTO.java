package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.UUID;

import com.bis.sourcelead.annotations.Required;

public class HolidayDTO implements Serializable {
private static final long serialVersionUID = 1L;

	private String id;
	
	private String orgLocationId;
	
	private String userId;
	
	@Required(desc = "Holiday Name")
	private String holidayName;  
	
	private String fromDate;
	
	private String toDate;
	
	private String halfDayCheckBox;
	
	private String halfDayAmPm;
	
	private String description;
	
	private String holidayStatus;
	
	private String status;
	
	private String holidayId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getHalfDayCheckBox() {
		return halfDayCheckBox;
	}

	public void setHalfDayCheckBox(String halfDayCheckBox) {
		this.halfDayCheckBox = halfDayCheckBox;
	}

	public String getHalfDayAmPm() {
		return halfDayAmPm;
	}

	public void setHalfDayAmPm(String halfDayAmPm) {
		this.halfDayAmPm = halfDayAmPm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHolidayStatus() {
		return holidayStatus;
	}

	public void setHolidayStatus(String holidayStatus) {
		this.holidayStatus = holidayStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(String holidayId) {
		this.holidayId = holidayId;
	}

}
