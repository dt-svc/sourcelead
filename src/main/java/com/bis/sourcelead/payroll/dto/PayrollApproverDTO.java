package com.bis.sourcelead.payroll.dto;

import java.util.List;

public class PayrollApproverDTO {
 
	private List<String> approver;
	
	private String automaticPayroll;
	
	private String payrollType;
	
	private String confAprrover;
	
    private String userId;
	
	private String approverName;
	
	private String roleName;
	
	private String payrollFrequencyUUID;
	
	private String payrollFrequencyId;
	
	private List<USPayrollDTO> uspayroll;
	
	private String confirmType;

	private String payPeriodNum;
	
	private String payType;
	
	private String payrollFrequency;
	
	
	
	public String getPayrollFrequency()
	{
		return payrollFrequency;
	}

	public void setPayrollFrequency(String payrollFrequency)
	{
		this.payrollFrequency = payrollFrequency;
	}

	public String getConfAprrover()
	{
		return confAprrover;
	}

	public void setConfAprrover(String confAprrover)
	{
		this.confAprrover = confAprrover;
	}

	public List<String> getApprover()
	{
		return approver;
	}

	public String getAutomaticPayroll()
	{
		return automaticPayroll;
	}

	public String getPayrollType()
	{
		return payrollType;
	}

	public void setApprover(List<String> approver)
	{
		this.approver = approver;
	}

	public void setAutomaticPayroll(String automaticPayroll)
	{
		this.automaticPayroll = automaticPayroll;
	}

	public void setPayrollType(String payrollType)
	{
		this.payrollType = payrollType;
	}

	public String getUserId()
	{
		return userId;
	}

	public String getApproverName()
	{
		return approverName;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public void setApproverName(String approverName)
	{
		this.approverName = approverName;
	}

	public String getRoleName()
	{
		return roleName;
	}

	public void setRoleName(String roleName)
	{
		this.roleName = roleName;
	}


	public String getPayrollFrequencyUUID()
	{
		return payrollFrequencyUUID;
	}

	public String getPayrollFrequencyId()
	{
		return payrollFrequencyId;
	}

	public void setPayrollFrequencyUUID(String payrollFrequencyUUID)
	{
		this.payrollFrequencyUUID = payrollFrequencyUUID;
	}

	public void setPayrollFrequencyId(String payrollFrequencyId)
	{
		this.payrollFrequencyId = payrollFrequencyId;
	}

	public List<USPayrollDTO> getUspayroll()
	{
		return uspayroll;
	}

	public void setUspayroll(List<USPayrollDTO> uspayroll)
	{
		this.uspayroll = uspayroll;
	}

	public String getConfirmType()
	{
		return confirmType;
	}

	public void setConfirmType(String confirmType)
	{
		this.confirmType = confirmType;
	}

	public String getPayPeriodNum()
	{
		return payPeriodNum;
	}

	public void setPayPeriodNum(String payPeriodNum)
	{
		this.payPeriodNum = payPeriodNum;
	}

	public String getPayType()
	{
		return payType;
	}

	public void setPayType(String payType)
	{
		this.payType = payType;
	}
	
	
	
}


