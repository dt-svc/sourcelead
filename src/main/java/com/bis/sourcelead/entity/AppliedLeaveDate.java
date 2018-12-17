package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "APPLIED_LEAVE_DATE")
public class AppliedLeaveDate {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "LEAVE_ID", nullable = false)
	private Leave leave;
	
	@Column(name = "APPLY_LEAVE_DATE")
	private Date applyLeaveDate;
	
	@Column(name = "APPLIED_HOURS")
	private String appliedHours;
	
	@Column(name = "APPLY_MONTH_NAME")
	private String applyMonthName;
	
	@Column(name = "IS_PAYROLL_INCLUDE")
	private String isPayrollInclude;
	
	@Column(name = "YEAR")
	private Integer year;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	public Long getId()
	{
		return id;
	}

	public Leave getLeave()
	{
		return leave;
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

	public Long getCreatedBy()
	{
		return createdBy;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Long getUpdatedBy()
	{
		return updatedBy;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setLeave(Leave leave)
	{
		this.leave = leave;
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

	public void setCreatedBy(Long createdBy)
	{
		this.createdBy = createdBy;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setUpdatedBy(Long updatedBy)
	{
		this.updatedBy = updatedBy;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public Integer getYear()
	{
		return year;
	}

	public void setYear(Integer year)
	{
		this.year = year;
	}

	public String getAppliedHours()
	{
		return appliedHours;
	}

	public void setAppliedHours(String appliedHours)
	{
		this.appliedHours = appliedHours;
	}
}
