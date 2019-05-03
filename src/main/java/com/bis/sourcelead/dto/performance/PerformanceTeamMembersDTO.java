package com.bis.sourcelead.dto.performance;

import java.math.BigInteger;

public class PerformanceTeamMembersDTO {

	private BigInteger teamMemberId;
	
	private String teamMemberName;

	public BigInteger getTeamMemberId() {
		return teamMemberId;
	}

	public void setTeamMemberId(BigInteger teamMemberId) {
		this.teamMemberId = teamMemberId;
	}

	public String getTeamMemberName() {
		return teamMemberName;
	}

	public void setTeamMemberName(String teamMemberName) {
		this.teamMemberName = teamMemberName;
	}
	
	
}
