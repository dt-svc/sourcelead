package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class DeductionSummaryDTO {
	
	private String fromDate;
	private String toDate;
	private String deductionType;
	
	private BigDecimal currentAmt;
	private BigDecimal ytd;
	private BigDecimal qtd;
	
	private int candidateCount;
	
	public BigDecimal getCurrentAmt() {
		return currentAmt;
	}
	public void setCurrentAmt(BigDecimal currentAmt) {
		this.currentAmt = currentAmt;
	}
	public String getDeductionType() {
		return deductionType;
	}
	public void setDeductionType(String deductionType) {
		this.deductionType = deductionType;
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
	public BigDecimal getYtd() {
		return ytd;
	}
	public void setYtd(BigDecimal ytd) {
		this.ytd = ytd;
	}
	public BigDecimal getQtd() {
		return qtd;
	}
	public void setQtd(BigDecimal qtd) {
		this.qtd = qtd;
	}
	public int getCandidateCount() {
		return candidateCount;
	}
	public void setCandidateCount(int candidateCount) {
		this.candidateCount = candidateCount;
	}
	
	
}
