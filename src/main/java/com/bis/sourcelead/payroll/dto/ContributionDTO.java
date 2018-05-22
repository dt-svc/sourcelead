package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class ContributionDTO {
	
	private String id; 
	
	private String contributionType;
	
	private String contributionMethod;
	
	private BigDecimal contributionAmount;
	
	private String associateContributionUUID;
	
	private String description;
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

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

	public String getAssociateContributionUUID()
	{
		return associateContributionUUID;
	}

	public void setAssociateContributionUUID(String associateContributionUUID)
	{
		this.associateContributionUUID = associateContributionUUID;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
}
