package com.bis.sourcelead.clockInOutEntity;

import java.sql.Time;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;

@Entity
@Table(name = "CLOCK_INOUT")
public class ClockInOut {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )
	private OrgRelations orgRelationID;
	
	@Column(name = "CLOCK_DATE")
	private Date clockDate;
	
	@Column(name = "CLOCK_IN_TIME")
	private Time clockInTime;
	
	@Column(name = "CLOCK_OUT_TIME")
	private Time clockOutTime;
	
	@Column(name = "JOBS_TASK_ID")
	private Long jobsTaskId;
	
	@Column(name = "PROJECTS_TASK_ID")
	private Long projectsTaskId;
	
	@Column(name = "GENERAL_TASK_ID")
	private Long generalTaskId;
	
	@Column(name = "COMMENTS",columnDefinition = "TEXT default NULL")
	private String comments;
	
	@Column(name = "BREAK_START_TIME")
	private Time breakStartTime;
	
	@Column(name = "BREAK_END_TIME")
	private Time breakEndTime;
	
	@ManyToOne
    @JoinColumn(name="USER_ID", nullable=false )
	private User userId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	public Long getId()
	{
		return id;
	}

	public OrgRelations getOrgRelationID()
	{
		return orgRelationID;
	}

	public Date getClockDate()
	{
		return clockDate;
	}

	public Time getClockInTime()
	{
		return clockInTime;
	}

	public Time getClockOutTime()
	{
		return clockOutTime;
	}

	public Long getJobsTaskId()
	{
		return jobsTaskId;
	}

	public Long getProjectsTaskId()
	{
		return projectsTaskId;
	}

	public Long getGeneralTaskId()
	{
		return generalTaskId;
	}

	public String getComments()
	{
		return comments;
	}

	public Time getBreakStartTime()
	{
		return breakStartTime;
	}

	public Time getBreakEndTime()
	{
		return breakEndTime;
	}

	public User getUserId()
	{
		return userId;
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

	public void setOrgRelationID(OrgRelations orgRelationID)
	{
		this.orgRelationID = orgRelationID;
	}

	public void setClockDate(Date clockDate)
	{
		this.clockDate = clockDate;
	}

	public void setClockInTime(Time clockInTime)
	{
		this.clockInTime = clockInTime;
	}

	public void setClockOutTime(Time clockOutTime)
	{
		this.clockOutTime = clockOutTime;
	}

	public void setJobsTaskId(Long jobsTaskId)
	{
		this.jobsTaskId = jobsTaskId;
	}

	public void setProjectsTaskId(Long projectsTaskId)
	{
		this.projectsTaskId = projectsTaskId;
	}

	public void setGeneralTaskId(Long generalTaskId)
	{
		this.generalTaskId = generalTaskId;
	}

	public void setComments(String comments)
	{
		this.comments = comments;
	}

	public void setBreakStartTime(Time breakStartTime)
	{
		this.breakStartTime = breakStartTime;
	}

	public void setBreakEndTime(Time breakEndTime)
	{
		this.breakEndTime = breakEndTime;
	}

	public void setUserId(User userId)
	{
		this.userId = userId;
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
