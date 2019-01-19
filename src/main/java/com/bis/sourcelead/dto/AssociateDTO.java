package com.bis.sourcelead.dto;

public class AssociateDTO {

	private Long candidateId;
	
	private String billRateCurrency;
	
	private Double billRate;
	
	private String billRateRateType;
	
	private Long associateRole;
	
	private Long associateManager;
	
	

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public String getBillRateCurrency() {
		return billRateCurrency;
	}

	public void setBillRateCurrency(String billRateCurrency) {
		this.billRateCurrency = billRateCurrency;
	}

	public Double getBillRate() {
		return billRate;
	}

	public void setBillRate(Double billRate) {
		this.billRate = billRate;
	}

	public String getBillRateRateType() {
		return billRateRateType;
	}

	public void setBillRateRateType(String billRateRateType) {
		this.billRateRateType = billRateRateType;
	}

	public Long getAssociateRole() {
		return associateRole;
	}

	public void setAssociateRole(Long associateRole) {
		this.associateRole = associateRole;
	}

	public Long getAssociateManager() {
		return associateManager;
	}

	public void setAssociateManager(Long associateManager) {
		this.associateManager = associateManager;
	}
}
