package com.bis.sourcelead.dto;

import java.util.List;

public class OrgStorageDetailDTO {
	private long orgId;
	private String orgName;
	private long size;
	private List<OrgLocationStorageDetailDTO> locationStorageDetail;
	
	public long getOrgId()
	{
		return orgId;
	}
	
	public void setOrgId(long orgId)
	{
		this.orgId = orgId;
	}
	
	public String getOrgName()
	{
		return orgName;
	}
	
	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}
	
	public long getSize()
	{
		return size;
	}
	
	public void setSize(long size)
	{
		this.size = size;
	}
	
	public List<OrgLocationStorageDetailDTO> getLocationStorageDetail()
	{
		return locationStorageDetail;
	}
	
	public void setLocationStorageDetail(List<OrgLocationStorageDetailDTO> locationStorageDetail)
	{
		this.locationStorageDetail = locationStorageDetail;
	}
}
