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

@Entity
@Table(name = "CLOCK_BREAK_INOUT")
public class Breaks {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "CLOCK_INOUT_ID")
	private Long clockInOutId;
	
	@Column(name = "BREAK_START_DATE")
	private Date breakStartDate;
	
	@Column(name = "BREAK_END_DATE")
	private Date breakEndDate;
	
	@Column(name = "BREAK_START_DATETIME")
	private Date breakStartDateTime;
	
	@Column(name = "BREAK_END_DATETIME")
	private Date breakEndDateTime;
	
	@Column(name = "BREAK_START_TIME")
	private Time breakStartTime;
	
	@Column(name = "BREAK_END_TIME")
	private Time breakEndTime;
	
	@Column(name = "TOTAL_BREAK_TIME")
	private Time totalBreakTime;
	
	@Column(name = "COMMENTS",columnDefinition = "TEXT default NULL")
	private String comments;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "UPDATED_BY")
	private Long updatedBy;
	
	@Column(name = "TOTAL_TIME")
	private String totalTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClockInOutId() {
		return clockInOutId;
	}

	public void setClockInOutId(Long clockInOutId) {
		this.clockInOutId = clockInOutId;
	}


	public Date getBreakStartDate() {
		return breakStartDate;
	}

	public void setBreakStartDate(Date breakStartDate) {
		this.breakStartDate = breakStartDate;
	}

	public Date getBreakEndDate() {
		return breakEndDate;
	}

	public void setBreakEndDate(Date breakEndDate) {
		this.breakEndDate = breakEndDate;
	}

	public Time getBreakStartTime() {
		return breakStartTime;
	}

	public void setBreakStartTime(Time breakStartTime) {
		this.breakStartTime = breakStartTime;
	}

	public Time getBreakEndTime() {
		return breakEndTime;
	}

	public void setBreakEndTime(Time breakEndTime) {
		this.breakEndTime = breakEndTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Time getTotalBreakTime() {
		return totalBreakTime;
	}

	public void setTotalBreakTime(Time totalBreakTime) {
		this.totalBreakTime = totalBreakTime;
	}

	public Date getBreakStartDateTime() {
		return breakStartDateTime;
	}

	public void setBreakStartDateTime(Date breakStartDateTime) {
		this.breakStartDateTime = breakStartDateTime;
	}

	public Date getBreakEndDateTime() {
		return breakEndDateTime;
	}

	public void setBreakEndDateTime(Date breakEndDateTime) {
		this.breakEndDateTime = breakEndDateTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	

}
