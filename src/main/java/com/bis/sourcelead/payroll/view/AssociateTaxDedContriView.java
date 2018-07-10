package com.bis.sourcelead.payroll.view;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASSOCIATE_TAX_DED_CONTRI_VIEW", schema = "SOURCELEAD")
public class AssociateTaxDedContriView {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "USER_ID")
	private Long userId;
	
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
	
	@Column (name = "FEDERAL_EXEMPT")
	private Boolean federalExempt;
	
	@Column (name = "MEDIACARE_EXEMPT")
	private Boolean mediacareExempt;
	
	@Column (name = "SOCIAL_SECURITY_EXEMPT")
	private Boolean socialSecurityExempt;
	
	@Column (name = "FUTA_EXEMPT")
	private Boolean futaExempt;
	
	@Column (name = "LOCAL_TAX")
	private String localTax;
	
	@Column (name = "LOCAL_TAX_STATE")
	private String localTaxState;
	
	@Column (name = "ASSOCIATE_TAX_UUID")
	private String associateTaxUUID;
	
	@Column (name = "DEDUCTION_TYPE_ID")
	private Long deductionTypeId;
	
	@Column (name = "DEDUCTION_METHOD")
	private String deductionMethod;
	
	@Column (name = "DEDUCTION_AMOUNT")
	private BigDecimal deductionAmount;
	
	@Column (name = "ASSOCIATE_DEDUCTION_UUID")
	private String associateDeductionUUID;
	
	@Column (name = "CONTRIBUTION_TYPE_ID")
	private Long contributionTypeId;
	
	@Column (name = "CONTRIBUTION_METHOD")
	private String contributionMethod;
	
	@Column (name = "CONTRIBUTION_AMOUNT")
	private BigDecimal contributionAmount;
	
	@Column (name = "ASSOCIATE_CONTRIBUTION_UUID")
	private String associateContributionUUID;
	
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
	
	@Column (name = "DED_LOCATION_ID")
	private Long dedLocationId;
	
	@Column (name = "CONTRI_LOCATION_ID")
	private Long contriLocationId;
	
	@Column (name = "BANK_LOCATION_ID")
	private Long bankLocationId;
	
	@Column (name="TAX_TYPE")
	private String taxType;

	public Long getUserId()
	{
		return userId;
	}

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

	public String getAssociateTaxUUID()
	{
		return associateTaxUUID;
	}

	public Long getDeductionTypeId()
	{
		return deductionTypeId;
	}

	public String getDeductionMethod()
	{
		return deductionMethod;
	}

	public BigDecimal getDeductionAmount()
	{
		return deductionAmount;
	}

	public String getAssociateDeductionUUID()
	{
		return associateDeductionUUID;
	}

	public Long getContributionTypeId()
	{
		return contributionTypeId;
	}

	public String getContributionMethod()
	{
		return contributionMethod;
	}

	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}

	public String getAssociateContributionUUID()
	{
		return associateContributionUUID;
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

	

	public void setUserId(Long userId)
	{
		this.userId = userId;
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

	public void setAssociateTaxUUID(String associateTaxUUID)
	{
		this.associateTaxUUID = associateTaxUUID;
	}

	public void setDeductionTypeId(Long deductionTypeId)
	{
		this.deductionTypeId = deductionTypeId;
	}

	public void setDeductionMethod(String deductionMethod)
	{
		this.deductionMethod = deductionMethod;
	}

	public void setDeductionAmount(BigDecimal deductionAmount)
	{
		this.deductionAmount = deductionAmount;
	}

	public void setAssociateDeductionUUID(String associateDeductionUUID)
	{
		this.associateDeductionUUID = associateDeductionUUID;
	}

	public void setContributionTypeId(Long contributionTypeId)
	{
		this.contributionTypeId = contributionTypeId;
	}

	public void setContributionMethod(String contributionMethod)
	{
		this.contributionMethod = contributionMethod;
	}

	public void setContributionAmount(BigDecimal contributionAmount)
	{
		this.contributionAmount = contributionAmount;
	}

	public void setAssociateContributionUUID(String associateContributionUUID)
	{
		this.associateContributionUUID = associateContributionUUID;
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

	public Long getDedLocationId()
	{
		return dedLocationId;
	}

	public Long getContriLocationId()
	{
		return contriLocationId;
	}

	public Long getBankLocationId()
	{
		return bankLocationId;
	}

	public void setTaxLocationId(Long taxLocationId)
	{
		this.taxLocationId = taxLocationId;
	}

	public void setDedLocationId(Long dedLocationId)
	{
		this.dedLocationId = dedLocationId;
	}

	public void setContriLocationId(Long contriLocationId)
	{
		this.contriLocationId = contriLocationId;
	}

	public void setBankLocationId(Long bankLocationId)
	{
		this.bankLocationId = bankLocationId;
	}

	public String getTaxType()
	{
		return taxType;
	}

	public void setTaxType(String taxType)
	{
		this.taxType = taxType;
	}
	
}
