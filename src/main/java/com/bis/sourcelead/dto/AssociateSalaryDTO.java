package com.bis.sourcelead.dto;

import java.util.List;

public class AssociateSalaryDTO {
	
	private String payType;
	
	private String payFrequency;
	
	private String standardHours;
	
	private String annualSal;
	
	private String perPaySal;
	
	private String hourlyRate;
	
	private String allowOverTimePay;
	
	List<AssociatePayItemDTO> associatePayItemDto;
	
	public String getPayType()
	{
		return payType;
	}
	public void setPayType(String payType)
	{
		this.payType = payType;
	}
	public String getPayFrequency()
	{
		return payFrequency;
	}
	public void setPayFrequency(String payFrequency)
	{
		this.payFrequency = payFrequency;
	}
	public String getStandardHours()
	{
		return standardHours;
	}
	public void setStandardHours(String standardHours)
	{
		this.standardHours = standardHours;
	}
	public String getAnnualSal()
	{
		return annualSal;
	}
	public void setAnnualSal(String annualSal)
	{
		this.annualSal = annualSal;
	}
	public String getPerPaySal()
	{
		return perPaySal;
	}
	public void setPerPaySal(String perPaySal)
	{
		this.perPaySal = perPaySal;
	}
	public String getHourlyRate()
	{
		return hourlyRate;
	}
	public void setHourlyRate(String hourlyRate)
	{
		this.hourlyRate = hourlyRate;
	}
	public String getAllowOverTimePay()
	{
		return allowOverTimePay;
	}
	public void setAllowOverTimePay(String allowOverTimePay)
	{
		this.allowOverTimePay = allowOverTimePay;
	}
	public List<AssociatePayItemDTO> getAssociatePayItemDto()
	{
		return associatePayItemDto;
	}
	public void setAssociatePayItemDto(List<AssociatePayItemDTO> associatePayItemDto)
	{
		this.associatePayItemDto = associatePayItemDto;
	}
	
	
}
