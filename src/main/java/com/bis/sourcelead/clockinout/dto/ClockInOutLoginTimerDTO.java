package com.bis.sourcelead.clockinout.dto;

import java.io.Serializable;

public class ClockInOutLoginTimerDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String isClockIn;
	
	private Long clockInOutId;
	
	private Long projectId;
	
	private Long projectTaskId;
	
	private Long generalTaskId;
	
	private Long jobId;
	
	private String isInBreak;
	
	private String time;
	
	private Long breakId;
	
	
	

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	
	public Long getProjectTaskId() {
		return projectTaskId;
	}

	public void setProjectTaskId(Long projectTaskId) {
		this.projectTaskId = projectTaskId;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
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

	public Long getGeneralTaskId() {
		return generalTaskId;
	}

	public void setGeneralTaskId(Long generalTaskId) {
		this.generalTaskId = generalTaskId;
	}

	public Long getBreakId() {
		return breakId;
	}

	public void setBreakId(Long breakId) {
		this.breakId = breakId;
	}

	public String getIsClockIn() {
		return isClockIn;
	}

	public void setIsClockIn(String isClockIn) {
		this.isClockIn = isClockIn;
	}

	public Long getClockInOutId() {
		return clockInOutId;
	}

	public void setClockInOutId(Long clockInOutId) {
		this.clockInOutId = clockInOutId;
	}
	

}
