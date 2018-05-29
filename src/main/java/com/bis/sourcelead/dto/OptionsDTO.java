package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class OptionsDTO implements Serializable {
	private long id;
	private String orgLocationId;
	private String userId;
	private String department;
	private List<String> roles;
	private String calendarMonth;
	private String weekends;
	private String halfDayRequest;
	private String leaveTransfer;
	private String workingHours;
    private String optionId;
    private String optionUUID;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getCalendarMonth() {
		return calendarMonth;
	}

	public void setCalendarMonth(String calendarMonth) {
		this.calendarMonth = calendarMonth;
	}

	public String getWeekends() {
		return weekends;
	}

	public void setWeekends(String weekends) {
		this.weekends = weekends;
	}

	public String getHalfDayRequest() {
		return halfDayRequest;
	}

	public void setHalfDayRequest(String halfDayRequest) {
		this.halfDayRequest = halfDayRequest;
	}

	public String getLeaveTransfer() {
		return leaveTransfer;
	}

	public void setLeaveTransfer(String leaveTransfer) {
		this.leaveTransfer = leaveTransfer;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public String getOptionUUID()
	{
		return optionUUID;
	}

	public void setOptionUUID(String optionUUID)
	{
		this.optionUUID = optionUUID;
	}

}
