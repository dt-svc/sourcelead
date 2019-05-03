package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;

public class PerformanceGoalsAssignToDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String goalName;
	
	private String assignedUser;
	
	private String userImgUrl;
	
	private String assignedDate;
	
	private BigInteger assignedUserId;

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getAssignedUser() {
		return assignedUser;
	}

	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}

	public String getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(String assignedDate) {
		this.assignedDate = assignedDate;
	}

	public BigInteger getAssignedUserId() {
		return assignedUserId;
	}

	public void setAssignedUserId(BigInteger assignedUserId) {
		this.assignedUserId = assignedUserId;
	}
	
	
}
