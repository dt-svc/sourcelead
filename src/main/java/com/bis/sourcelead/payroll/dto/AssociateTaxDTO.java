package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.annotations.Required;

public class AssociateTaxDTO {
	
	private Long id;
	
	@Required(desc = "Federal Tax Filling Status")
	private String federaltaxFillingStatus;
	
	@Required(desc = "Federal Tax Allowances")
	private String federaltaxAllowances;
	
	@Required(desc = "Federal Tax Additional With Holding")
	private String federaltaxAdditionalWithHolding;
	
//	@Required(desc = "State Tax Filling Status")
	private String stateTaxFillingStatus;
	
//	@Required(desc = "State Tax Allowances")
	private String stateTaxAllowances;
	
//	@Required(desc = "State Tax Additional With Holding")
	private String stateTaxAdditionalWithHolding;
	
	private String localTax;
	
	private String localTaxState;
	
	private String federalExempt;
	
	private String medicareExempt;
	
	private String socailSecurityExempt;
	
	private String futaExempt;
	
	private String sutaExempt;
	
	private String residenceLocationFillingStatus;
	
	private String residenceLocationAllowances;
	
	private String residenceLocationAdditionalWithHolding;
	
	private String residenceLocationSupplementType;
	
	private String isLocalTaxYes;

	private String state;
	
	private String city;
	
	private Long userId;
	
	private String supplementType;
	
	private String location;
	
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFederaltaxFillingStatus()
	{
		return federaltaxFillingStatus;
	}
	
	public void setFederaltaxFillingStatus(String federaltaxFillingStatus)
	{
		this.federaltaxFillingStatus = federaltaxFillingStatus;
	}
	
	public String getFederaltaxAllowances()
	{
		return federaltaxAllowances;
	}
	
	public void setFederaltaxAllowances(String federaltaxAllowances)
	{
		this.federaltaxAllowances = federaltaxAllowances;
	}
	
	public String getFederaltaxAdditionalWithHolding()
	{
		return federaltaxAdditionalWithHolding;
	}
	
	public void setFederaltaxAdditionalWithHolding(String federaltaxAdditionalWithHolding)
	{
		this.federaltaxAdditionalWithHolding = federaltaxAdditionalWithHolding;
	}
	
	public String getStateTaxFillingStatus()
	{
		return stateTaxFillingStatus;
	}
	
	public void setStateTaxFillingStatus(String stateTaxFillingStatus)
	{
		this.stateTaxFillingStatus = stateTaxFillingStatus;
	}
	
	public String getStateTaxAllowances()
	{
		return stateTaxAllowances;
	}
	
	public void setStateTaxAllowances(String stateTaxAllowances)
	{
		this.stateTaxAllowances = stateTaxAllowances;
	}
	
	public String getStateTaxAdditionalWithHolding()
	{
		return stateTaxAdditionalWithHolding;
	}
	
	public void setStateTaxAdditionalWithHolding(String stateTaxAdditionalWithHolding)
	{
		this.stateTaxAdditionalWithHolding = stateTaxAdditionalWithHolding;
	}
	
	public String getLocalTax()
	{
		return localTax;
	}
	
	public void setLocalTax(String localTax)
	{
		this.localTax = localTax;
	}

	public String getFederalExempt()
	{
		return federalExempt;
	}

	public void setFederalExempt(String federalExempt)
	{
		this.federalExempt = federalExempt;
	}

	public String getMedicareExempt()
	{
		return medicareExempt;
	}

	public void setMedicareExempt(String medicareExempt)
	{
		this.medicareExempt = medicareExempt;
	}

	public String getSocailSecurityExempt()
	{
		return socailSecurityExempt;
	}

	public void setSocailSecurityExempt(String socailSecurityExempt)
	{
		this.socailSecurityExempt = socailSecurityExempt;
	}

	public String getFutaExempt()
	{
		return futaExempt;
	}

	public void setFutaExempt(String futaExempt)
	{
		this.futaExempt = futaExempt;
	}

	public String getSutaExempt()
	{
		return sutaExempt;
	}

	public void setSutaExempt(String sutaExempt)
	{
		this.sutaExempt = sutaExempt;
	}

	public String getResidenceLocationFillingStatus()
	{
		return residenceLocationFillingStatus;
	}

	public void setResidenceLocationFillingStatus(String residenceLocationFillingStatus)
	{
		this.residenceLocationFillingStatus = residenceLocationFillingStatus;
	}

	public String getResidenceLocationAllowances()
	{
		return residenceLocationAllowances;
	}

	public void setResidenceLocationAllowances(String residenceLocationAllowances)
	{
		this.residenceLocationAllowances = residenceLocationAllowances;
	}

	public String getResidenceLocationAdditionalWithHolding()
	{
		return residenceLocationAdditionalWithHolding;
	}

	public void setResidenceLocationAdditionalWithHolding(String residenceLocationAdditionalWithHolding)
	{
		this.residenceLocationAdditionalWithHolding = residenceLocationAdditionalWithHolding;
	}

	public String getResidenceLocationSupplementType()
	{
		return residenceLocationSupplementType;
	}

	public void setResidenceLocationSupplementType(String residenceLocationSupplementType)
	{
		this.residenceLocationSupplementType = residenceLocationSupplementType;
	}

	public String getIsLocalTaxYes()
	{
		return isLocalTaxYes;
	}

	public void setIsLocalTaxYes(String isLocalTaxYes)
	{
		this.isLocalTaxYes = isLocalTaxYes;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getSupplementType()
	{
		return supplementType;
	}

	public void setSupplementType(String supplementType)
	{
		this.supplementType = supplementType;
	}

	public String getLocalTaxState()
	{
		return localTaxState;
	}

	public void setLocalTaxState(String localTaxState)
	{
		this.localTaxState = localTaxState;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
