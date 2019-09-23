package com.bis.sourcelead.dto.performance;

import java.io.Serializable;
import java.math.BigInteger;

public class PerformanceGoalsDoNutChartCountsDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private BigInteger goalCount;
	
	private BigInteger selfCount;
	
	private BigInteger assignedCount;

	public BigInteger getGoalCount() {
		return goalCount;
	}

	public void setGoalCount(BigInteger goalCount) {
		this.goalCount = goalCount;
	}

	public BigInteger getSelfCount() {
		return selfCount;
	}

	public void setSelfCount(BigInteger selfCount) {
		this.selfCount = selfCount;
	}

	public BigInteger getAssignedCount() {
		return assignedCount;
	}

	public void setAssignedCount(BigInteger assignedCount) {
		this.assignedCount = assignedCount;
	}


}
