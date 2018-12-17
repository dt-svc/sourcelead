package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APPLIED_LEAVE_DATE_VIEW")
public class AppliedLeaveDateView {
	
	@Id
    @Column(name = "ID", nullable = false)
	private Long id;
	
    @Column(name = "USER_ID", nullable = false)
    private Long userId;
	
	@Column(name = "ORGLOCATION_ID", nullable = false)
	private Long orglocationId;
	
	@Column (name = "LEAVE_STATUS")
	private String leaveStatus;

	@Column(name = "REASON",columnDefinition = "TEXT default NULL")
	private String reason;
	
	@Column (name = "APPLY_LEAVE_DATE")
	private Date applyLeaveDate;
	
	@Column (name = "FROM_DATE")
	private Date fromDate;
	
	@Column (name = "TO_DATE")
	private Date toDate;
	
	@Column (name = "APPLY_MONTH_NAME")
	private String applyMonthName;
	
	@Column (name = "IS_PAYROLL_INCLUDE")
	private String isPayrollInclude;
	
	@Column (name = "STATUS")
	private Integer status;
	
	@Column (name = "LEAVE_TYPE_ID")
	private Long leaveTypeId;
	
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
	
	@Column(name = "ORG_ID")
	private Long orgId;

	public Long getUserId()
	{
		return userId;
	}

	public Date getApplyLeaveDate()
	{
		return applyLeaveDate;
	}

	public String getApplyMonthName()
	{
		return applyMonthName;
	}

	public String getIsPayrollInclude()
	{
		return isPayrollInclude;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public void setApplyLeaveDate(Date applyLeaveDate)
	{
		this.applyLeaveDate = applyLeaveDate;
	}

	public void setApplyMonthName(String applyMonthName)
	{
		this.applyMonthName = applyMonthName;
	}

	public void setIsPayrollInclude(String isPayrollInclude)
	{
		this.isPayrollInclude = isPayrollInclude;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Long getOrglocationId()
	{
		return orglocationId;
	}

	public String getLeaveStatus()
	{
		return leaveStatus;
	}

	public void setOrglocationId(Long orglocationId)
	{
		this.orglocationId = orglocationId;
	}

	public void setLeaveStatus(String leaveStatus)
	{
		this.leaveStatus = leaveStatus;
	}

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public Long getLeaveTypeId()
	{
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public Long getOrgId()
	{
		return orgId;
	}

	public void setOrgId(Long orgId)
	{
		this.orgId = orgId;
	}
}
