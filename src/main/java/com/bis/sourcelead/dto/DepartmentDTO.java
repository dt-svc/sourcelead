package com.bis.sourcelead.dto;

import java.math.BigInteger;
import java.util.Date;


import com.bis.sourcelead.annotations.Required;
import com.bis.sourcelead.entity.OrgLocations;

public class DepartmentDTO {
	
	@Required(desc="departmentName")
	private String departmentName;
	
	private String departmentDesc;
	
	private Long departmentId;
	
	private Integer status;
	   		
	private Date createdDate;
	   	
	private String createdBy;
	   	
	private Date updatedDate;
	   	
	private String updatedBy;
	   	
	private OrgLocations orgLocationId;
	
	private BigInteger deptId;

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public String getDepartmentDesc()
	{
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc)
	{
		this.departmentDesc = departmentDesc;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public void setCreatedDate(Date createdDate)
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

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate)
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

	public OrgLocations getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public BigInteger getDeptId() {
		return deptId;
	}

	public void setDeptId(BigInteger deptId) {
		this.deptId = deptId;
	}
	
	
}
