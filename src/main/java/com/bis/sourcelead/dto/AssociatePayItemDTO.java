package com.bis.sourcelead.dto;

import java.math.BigDecimal;
import java.util.List;

import com.bis.sourcelead.payroll.dto.PayItemDTO;

public class AssociatePayItemDTO {
	
	private String id;
	private Long payItemId;
	private String associateSalaryId;
	private String payItemName;
	private String payItemSchedule;
	private String overRide;
	private String effectiveDate;
    private String endDate;
    private String payItemAmount;
    private BigDecimal amount;
	private String status;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String updatedBy;
	private String payItemCheck;
	private String prePayItemName;
	private String prePayItemBlock;
	private String prePayItemAmt;
	private double payItemAmt;
	private String isBlocked;
	
	
	public String getIsBlocked()
	{
		return isBlocked;
	}
	public void setIsBlocked(String isBlocked)
	{
		this.isBlocked = isBlocked;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getAssociateSalaryId()
	{
		return associateSalaryId;
	}
	public void setAssociateSalaryId(String associateSalaryId)
	{
		this.associateSalaryId = associateSalaryId;
	}
	public String getPayItemName()
	{
		return payItemName;
	}
	public void setPayItemName(String payItemName)
	{
		this.payItemName = payItemName;
	}
	public String getPayItemSchedule()
	{
		return payItemSchedule;
	}
	public void setPayItemSchedule(String payItemSchedule)
	{
		this.payItemSchedule = payItemSchedule;
	}
	public String getOverRide()
	{
		return overRide;
	}
	public void setOverRide(String overRide)
	{
		this.overRide = overRide;
	}
	public String getEffectiveDate()
	{
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}
	public String getEndDate()
	{
		return endDate;
	}
	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}
	public String getPayItemAmount()
	{
		return payItemAmount;
	}
	public void setPayItemAmount(String payItemAmount)
	{
		this.payItemAmount = payItemAmount;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getCreatedDate()
	{
		return createdDate;
	}
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	public String getCreatedBy()
	{
		return createdBy;
	}
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	public String getUpdatedDate()
	{
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	public String getPayItemCheck()
	{
		return payItemCheck;
	}
	public void setPayItemCheck(String payItemCheck)
	{
		this.payItemCheck = payItemCheck;
	}
	public Long getPayItemId()
	{
		return payItemId;
	}
	public void setPayItemId(Long payItemId)
	{
		this.payItemId = payItemId;
	}
	public BigDecimal getAmount()
	{
		return amount;
	}
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
	public String getPrePayItemName()
	{
		return prePayItemName;
	}
	public String getPrePayItemBlock()
	{
		return prePayItemBlock;
	}
	public String getPrePayItemAmt()
	{
		return prePayItemAmt;
	}
	public void setPrePayItemName(String prePayItemName)
	{
		this.prePayItemName = prePayItemName;
	}
	public void setPrePayItemBlock(String prePayItemBlock)
	{
		this.prePayItemBlock = prePayItemBlock;
	}
	public void setPrePayItemAmt(String prePayItemAmt)
	{
		this.prePayItemAmt = prePayItemAmt;
	}
	public double getPayItemAmt()
	{
		return payItemAmt;
	}
	public void setPayItemAmt(double payItemAmt)
	{
		this.payItemAmt = payItemAmt;
	}
	
}
