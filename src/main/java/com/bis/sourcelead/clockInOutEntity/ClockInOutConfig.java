package com.bis.sourcelead.clockInOutEntity;

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


import com.bis.sourcelead.entity.OrgLocations;

@Entity
@Table(name = "CLOCK_INOUT_CONF")
public class ClockInOutConfig {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
	private Long id;
	
	@Column(name = "TRACK_EMP_LOCATION")
	private Boolean isTrackEmpLocation;
	
	@Column(name = "TRACK_TASK_ASSIGNED")
	private Boolean isTrackTaskAssigned;
	
	@Column(name = "IS_JOBS_CHECKED")
	private Boolean isJobChecked;
	
	@Column(name = "IS_PROJECT_TASKS_CHECKED")
	private Boolean isProjectTasksChecked;
	
	@Column(name = "IS_GENERAL_TASKS_CHECKED")
	private Boolean isGeneralTaskChecked;
	
	@Column(name = "IS_GOALS_CHECKED")
	private Boolean isGoalChecked;

	@Column(name = "IS_OTHER_CHECKED")
	private Boolean isOtherChecked;
	
	@Column(name = "CLOCK_IN_COMMENT")
	private Boolean isClockInComment;
	
	@Column(name = "BREAKS")
	private Boolean isBreak;
	
	@Column(name = "AUTOMATIC_CLOCKOUT")
	private Boolean isAutomaticCheckOut;
	
	@Column(name = "IS_TIMESHEET_FILL")
	private Boolean isTimesheetFill;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ORG_LOCATION_ID", nullable=false )   
	private OrgLocations orgLocationId;
	
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

	public Boolean getIsTrackEmpLocation() {
		return isTrackEmpLocation;
	}

	public void setIsTrackEmpLocation(Boolean isTrackEmpLocation) {
		this.isTrackEmpLocation = isTrackEmpLocation;
	}

	public Boolean getIsTrackTaskAssigned() {
		return isTrackTaskAssigned;
	}

	public void setIsTrackTaskAssigned(Boolean isTrackTaskAssigned) {
		this.isTrackTaskAssigned = isTrackTaskAssigned;
	}

	public Boolean getIsJobChecked() {
		return isJobChecked;
	}

	public void setIsJobChecked(Boolean isJobChecked) {
		this.isJobChecked = isJobChecked;
	}

	public Boolean getIsProjectTasksChecked() {
		return isProjectTasksChecked;
	}

	public void setIsProjectTasksChecked(Boolean isProjectTasksChecked) {
		this.isProjectTasksChecked = isProjectTasksChecked;
	}

	public Boolean getIsGeneralTaskChecked() {
		return isGeneralTaskChecked;
	}

	public void setIsGeneralTaskChecked(Boolean isGeneralTaskChecked) {
		this.isGeneralTaskChecked = isGeneralTaskChecked;
	}

	public Boolean getIsGoalChecked() {
		return isGoalChecked;
	}

	public void setIsGoalChecked(Boolean isGoalChecked) {
		this.isGoalChecked = isGoalChecked;
	}

	public Boolean getIsOtherChecked() {
		return isOtherChecked;
	}

	public void setIsOtherChecked(Boolean isOtherChecked) {
		this.isOtherChecked = isOtherChecked;
	}

	public Boolean getIsClockInComment() {
		return isClockInComment;
	}

	public void setIsClockInComment(Boolean isClockInComment) {
		this.isClockInComment = isClockInComment;
	}

	public Boolean getIsBreak() {
		return isBreak;
	}

	public void setIsBreak(Boolean isBreak) {
		this.isBreak = isBreak;
	}

	public Boolean getIsAutomaticCheckOut() {
		return isAutomaticCheckOut;
	}

	public void setIsAutomaticCheckOut(Boolean isAutomaticCheckOut) {
		this.isAutomaticCheckOut = isAutomaticCheckOut;
	}

	public OrgLocations getOrgLocationId() {
		return orgLocationId;
	}

	public void setOrgLocationId(OrgLocations orgLocationId) {
		this.orgLocationId = orgLocationId;
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

	public Boolean getIsTimesheetFill()
	{
		return isTimesheetFill;
	}

	public void setIsTimesheetFill(Boolean isTimesheetFill)
	{
		this.isTimesheetFill = isTimesheetFill;
	}
	
	
	

}
