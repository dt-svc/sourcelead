package com.bis.sourcelead.payroll.dto;

import java.math.BigInteger;
import java.util.Date;

public class PayPeriodDTO {
	
	private BigInteger groupId;
	
	private Date freqStartDate;
	
	private Date freqEndDate;
	
	private Integer payPeriodNumber;
	
	private String approverStatus;
	
	private String department;
	
	private Long payGroup;
	
	private String payRollType;
	
	private String convertedFreqStartDate;
	
	private String convertedFreqEndDate;
	
	private BigInteger paySheetFreqId;
	
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
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String department)
	{
		this.department = department;
	}
	public Long getPayGroup()
	{
		return payGroup;
	}
	public void setPayGroup(Long payGroup)
	{
		this.payGroup = payGroup;
	}
	public String getPayRollType()
	{
		return payRollType;
	}
	public void setPayRollType(String payRollType)
	{
		this.payRollType = payRollType;
	}
	public String getConvertedFreqStartDate() {
		return convertedFreqStartDate;
	}
	public void setConvertedFreqStartDate(String convertedFreqStartDate) {
		this.convertedFreqStartDate = convertedFreqStartDate;
	}
	public String getConvertedFreqEndDate() {
		return convertedFreqEndDate;
	}
	public void setConvertedFreqEndDate(String convertedFreqEndDate) {
		this.convertedFreqEndDate = convertedFreqEndDate;
	}
	public BigInteger getPaySheetFreqId() {
		return paySheetFreqId;
	}
	public void setPaySheetFreqId(BigInteger paySheetFreqId) {
		this.paySheetFreqId = paySheetFreqId;
	}
	
}
