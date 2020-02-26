package com.bis.sourcelead.clockinout.dto;

import java.io.Serializable;

public class ClockInOutLoginTimerDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String isClockIn;
	
	private String clockInOutId;
	
	private String projectId;
	
	private String projectTaskId;
	
	private String generalTaskId;
	
	private String jobId;
	
	private String isInBreak;
	
	private String time;
	
	private String breakId;
	
	

	public String getIsClockIn() {
		return isClockIn;
	}

	public void setIsClockIn(String isClockIn) {
		this.isClockIn = isClockIn;
	}

	public String getClockInOutId() {
		return clockInOutId;
	}

	public void setClockInOutId(String clockInOutId) {
		this.clockInOutId = clockInOutId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(String projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public String getGeneralTaskId() {
		return generalTaskId;
	}

	public void setGeneralTaskId(String generalTaskId) {
		this.generalTaskId = generalTaskId;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getIsInBreak() {
		return isInBreak;
	}

	public void setIsInBreak(String isInBreak) {
		this.isInBreak = isInBreak;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBreakId() {
		return breakId;
	}

	public void setBreakId(String breakId) {
		this.breakId = breakId;
	}
	
	
	

	
	

}
