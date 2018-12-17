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
	
	private String orgDepartments;
	
	private String userRole;
	
	private String leaveTypeName;
	
	private List<Long> departmentList;
	
	private List<Long> reportRoles;
	
	private List<Long> designationType;
	
	private List<Long> associateList;

	private List<Long> leaveTypeList;
	
	private String fromHalfDay;

	private String toHalfDay;

	private String fromDateFromHalfTime;
	
	private String fromDateToHalfTime;
	
	private String toDateFromHalfTime;
	
	private String toDateToHalfTime;
	
	private String includeHolidays;
	
	private String includeWeekoff;
	
	private float noOfDays;
	
	private Long userId;
	
	private String eid;
	
	private String status;

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

	public String getFromHalfDay()
	{
		return fromHalfDay;
	}

	public void setFromHalfDay(String fromHalfDay)
	{
		this.fromHalfDay = fromHalfDay;
	}

	public String getToHalfDay()
	{
		return toHalfDay;
	}

	public void setToHalfDay(String toHalfDay)
	{
		this.toHalfDay = toHalfDay;
	}

	public String getFromDateFromHalfTime()
	{
		return fromDateFromHalfTime;
	}

	public void setFromDateFromHalfTime(String fromDateFromHalfTime)
	{
		this.fromDateFromHalfTime = fromDateFromHalfTime;
	}

	public String getFromDateToHalfTime()
	{
		return fromDateToHalfTime;
	}

	public void setFromDateToHalfTime(String fromDateToHalfTime)
	{
		this.fromDateToHalfTime = fromDateToHalfTime;
	}

	public String getToDateFromHalfTime()
	{
		return toDateFromHalfTime;
	}

	public void setToDateFromHalfTime(String toDateFromHalfTime)
	{
		this.toDateFromHalfTime = toDateFromHalfTime;
	}

	public String getToDateToHalfTime()
	{
		return toDateToHalfTime;
	}

	public void setToDateToHalfTime(String toDateToHalfTime)
	{
		this.toDateToHalfTime = toDateToHalfTime;
	}

	public String getIncludeHolidays()
	{
		return includeHolidays;
	}

	public void setIncludeHolidays(String includeHolidays)
	{
		this.includeHolidays = includeHolidays;
	}

	public String getIncludeWeekoff()
	{
		return includeWeekoff;
	}

	public void setIncludeWeekoff(String includeWeekoff)
	{
		this.includeWeekoff = includeWeekoff;
	}

	public float getNoOfDays()
	{
		return noOfDays;
	}

	public void setNoOfDays(float noOfDays)
	{
		this.noOfDays = noOfDays;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getEid()
	{
		return eid;
	}

	public void setEid(String eid)
	{
		this.eid = eid;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}


}
