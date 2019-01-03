package com.bis.sourcelead.payroll.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "US_PAY_SCHEDULE_DATE")
public class USPayScheduleDate {
	
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "SCHEDULE_ID", nullable = false)
	private UsPayrollSchedule scheduleId;
	
	@Column(name = "PAY_PERIOD_SDATE")
	private Date payPeriodStartDate;
	
	@Column(name = "PAY_PERIOD_EDATE")
	private Date payPeriodEndDate;
	
	@Column(name = "PAY_CHECK_DATE")
	private Date checkDate;
	
	@Column(name = "PAY_RUN_DATE")
	private Date payRunDate;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public Long getId()
	{
		return id;
	}

	public UsPayrollSchedule getScheduleId()
	{
		return scheduleId;
	}

	public Date getPayPeriodStartDate()
	{
		return payPeriodStartDate;
	}

	public Date getPayPeriodEndDate()
	{
		return payPeriodEndDate;
	}

	public Date getCheckDate()
	{
		return checkDate;
	}

	public Date getPayRunDate()
	{
		return payRunDate;
	}

	public Integer getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setScheduleId(UsPayrollSchedule scheduleId)
	{
		this.scheduleId = scheduleId;
	}

	public void setPayPeriodStartDate(Date payPeriodStartDate)
	{
		this.payPeriodStartDate = payPeriodStartDate;
	}

	public void setPayPeriodEndDate(Date payPeriodEndDate)
	{
		this.payPeriodEndDate = payPeriodEndDate;
	}

	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}

	public void setPayRunDate(Date payRunDate)
	{
		this.payRunDate = payRunDate;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}
	
	
	
	
	
}
