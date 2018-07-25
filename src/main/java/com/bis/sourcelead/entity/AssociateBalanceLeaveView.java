package com.bis.sourcelead.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ASSOCIATE_BAL_LEAVE_VIEW")
public class AssociateBalanceLeaveView {
	
	@Id
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column (name = "LEAVE_TYPE_ID")
	private Long leaveTypeId;
	
	
	@Column (name = "VISIBLE_IN_BALANCE_LEAVES")
	private String visibleInBalanceLeaves;
	
	@Column (name = "INCLUDE_WEEKOFF")
	private String includeWeekoff;
	
	@Column (name = "INCLUDE_HOLIDAYS")
	private String includeHolidays;
	
	@Column (name = "PERIOD")
	private String period;
	
	@Column (name = "ACCURAL_POLICY")
	private String accuralPolicy;
	
	
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "TOTAL_LEAVES")
	private String totalLeaves;
	
	@Column(name = "ASSO_BAL_LEAVE_UUID")
	private String assoBalLeaveUUID;
	
	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "LEAVE_TYPE_NAME")
	private String leaveTypeName;
	
	@Column(name = "CREATED_BY_FIRST_NAME")
	private String createdByFirstName;
	
	@Column(name = "CREATED_BY_LAST_NAME")
	private String createdByLastName;
	
	@Column(name = "ORG_LOCATION_ID")
	private Long orgLocationId;
	
	@Column (name = "NO_OF_DAYS_PER_PERIOD")
	private Float noOfDaysPerPeriod;
	
	@Column (name = "EFFECTIVE_GENDER")
	private String effectiveGender;
	
	@Transient
	private String fromHalfDay;
	
	@Transient
	private String toHalfDay;
	
	@Transient
	private String fromHalfFromTime;
	
	@Transient
	private String fromHalfToTime;
	
	@Transient
	private String toHalfFromTime;
	
	@Transient
	private String toHalfToTime;

	public Float getNoOfDaysPerPeriod()
	{
		return noOfDaysPerPeriod;
	}

	public void setNoOfDaysPerPeriod(Float noOfDaysPerPeriod)
	{
		this.noOfDaysPerPeriod = noOfDaysPerPeriod;
	}

	public Long getId()
	{
		return id;
	}

	public Long getUserId()
	{
		return userId;
	}



	public Integer getStatus()
	{
		return status;
	}



	public String getLeaveTypeName()
	{
		return leaveTypeName;
	}

	public String getCreatedByFirstName()
	{
		return createdByFirstName;
	}

	public String getCreatedByLastName()
	{
		return createdByLastName;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	

	public String getTotalLeaves()
	{
		return totalLeaves;
	}

	public String getAssoBalLeaveUUID()
	{
		return assoBalLeaveUUID;
	}

	public void setTotalLeaves(String totalLeaves)
	{
		this.totalLeaves = totalLeaves;
	}

	public void setAssoBalLeaveUUID(String assoBalLeaveUUID)
	{
		this.assoBalLeaveUUID = assoBalLeaveUUID;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Date getCreatedDate()
	{
		return createdDate;
	}

	public Date getUpdatedDate()
	{
		return updatedDate;
	}

	public void setCreatedDate(Date createdDate)
	{
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}

	public void setLeaveTypeName(String leaveTypeName)
	{
		this.leaveTypeName = leaveTypeName;
	}

	public void setCreatedByFirstName(String createdByFirstName)
	{
		this.createdByFirstName = createdByFirstName;
	}

	public void setCreatedByLastName(String createdByLastName)
	{
		this.createdByLastName = createdByLastName;
	}

	public Long getLeaveTypeId()
	{
		return leaveTypeId;
	}

	public void setLeaveTypeId(Long leaveTypeId)
	{
		this.leaveTypeId = leaveTypeId;
	}

	public Long getOrgLocationId()
	{
		return orgLocationId;
	}

	public void setOrgLocationId(Long orgLocationId)
	{
		this.orgLocationId = orgLocationId;
	}

	public String getVisibleInBalanceLeaves()
	{
		return visibleInBalanceLeaves;
	}

	public String getIncludeWeekoff()
	{
		return includeWeekoff;
	}

	public String getIncludeHolidays()
	{
		return includeHolidays;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setVisibleInBalanceLeaves(String visibleInBalanceLeaves)
	{
		this.visibleInBalanceLeaves = visibleInBalanceLeaves;
	}

	public void setIncludeWeekoff(String includeWeekoff)
	{
		this.includeWeekoff = includeWeekoff;
	}

	public void setIncludeHolidays(String includeHolidays)
	{
		this.includeHolidays = includeHolidays;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getAccuralPolicy()
	{
		return accuralPolicy;
	}

	public void setAccuralPolicy(String accuralPolicy)
	{
		this.accuralPolicy = accuralPolicy;
	}

	public String getEffectiveGender()
	{
		return effectiveGender;
	}

	public void setEffectiveGender(String effectiveGender)
	{
		this.effectiveGender = effectiveGender;
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

	public String getFromHalfFromTime()
	{
		return fromHalfFromTime;
	}

	public void setFromHalfFromTime(String fromHalfFromTime)
	{
		this.fromHalfFromTime = fromHalfFromTime;
	}

	public String getFromHalfToTime()
	{
		return fromHalfToTime;
	}

	public void setFromHalfToTime(String fromHalfToTime)
	{
		this.fromHalfToTime = fromHalfToTime;
	}

	public String getToHalfFromTime()
	{
		return toHalfFromTime;
	}

	public void setToHalfFromTime(String toHalfFromTime)
	{
		this.toHalfFromTime = toHalfFromTime;
	}

	public String getToHalfToTime()
	{
		return toHalfToTime;
	}

	public void setToHalfToTime(String toHalfToTime)
	{
		this.toHalfToTime = toHalfToTime;
	}
}
