package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ContributionHistoryDTO {
	
	private BigInteger id;
	
	private BigInteger userId;
	
	private BigInteger contributionId;
	
	private String contributionMethod;
	
	private BigDecimal contributionAmount;
	
	private String scheduleType;
	
	private String taxType;
	 
	private String contributionType;

	public BigInteger getId()
	{
		return id;
	}

	public BigInteger getUserId()
	{
		return userId;
	}

	public BigInteger getContributionId()
	{
		return contributionId;
	}

	public String getContributionMethod()
	{
		return contributionMethod;
	}


	public String getScheduleType()
	{
		return scheduleType;
	}

	public String getTaxType()
	{
		return taxType;
	}

	public String getContributionType()
	{
		return contributionType;
	}

	public void setId(BigInteger id)
	{
		this.id = id;
	}

	public void setUserId(BigInteger userId)
	{
		this.userId = userId;
	}

	public void setContributionId(BigInteger contributionId)
	{
		this.contributionId = contributionId;
	}

	public void setContributionMethod(String contributionMethod)
	{
		this.contributionMethod = contributionMethod;
	}


	public void setScheduleType(String scheduleType)
	{
		this.scheduleType = scheduleType;
	}

	public void setTaxType(String taxType)
	{
		this.taxType = taxType;
	}

	public void setContributionType(String contributionType)
	{
		this.contributionType = contributionType;
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
