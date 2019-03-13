package com.bis.sourcelead.dto;

import java.math.BigDecimal;

import com.bis.sourcelead.annotations.Required;

public class AssociateRequestDTO {

	private String candidateId;
	
	private String resourceRequestId;

	private String candidateFullName;
	
	@Required(desc = "candidatePayRate")
	private String candidatePayRate;
	
	private String candidatePayRateCurrency;
	
	private String candidatePayRateType;

	private String billRate;
	
	private String billRateCurrency;
	
	private String billRateRateType;

	private String requestStartDate;
	
	private String requestEndDate;
	
	@Required(desc = "consultingType")
	private String consultingType;
	
	private String estHours;
	
	private String estBudgetCurrencyType;
	
	private String estimatedBudgetCurrencyType;
	
	private String estBudget;

	private String validateRow;
	
	private BigDecimal percentageOfWork;
	
	public String getCandidateId() {
		return candidateId;
	}

	public String getResourceRequestId() {
		return resourceRequestId;
	}

	public String getCandidateFullName() {
		return candidateFullName;
	}

	public String getCandidatePayRate() {
		return candidatePayRate;
	}

	public String getCandidatePayRateCurrency() {
		return candidatePayRateCurrency;
	}

	public String getCandidatePayRateType() {
		return candidatePayRateType;
	}

	public String getBillRate() {
		return billRate;
	}

	public String getBillRateCurrency() {
		return billRateCurrency;
	}

	public String getBillRateRateType() {
		return billRateRateType;
	}

	public String getRequestStartDate() {
		return requestStartDate;
	}

	public String getRequestEndDate() {
		return requestEndDate;
	}

	public String getConsultingType() {
		return consultingType;
	}

	public String getEstHours() {
		return estHours;
	}

	public String getEstBudget() {
		return estBudget;
	}

	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	public void setResourceRequestId(String resourceRequestId) {
		this.resourceRequestId = resourceRequestId;
	}

	public void setCandidateFullName(String candidateFullName) {
		this.candidateFullName = candidateFullName;
	}

	public void setCandidatePayRate(String candidatePayRate) {
		this.candidatePayRate = candidatePayRate;
	}

	public void setCandidatePayRateCurrency(String candidatePayRateCurrency) {
		this.candidatePayRateCurrency = candidatePayRateCurrency;
	}

	public void setCandidatePayRateType(String candidatePayRateType) {
		this.candidatePayRateType = candidatePayRateType;
	}

	public void setBillRate(String billRate) {
		this.billRate = billRate;
	}

	public void setBillRateCurrency(String billRateCurrency) {
		this.billRateCurrency = billRateCurrency;
	}

	public void setBillRateRateType(String billRateRateType) {
		this.billRateRateType = billRateRateType;
	}

	public void setRequestStartDate(String requestStartDate) {
		this.requestStartDate = requestStartDate;
	}

	public void setRequestEndDate(String requestEndDate) {
		this.requestEndDate = requestEndDate;
	}

	public void setConsultingType(String consultingType) {
		this.consultingType = consultingType;
	}

	public void setEstHours(String estHours) {
		this.estHours = estHours;
	}

	public void setEstBudget(String estBudget) {
		this.estBudget = estBudget;
	}

	public String getValidateRow() {
		return validateRow;
	}

	public void setValidateRow(String validateRow) {
		this.validateRow = validateRow;
	}

	public String getEstBudgetCurrencyType() {
		return estBudgetCurrencyType;
	}

	public void setEstBudgetCurrencyType(String estBudgetCurrencyType) {
		this.estBudgetCurrencyType = estBudgetCurrencyType;
	}

	public String getEstimatedBudgetCurrencyType() {
		return estimatedBudgetCurrencyType;
	}

	public void setEstimatedBudgetCurrencyType(String estimatedBudgetCurrencyType) {
		this.estimatedBudgetCurrencyType = estimatedBudgetCurrencyType;
	}

	public BigDecimal getPercentageOfWork() {
		return percentageOfWork;
	}

	public void setPercentageOfWork(BigDecimal percentageOfWork) {
		this.percentageOfWork = percentageOfWork;
	}
	
	
}
