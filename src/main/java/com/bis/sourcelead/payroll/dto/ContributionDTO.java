package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class ContributionDTO {
	
	private String id; 
	
	private String contributionType;
	
	private String contributionMethod;
	
	private BigDecimal contributionAmount;
	
	private double amount;
	
	private String associateContributionUUID;
	
	private String description;
	
	private String payrollDeductionId;
	
	private String taxType;
	
	private int status;

	private String contributionSchedule;
	
	private String preContributionType;
	
	private BigDecimal preContributionAmt;

	private double postContributionAmt;
	
	private double contributionAmt;
	
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

	public String getPayrollDeductionId()
	{
		return payrollDeductionId;
	}

	public void setPayrollDeductionId(String payrollDeductionId)
	{
		this.payrollDeductionId = payrollDeductionId;
	}

	public String getTaxType()
	{
		return taxType;
	}

	public void setTaxType(String taxType)
	{
		this.taxType = taxType;
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getContributionSchedule()
	{
		return contributionSchedule;
	}

	public void setContributionSchedule(String contributionSchedule)
	{
		this.contributionSchedule = contributionSchedule;
	}

	public String getPreContributionType()
	{
		return preContributionType;
	}

	public BigDecimal getPreContributionAmt()
	{
		return preContributionAmt;
	}

	public void setPreContributionType(String preContributionType)
	{
		this.preContributionType = preContributionType;
	}

	public void setPreContributionAmt(BigDecimal preContributionAmt)
	{
		this.preContributionAmt = preContributionAmt;
	}

	public double getPostContributionAmt()
	{
		return postContributionAmt;
	}

	public void setPostContributionAmt(double postContributionAmt)
	{
		this.postContributionAmt = postContributionAmt;
	}

	public double getContributionAmt()
	{
		return contributionAmt;
	}

	public void setContributionAmt(double contributionAmt)
	{
		this.contributionAmt = contributionAmt;
	}
	
	
}
