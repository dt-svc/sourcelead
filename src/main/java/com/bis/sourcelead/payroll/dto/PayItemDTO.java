package com.bis.sourcelead.payroll.dto;

import java.util.List;

import com.bis.sourcelead.annotations.Required;

public class PayItemDTO {
	
	private Long id;
	
	@Required(desc = "Pay Item Name")
	private String payItemName;
	
	private String paySchedule;
	
	private List<String> allowEmployeementType;
	
	private String description;
	
	private String allowOverTime;
	
	private String empType;
	
	private String payItemUUID;
	
	private String associateEmpType;
	
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getPayItemName()
	{
		return payItemName;
	}
	public void setPayItemName(String payItemName)
	{
		this.payItemName = payItemName;
	}
	public String getPaySchedule()
	{
		return paySchedule;
	}
	public void setPaySchedule(String paySchedule)
	{
		this.paySchedule = paySchedule;
	}
	public List<String> getAllowEmployeementType()
	{
		return allowEmployeementType;
	}
	public void setAllowEmployeementType(List<String> allowEmployeementType)
	{
		this.allowEmployeementType = allowEmployeementType;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getAllowOverTime()
	{
		return allowOverTime;
	}
	public void setAllowOverTime(String allowOverTime)
	{
		this.allowOverTime = allowOverTime;
	}
	public String getEmpType()
	{
		return empType;
	}
	public void setEmpType(String empType)
	{
		this.empType = empType;
	}
	public String getPayItemUUID()
	{
		return payItemUUID;
	}
	public void setPayItemUUID(String payItemUUID)
	{
		this.payItemUUID = payItemUUID;
	}
	public String getAssociateEmpType()
	{
		return associateEmpType;
	}
	public void setAssociateEmpType(String associateEmpType)
	{
		this.associateEmpType = associateEmpType;
	}
	
	
}
