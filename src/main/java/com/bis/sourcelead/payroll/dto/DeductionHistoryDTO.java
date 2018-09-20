package com.bis.sourcelead.payroll.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DeductionHistoryDTO {
	
	private BigInteger id;
	
	private BigInteger candidateId;
	
	private BigInteger deductionId;
	
	private String deductionMethod; 
	
	private BigDecimal amount;
	
	private String scheduleType;
	
	private String taxType;
	
	private String deductionType;

	public BigInteger getId()
	{
		return id;
	}


	public BigInteger getDeductionId()
	{
		return deductionId;
	}

	public String getDeductionMethod()
	{
		return deductionMethod;
	}

	public BigDecimal getAmount()
	{
		return amount;
	}

	public String getScheduleType()
	{
		return scheduleType;
	}

	public String getTaxType()
	{
		return taxType;
	}

	public void setId(BigInteger id)
	{
		this.id = id;
	}


	public void setDeductionId(BigInteger deductionId)
	{
		this.deductionId = deductionId;
	}

	public void setDeductionMethod(String deductionMethod)
	{
		this.deductionMethod = deductionMethod;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	public void setScheduleType(String scheduleType)
	{
		this.scheduleType = scheduleType;
	}

	public void setTaxType(String taxType)
	{
		this.taxType = taxType;
	}

	public String getDeductionType()
	{
		return deductionType;
	}

	public void setDeductionType(String deductionType)
	{
		this.deductionType = deductionType;
	}


	public BigInteger getCandidateId()
	{
		return candidateId;
	}


	public void setCandidateId(BigInteger candidateId)
	{
		this.candidateId = candidateId;
	}

	
	
	
}
