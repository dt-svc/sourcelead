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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bis.sourcelead.entity.OrgLocations;
import com.bis.sourcelead.entity.OrgRelations;
import com.bis.sourcelead.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author rakshith
 *
 */
@Entity
@Table(name = "CLOCK_INOUT")
public class ClockInOut {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )
	private OrgLocations orgLocations;
	
	@Column(name = "CLOCK_IN_DATE")
	private Date clockInDate;
	
	@Column(name = "CLOCK_OUT_DATE")
	private Date clockOutDate;
	
	@Column(name = "CLOCK_IN_DATETIME")
	private Date clockInDateTime;
	
	@Column(name = "CLOCK_OUT_DATETIME")
	private Date clockOutDateTime;
	
	@Column(name = "CLOCK_IN_TIME")
	private Time clockInTime;
	
	@Column(name = "CLOCK_OUT_TIME")
	private Time clockOutTime;
	
	@Column(name = "TOTAL_CLOCK_TIME")
	private Time totalClockTime;
	
	@Column(name = "JOBS_TASK_ID")
	private Long jobsTaskId;
	
	@Column(name = "PROJECT_ID")
	private Long projectId;
	
	@Column(name = "PROJECTS_TASK_ID")
	private Long projectsTaskId;
	
	@Column(name = "GENERAL_TASK_ID")
	private Long generalTaskId;
	
	@Column(name = "COMMENTS",columnDefinition = "TEXT default NULL")
	private String comments;
	
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
	
	@Column(name = "TOTAL_TIME")
	private String totalTime;
	
	@Column(name = "GEO_LOCATION_DATA", columnDefinition = "TEXT default NULL")
	private String geoLocationData;
	
	@JsonProperty
	@Transient
	private String clockInDateStr;
	
	@JsonProperty
	@Transient
	private String clockOutDateStr;

	public Long getId()
	{
		return id;
	}

	public OrgLocations getOrgLocations() {
		return orgLocations;
	}

	public Date getClockInDate() {
		return clockInDate;
	}
	
	public Date getClockOutDate() {
		return clockOutDate;
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

	public void setOrgLocations(OrgLocations orgLocations) {
		this.orgLocations = orgLocations;
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
	
	public void setClockInDate(Date clockInDate) {
		this.clockInDate = clockInDate;
	}

	public void setClockOutDate(Date clockOutDate) {
		this.clockOutDate = clockOutDate;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Time getTotalClockTime() {
		return totalClockTime;
	}

	public void setTotalClockTime(Time totalClockTime) {
		this.totalClockTime = totalClockTime;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public Date getClockInDateTime() {
		return clockInDateTime;
	}

	public void setClockInDateTime(Date clockInDateTime) {
		this.clockInDateTime = clockInDateTime;
	}

	public Date getClockOutDateTime() {
		return clockOutDateTime;
	}

	public void setClockOutDateTime(Date clockOutDateTime) {
		this.clockOutDateTime = clockOutDateTime;
	}

	public String getClockInDateStr() {
		return clockInDateStr;
	}

	public void setClockInDateStr(String clockInDateStr) {
		this.clockInDateStr = clockInDateStr;
	}

	public String getClockOutDateStr() {
		return clockOutDateStr;
	}

	public void setClockOutDateStr(String clockOutDateStr) {
		this.clockOutDateStr = clockOutDateStr;
	}

	public String getGeoLocationData() {
		return geoLocationData;
	}

	public void setGeoLocationData(String geoLocationData) {
		this.geoLocationData = geoLocationData;
	}

}
