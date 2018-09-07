package com.bis.sourcelead.dto;

/**
 * @author VenkatKesav
 *
 */
public class RequirementIncentiveDTO {

	private String joiners;
	
	private String joinersAmountType;
	
	private String joinersAmount;
	
	private Long id;

	public String getJoiners() {
		return joiners;
	}

	public void setJoiners(String joiners) {
		this.joiners = joiners;
	}

	public String getJoinersAmountType() {
		return joinersAmountType;
	}

	public void setJoinersAmountType(String joinersAmountType) {
		this.joinersAmountType = joinersAmountType;
	}

	public String getJoinersAmount() {
		return joinersAmount;
	}

	public void setJoinersAmount(String joinersAmount) {
		this.joinersAmount = joinersAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
}
