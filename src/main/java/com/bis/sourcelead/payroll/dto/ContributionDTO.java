package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class ContributionDTO {
	
	private String contributionType;
	
	private String contributionMethod;
	
	private BigDecimal contributionAmount;
	
	public String getContributionType()
	{
		return contributionType;
	}
	
	public void setContributionType(String contributionType)
	{
		this.contributionType = contributionType;
	}
	
	public String getContributionMethod()
	{
		return contributionMethod;
	}
	
	public void setContributionMethod(String contributionMethod)
	{
		this.contributionMethod = contributionMethod;
	}
	
	public BigDecimal getContributionAmount()
	{
		return contributionAmount;
	}
	
	public void setContributionAmount(BigDecimal contributionAmount)
	{
		this.contributionAmount = contributionAmount;
	}
}
