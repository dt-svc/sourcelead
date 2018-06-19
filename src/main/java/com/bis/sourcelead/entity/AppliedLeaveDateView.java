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
    @Column(name = "USER_ID", nullable = false)
    private Long userId;
	
	@Column(name = "ORGLOCATION_ID", nullable = false)
	private Long orglocationId;
	
	@Column (name = "LEAVE_STATUS")
	private String leaveStatus;

	@Column (name = "APPLY_LEAVE_DATE")
	private Date applyLeaveDate;
	
	@Column (name = "APPLY_MONTH_NAME")
	private String applyMonthName;
	
	@Column (name = "IS_PAYROLL_INCLUDE")
	private String isPayrollInclude;
	
	@Column (name = "STATUS")
	private Integer status;

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
	
	
}
