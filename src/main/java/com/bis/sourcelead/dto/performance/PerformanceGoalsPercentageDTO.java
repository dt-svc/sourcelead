package com.bis.sourcelead.dto.performance;

import java.io.Serializable;

public class PerformanceGoalsPercentageDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
	private Long goalId;
	
	private String goalPercentage;
	
	private String goalUUID;
	
	private double percentageDone;

	public Long getGoalId() {
		return goalId;
	}

	public void setGoalId(Long goalId) {
		this.goalId = goalId;
	}

	public double getPercentageDone() {
		return percentageDone;
	}

	public void setPercentageDone(double percentageDone) {
		this.percentageDone = percentageDone;
	}

	public String getGoalPercentage() {
		return goalPercentage;
	}

	public void setGoalPercentage(String goalPercentage) {
		this.goalPercentage = goalPercentage;
	}

	public String getGoalUUID() {
		return goalUUID;
	}

	public void setGoalUUID(String goalUUID) {
		this.goalUUID = goalUUID;
	}
	
	
	
}
