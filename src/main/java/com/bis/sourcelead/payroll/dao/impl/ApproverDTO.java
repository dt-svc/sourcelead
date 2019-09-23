package com.bis.sourcelead.payroll.dao.impl;

import java.util.List;

import com.bis.sourcelead.dto.UserDTO;
import com.bis.sourcelead.payroll.entity.ConfigurePayrollApprover;

public class ApproverDTO {
	
	private List<UserDTO> user;
	
	private String payrollType;
	
	private String automaticPayroll;
	
	private List<Long> payrollApprover;
	
	private String confAprrover;
	
	private String automaticFrequency;
	

	public String getConfAprrover()
	{
		return confAprrover;
	}


	public void setConfAprrover(String confAprrover)
	{
		this.confAprrover = confAprrover;
	}


	public List<Long> getPayrollApprover()
	{
		return payrollApprover;
	}


	public void setPayrollApprover(List<Long> payrollApprover)
	{
		this.payrollApprover = payrollApprover;
	}


	public List<UserDTO> getUser()
	{
		return user;
	}

	public void setUser(List<UserDTO> user)
	{
		this.user = user;
	}


	public String getPayrollType()
	{
		return payrollType;
	}


	public String getAutomaticPayroll()
	{
		return automaticPayroll;
	}


	public void setPayrollType(String payrollType)
	{
		this.payrollType = payrollType;
	}


	public void setAutomaticPayroll(String automaticPayroll)
	{
		this.automaticPayroll = automaticPayroll;
	}


	public String getAutomaticFrequency()
	{
		return automaticFrequency;
	}


	public void setAutomaticFrequency(String automaticFrequency)
	{
		this.automaticFrequency = automaticFrequency;
	}

	
	
	
	
}
