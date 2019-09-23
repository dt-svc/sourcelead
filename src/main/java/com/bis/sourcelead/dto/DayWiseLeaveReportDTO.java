package com.bis.sourcelead.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
 
public class DayWiseLeaveReportDTO extends SearchDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String searchValue;
	private String userRole;
	private String orgDepartments;
	private String employmentType;
	private String fromDate;
	
	private String exportType;
	private Long loginUserId;
	private Long reportingManagerId;
	private Long id;
	private Long userId;
	private Long orgId;
	private String name;
	private Long orglocationId;
	private String reason;
	private String toDate;
	private String status;
	private Long leaveTypeId;
	private String leaveTypeName;
	private String approver;
	private String leaveTypeBillable;
	private String duration;
	
	private String userDeptName;
	
	private String userRoleName;
	
	private Long roleId;
	
	private Long deptId;
	
	private String appliedOn;
	
	private String approvedOn;
	
	private String fromDateStr;
	
	private String toDateStr;

	private String ReportingManager;
	
	public String getSearchValue()
	{
		return searchValue;
	}
	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}
	public String getUserRole()
	{
		return userRole;
	}
	public void setUserRole(String userRole)
	{
		this.userRole = userRole;
	}
	public String getOrgDepartments()
	{
		return orgDepartments;
	}
	public void setOrgDepartments(String orgDepartments)
	{
		this.orgDepartments = orgDepartments;
	}
	public String getEmploymentType()
	{
		return employmentType;
	}
	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
	}
	public String getFromDate()
	{
		return fromDate;
	}
	public void setFromDate(String fromDate)
	{
		this.fromDate = fromDate;
	}
	public String getExportType()
	{
		return exportType;
	}
	public void setExportType(String exportType)
	{
		this.exportType = exportType;
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getUserId()
	{
		return userId;
	}
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	public Long getOrgId()
	{
		return orgId;
	}
	public void setOrgId(Long orgId)
	{
		this.orgId = orgId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Long getOrglocationId()
	{
		return orglocationId;
	}
	public void setOrglocationId(Long orglocationId)
	{
		this.orglocationId = orglocationId;
	}
	public String getReason()
	{
		return reason;
	}
	public void setReason(String reason)
	{
		this.reason = reason;
	}
	public String getToDate()
	{
		return toDate;
	}
	public void setToDate(String toDate)
	{
		this.toDate = toDate;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public Long getLeaveTypeId()
	{
		return leaveTypeId;
	}
	public void setLeaveTypeId(Long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}
	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}
	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}
	public String getApprover()
	{
		return approver;
	}
	public void setApprover(String approver)
	{
		this.approver = approver;
	}
	public String getLeaveTypeBillable()
	{
		return leaveTypeBillable;
	}
	public void setLeaveTypeBillable(String leaveTypeBillable)
	{
		this.leaveTypeBillable = leaveTypeBillable;
	}
	public String getDuration()
	{
		return duration;
	}
	public void setDuration(String duration)
	{
		this.duration = duration;
	}
	public String getUserDeptName()
	{
		return userDeptName;
	}
	public void setUserDeptName(String userDeptName)
	{
		this.userDeptName = userDeptName;
	}
	public String getUserRoleName()
	{
		return userRoleName;
	}
	public void setUserRoleName(String userRoleName)
	{
		this.userRoleName = userRoleName;
	}
	public Long getRoleId()
	{
		return roleId;
	}
	public void setRoleId(Long roleId)
	{
		this.roleId = roleId;
	}
	public Long getDeptId()
	{
		return deptId;
	}
	public void setDeptId(Long deptId)
	{
		this.deptId = deptId;
	}
	public String getAppliedOn()
	{
		return appliedOn;
	}
	public void setAppliedOn(String appliedOn)
	{
		this.appliedOn = appliedOn;
	}
	public String getApprovedOn()
	{
		return approvedOn;
	}
	public void setApprovedOn(String approvedOn)
	{
		this.approvedOn = approvedOn;
	}
	public String getFromDateStr()
	{
		return fromDateStr;
	}
	public void setFromDateStr(String fromDateStr)
	{
		this.fromDateStr = fromDateStr;
	}
	public String getToDateStr()
	{
		return toDateStr;
	}
	public void setToDateStr(String toDateStr)
	{
		this.toDateStr = toDateStr;
	}

	public Long getReportingManagerId()
	{
		return reportingManagerId;
	}
	public void setReportingManagerId(Long reportingManagerId)
	{
		this.reportingManagerId = reportingManagerId;
	}
	public Long getLoginUserId()
	{
		return loginUserId;
	}
	public void setLoginUserId(Long loginUserId)
	{
		this.loginUserId = loginUserId;
	}
	public String getReportingManager()
	{
		return ReportingManager;
	}
	public void setReportingManager(String reportingManager)
	{
		ReportingManager = reportingManager;
	}
	
	
}
