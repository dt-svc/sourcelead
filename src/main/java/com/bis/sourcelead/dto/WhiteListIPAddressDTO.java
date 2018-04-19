package com.bis.sourcelead.dto;

import com.bis.sourcelead.annotations.Required;

public class WhiteListIPAddressDTO {
	private long id;
	
	@Required(desc="fromIpAddress")
	private String fromIpAddress;
	
	@Required(desc="toIpAddress")
	private String toIpAddress;
	
	private String status;
	private String createdBy;
	private String updatedBy;
	private String createdDate;
	private String updatedDate;
	private String createdUserName;
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public String getFromIpAddress()
	{
		return fromIpAddress;
	}
	
	public void setFromIpAddress(String fromIpAddress)
	{
		this.fromIpAddress = fromIpAddress;
	}
	
	public String getToIpAddress()
	{
		return toIpAddress;
	}
	
	public void setToIpAddress(String toIpAddress)
	{
		this.toIpAddress = toIpAddress;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	public String getCreatedBy()
	{
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}
	
	public String getUpdatedBy()
	{
		return updatedBy;
	}
	
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	
	public String getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(String createdDate)
	{
		this.createdDate = createdDate;
	}
	
	public String getUpdatedDate()
	{
		return updatedDate;
	}
	
	public void setUpdatedDate(String updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	
	public String getCreatedUserName()
	{
		return createdUserName;
	}
	
	public void setCreatedUserName(String createdUserName)
	{
		this.createdUserName = createdUserName;
	}
}
