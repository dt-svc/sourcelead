package com.bis.sourcelead.clockInOutDTO;

public class ClockInOutDTO {
	
	private Long clockInOutId;
	private Long jobs;
	private Long projects;
	private Long projectTasks;
	private Long generalTasks;
	private String comments;
	
	public Long getClockInOutId() {
		return clockInOutId;
	}
	public void setClockInOutId(Long clockInOutId) {
		this.clockInOutId = clockInOutId;
	}
	public Long getJobs() {
		return jobs;
	}
	public void setJobs(Long jobs) {
		this.jobs = jobs;
	}
	public Long getProjects() {
		return projects;
	}
	public void setProjects(Long projects) {
		this.projects = projects;
	}
	public Long getProjectTasks() {
		return projectTasks;
	}
	public void setProjectTasks(Long projectTasks) {
		this.projectTasks = projectTasks;
	}
	public Long getGeneralTasks() {
		return generalTasks;
	}
	public void setGeneralTasks(Long generalTasks) {
		this.generalTasks = generalTasks;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

}
