package com.bis.sourcelead.payroll.dto;

import java.util.List;

public class PaysheetFrequencyDTO {
	
	private String id;
	
	private String groupId;
	
	private String payPeriod;
	
	private String orgLocationId;
	
	private List<USPaysheetDTO> uSPaysheetDTO;
	
	private List<USPayrollDTO> payrollDTO;
	
	private String payrollFrequencyUUID;
	
	public String getGroupId()
	{
		return groupId;
	}
	public String getPayPeriod()
	{
		return payPeriod;
	}
	public String getOrgLocationId()
	{
		return orgLocationId;
	}
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}
	public void setPayPeriod(String payPeriod)
	{
		this.payPeriod = payPeriod;
	}
	public void setOrgLocationId(String orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}
	public List<USPaysheetDTO> getuSPaysheetDTO()
	{
		return uSPaysheetDTO;
	}
	public void setuSPaysheetDTO(List<USPaysheetDTO> uSPaysheetDTO)
	{
		this.uSPaysheetDTO = uSPaysheetDTO;
	}
	public String getPayrollFrequencyUUID()
	{
		return payrollFrequencyUUID;
	}
	public void setPayrollFrequencyUUID(String payrollFrequencyUUID)
	{
		this.payrollFrequencyUUID = payrollFrequencyUUID;
	}
	public List<USPayrollDTO> getPayrollDTO()
	{
		return payrollDTO;
	}
	public void setPayrollDTO(List<USPayrollDTO> payrollDTO)
	{
		this.payrollDTO = payrollDTO;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	
	
}
