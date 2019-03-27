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
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CLOCK_INOUT_ID", nullable=false )   
	private ClockInOut clockInOutId;
	
	@Column(name = "BREAK_DATE")
	private Date breakDate;
	
	@Column(name = "BREAK_START_TIME")
	private Time breakStartTime;
	
	@Column(name = "BREAK_END_TIME")
	private Time breakEndTime;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClockInOut getClockInOutId() {
		return clockInOutId;
	}

	public void setClockInOutId(ClockInOut clockInOutId) {
		this.clockInOutId = clockInOutId;
	}

	public Date getBreakDate() {
		return breakDate;
	}

	public void setBreakDate(Date breakDate) {
		this.breakDate = breakDate;
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
	
	

}
