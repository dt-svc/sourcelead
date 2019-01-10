package com.bis.sourcelead.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.payroll.dto.PayItemDTO;

public class AssociateSalaryDTO {
	
	private String id;
	
	@Required(desc = "Pay Type")
	private String payType;
	
	@Required(desc = "Pay Frequency")
	private String payFrequency;
	
	@Required(desc = "Pay Group")
	private String payGroup;
	
	private String standardHours;
	
	private String annualSal;
	
	private String perPaySal;
	
	private String hourlyRate;
	
	private String allowOverTimePay;
	
	private Long userId;
	
	private String associateSalUUID;
	
	List<AssociatePayItemDTO> associatePayItemDto;
	
	List<PayItemDTO> orgPayItems;
	
	private String previousPayType;
	
	private String previousPayFrequency;
	
	private String previousStandardHours;
	
	private String previousAnnualSal;
	
	private String previousPerPaySal;
	
	private String previousHourlyRate;
	
	private String salaryType;
	
	private String effectiveDate;
	
	private String increasePer;
	
	private String changeReason;
	
	private String payGroupUUID;
	
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
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
		if(StringUtils.isNotBlank(standardHours)) {
			this.standardHours = standardHours;
		}else {
			this.standardHours = "0";
		}
		
	}
	public String getAnnualSal()
	{
		return annualSal;
	}
	public void setAnnualSal(String annualSal)
	{
		if(StringUtils.isNotBlank(annualSal)) {
			this.annualSal = annualSal;
		}else {
			this.annualSal = "0";
		}
		
	}
	public String getPerPaySal()
	{
		return perPaySal;
	}
	public void setPerPaySal(String perPaySal)
	{
		if(StringUtils.isNotBlank(perPaySal)) {
			this.perPaySal = perPaySal;
		}else {
			this.perPaySal = "0";
		}
	}
	public String getHourlyRate()
	{
		return hourlyRate;
	}
	public void setHourlyRate(String hourlyRate)
	{
		if(StringUtils.isNotBlank(hourlyRate)) {
			this.hourlyRate = hourlyRate;
		}else {
			this.hourlyRate = "0";
		}
		
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
	public Long getUserId()
	{
		return userId;
	}
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	public String getAssociateSalUUID()
	{
		return associateSalUUID;
	}
	public void setAssociateSalUUID(String associateSalUUID)
	{
		this.associateSalUUID = associateSalUUID;
	}
	public String getPreviousPayType()
	{
		return previousPayType;
	}
	public void setPreviousPayType(String previousPayType)
	{
		this.previousPayType = previousPayType;
	}
	public String getPreviousPayFrequency()
	{
		return previousPayFrequency;
	}
	public void setPreviousPayFrequency(String previousPayFrequency)
	{
		this.previousPayFrequency = previousPayFrequency;
	}
	public String getPreviousStandardHours()
	{
		return previousStandardHours;
	}
	public void setPreviousStandardHours(String previousStandardHours)
	{
		this.previousStandardHours = previousStandardHours;
	}
	public String getPreviousAnnualSal()
	{
		return previousAnnualSal;
	}
	public void setPreviousAnnualSal(String previousAnnualSal)
	{
		this.previousAnnualSal = previousAnnualSal;
	}
	public String getPreviousPerPaySal()
	{
		return previousPerPaySal;
	}
	public void setPreviousPerPaySal(String previousPerPaySal)
	{
		this.previousPerPaySal = previousPerPaySal;
	}
	public String getPreviousHourlyRate()
	{
		return previousHourlyRate;
	}
	public void setPreviousHourlyRate(String previousHourlyRate)
	{
		this.previousHourlyRate = previousHourlyRate;
	}
	public String getSalaryType()
	{
		return salaryType;
	}
	public void setSalaryType(String salaryType)
	{
		this.salaryType = salaryType;
	}
	public String getEffectiveDate()
	{
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}
	public String getIncreasePer()
	{
		return increasePer;
	}
	public void setIncreasePer(String increasePer)
	{
		this.increasePer = increasePer;
	}
	public String getChangeReason()
	{
		return changeReason;
	}
	public void setChangeReason(String changeReason)
	{
		this.changeReason = changeReason;
	}
	public String getPayGroup() {
		return payGroup;
	}
	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}
	public String getPayGroupUUID() {
		return payGroupUUID;
	}
	public void setPayGroupUUID(String payGroupUUID) {
		this.payGroupUUID = payGroupUUID;
	}
	public List<PayItemDTO> getOrgPayItems() {
		return orgPayItems;
	}
	public void setOrgPayItems(List<PayItemDTO> orgPayItems) {
		this.orgPayItems = orgPayItems;
	}
	
}
