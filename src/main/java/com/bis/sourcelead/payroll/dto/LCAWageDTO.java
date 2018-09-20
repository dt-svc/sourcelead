package com.bis.sourcelead.payroll.dto;

import java.math.BigInteger;
import java.util.Date;

public class LCAWageDTO {
	
	private BigInteger lcaHisId;
	
	private BigInteger candidateId;
   
	private BigInteger lcaId;
   
	private String lcaWageType;
   
	private String lcaStatus;
   
	private Date startDate;
  
	private Date endDate;
  
	private String lcaWage;

	public BigInteger getLcaHisId()
	{
		return lcaHisId;
	}

	public BigInteger getCandidateId()
	{
		return candidateId;
	}

	public BigInteger getLcaId()
	{
		return lcaId;
	}

	public String getLcaWageType()
	{
		return lcaWageType;
	}

	public String getLcaStatus()
	{
		return lcaStatus;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public String getLcaWage()
	{
		return lcaWage;
	}

	public void setLcaHisId(BigInteger lcaHisId)
	{
		this.lcaHisId = lcaHisId;
	}

	public void setCandidateId(BigInteger candidateId)
	{
		this.candidateId = candidateId;
	}

	public void setLcaId(BigInteger lcaId)
	{
		this.lcaId = lcaId;
	}

	public void setLcaWageType(String lcaWageType)
	{
		this.lcaWageType = lcaWageType;
	}

	public void setLcaStatus(String lcaStatus)
	{
		this.lcaStatus = lcaStatus;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public void setLcaWage(String lcaWage)
	{
		this.lcaWage = lcaWage;
	}
	

}
