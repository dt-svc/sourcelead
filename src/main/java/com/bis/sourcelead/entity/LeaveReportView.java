package com.bis.sourcelead.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "LEAVE_REPORT_VIEW")
public class LeaveReportView {
	
	@Id
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "USER_ID", nullable = false)
	private Long userId;
	
	@Column(name = "ORG_ID", nullable = false)
	private Long orgId;
	
	@Column(name = "EMPLOYEE_NAME")
	private String name;
	
	@Column(name = "ORGLOCATION_ID", nullable = false)
	private Long orglocationId;
	
	@Column(name = "REPORTING_MANAGER_ID", nullable = false)
	private Long reportingManagerId;
	
	@Column(name = "Employee_ID", nullable = false)
	private Long employeeId;
	
	@Column(name = "Emp_ID", nullable = false)
	private String eid;
	
	@Column(name = "REASON",columnDefinition = "TEXT default NULL")
	private String reason;
	
	@Column(name = "FROM_DATE")
	private Date fromDate;
	
	@Column(name = "TO_DATE")
	private Date toDate;
	
	@Column(name = "LEAVE_STATUS")
	private String status;
	
	@Column(name = "LEAVE_TYPE_ID")
	private Long leaveTypeId;
	
	@Column(name = "LEAVE_TYPE_NAME")
	private String leaveTypeName;

	@Column(name = "APPROVER_NAME" , columnDefinition = "TEXT default NULL")
    private String approver;
	
	@Column(name = "LEAVE_TYPE_BILLABLE", columnDefinition = "TEXT default NULL")
	private String leaveTypeBillable;
	
	@Column(name = "DURATION", columnDefinition = "TEXT default NULL")
	private String duration;
	
	@Column(name = "EMP_TYPE", columnDefinition = "TEXT default NULL")
	private String employmentType;
	
	@Column(name = "USER_DEPT_NAME", columnDefinition = "TEXT default NULL")
	private String userDeptName;
	
	@Column(name = "USER_ROLE_NAME")
	private String userRoleName;
	
	@Column(name = "ROLE_ID", nullable = false)
	private Long roleId;
	
	@Column(name = "DEPARTMENT_ID", nullable = false)
	private Long deptId;
	
	@Column(name = "CREATED_DATE")
	private Date appliedOn;
	
	@Column(name = "UPDATED_DATE")
	private Date approvedOn; 
	
	@Column(name = "REPORTING_MANAGER")
	private String ReportingManager;
	@Transient
	private List<Map<String, String>> order;
	
	@Column(name = "FROM_HALF_DAY")
	private String fromHalfDay;

	@Column(name = "TO_HALF_DAY")
	private String toHalfDay;

	@Column(name = "FROM_DATE_FROM_TIME")
	private String fromDateFromHalfTime;
	
	@Column(name = "FROM_DATE_TO_TIME")
	private String fromDateToHalfTime;
	
	@Column(name = "TO_DATE_FROM_TIME")
	private String toDateFromHalfTime;
	
	@Column(name = "TO_DATE_TO_TIME")
	private String toDateToHalfTime;
	
	@Column(name = "INCLUDE_HOLIDAYS")
	private String includeHolidays;
	
	@Column(name = "INCLUDE_WEEKOFF")
	private String includeWeekoff;
	@Transient
	private String sortColumn;
	@Transient
	private String sortOrder;
	
	@Transient
	@JsonProperty
	private String fromDateStr;
	
	@Transient
	@JsonProperty
	private String toDateStr;
	
	@Transient
	@JsonProperty
	private String appliedOnDateStr;
	
	@Transient
	@JsonProperty
	private String approvedOnDateStr;
	
	@Transient
	@JsonProperty
	private String noOfDays;
	
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

	public Date getFromDate()
	{
		return fromDate;
	}
	
	public void setFromDate(Date fromDate)
	{
		this.fromDate = fromDate;
	}
	
	public Date getToDate()
	{
		return toDate;
	}
	
	public void setToDate(Date toDate)
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
	
	public String getEmploymentType()
	{
		return employmentType;
	}
	
	public void setEmploymentType(String employmentType)
	{
		this.employmentType = employmentType;
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

	public List<Map<String, String>> getOrder()
	{
		return order;
	}

	public void setOrder(List<Map<String, String>> order)
	{
		this.order = order;
	}

	public String getSortColumn()
	{
		return sortColumn;
	}

	public void setSortColumn(String sortColumn)
	{
		this.sortColumn = sortColumn;
	}

	public String getSortOrder()
	{
		return sortOrder;
	}

	public void setSortOrder(String sortOrder)
	{
		this.sortOrder = sortOrder;
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

	public Date getAppliedOn()
	{
		return appliedOn;
	}

	public void setAppliedOn(Date appliedOn)
	{
		this.appliedOn = appliedOn;
	}

	public Date getApprovedOn()
	{
		return approvedOn;
	}

	public void setApprovedOn(Date approvedOn)
	{
		this.approvedOn = approvedOn;
	}

	public String getAppliedOnDateStr()
	{
		return appliedOnDateStr;
	}

	public void setAppliedOnDateStr(String appliedOnDateStr)
	{
		this.appliedOnDateStr = appliedOnDateStr;
	}

	public String getApprovedOnDateStr()
	{
		return approvedOnDateStr;
	}

	public void setApprovedOnDateStr(String approvedOnDateStr)
	{
		this.approvedOnDateStr = approvedOnDateStr;
	}

	public Long getEmployeeId()
	{
		return employeeId;
	}

	public void setEmployeeId(Long employeeId)
	{
		this.employeeId = employeeId;
	}

	public Long getReportingManagerId()
	{
		return reportingManagerId;
	}

	public void setReportingManagerId(Long reportingManagerId)
	{
		this.reportingManagerId = reportingManagerId;
	}

	public String getReportingManager()
	{
		return ReportingManager;
	}

	public void setReportingManager(String reportingManager)
	{
		ReportingManager = reportingManager;
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

	public String getNoOfDays()
	{
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays)
	{
		this.noOfDays = noOfDays;
	}

	public String getEid()
	{
		return eid;
	}

	public void setEid(String eid)
	{
		this.eid = eid;
	}


}
