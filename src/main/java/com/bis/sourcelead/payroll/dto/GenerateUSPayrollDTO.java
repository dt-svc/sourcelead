package com.bis.sourcelead.payroll.dto;

import com.bis.sourcelead.dto.SearchDTO;

public class GenerateUSPayrollDTO extends SearchDTO{
	
	private String groupId;
	
	private String payPeriod;
	  
	private String payrollPayDate;
	
	private String payType;
	
	public String getGroupId()
	{
		return groupId;
	}
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	public String getPayPeriod()
	{
		return payPeriod;
	}
	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
	}
	public String getPayrollPayDate()
	{
		return payrollPayDate;
	}
	public void setPayrollPayDate(String payrollPayDate)
	{
		this.payrollPayDate = payrollPayDate;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}
