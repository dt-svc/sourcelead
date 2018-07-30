package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class DeductionDTO {
	
	private String id;
	
	private String deductionType;
	
	private String method;
	
	private BigDecimal amount;
	
	private double dedAmount;
	
	public double getDedAmount()
	{
		return dedAmount;
	}

	public void setDedAmount(double dedAmount)
	{
		this.dedAmount = dedAmount;
	}

	private String description;
	
	private String associateDeductionUUID;
	
	private String dedTaxType;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDeductionType()
	{
		return deductionType;
	}

	public void setDeductionType(String deductionType)
	{
		this.deductionType = deductionType;
	}

	public String getMethod()
	{
		return method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getAssociateDeductionUUID()
	{
		return associateDeductionUUID;
	}

	public void setAssociateDeductionUUID(String associateDeductionUUID)
	{
		this.associateDeductionUUID = associateDeductionUUID;
	}

	public String getDedTaxType()
	{
		return dedTaxType;
	}

	public void setDedTaxType(String dedTaxType)
	{
		this.dedTaxType = dedTaxType;
	}
	
	
}
