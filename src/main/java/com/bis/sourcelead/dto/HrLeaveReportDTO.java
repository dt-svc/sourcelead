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
	
	private List<String> employmentType;
	
	
	private List<String> departmentIds;
	
	private String exportType;
	
	private List<String> reportRolesId;
	
	private List<String> associateListId;
	
	private List<String> leaveTypeIds;
	
	private String templateType;
	
	private String orgDepartments;
	
	private String userRole;
	
	private String leaveTypeName;
	
	private List<Long> departmentList;
	
	private List<Long> reportRoles;
	
	private List<Long> designationType;
	
	private List<Long> associateList;

	private List<Long> leaveTypeList;

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

	public List<String> getEmploymentType()
	{
		return employmentType;
	}

	public void setEmploymentType(List<String> employmentType)
	{
		this.employmentType = employmentType;
	}

	public List<String> getDepartmentIds()
	{
		return departmentIds;
	}

	public void setDepartmentIds(List<String> departmentIds)
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

	public List<String> getReportRolesId()
	{
		return reportRolesId;
	}

	public void setReportRolesId(List<String> reportRolesId)
	{
		this.reportRolesId = reportRolesId;
	}

	public List<String> getAssociateListId()
	{
		return associateListId;
	}

	public void setAssociateListId(List<String> associateListId)
	{
		this.associateListId = associateListId;
	}

	public List<String> getLeaveTypeIds()
	{
		return leaveTypeIds;
	}

	public void setLeaveTypeIds(List<String> leaveTypeIds)
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

	public String getOrgDepartments()
	{
		return orgDepartments;
	}

	public void setOrgDepartments(String orgDepartments)
	{
		this.orgDepartments = orgDepartments;
	}

	public String getUserRole()
	{
		return userRole;
	}

	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}

	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}

	public List<Long> getDepartmentList()
	{
		return departmentList;
	}

	public void setDepartmentList(List<Long> departmentList)
	{
		this.departmentList = departmentList;
	}

	public List<Long> getReportRoles()
	{
		return reportRoles;
	}

	public void setReportRoles(List<Long> reportRoles)
	{
		this.reportRoles = reportRoles;
	}

	public List<Long> getDesignationType()
	{
		return designationType;
	}

	public void setDesignationType(List<Long> designationType)
	{
		this.designationType = designationType;
	}

	public List<Long> getAssociateList()
	{
		return associateList;
	}

	public void setAssociateList(List<Long> associateList)
	{
		this.associateList = associateList;
	}

	public List<Long> getLeaveTypeList()
	{
		return leaveTypeList;
	}

	public void setLeaveTypeList(List<Long> leaveTypeList)
	{
		this.leaveTypeList = leaveTypeList;
	}

	
	
}
