package com.bis.sourcelead.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HrLeaveReportDTO extends SearchDTO {

	private static final long serialVersionUID = -3365114820091971186L;

	private String startDate;
	
	private String endDate;
	
	private Long locationsId;
	
	private Long loginUserId;
	
	private String employmentType;
	
	
	private String departmentIds;
	
	private String exportType;
	
	private String reportRolesId;
	
	private String associateListId;
	
	private String leaveTypeIds;
	
	private String templateType;

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public Long getLocationsId()
	{
		return locationsId;
	}

	public void setLocationsId(Long locationsId)
	{
		this.locationsId = locationsId;
	}

	public Long getLoginUserId()
	{
		return loginUserId;
	}

	public void setLoginUserId(Long loginUserId)
	{
		this.loginUserId = loginUserId;
	}

	public String getEmploymentType()
	{
		return employmentType;
	}

	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
	}

	public String getDepartmentIds()
	{
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds)
	{
		this.departmentIds = departmentIds;
	}

	public String getExportType()
	{
		return exportType;
	}

	public void setExportType(String exportType)
	{
		this.exportType = exportType;
	}

	public String getReportRolesId()
	{
		return reportRolesId;
	}

	public void setReportRolesId(String reportRolesId)
	{
		this.reportRolesId = reportRolesId;
	}

	public String getAssociateListId()
	{
		return associateListId;
	}

	public void setAssociateListId(String associateListId)
	{
		this.associateListId = associateListId;
	}

	public String getLeaveTypeIds()
	{
		return leaveTypeIds;
	}

	public void setLeaveTypeIds(String leaveTypeIds)
	{
		this.leaveTypeIds = leaveTypeIds;
	}

	public String getTemplateType()
	{
		return templateType;
	}

	public void setTemplateType(String templateType)
	{
		this.templateType = templateType;
	}
	
}
