package com.bis.sourcelead.dto;

import javax.websocket.Session;

public class DashBoardSocketCountDTO {
	private Session session;
	private String userId;
	private String timesheetType;
	private String orgLocationId;
	private String candidateId;
	private String countFor;
	private String expenseType;
	private String isGlobalAccess;
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTimesheetType() {
		return timesheetType;
	}
	public void setTimesheetType(String timesheetType) {
		this.timesheetType = timesheetType;
	}
	public String getOrgLocationId() {
		return orgLocationId;
	}
	public void setOrgLocationId(String orgLocationId) {
		this.orgLocationId = orgLocationId;
	}
	public String getCandidateId() {
		return candidateId;
	}
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}
	public String getCountFor() {
		return countFor;
	}
	public void setCountFor(String countFor) {
		this.countFor = countFor;
	}
	public String getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	public String getIsGlobalAccess() {
		return isGlobalAccess;
	}
	public void setIsGlobalAccess(String isGlobalAccess) {
		this.isGlobalAccess = isGlobalAccess;
	}
	
	
	
}
