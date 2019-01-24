package com.bis.sourcelead.payroll.view;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSOCIATE_TAX_DED_CONTRI_VIEW")
public class AssociateTaxDedContriView {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "CANDIDATE_ID")
	private Long candidateId;
	
	@Column(name = "ASSOCIATE_TAX_ID")
	private Long associateTaxId;
	
	@Column (name = "FEDERAL_TAX_FILLIING_STATUS")
	private String federalTaxFilliingStatus;
	
	@Column (name = "FEDERAL_TAX_ALLOWANCES")
	private String federalTaxAllowances;
	
	@Column (name = "FEDERAL_TAX_ADDITIONNAL_WITH_HOLDING")
	private String federalTaxAdditionnalWithHolding;
	
	@Column (name = "STATE_TAX_FILLIING_STATUS")
	private String stateTaxFilliingStatus;
	
	@Column (name = "STATE_TAX_ALLOWANCES")
	private String stateTaxAllowances;
	
	@Column (name = "STATE_TAX_ADDITIONNAL_WITH_HOLDING")
	private String stateTaxAdditionnalWithHolding;
	
	@Column (name = "RESIDENCE_TAX_FILLIING_STATUS")
	private String residenceTaxFilliingStatus;
	
	@Column (name = "RESIDENCE_TAX_ALLOWANCES")
	private String residenceTaxAllowances;

	@Column (name = "RESIDENCE_TAX_ADDITIONNAL_WITH_HOLDING")
	private String residenceTaxAdditionnalWithHolding;
	
	@Column (name = "SUPPLEMENT_TYPE")
	private String supplementType;
	
	@Column (name = "FEDERAL_EXEMPT")
	private Boolean federalExempt;
	
	@Column (name = "MEDIACARE_EXEMPT")
	private Boolean mediacareExempt;
	
	@Column (name = "SOCIAL_SECURITY_EXEMPT")
	private Boolean socialSecurityExempt;
	
	@Column (name = "FUTA_EXEMPT")
	private Boolean futaExempt;
	
	@Column (name = "SUTA_EXEMPT")
	private Boolean sutaExempt;
	
	@Column (name = "LOCAL_TAX")
	private String localTax;
	
	@Column (name = "LOCAL_TAX_STATE")
	private String localTaxState;
	
	@Column (name="ASSOCIATE_BANK_ID")
	private Long associateBankId;
	
	@Column (name="ROUTING_NUMBER")
	private String routingNumber;
	
	@Column (name = "ACCOUNT_NUMBER")
	private String accountNumber;
	
	
	@Column (name = "ACCOUNT_TYPE")
	private String accountType;
	
	@Column (name = "BANK_NAME")
	private String bankName;
	
	@Column (name = "ASSOCIATE_BANK_UUID")
	private String associateBankUUID;
	
	@Column (name = "TAX_LOCATION_ID")
	private Long taxLocationId;
	
	
	@Column (name = "BANK_LOCATION_ID")
	private Long bankLocationId;
	
	
	@Column (name="ANNUAL_SALARY")
	private BigDecimal annualSalary;
	
	@Column (name="HOURLY_RATE")
	private BigDecimal hourlyRate;
	
	@Column (name="PAY_TYPE")
	private String payType;
	
	@Column (name="PAY_FREQUENCY")
	private String payFrequency;
	

	public String getFederalTaxFilliingStatus()
	{
		return federalTaxFilliingStatus;
	}

	public String getFederalTaxAllowances()
	{
		return federalTaxAllowances;
	}

	public String getFederalTaxAdditionnalWithHolding()
	{
		return federalTaxAdditionnalWithHolding;
	}

	public String getStateTaxFilliingStatus()
	{
		return stateTaxFilliingStatus;
	}

	public String getStateTaxAllowances()
	{
		return stateTaxAllowances;
	}

	public String getStateTaxAdditionnalWithHolding()
	{
		return stateTaxAdditionnalWithHolding;
	}

	public String getResidenceTaxFilliingStatus()
	{
		return residenceTaxFilliingStatus;
	}

	public String getResidenceTaxAllowances()
	{
		return residenceTaxAllowances;
	}

	public String getResidenceTaxAdditionnalWithHolding()
	{
		return residenceTaxAdditionnalWithHolding;
	}

	public Boolean getFederalExempt()
	{
		return federalExempt;
	}

	public Boolean getMediacareExempt()
	{
		return mediacareExempt;
	}

	public Boolean getSocialSecurityExempt()
	{
		return socialSecurityExempt;
	}

	public Boolean getFutaExempt()
	{
		return futaExempt;
	}

	public String getLocalTax()
	{
		return localTax;
	}

	public String getLocalTaxState()
	{
		return localTaxState;
	}


	public String getAccountNumber()
	{
		return accountNumber;
	}

	public String getAccountType()
	{
		return accountType;
	}

	public String getBankName()
	{
		return bankName;
	}

	public String getAssociateBankUUID()
	{
		return associateBankUUID;
	}


	public void setFederalTaxFilliingStatus(String federalTaxFilliingStatus)
	{
		this.federalTaxFilliingStatus = federalTaxFilliingStatus;
	}

	public void setFederalTaxAllowances(String federalTaxAllowances)
	{
		this.federalTaxAllowances = federalTaxAllowances;
	}

	public void setFederalTaxAdditionnalWithHolding(String federalTaxAdditionnalWithHolding)
	{
		this.federalTaxAdditionnalWithHolding = federalTaxAdditionnalWithHolding;
	}

	public void setStateTaxFilliingStatus(String stateTaxFilliingStatus)
	{
		this.stateTaxFilliingStatus = stateTaxFilliingStatus;
	}

	public void setStateTaxAllowances(String stateTaxAllowances)
	{
		this.stateTaxAllowances = stateTaxAllowances;
	}

	public void setStateTaxAdditionnalWithHolding(String stateTaxAdditionnalWithHolding)
	{
		this.stateTaxAdditionnalWithHolding = stateTaxAdditionnalWithHolding;
	}

	public void setResidenceTaxFilliingStatus(String residenceTaxFilliingStatus)
	{
		this.residenceTaxFilliingStatus = residenceTaxFilliingStatus;
	}

	public void setResidenceTaxAllowances(String residenceTaxAllowances)
	{
		this.residenceTaxAllowances = residenceTaxAllowances;
	}

	public void setResidenceTaxAdditionnalWithHolding(String residenceTaxAdditionnalWithHolding)
	{
		this.residenceTaxAdditionnalWithHolding = residenceTaxAdditionnalWithHolding;
	}

	public void setFederalExempt(Boolean federalExempt)
	{
		this.federalExempt = federalExempt;
	}

	public void setMediacareExempt(Boolean mediacareExempt)
	{
		this.mediacareExempt = mediacareExempt;
	}

	public void setSocialSecurityExempt(Boolean socialSecurityExempt)
	{
		this.socialSecurityExempt = socialSecurityExempt;
	}

	public void setFutaExempt(Boolean futaExempt)
	{
		this.futaExempt = futaExempt;
	}

	public void setLocalTax(String localTax)
	{
		this.localTax = localTax;
	}

	public void setLocalTaxState(String localTaxState)
	{
		this.localTaxState = localTaxState;
	}


	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public void setAssociateBankUUID(String associateBankUUID)
	{
		this.associateBankUUID = associateBankUUID;
	}

	public Long getTaxLocationId()
	{
		return taxLocationId;
	}


	public Long getBankLocationId()
	{
		return bankLocationId;
	}

	public void setTaxLocationId(Long taxLocationId)
	{
		this.taxLocationId = taxLocationId;
	}


	public void setBankLocationId(Long bankLocationId)
	{
		this.bankLocationId = bankLocationId;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public BigDecimal getAnnualSalary()
	{
		return annualSalary;
	}

	public BigDecimal getHourlyRate()
	{
		return hourlyRate;
	}

	public String getPayType()
	{
		return payType;
	}

	public String getPayFrequency()
	{
		return payFrequency;
	}

	public void setAnnualSalary(BigDecimal annualSalary)
	{
		this.annualSalary = annualSalary;
	}

	public void setHourlyRate(BigDecimal hourlyRate)
	{
		this.hourlyRate = hourlyRate;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}

	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}

	public Long getCandidateId()
	{
		return candidateId;
	}

	public void setCandidateId(Long candidateId)
	{
		this.candidateId = candidateId;
	}

	public Long getAssociateBankId() {
		return associateBankId;
	}

	public void setAssociateBankId(Long associateBankId) {
		this.associateBankId = associateBankId;
	}

	public String getRoutingNumber() {
		return routingNumber;
	}

	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}

	public Long getAssociateTaxId() {
		return associateTaxId;
	}

	public void setAssociateTaxId(Long associateTaxId) {
		this.associateTaxId = associateTaxId;
	}

	public Boolean getSutaExempt() {
		return sutaExempt;
	}

	public void setSutaExempt(Boolean sutaExempt) {
		this.sutaExempt = sutaExempt;
	}

	public String getSupplementType() {
		return supplementType;
	}

	public void setSupplementType(String supplementType) {
		this.supplementType = supplementType;
	}
	
}
