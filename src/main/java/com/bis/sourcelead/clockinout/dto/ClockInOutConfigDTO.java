package com.bis.sourcelead.clockinout.dto;

public class ClockInOutConfigDTO {
	
	private String isTrackEmpLocation;
	private String isTrackTaskAssigned;
	private String isJobChecked;
	private String isProjectTasksChecked;
	private String isGeneralTaskChecked;
	private String isGoalChecked;
	private String isOtherChecked;
	private String isClockInComment;
	private String isBreak;
	private String isAutomaticCheckOut;
	private String isTimesheetFill;
	
	private Long clockInOutId;
	
	public String getIsTrackEmpLocation() {
		return isTrackEmpLocation;
	}
	public void setIsTrackEmpLocation(String isTrackEmpLocation) {
		this.isTrackEmpLocation = isTrackEmpLocation;
	}
	public String getIsTrackTaskAssigned() {
		return isTrackTaskAssigned;
	}
	public void setIsTrackTaskAssigned(String isTrackTaskAssigned) {
		this.isTrackTaskAssigned = isTrackTaskAssigned;
	}
	public String getIsJobChecked() {
		return isJobChecked;
	}
	public void setIsJobChecked(String isJobChecked) {
		this.isJobChecked = isJobChecked;
	}
	public String getIsProjectTasksChecked() {
		return isProjectTasksChecked;
	}
	public void setIsProjectTasksChecked(String isProjectTasksChecked) {
		this.isProjectTasksChecked = isProjectTasksChecked;
	}
	public String getIsGeneralTaskChecked() {
		return isGeneralTaskChecked;
	}
	public void setIsGeneralTaskChecked(String isGeneralTaskChecked) {
		this.isGeneralTaskChecked = isGeneralTaskChecked;
	}
	public String getIsGoalChecked() {
		return isGoalChecked;
	}
	public void setIsGoalChecked(String isGoalChecked) {
		this.isGoalChecked = isGoalChecked;
	}
	public String getIsOtherChecked() {
		return isOtherChecked;
	}
	public void setIsOtherChecked(String isOtherChecked) {
		this.isOtherChecked = isOtherChecked;
	}
	public String getIsClockInComment() {
		return isClockInComment;
	}
	public void setIsClockInComment(String isClockInComment) {
		this.isClockInComment = isClockInComment;
	}
	public String getIsBreak() {
		return isBreak;
	}
	public void setIsBreak(String isBreak) {
		this.isBreak = isBreak;
	}
	public String getIsAutomaticCheckOut() {
		return isAutomaticCheckOut;
	}
	public void setIsAutomaticCheckOut(String isAutomaticCheckOut) {
		this.isAutomaticCheckOut = isAutomaticCheckOut;
	}
	public Long getClockInOutId() {
		return clockInOutId;
	}
	public void setClockInOutId(Long clockInOutId) {
		this.clockInOutId = clockInOutId;
	}
	public String getIsTimesheetFill()
	{
		return isTimesheetFill;
	}
	public void setIsTimesheetFill(String isTimesheetFill)
	{
		this.isTimesheetFill = isTimesheetFill;
	}
	

}
