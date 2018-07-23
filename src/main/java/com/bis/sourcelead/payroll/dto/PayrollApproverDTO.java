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
	
	
}


