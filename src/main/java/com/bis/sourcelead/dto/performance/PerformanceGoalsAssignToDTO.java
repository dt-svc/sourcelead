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
	
	private Long userId;
	
	private Long orgLocationId;
	
	private Long orgId;
	
	private String assignedUUID;
	
	private Integer goalsScore;
	
	

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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId) {
		this.orgLocationId = orgLocationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getAssignedUUID() {
		return assignedUUID;
	}

	public void setAssignedUUID(String assignedUUID) {
		this.assignedUUID = assignedUUID;
	}

	public Integer getGoalsScore() {
		return goalsScore;
	}

	public void setGoalsScore(Integer goalsScore) {
		this.goalsScore = goalsScore;
	}

	
}
