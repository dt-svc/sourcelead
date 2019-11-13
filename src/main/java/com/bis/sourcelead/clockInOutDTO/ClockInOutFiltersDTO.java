package com.bis.sourcelead.clockInOutDTO;

public class ClockInOutFiltersDTO {
	
	private String userRole;
	private String loginUserRole;
	private String orgDepartments;
	
	private String jobsList;
	private String projectList;
	private String tasksList;
	private String generalTasksList;
	private String associateList;
	private String teamsList;
	private String dayWisetype;
	private String type;
	private String currentDate;
	
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getLoginUserRole() {
		return loginUserRole;
	}
	public void setLoginUserRole(String loginUserRole) {
		this.loginUserRole = loginUserRole;
	}
	public String getOrgDepartments() {
		return orgDepartments;
	}
	public void setOrgDepartments(String orgDepartments) {
		this.orgDepartments = orgDepartments;
	}
	
	public String getJobsList() {
		return jobsList;
	}
	public void setJobsList(String jobsList) {
		this.jobsList = jobsList;
	}
	public String getProjectList() {
		return projectList;
	}
	public void setProjectList(String projectList) {
		this.projectList = projectList;
	}
	public String getTasksList() {
		return tasksList;
	}
	public void setTasksList(String tasksList) {
		this.tasksList = tasksList;
	}
	public String getGeneralTasksList() {
		return generalTasksList;
	}
	public void setGeneralTasksList(String generalTasksList) {
		this.generalTasksList = generalTasksList;
	}
	public String getAssociateList() {
		return associateList;
	}
	public void setAssociateList(String associateList) {
		this.associateList = associateList;
	}
	public String getTeamsList() {
		return teamsList;
	}
	public void setTeamsList(String teamsList) {
		this.teamsList = teamsList;
	}
	
	public String getDayWisetype() {
		return dayWisetype;
	}
	public void setDayWisetype(String dayWisetype) {
		this.dayWisetype = dayWisetype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}	
}
