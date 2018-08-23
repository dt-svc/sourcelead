package com.bis.sourcelead.payroll.dto;

import java.math.BigInteger;
import java.util.Date;

public class PayPeriodDTO {
	
	private BigInteger groupId;
	
	private Date freqStartDate;
	
	private Date freqEndDate;
	
	private Integer payPeriodNumber;
	
	private String approverStatus;
	
	public BigInteger getGroupId()
	{
		return groupId;
	}
	public Date getFreqStartDate()
	{
		return freqStartDate;
	}
	public Date getFreqEndDate()
	{
		return freqEndDate;
	}
	public Integer getPayPeriodNumber()
	{
		return payPeriodNumber;
	}
	public String getApproverStatus()
	{
		return approverStatus;
	}
	public void setGroupId(BigInteger groupId)
	{
		this.groupId = groupId;
	}
	public void setFreqStartDate(Date freqStartDate)
	{
		this.freqStartDate = freqStartDate;
	}
	public void setFreqEndDate(Date freqEndDate)
	{
		this.freqEndDate = freqEndDate;
	}
	public void setPayPeriodNumber(Integer payPeriodNumber)
	{
		this.payPeriodNumber = payPeriodNumber;
	}
	public void setApproverStatus(String approverStatus)
	{
		this.approverStatus = approverStatus;
	}
	
}
