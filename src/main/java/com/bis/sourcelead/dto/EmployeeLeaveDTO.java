package com.bis.sourcelead.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EmployeeLeaveDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long leaveId;
	
	private String fromDate;
	
	private String toDate;
	
	private Long userId;
	
	private Long locationId;
	
	private String leaveDate;
	
	private String dayOfTheDate;
	
	private String hours;
	
	private String leaveReason;
	
	private boolean holiday;
	
	private Long holidayId;
	
	private Long holidayUserId;
	
	private Long holidayUserLocationId;

	public Long getLeaveId() {
		return leaveId;
	}

	public void setLeaveId(Long leaveId) {
		this.leaveId = leaveId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getDayOfTheDate() {
		return dayOfTheDate;
	}

	public void setDayOfTheDate(String dayOfTheDate) {
		this.dayOfTheDate = dayOfTheDate;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public boolean getHoliday() {
		return holiday;
	}

	public void setHoliday(boolean holiday) {
		this.holiday = holiday;
	}

	public Long getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(Long holidayId) {
		this.holidayId = holidayId;
	}

	public Long getHolidayUserId() {
		return holidayUserId;
	}

	public void setHolidayUserId(Long holidayUserId) {
		this.holidayUserId = holidayUserId;
	}

	public Long getHolidayUserLocationId() {
		return holidayUserLocationId;
	}

	public void setHolidayUserLocationId(Long holidayUserLocationId) {
		this.holidayUserLocationId = holidayUserLocationId;
	}
	
	
	
}
