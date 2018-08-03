package com.bis.sourcelead.payroll.dto;

import java.util.List;

import com.bis.sourcelead.dto.AssociatePayItemDTO;

public class PayAdjustmentDTO {
	
	private String payrollId;
	
	private String payrollUUID;
	
	private String depositType;
	
	private String regularHours;
	
	private String regularHoursAmt;
	
	private String overTimeHours;
	
	private String overTimeHoursAmt;
	
	private List<AssociatePayItemDTO> payItem;
	
	private List<DeductionDTO> preDeduction;
	
	private List<DeductionDTO> postDeduction;
	
	private List<ContributionDTO> contribution;
	

	public String getDepositType()
	{
		return depositType;
	}

	public String getRegularHours()
	{
		return regularHours;
	}

	public String getRegularHoursAmt()
	{
		return regularHoursAmt;
	}

	public String getOverTimeHours()
	{
		return overTimeHours;
	}

	public String getOverTimeHoursAmt()
	{
		return overTimeHoursAmt;
	}

	public void setDepositType(String depositType)
	{
		this.depositType = depositType;
	}

	public void setRegularHours(String regularHours)
	{
		this.regularHours = regularHours;
	}

	public void setRegularHoursAmt(String regularHoursAmt)
	{
		this.regularHoursAmt = regularHoursAmt;
	}

	public void setOverTimeHours(String overTimeHours)
	{
		this.overTimeHours = overTimeHours;
	}

	public void setOverTimeHoursAmt(String overTimeHoursAmt)
	{
		this.overTimeHoursAmt = overTimeHoursAmt;
	}

	public String getPayrollId()
	{
		return payrollId;
	}

	public String getPayrollUUID()
	{
		return payrollUUID;
	}

	public List<AssociatePayItemDTO> getPayItem()
	{
		return payItem;
	}

	public List<DeductionDTO> getPreDeduction()
	{
		return preDeduction;
	}

	public List<DeductionDTO> getPostDeduction()
	{
		return postDeduction;
	}

	public void setPayrollId(String payrollId)
	{
		this.payrollId = payrollId;
	}

	public void setPayrollUUID(String payrollUUID)
	{
		this.payrollUUID = payrollUUID;
	}

	public void setPayItem(List<AssociatePayItemDTO> payItem)
	{
		this.payItem = payItem;
	}

	public void setPreDeduction(List<DeductionDTO> preDeduction)
	{
		this.preDeduction = preDeduction;
	}

	public void setPostDeduction(List<DeductionDTO> postDeduction)
	{
		this.postDeduction = postDeduction;
	}

	public List<ContributionDTO> getContribution()
	{
		return contribution;
	}

	public void setContribution(List<ContributionDTO> contribution)
	{
		this.contribution = contribution;
	}
	
	
	
}
