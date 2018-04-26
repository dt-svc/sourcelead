package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;

public class DeductionDTO {
	
	private String deductionType;
	
	private String method;
	
	private BigDecimal amount;

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
	
	
}
