package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LEAVE_CF_HISTORY")
public class LeaveCFHistory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
	
	@Column(name = "LEAVE_TYPE_ID", nullable = false)
	private Long leaveTypeID;
	
	@Column(name = "USER_ROLE_ID", nullable = false)
	private Long userRoleId;
	
	@Column (name = "CF_TYPE")
	private String cfType;
	
	@Column (name = "MONTH_DATE")
	private Date monthDate;
	
	@Column (name = "MONTH_NAME")
	private String monthName;
	
	@Column (name = "YEAR")
	private int year;
	
	@Column (name = "STATUS")
	private int status;
	
	@Column (name = "CREATED_DATE")
	private Date createdDate;
	
	@Column (name = "CREATED_BY")
	private Long createdBy;
	
	@Column (name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column (name = "UPDATED_BY")
	private Long updatedBy;

	public Long getId()
	{
		return id;
	}

	public Long getLeaveTypeID()
	{
		return leaveTypeID;
	}

	public Long getUserRoleId()
	{
		return userRoleId;
	}

	public String getCfType()
	{
		return cfType;
	}

	public Date getMonthDate()
	{
		return monthDate;
	}

	public String getMonthName()
	{
		return monthName;
	}

	public int getYear()
	{
		return year;
	}

	public int getStatus()
	{
		return status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setLeaveTypeID(Long leaveTypeID)
	{
		this.leaveTypeID = leaveTypeID;
	}

	public void setUserRoleId(Long userRoleId)
	{
		this.userRoleId = userRoleId;
	}

	public void setCfType(String cfType)
	{
		this.cfType = cfType;
	}

	public void setMonthDate(Date monthDate)
	{
		this.monthDate = monthDate;
	}

	public void setMonthName(String monthName)
	{
		this.monthName = monthName;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	
	
	
	
}
